package ru.inovus.mimimimetr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.inovus.mimimimetr.entity.User;
import ru.inovus.mimimimetr.entity.Vote;
import ru.inovus.mimimimetr.entity.contender.Contender;
import ru.inovus.mimimimetr.entity.contender.ContenderView;
import ru.inovus.mimimimetr.service.*;

import java.util.List;


@Controller
@RequestMapping("/voting")
public class VotingController {

    private final ContenderService contenderService;
    private final UserService userService;
    private final VoteService voteService;
    private final ContenderViewService contenderViewService;

    @Autowired
    public VotingController(ContenderService contenderService, UserService userService, VoteService voteService, ContenderViewService contenderViewService) {
        this.contenderService = contenderService;
        this.userService = userService;
        this.voteService = voteService;
        this.contenderViewService = contenderViewService;
    }

    @GetMapping("")
    public String getContenders(Model model) {
        List<Contender> contenders;

        try {
            contenders = contenderService.getContendersWithoutVotesFromUser(userService.getCurrentUser(), 2);
        } catch (ContendersNotFoundException exception) {
            return "redirect:voting/leaderboard";
        }

        model.addAttribute("contenders", contenders);
        return "voting/voting";
    }

    @PostMapping("")
    public String vote(@RequestParam("selectedContenderId") Long selectedContenderId) {

        Vote vote = new Vote();
        User user = userService.getCurrentUser();
        Contender contender = contenderService.findById(selectedContenderId).get();
        ContenderView view = new ContenderView();
        view.setContender(contenderService.findById(selectedContenderId).get());
        view.setUser(userService.getCurrentUser());

        if (contenderService.isUserAlreadyVotedFor(selectedContenderId)) {
            vote = user.getVotes()
                    .stream()
                    .filter(userVote -> userVote.getContender().getId().equals(selectedContenderId))
                    .findAny().get();
        } else {
            vote.setContender(contender);
            vote.setUser(user);
        }

        voteService.save(vote);
        contenderViewService.save(view);

        return "redirect:voting";
    }

    @GetMapping("/leaderboard")
    public String getLeaderboard(Model model) {
        model.addAttribute("top", contenderService.getTop(10));
        return "voting/leaderboard";
    }
}
