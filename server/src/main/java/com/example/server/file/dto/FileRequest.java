package com.example.server.file.dto;

import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.lang.NonNull;
import org.springframework.web.multipart.MultipartFile;

public record FileRequest(
        @Nonnull
        @NotEmpty
        String fileName,
        @NonNull
        MultipartFile file
) {
}
