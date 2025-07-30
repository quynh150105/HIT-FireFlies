package com.example.hit_networking_base.service;

import com.example.hit_networking_base.domain.dto.response.UserExportDTO;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public interface ExportExcelService {
    ByteArrayOutputStream exportUsersToExcel(List<UserExportDTO> data) throws IOException;
}
