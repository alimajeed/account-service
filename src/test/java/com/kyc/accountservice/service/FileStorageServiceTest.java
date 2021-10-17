package com.kyc.accountservice.service;

import com.kyc.accountservice.exception.CustomFileNotFoundException;
import com.kyc.accountservice.exception.FileStorageException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FileStorageServiceTest {

    @Autowired
    FileStorageService fileStorageService;

    @Test
    void storeFile() throws IOException, FileStorageException, ExecutionException, InterruptedException {
        FileInputStream inputFile = new FileInputStream( "docs/alimajeed_resume.pdf");
        String fileName = "alimajeed_resume.pdf";
        MockMultipartFile file = new MockMultipartFile("file", fileName, "multipart/form-data", inputFile);

        CompletableFuture<String> storeFile = fileStorageService.storeFile(file);
        assertTrue(storeFile.get().contains(fileName));
    }

    @Test
    void loadFileAsResource() throws CustomFileNotFoundException {
        String fileName = "alimajeed_resume.pdf";
        Resource resource = fileStorageService.loadFileAsResource(fileName);
        assertNotNull(resource);
        assertEquals(fileName, resource.getFilename());
    }
}