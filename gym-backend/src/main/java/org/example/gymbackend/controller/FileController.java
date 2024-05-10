package org.example.gymbackend.controller;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/fileController")
@CrossOrigin
public class FileController {
    @GetMapping
    public void getFile(@RequestParam String image, HttpServletResponse response) throws IOException {
        FileInputStream inputStream = new FileInputStream("gym-backend/src/main/resources/" + image);

        ServletOutputStream outputStream = response.getOutputStream();

        inputStream.transferTo(outputStream);

        inputStream.close();
        outputStream.close();
    }

    @PostMapping
    public String saveFile(@RequestParam MultipartFile file) throws IOException {
        String image = UUID.randomUUID() + file.getOriginalFilename();
        FileOutputStream outputStream = new FileOutputStream("gym-backend/src/main/resources/" + image);
        outputStream.write(file.getBytes());
        outputStream.close();
        return image;
    }
}
