package com.example.hit_networking_base.service.impl;

import com.example.hit_networking_base.constant.Gender;
import com.example.hit_networking_base.constant.Role;
import com.example.hit_networking_base.domain.entity.User;
import com.example.hit_networking_base.exception.UserException;
import com.example.hit_networking_base.repository.UserRepository;
import com.example.hit_networking_base.service.ExcelUploadService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.hit_networking_base.util.*;

@Service
@RequiredArgsConstructor
public class ExcelUploadServiceImpl implements ExcelUploadService {
    private  final PasswordEncoder passwordencoder;
    private final UserRepository repository;


    @Override
    public boolean isValidExcelFile(MultipartFile file) {
        if (file == null) return false;

        String contentType = file.getContentType();
        boolean isExcelMimeType = contentType != null && (
                contentType.equals("application/vnd.ms-excel") ||
                        contentType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
        );

        String fileName = file.getOriginalFilename();
        boolean isExcelExtension = fileName != null && (
                fileName.endsWith(".xls") || fileName.endsWith(".xlsx")
        );

        return isExcelMimeType && isExcelExtension;
    }


    @Override
    public List<User> getCustomerDataFromExcel(InputStream inputStream) {

       List<User> users = new ArrayList<>();

       try {
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheet = workbook.getSheetAt(0);

            int numberOfRows = sheet.getPhysicalNumberOfRows();
            long counter = repository.findMaxUserId();

            for (int rowIndex = 1; rowIndex < numberOfRows; rowIndex++) { // Bắt đầu từ 1 để bỏ tiêu đề
                Row row = sheet.getRow(rowIndex);
                if (row == null) continue; // Bỏ qua dòng trống

                // Kiểm tra số ô: nếu ít hơn 4 thì có thể thiếu dữ liệu
                if (row.getLastCellNum() < 4) {
                    System.out.println("Dòng " + (rowIndex + 1) + " có ít hơn 4 ô. Bỏ qua.");
                    continue;
                }

                User user = new User();

                Cell nameCell = row.getCell(0);
                Cell genderCell = row.getCell(1);
                Cell dobCell = row.getCell(2);
                Cell emailCell = row.getCell(3);

                // Họ tên
                //com.example.hit_networking_base.Util.ExcelHelper
                String fullName = excelHelper.getCellValueAsString(nameCell);
                if (fullName == null || fullName.isBlank()) {
                    System.out.println("Họ tên bị trống tại dòng " + (rowIndex + 1));
                    continue;
                }
                user.setFullName(fullName);

                String genderStr = excelHelper.getCellValueAsString(genderCell).trim().toUpperCase();
                if (genderStr.equals("NAM")) {
                    user.setGender(Gender.MALE);
                } else if (genderStr.equals("NỮ")) {
                    user.setGender(Gender.FEMALE);
                } else {
                    throw new UserException("Giới tính không hợp lệ tại dòng " + (rowIndex + 1) + ": " + genderStr);
                }

                if (dobCell == null || dobCell.getCellType() == CellType.BLANK) {
                    throw new UserException("Ngày sinh bị trống tại dòng " + (rowIndex + 1));
                }

                LocalDate dob = null;
                try {
                    if (dobCell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(dobCell)) {
                        Date date = dobCell.getDateCellValue();
                        dob = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    } else {
                        String dateStr = excelHelper.getCellValueAsString(dobCell).trim();
                        List<DateTimeFormatter> formatters = List.of(
                                DateTimeFormatter.ofPattern("d/M/yyyy"),
                                DateTimeFormatter.ofPattern("dd/MM/yyyy"),
                                DateTimeFormatter.ofPattern("yyyy-MM-dd")
                        );
                        for (DateTimeFormatter formatter : formatters) {
                            try {
                                dob = LocalDate.parse(dateStr, formatter);
                                break;
                            } catch (Exception ignored) {}
                        }
                        if (dob == null) {
                            throw new UserException("Định dạng ngày sinh không hợp lệ tại dòng " + (rowIndex + 1) + ": " + dateStr);
                        }
                    }
                    user.setDob(dob);
                } catch (Exception ex) {
                    throw new UserException("Lỗi xử lý ngày sinh tại dòng " + (rowIndex + 1) + ": " + ex.getMessage());
                }

                String email = excelHelper.getCellValueAsString(emailCell).trim();
                if (email.isEmpty()) {
                    throw new UserException("Email bị thiếu tại dòng " + (rowIndex + 1));
                }
                user.setEmail(email);
                user.setRole(Role.TV);
                user.setCreatedAt(LocalDate.now());

                String[] parts = user.getFullName().trim().split("\\s+");
                String lastWord = parts[parts.length - 1];
                String username = vietNameseUtils.removeAccents(lastWord) + "hit" + counter;
                user.setUsername(username.toLowerCase());

                String password = GenPassword.generatePassword();
                user.setPasswordHash(passwordencoder.encode(password));

                if (repository.existsByUsernameAndDeletedAtIsNull(user.getUsername())) {
                    throw new UserException("Username đã tồn tại: " + user.getUsername());
                }
                System.out.println("Thêm user: " + user.getFullName() + ", dob = " + user.getDob() + ", email = " + user.getEmail());

                users.add(user);
                counter++;
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return users;
    }
}
