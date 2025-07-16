package com.example.hit_networking_base.controller;

import com.example.hit_networking_base.base.RestApiV1;
import com.example.hit_networking_base.constant.UrlConstant;
import com.example.hit_networking_base.domain.dto.response.ImportResult;
import com.example.hit_networking_base.service.ExcelUploadService;
import com.example.hit_networking_base.service.ExportExcelService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource; // cần dòng này
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@RequiredArgsConstructor
@RestApiV1
public class ExportExcelController {

    private final ExcelUploadService uploadService;
    private final ExportExcelService service;

    @PostMapping(value = UrlConstant.Admin.EXPORT)
    public ResponseEntity<Resource> exportExcelFile(@RequestParam("file")MultipartFile file) throws IOException{
        ImportResult result = uploadService.getCustomerDataFromExcel(file.getInputStream());

        ByteArrayOutputStream excelFile = service.exportUsersToExcel(result.getExportDTOS());
        ByteArrayResource resource = new ByteArrayResource(excelFile.toByteArray());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=imported_users.xlsx")
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(resource);
    }
}
