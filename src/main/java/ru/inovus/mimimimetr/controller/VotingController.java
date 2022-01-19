package ru.inovus.mimimimetr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.inovus.mimimimetr.entity.Vote;
import ru.inovus.mimimimetr.entity.contender.Contender;
import ru.inovus.mimimimetr.service.ContenderService;
import ru.inovus.mimimimetr.service.UserService;

import java.util.List;


@Controller
@RequestMapping("/voting")
public class VotingController {

    private final ContenderService contenderService;
    private final UserService userService;

    @Autowired
    public VotingController(ContenderService contenderService, UserService userService) {
        this.contenderService = contenderService;
        this.userService = userService;
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

    @PostMapping("")
    public String vote(@RequestParam("id") Long contenderId) {

        Vote vote = new Vote();
        vote.setContender(contenderService.findById(contenderId).orElseThrow());
        vote.setUser(userService.getCurrentUser());
        contenderService.saveVote(vote);

        return "redirect:/voting";
    }

    @GetMapping("/leaderboard")
    public String getLeaderboard(Model model) {
        model.addAttribute("top", contenderService.getTop(10));
        return "/voting/leaderboard";
    }
}
