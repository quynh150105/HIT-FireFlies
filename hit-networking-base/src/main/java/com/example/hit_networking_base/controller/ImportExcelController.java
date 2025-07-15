package com.example.hit_networking_base.controller;

import com.example.hit_networking_base.base.RestApiV1;
import com.example.hit_networking_base.base.VsResponseUtil;
import com.example.hit_networking_base.constant.UrlConstant;
import com.example.hit_networking_base.exception.UserException;
import com.example.hit_networking_base.service.SaveListAccountUser;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@RestApiV1
@RequiredArgsConstructor
public class ImportExcelController {
    private final SaveListAccountUser saveListAccountUser;

    @PostMapping(value = UrlConstant.Admin.IMPORT, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> importExcelFile(@ModelAttribute MultipartFile file){
        try {
            saveListAccountUser.saveListAccUsersToDatabase(file);
            return VsResponseUtil.success("import thanh cong");
        }
        catch (UserException e){
            return VsResponseUtil.error("import that bai");
        }
    }




}
