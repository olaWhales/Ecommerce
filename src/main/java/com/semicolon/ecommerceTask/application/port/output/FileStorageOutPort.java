package com.semicolon.ecommerceTask.application.port.output;

import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorageOutPort {
    String storeFile(MultipartFile file) throws IOException;
}