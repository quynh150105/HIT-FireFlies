package com.example.hit_networking_base.controller;

import com.example.hit_networking_base.base.RestApiV1;
import com.example.hit_networking_base.base.VsResponseUtil;
import com.example.hit_networking_base.constant.UrlConstant;
import com.example.hit_networking_base.domain.dto.response.ImportResult;
import com.example.hit_networking_base.domain.dto.response.UserExportDTO;
import com.example.hit_networking_base.domain.entity.User;
import com.example.hit_networking_base.domain.mapstruct.UserMapper;
import com.example.hit_networking_base.service.ExcelUploadService;
import com.example.hit_networking_base.service.ExportExcelService;
import com.example.hit_networking_base.service.SaveListAccountUser;
import com.example.hit_networking_base.service.SendEmailService;
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
import java.util.List;

@RequiredArgsConstructor
@RestApiV1
public class ExportExcelController {

    private final SaveListAccountUser saveListAccountUser;

    @PostMapping(value = UrlConstant.Admin.EXPORT)
    public ResponseEntity<?> exportExcelFile(@RequestParam("file")MultipartFile file) throws IOException{
        return VsResponseUtil.success(saveListAccountUser.saveListAccUsersToDatabase(file));
    }
}
