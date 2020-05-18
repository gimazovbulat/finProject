package ru.itis.controllers;

import org.springframework.context.annotation.Profile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import ru.itis.dto.UserDto;
import ru.itis.restSecurity.CurrentUser;
import ru.itis.services.interfaces.FilesService;
import ru.itis.services.interfaces.UsersService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@Profile("mvc")
public class FilesController {
    private final FilesService filesService;
    private final UsersService usersService;

    public FilesController(FilesService filesService, UsersService usersService) {
        this.filesService = filesService;
        this.usersService = usersService;
    }

    @GetMapping(value = "/files")
    public ModelAndView uploadFile() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("files");
        return modelAndView;
    }

    @PostMapping(value = "/files")
    public ModelAndView uploadFile(@RequestParam("file") MultipartFile multipartFile, @CurrentUser UserDetails userDetails) {
        UserDto user = usersService.findUser(userDetails.getUsername());
        ModelAndView model = new ModelAndView();
        model.setViewName("files");

        filesService.save(multipartFile, user.getId());
        model.addObject("status", "file was successfully downloaded");
        return model;
    }

    // localhost:8080/files/123809183093qsdas09df8af.jpeg
    @GetMapping(value = "/files/{file-name:.+}")
    public void getFile(@PathVariable("file-name") String fileName, HttpServletResponse response) {
        try {
            filesService.downloadFile(response, fileName);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}