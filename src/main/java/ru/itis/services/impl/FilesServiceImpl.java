package ru.itis.services.impl;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.itis.dao.interfaces.FilesRepository;
import ru.itis.models.FileInfo;
import ru.itis.services.interfaces.FilesService;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import static org.apache.commons.io.IOUtils.copy;

@Service
public class FilesServiceImpl implements FilesService {
    private final FilesRepository filesRepository;

    public FilesServiceImpl(FilesRepository filesRepository) {
        this.filesRepository = filesRepository;
    }

    @Value("${storage.dir}")
    private String storageDir;

    @Override
    public FileInfo save(MultipartFile file, Long userId) {
        String fileOrigName = file.getOriginalFilename();
        String storageName = UUID.randomUUID() + "." + FilenameUtils.getExtension(fileOrigName);
        Path path = Paths.get(storageDir + storageName);

        try {
            Files.copy(file.getInputStream(), path);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }

        FileInfo fileInfo = FileInfo.builder()
                .originalFileName(fileOrigName)
                .storageFileName(storageName)
                .type(file.getContentType())
                .url("/files/" + storageName)
                .build();

        filesRepository.save(fileInfo);

        return fileInfo;
    }

    @Override
    public void downloadFile(HttpServletResponse response, String fileName) throws IOException {
        FileInfo fileInfo = filesRepository.findByName(fileName);
        response.setContentType(fileInfo.getType());
        InputStream inputStream = new FileInputStream(new File(storageDir + fileInfo.getStorageFileName()));
        copy(inputStream, response.getOutputStream());
        response.flushBuffer();
    }

}
