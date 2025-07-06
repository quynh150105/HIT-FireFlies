package com.example.hit_networking_base.service.impl;

import com.example.hit_networking_base.Util.ExcelHelper;
import com.example.hit_networking_base.Util.PasswordGenerator;
import com.example.hit_networking_base.Util.VietNameseUtils;
import com.example.hit_networking_base.domain.entity.User;
import com.example.hit_networking_base.exception.UserException;
import com.example.hit_networking_base.repository.UserRepository;
import com.example.hit_networking_base.service.ExcelUploadService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFShape;
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
import java.util.*;

@Service
@RequiredArgsConstructor
public class ExcelUploadServiceImpl implements ExcelUploadService {
    private  final PasswordEncoder passwordencoder;

    private final UserRepository repository;

    @Override
    public boolean isValidExcelFile(MultipartFile file) {
        return Objects.equals(file.getContentType(),"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    }

    @Override
    public List<User> getCustomerDataFromExcel(InputStream inputStream) {
       List<User> users = new ArrayList<>();

        try {
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheet = workbook.getSheetAt(0);

            //Dinh bug dob is not null
//            int rowIndex = 0;
//            for(Row row : sheet){
//                if(rowIndex == 0){
//                    rowIndex++;
//                    continue;
//                }
//                Iterator<Cell> cellIterator = row.iterator();
//                int cellIndex = 0;
//                User user = new User();
//                while(cellIterator.hasNext()){
//                    Cell cell = cellIterator.next();
//                    switch(cellIndex){
//                        case 0:
//                            String fullName = ExcelHelper.getCellValueAsString(cell);
//                            user.setFullName(fullName);
//                            break;
//                        case 1:
//                            String genderStr = ExcelHelper.getCellValueAsString(cell).trim().toUpperCase();
//                            if (genderStr.equals("NAM")) {
//                                user.setGender(User.Gender.NAM);
//                            } else if (genderStr.equals("NỮ")) {
//                                user.setGender(User.Gender.NU);
//                            } else {
//                                throw new RuntimeException("Giá trị giới tính không hợp lệ: '" + genderStr + "' tại dòng " + row.getRowNum());
//                            }
//                            break;
//
//                        case 2:
//                            if (cell == null || cell.getCellType() == CellType.BLANK) {
//                                throw new UserException(" Ngày sinh bị trống tại dòng " + (row.getRowNum() + 1));
//                            }
//
//                            LocalDate dob = null;
//                            try {
//                                if (cell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(cell)) {
//                                    Date date = cell.getDateCellValue();
//                                    dob = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//                                } else {
//                                    String dateStr = ExcelHelper.getCellValueAsString(cell).trim();
//                                    List<DateTimeFormatter> formatters = List.of(
//                                            DateTimeFormatter.ofPattern("d/M/yyyy"),
//                                            DateTimeFormatter.ofPattern("dd/MM/yyyy"),
//                                            DateTimeFormatter.ofPattern("yyyy-MM-dd")
//                                    );
//                                    for (DateTimeFormatter formatter : formatters) {
//                                        try {
//                                            dob = LocalDate.parse(dateStr, formatter);
//                                            break;
//                                        } catch (Exception ignored) {}
//                                    }
//                                    if (dob == null) {
//                                        throw new UserException("Định dạng ngày sinh không hợp lệ tại dòng " + (row.getRowNum() + 1) + ": " + dateStr);
//                                    }
//                                }
//
//                                user.setDob(dob);
//                            } catch (Exception ex) {
//                                throw new UserException("Lỗi đọc ngày sinh tại dòng " + (row.getRowNum() + 1) + ": " + ex.getMessage());
//                            }
//                            break;
//
//                        case 3:
//                            user.setEmail(ExcelHelper.getCellValueAsString(cell).trim());
//                            break;
//
//                        default:
//                            break;
//
//                    }
//                    cellIndex++;
//                }
//                user.setRole(User.Role.TV);
//                user.setCreatedAt(LocalDate.now());
//                user.setPasswordHash(passwordencoder.encode(PasswordGenerator.generatePassword()));
//                user.setUsername(VietNameseUtils.removeAccents(user.getFullName().replaceAll("\\s+", "")) + "123");
//                if(repository.existsByUsername(user.getUsername())){
//                    throw new UserException("userName da ton tai");
//                }
//                else{
//                    System.out.println("📦 Đang xử lý user: " + user.getFullName() + ", dob = " + user.getDob() + ", email = " + user.getEmail());
//                    users.add(user);
//                }
//
//            }
            int numberOfRows = sheet.getPhysicalNumberOfRows();

            for (int rowIndex = 1; rowIndex < numberOfRows; rowIndex++) { // Bắt đầu từ 1 để bỏ tiêu đề
                Row row = sheet.getRow(rowIndex);
                if (row == null) continue; // Bỏ qua dòng trống

                // Kiểm tra số ô: nếu ít hơn 4 thì có thể thiếu dữ liệu
                if (row.getLastCellNum() < 4) {
                    System.out.println("⚠️ Dòng " + (rowIndex + 1) + " có ít hơn 4 ô. Bỏ qua.");
                    continue;
                }

                User user = new User();

                Cell nameCell = row.getCell(0);
                Cell genderCell = row.getCell(1);
                Cell dobCell = row.getCell(2);
                Cell emailCell = row.getCell(3);

                // Họ tên
                String fullName = ExcelHelper.getCellValueAsString(nameCell);
                if (fullName == null || fullName.isBlank()) {
                    System.out.println("⚠️ Họ tên bị trống tại dòng " + (rowIndex + 1));
                    continue;
                }
                user.setFullName(fullName);

                // Giới tính
                String genderStr = ExcelHelper.getCellValueAsString(genderCell).trim().toUpperCase();
                if (genderStr.equals("NAM")) {
                    user.setGender(User.Gender.NAM);
                } else if (genderStr.equals("NỮ")) {
                    user.setGender(User.Gender.NU);
                } else {
                    throw new UserException("Giới tính không hợp lệ tại dòng " + (rowIndex + 1) + ": " + genderStr);
                }

                // Ngày sinh
                if (dobCell == null || dobCell.getCellType() == CellType.BLANK) {
                    throw new UserException("Ngày sinh bị trống tại dòng " + (rowIndex + 1));
                }

                LocalDate dob = null;
                try {
                    if (dobCell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(dobCell)) {
                        Date date = dobCell.getDateCellValue();
                        dob = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    } else {
                        String dateStr = ExcelHelper.getCellValueAsString(dobCell).trim();
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

                // Email
                String email = ExcelHelper.getCellValueAsString(emailCell).trim();
                if (email.isEmpty()) {
                    throw new UserException("Email bị thiếu tại dòng " + (rowIndex + 1));
                }
                user.setEmail(email);

                // Các thông tin khác
                user.setRole(User.Role.BQT);
                user.setCreatedAt(LocalDate.now());
                String password = PasswordGenerator.generatePassword();
                user.setPasswordHash(passwordencoder.encode(password));
                user.setUsername(VietNameseUtils.removeAccents(user.getFullName().replaceAll("\\s+", "")) + "123");

                if (repository.existsByUsername(user.getUsername())) {
                    throw new UserException("Username đã tồn tại: " + user.getUsername());
                }

                System.out.println("✅ Thêm user: " + user.getFullName() + ", dob = " + user.getDob() + ", email = " + user.getEmail());
                users.add(user);
                System.out.println("password: " + password);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return users;
    }


}
