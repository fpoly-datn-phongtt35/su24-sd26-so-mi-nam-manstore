package com.example.manstore.controller.admin;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/api")
public class FileUploadController {

    @PostMapping("/upload-image")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            // Lưu trữ tệp vào hệ thống tệp hoặc dịch vụ lưu trữ tệp, ví dụ như AWS S3, Google Cloud Storage, hoặc thư mục cục bộ.
            String imageUrl = storeFile(file);

            return ResponseEntity.ok(imageUrl);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi khi tải lên hình ảnh: " + e.getMessage());
        }
    }

    private String storeFile(MultipartFile file) throws IOException {
        // Giả sử bạn lưu trữ tệp vào thư mục cục bộ
        String uploadDir = "uploads/";
        File uploadDirFile = new File(uploadDir);
        if (!uploadDirFile.exists()) {
            uploadDirFile.mkdirs();
        }

        String originalFilename = file.getOriginalFilename();
        String filePath = uploadDir + originalFilename;
        File dest = new File(filePath);
        file.transferTo(dest);

        // Trả về URL của hình ảnh
        return "/uploads/" + originalFilename;
    }
}

