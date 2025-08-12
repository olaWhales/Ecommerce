package com.semicolon.ecommerceTask.infrastructure.adapter.output.fileStorage;

import com.semicolon.ecommerceTask.application.port.output.FileStorageOutPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@Slf4j
public class FileStorageAdapter implements FileStorageOutPort {

    @Value("${file.upload.dir:./uploads}")
    private String uploadDir;

    @Override
    public String uploadImage(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IOException("Failed to store empty file");
        }

        // Ensure the upload directory exists
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        try {
            String originalFileName = file.getOriginalFilename();
            String fileExtension = "";
            if (originalFileName != null && originalFileName.contains(".")) {
                fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
            }
            String uniqueFileName = UUID.randomUUID().toString() + fileExtension;

            Path filePath = uploadPath.resolve(uniqueFileName);

            // Save the file to the local disk
            Files.copy(file.getInputStream(), filePath);

            log.info("File uploaded to: {}", filePath.toAbsolutePath());

            return "/uploads/" + uniqueFileName;

        } catch (IOException e) {
            log.error("Could not upload file: {}", file.getOriginalFilename(), e);
            throw new IOException("Could not upload file: " + file.getOriginalFilename(), e);
        }
    }
}