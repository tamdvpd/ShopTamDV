package com.example.shop.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/admin")
public class UploadController {
    @PostMapping("/uploads")
    public ResponseEntity<Map<String, String>> upload(@RequestParam("file") MultipartFile file) throws IOException {
        Path dir = Paths.get("/tmp");
        Files.createDirectories(dir);
        String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path filepath = dir.resolve(filename);
        Files.copy(file.getInputStream(), filepath);
        String url = "http://localhost:8080/files/" + filename;
        return ResponseEntity.ok(Map.of("url", url));
    }
}
