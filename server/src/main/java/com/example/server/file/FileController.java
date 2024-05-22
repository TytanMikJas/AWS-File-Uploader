package com.example.server.file;

import com.example.server.file.dto.FileRequest;
import com.example.server.file.dto.FileResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("files")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class FileController {
    private final FileService fileService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> saveFile(
            @Valid @ModelAttribute FileRequest request
    ) {
        try {
            return fileService.save(request);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Failed to save file due to IO error.");
        }
    }

    @GetMapping
    public ResponseEntity<List<FileResponse>> findAllFiles() {
        try {
            return ResponseEntity.ok(fileService.findAll());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFile(@PathVariable("id") Integer fileId) {
        try {
            fileService.delete(fileId);
            return ResponseEntity.noContent().build();
        } catch (FileNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PatchMapping("/{id}/{fileName}")
    public ResponseEntity<?> updateFile(
            @PathVariable("id") Integer fileId,
            @PathVariable("fileName") String fileName
    ) {
        try {
            fileService.update(fileId, fileName);
            return ResponseEntity.ok().build();
        } catch (FileNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
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
