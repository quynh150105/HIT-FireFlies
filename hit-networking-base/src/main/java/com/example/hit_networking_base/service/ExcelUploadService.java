package com.example.hit_networking_base.service;

import com.example.hit_networking_base.domain.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

public interface ExcelUploadService {
     public boolean isValidExcelFile(MultipartFile file);

    List<User> getCustomerDataFromExcel(InputStream inputStream);

}
