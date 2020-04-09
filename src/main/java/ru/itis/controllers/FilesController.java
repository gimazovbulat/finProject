package ru.itis.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.itis.services.interfaces.FilesService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class FilesController {
    private final FilesService filesService;

    public FilesController(FilesService filesService) {
        this.filesService = filesService;
    }

    /*   @GetMapping(value = "/files")
       public ModelAndView uploadFile() {
           ModelAndView modelAndView = new ModelAndView();
           modelAndView.setViewName("files");
           return modelAndView;
       }

       @PostMapping(value = "/files")
       public ModelAndView uploadFile(@RequestParam("file") MultipartFile multipartFile, Authentication authentication) {
           UserDetails userDetails = (UserDetails) authentication.getPrincipal();
           Optional<User> userOptional = usersRepository.findByEmail(userDetails.getUsername());
           ModelAndView model = new ModelAndView();
           model.setViewName("files");

           if (userOptional.isPresent()) {
               filesService.save(multipartFile, userOptional.get().getId());
               model.addObject("status", "file was successfully downloaded");
           }
           return model;
       }
       // localhost:8080/files/123809183093qsdas09df8af.jpeg
   */
    @GetMapping(value = "/files/{file-name:.+}")
    public void getFile(@PathVariable("file-name") String fileName, HttpServletResponse response) {
        try {
            filesService.downloadFile(response, fileName);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}