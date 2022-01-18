package ru.inovus.mimimimetr.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller()
@RequestMapping("/voting")
public class VotingController {

    @GetMapping("")
    public String getContenders() {
        return "voting";
    }
}
