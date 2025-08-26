package com.semicolon.ecommerceTask.infrastructure.output.fileStorage;

import com.semicolon.ecommerceTask.application.port.output.FileStorageOutPort;
import com.semicolon.ecommerceTask.infrastructure.utilities.MessageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;
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
    public String storeFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {throw new IOException(MessageUtil.FAILED_TO_EMPTY_FILE);}
        String contentType = file.getContentType();
        assert contentType != null;
        if (!contentType.startsWith("image/")) {throw new IOException(MessageUtil.ONLY_IMAGE_FILES_ARE_ALLOWED);}
        Path uploadPath = Paths.get(uploadDir).toAbsolutePath();
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        try (InputStream inputStream = file.getInputStream()) {
            String originalFileName = file.getOriginalFilename();
            String fileExtension = originalFileName != null && originalFileName.contains(MessageUtil.DOT)
                ? originalFileName.substring(originalFileName.lastIndexOf(MessageUtil.DOT))
                : ".jpg";
            String uniqueFileName = UUID.randomUUID().toString() + fileExtension;
            Path filePath = uploadPath.resolve(uniqueFileName);
            Files.copy(inputStream, filePath);
            return "/uploads/" + uniqueFileName;
        } catch (IOException e) {
//            log.error("Could not upload file: {}", file.getOriginalFilename(), e);
            throw new IOException(MessageUtil.COULD_NOT_UPLOAD_FILE + file.getOriginalFilename(), e);
        }
    }
}
