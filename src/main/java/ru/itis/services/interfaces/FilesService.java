package ru.itis.services.interfaces;

import org.springframework.web.multipart.MultipartFile;
import ru.itis.models.FileInfo;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface FilesService {
    FileInfo save(MultipartFile file, Long userId);
    void downloadFile(HttpServletResponse response, String fileName) throws IOException;
}
