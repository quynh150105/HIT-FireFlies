package com.example.hit_networking_base.controller;

import com.example.hit_networking_base.base.RestApiV1;
import com.example.hit_networking_base.base.VsResponseUtil;
import com.example.hit_networking_base.constant.UrlConstant;
import com.example.hit_networking_base.exception.UserException;
import com.example.hit_networking_base.service.SaveListAccountUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@RestApiV1
@RequiredArgsConstructor
public class ImportExcelController {
    private final SaveListAccountUser saveListAccountUser;

    @PostMapping(UrlConstant.Admin.IMPORT)
    public ResponseEntity<?> importExcelFile(@RequestParam("file")MultipartFile file){
        try {
            saveListAccountUser.saveListAccUsersToDatabase(file);
            return VsResponseUtil.success("import thanh cong");
        }
        catch (UserException e){
            return VsResponseUtil.error("import that bai");
        }
    }

    @GetMapping(UrlConstant.Admin.GETALL)
    public ResponseEntity<?> getAllUsers(){
        return  VsResponseUtil.success(saveListAccountUser.getAllUser());
    }


}
