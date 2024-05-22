package com.example.server.file;

import com.example.server.file.dto.FileMapper;
import com.example.server.file.dto.FileRequest;
import com.example.server.file.dto.FileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FileService {
    private final FileRepository fileRepository;
    private final FileMapper fileMapper;

    @Value("${aws.bucket.url}")
    private String BUCKET_URL;

    public ResponseEntity<?> save(FileRequest request) throws IOException {
        String fileName = request.fileName();
        MultipartFile file = request.file();

        String url = BUCKET_URL + fileName;

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        MediaType mediaType = MediaType.parseMediaType(Objects.requireNonNull(file.getContentType()));
        headers.setContentType(mediaType);

        try {
            byte[] fileBytes = file.getBytes();
            HttpEntity<byte[]> requestEntity = new HttpEntity<>(fileBytes, headers);
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                File newFile = fileMapper.toFile(fileName, url);
                fileRepository.save(newFile);
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.status(response.getStatusCode()).build();
            }
        } catch (IOException e) {
            return ResponseEntity.status(500).build();
        }
    }

    public List<FileResponse> findAll() {
        List<File> fileList = fileRepository.findAll();
        return fileList.stream()
                .map(fileMapper::toFileResponse)
                .toList();
    }

    public void delete(Integer fileId) {
        File file = fileRepository.findById(fileId)
                .orElseThrow(() -> new RuntimeException("File not found"));

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete(file.getFileUrl());

        fileRepository.delete(file);
    }

    public void update(Integer fileId, String fileName) {
        File file = fileRepository.findById(fileId)
                .orElseThrow(() -> new RuntimeException("File not found"));

        file.setFileName(fileName);

        fileRepository.save(file);
    }

    public ResponseEntity<?> downloadFile(Integer fileId) throws IOException {
        File file = fileRepository.findById(fileId)
                .orElseThrow(() -> new RuntimeException("File not found"));
        String fileUrl = file.getFileUrl();

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<byte[]> response = restTemplate.getForEntity(fileUrl, byte[].class);

        if (response.getStatusCode().is2xxSuccessful()) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDisposition(ContentDisposition.attachment().filename(file.getFileName()).build());

            return new ResponseEntity<>(response.getBody(), headers, HttpStatus.OK);
        } else {
            return ResponseEntity.status(response.getStatusCode()).build();
        }
    }
}
