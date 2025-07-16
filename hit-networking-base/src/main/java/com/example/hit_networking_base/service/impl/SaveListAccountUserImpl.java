package com.example.hit_networking_base.service.impl;

import com.example.hit_networking_base.constant.ErrorMessage;
import com.example.hit_networking_base.domain.dto.response.ImportResult;
import com.example.hit_networking_base.domain.entity.User;
import com.example.hit_networking_base.exception.BadRequestException;
import com.example.hit_networking_base.repository.UserRepository;
import com.example.hit_networking_base.service.SaveListAccountUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.example.hit_networking_base.service.ExcelUploadService;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class SaveListAccountUserImpl implements SaveListAccountUser {
    private final UserRepository repository;
    private final ExcelUploadService uploadService;


    @Override

    public String saveListAccUsersToDatabase(MultipartFile file) {

        if(file == null)
            throw new BadRequestException(ErrorMessage.ImportFileExcel.ERR_WRONG_FORMAT);
        // Kiểm tra định dạng MIME
        String contentType = file.getContentType();
        if (contentType == null || (!contentType.equals("application/vnd.ms-excel")
                && !contentType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))) {
            throw new BadRequestException(ErrorMessage.ImportFileExcel.ERR_WRONG_FORMAT);

        }

        // Kiểm tra phần mở rộng file (phòng trường hợp MIME bị giả mạo)
        String fileName = file.getOriginalFilename();
        if (fileName == null || !(fileName.endsWith(".xls") || fileName.endsWith(".xlsx"))) {
            throw new BadRequestException(ErrorMessage.ImportFileExcel.ERR_WRONG_FORMAT);
        }

        try {
           // List<User> users = uploadService.getCustomerDataFromExcel(file.getInputStream());
            List<User>  users = uploadService.getCustomerDataFromExcel(file.getInputStream());
            repository.saveAll(users);
        } catch (IOException e) {
            throw new BadRequestException(ErrorMessage.ImportFileExcel.ERR_WRONG_READ + e.getMessage());
        }
        return "SUCCESS";
    }

    @Override
    public List<User> getAllUser(){
        return null;
    }

}
