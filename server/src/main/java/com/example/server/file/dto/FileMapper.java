package com.example.server.file.dto;

import com.example.server.file.File;
import org.springframework.stereotype.Service;

@Service
public class FileMapper {

    public FileResponse toFileResponse(File file) {
        return FileResponse.builder()
                .fileId(file.getFileId())
                .fileName(file.getFileName())
                .build();
    }

    public File toFile(String fileName, String url) {
        return File.builder()
                .fileName(fileName)
                .fileUrl(url)
                .build();
    }
}
