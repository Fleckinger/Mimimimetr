package ru.inovus.mimimimetr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.inovus.mimimimetr.entity.Vote;
import ru.inovus.mimimimetr.entity.contender.ContendersPair;
import ru.inovus.mimimimetr.service.ContenderService;
import ru.inovus.mimimimetr.service.PairNotFoundException;
import ru.inovus.mimimimetr.service.UserService;


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
        ContendersPair pair;

        try {
            pair = contenderService.getPair();
        } catch (PairNotFoundException exception) {
            return "redirect:voting/leaderboard";
        }

        model.addAttribute("pair", pair);
        return "voting/voting";
    }

    @PostMapping("")
    public String vote(@RequestParam("selectedContenderId") Long selectedContenderId,
                       @RequestParam("secondContenderId") Long secondContenderId) {

        Vote vote = new Vote();

        if (contenderService.isUserAlreadyVotedFor(selectedContenderId)) {
            vote = userService.getCurrentUser().getVotes()
                    .stream()
                    .filter(userVote -> userVote.getContender().getId().equals(selectedContenderId))
                    .findAny()
                    .orElseThrow();
        } else {
            vote.setContender(contenderService.findById(selectedContenderId).orElseThrow());
            vote.setUser(userService.getCurrentUser());
            contenderService.saveVote(vote);
        }

        ContendersPair pair = new ContendersPair();
        pair.setFirstContender(contenderService.findById(selectedContenderId).orElseThrow());
        pair.setSecondContender(contenderService.findById(secondContenderId).orElseThrow());
        pair.setVote(vote);
        contenderService.saveContendersPair(pair);

        return "redirect:voting";
    }

    @GetMapping("/leaderboard")
    public String getLeaderboard(Model model) {
        model.addAttribute("top", contenderService.getTop(10));
        return "voting/leaderboard";
    }
}
