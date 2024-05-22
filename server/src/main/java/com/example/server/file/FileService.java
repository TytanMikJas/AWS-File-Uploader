package com.example.server.file;

import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FileService {
    private final FileRepository fileRepository;
    private final FileMapper fileMapper;

    public ResponseEntity<?> save(MultipartFile file, String fileName) throws IOException {
        String url = Constants.BUCKET_URL.getValue() + fileName;

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        MediaType mediaType = MediaType.parseMediaType(Objects.requireNonNull(file.getContentType()));
        headers.setContentType(mediaType);

        try {
            byte[] fileBytes = file.getBytes();
            HttpEntity<byte[]> requestEntity = new HttpEntity<>(fileBytes, headers);
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                File newFile = File.builder()
                        .fileUrl(url)
                        .fileName(fileName)
                        .build();

                fileRepository.save(newFile);
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.status(response.getStatusCode()).build();
            }
        } catch (IOException e) {
            e.printStackTrace();
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
