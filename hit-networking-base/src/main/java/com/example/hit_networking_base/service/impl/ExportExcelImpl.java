package com.example.hit_networking_base.service.impl;

import com.example.hit_networking_base.domain.dto.response.UserExportDTO;
import com.example.hit_networking_base.service.ExportExcelService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExportExcelImpl implements ExportExcelService {

    @Override
    public ByteArrayOutputStream exportUsersToExcel(List<UserExportDTO> data) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            XSSFSheet sheet = workbook.createSheet("Exported Users");

            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("Username");
            header.createCell(1).setCellValue("Password");


            int rowIdx = 1;
            for (UserExportDTO dto : data) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(dto.getUsername());
                row.createCell(1).setCellValue(dto.getPassword());
            }

            workbook.write(out);
        }
        return out;
    }
}
