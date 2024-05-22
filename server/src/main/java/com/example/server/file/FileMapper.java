package com.example.server.file;

import org.springframework.stereotype.Service;

@Service
public class FileMapper {

    public FileResponse toFileResponse(File file) {
        return FileResponse.builder()
                .fileId(file.getFileId())
                .fileName(file.getFileName())
                .build();
    }
}
