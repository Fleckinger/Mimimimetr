package ru.inovus.mimimimetr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.inovus.mimimimetr.entity.contender.Contender;
import ru.inovus.mimimimetr.service.ContenderService;

import java.util.List;


@Controller
@RequestMapping("/voting")
public class VotingController {

    private final ContenderService contenderService;

    @Autowired
    public VotingController(ContenderService contenderService) {
        this.contenderService = contenderService;
    }

    @GetMapping("")
    public String getContenders(Model model) {
        List<Contender> pair = contenderService.getPair();
        if (pair.size() != 2) {
            return "redirect:/voting/leaderboard";
        }
        model.addAttribute("pair", contenderService.getPair());
        return "/voting/voting";
    }

    @GetMapping("/leaderboard")
    public String getLeaderboard(Model model) {
        model.addAttribute("", contenderService.getTop(10));
        return "/voting/leaderboard";
    }
}
