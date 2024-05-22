package com.example.server.file;

import com.example.server.file.dto.FileRequest;
import com.example.server.file.dto.FileResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("files")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class FileController {
    private final FileService fileService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> saveFile(
            @Valid @ModelAttribute FileRequest request
    ) {
        try {
            return fileService.save(request);
        } catch (IOException e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping
    public List<FileResponse> findAllFiles() {
        return fileService.findAll();
    }

    @DeleteMapping("/{id}")
    public void deleteFile(
            @PathVariable("id") Integer fileId
    ) {
        fileService.delete(fileId);
    }

    @PatchMapping("/{id}/{fileName}")
    public void updateFile(
            @PathVariable("id") Integer fileId,
            @PathVariable("fileName") String fileName
    ) {
        fileService.update(fileId, fileName);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> downloadFile(@PathVariable("id") Integer fileId) {
        try {
            return fileService.downloadFile(fileId);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
