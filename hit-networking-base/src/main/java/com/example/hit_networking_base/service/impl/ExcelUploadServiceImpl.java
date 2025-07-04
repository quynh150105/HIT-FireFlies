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

            int rowIndex = 0;
            for(Row row : sheet){
                if(rowIndex == 0){
                    rowIndex++;
                    continue;
                }
                Iterator<Cell> cellIterator = row.iterator();
                int cellIndex = 0;
                User user = new User();
                while(cellIterator.hasNext()){
                    Cell cell = cellIterator.next();
                    switch(cellIndex){
                        case 0:
                            String fullName = ExcelHelper.getCellValueAsString(cell);
                            user.setFullName(fullName);
                            break;
                        case 1:
                            String genderStr = ExcelHelper.getCellValueAsString(cell).trim().toUpperCase();
                            if (genderStr.equals("NAM")) {
                                user.setGender(User.Gender.NAM);
                            } else if (genderStr.equals("NỮ")) {
                                user.setGender(User.Gender.NU);
                            } else {
                                throw new RuntimeException("Giá trị giới tính không hợp lệ: '" + genderStr + "' tại dòng " + row.getRowNum());
                            }
                            break;

                        case 2:
                            if (cell == null || cell.getCellType() == CellType.BLANK) {
                                throw new UserException(" Ngày sinh bị trống tại dòng " + (row.getRowNum() + 1));
                            }

                            LocalDate dob = null;
                            try {
                                if (cell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(cell)) {
                                    Date date = cell.getDateCellValue();
                                    dob = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                                } else {
                                    String dateStr = ExcelHelper.getCellValueAsString(cell).trim();
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
                                        throw new UserException("Định dạng ngày sinh không hợp lệ tại dòng " + (row.getRowNum() + 1) + ": " + dateStr);
                                    }
                                }

                                user.setDob(dob);
                            } catch (Exception ex) {
                                throw new UserException("Lỗi đọc ngày sinh tại dòng " + (row.getRowNum() + 1) + ": " + ex.getMessage());
                            }
                            break;

                        case 3:
                            user.setEmail(ExcelHelper.getCellValueAsString(cell).trim());
                            break;

                        default:
                            break;

                    }
                    cellIndex++;
                }
                user.setRole(User.Role.TV);
                user.setCreatedAt(LocalDate.now());
                user.setPasswordHash(passwordencoder.encode(PasswordGenerator.generatePassword()));
                user.setUsername(VietNameseUtils.removeAccents(user.getFullName().replaceAll("\\s+", "")) + "123");
                if(repository.existsByUsername(user.getUsername())){
                    throw new UserException("userName da ton tai");
                }
                else{
                    System.out.println("📦 Đang xử lý user: " + user.getFullName() + ", dob = " + user.getDob() + ", email = " + user.getEmail());
                    users.add(user);
                }

            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return users;
    }


}
