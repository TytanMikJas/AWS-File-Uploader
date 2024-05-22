package com.example.server.file;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("files")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class FileController {
    private final FileService fileService;

    @PostMapping
    public ResponseEntity<?> saveFile(
         @RequestPart("file") MultipartFile file,
         @RequestParam("fileName") String fileName
    ) {
        try {
            return fileService.save(file, fileName);
        } catch (IOException e) {
            e.printStackTrace();
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
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}
