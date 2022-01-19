package ru.inovus.mimimimetr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.inovus.mimimimetr.entity.User;
import ru.inovus.mimimimetr.entity.contender.Contender;
import ru.inovus.mimimimetr.repository.ContenderRepository;

import java.util.List;

@Service
public class ContenderService {

    private ContenderRepository contenderRepository;
    private UserService userService;

    @Autowired
    public ContenderService(ContenderRepository contenderRepository, UserService userService) {
        this.contenderRepository = contenderRepository;
        this.userService = userService;
    }

    public ContenderService() {
    }

    public List<Contender> getPair() {
        User currentUser = userService.getCurrentUser();

        return contenderRepository
                .getContenderWithoutVoteFromUser(currentUser, PageRequest.of(0, 2)).getContent();
    }

    public List<Contender> getTop(int number) {
        return contenderRepository.findTopByVotes(PageRequest.of(0, number));
    }

}
