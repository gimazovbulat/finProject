package ru.itis.services.impl;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.itis.aspects.SendMailAnno;
import ru.itis.dao.interfaces.FilesRepository;
import ru.itis.dto.UserDto;
import ru.itis.models.FileInfo;
import ru.itis.models.User;
import ru.itis.services.interfaces.FilesService;
import ru.itis.services.interfaces.UsersService;

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
    private final UsersService usersService;

    public FilesServiceImpl(FilesRepository filesRepository, UsersService usersService) {
        this.filesRepository = filesRepository;
        this.usersService = usersService;
    }

    @Value("${storage.dir}")
    private String storageDir;

    @SendMailAnno
    @Override
    public FileInfo save(MultipartFile file, Long userId) {
        String fileOrigName = file.getOriginalFilename();
        String storageName = UUID.randomUUID() + "." + FilenameUtils.getExtension(fileOrigName);
        Path path = Paths.get(storageDir + storageName);
        UserDto userDto = usersService.findUser(userId);

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
                .user(User.fromUserDto(userDto))
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
