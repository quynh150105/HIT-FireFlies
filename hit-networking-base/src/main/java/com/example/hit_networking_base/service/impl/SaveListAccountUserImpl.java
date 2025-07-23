package com.example.hit_networking_base.service.impl;

import com.example.hit_networking_base.constant.ErrorMessage;
import com.example.hit_networking_base.domain.dto.response.ImportResult;
import com.example.hit_networking_base.domain.entity.User;
import com.example.hit_networking_base.domain.mapstruct.UserMapper;
import com.example.hit_networking_base.exception.BadRequestException;
import com.example.hit_networking_base.repository.UserRepository;
import com.example.hit_networking_base.service.SaveListAccountUser;
import com.example.hit_networking_base.service.SendEmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.example.hit_networking_base.service.ExcelUploadService;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class SaveListAccountUserImpl implements SaveListAccountUser {
    private final UserRepository userRepository;
    private final ExcelUploadService uploadService;
    private final UserMapper userMapper;
    private final SendEmailService sendEmailService;


    @Override
    public String saveListAccUsersToDatabase(MultipartFile file) {

        if(!uploadService.isValidExcelFile(file))
            throw new BadRequestException(ErrorMessage.ImportFileExcel.ERR_WRONG_FORMAT);

        try {
            List<User>  users = uploadService.getCustomerDataFromExcel(file.getInputStream());
            userRepository.saveAll(users);
            sendEmailService.sendEmailToCreateUser(userMapper.toListUserExportDTO(users));
        } catch (IOException e) {
            throw new BadRequestException(ErrorMessage.ImportFileExcel.ERR_WRONG_READ + e.getMessage());
        }
        return "SUCCESS";
    }

}
