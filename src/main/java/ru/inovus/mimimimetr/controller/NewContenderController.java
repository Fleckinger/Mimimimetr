package ru.inovus.mimimimetr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.inovus.mimimimetr.entity.contender.Contender;
import ru.inovus.mimimimetr.entity.contender.ContenderType;
import ru.inovus.mimimimetr.service.ContenderService;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashSet;

@Controller("/")
public class NewContenderController {

    private final ContenderService contenderService;

    @Autowired
    public NewContenderController(ContenderService contenderService) {
        this.contenderService = contenderService;
    }

    @GetMapping("/newcontender")
    public String newContenderForm() {
        return "newcontender";
    }

    @PostMapping("/newcontender")
    public String newContender(@RequestParam("image") MultipartFile file, String name) {

        Contender contender = new Contender();
        contender.setName(name);
        contender.setType(ContenderType.CAT);
        contender.setVotes(new HashSet<>());
        contender = contenderService.save(contender);
        contender.setImage(contender.getId() + ".jpg");
        contenderService.save(contender);

        String fileName = contender.getId().toString() + ".jpg";
        Path uploadPath = Paths.get("src/main/resources/static/images/cats/");


        try (InputStream inputStream = file.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException ioException) {
            ioException.printStackTrace();
            return "redirect:newcontender";
        }

        return "redirect:voting";
    }
}
