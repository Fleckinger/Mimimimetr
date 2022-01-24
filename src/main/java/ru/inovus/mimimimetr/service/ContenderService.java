package ru.inovus.mimimimetr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.inovus.mimimimetr.entity.User;
import ru.inovus.mimimimetr.entity.contender.Contender;
import ru.inovus.mimimimetr.repository.ContenderRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Transactional
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

    public Contender save(Contender contender) {
        return contenderRepository.save(contender);
    }

    public Optional<Contender> findById(Long id) {
        return contenderRepository.findById(id);
    }

    public Contender update(Contender contender) {
        return save(contender);
    }

    public void delete(Contender contender) {
        contenderRepository.delete(contender);
    }

    public List<Contender> getContendersWithoutVotesFromUser(User user, int numberOfContenders) throws ContendersNotFoundException {

        List<Contender> contenders = contenderRepository.findAllInRandomOrder()
                .stream()
                .filter(contender -> !isContenderVotedByUser(contender, user))
                .limit(numberOfContenders)
                .collect(Collectors.toList());

        if (contenders.size() < numberOfContenders) { throw new ContendersNotFoundException();}

        return contenders;
    }

    public List<Contender> getTop(int number) {
        return contenderRepository.findTopByVotes(PageRequest.of(0, number));
    }

    public boolean isContenderVotedByUser(Contender contender, User user) {
        return contender.getVotes()
                .stream()
                .anyMatch(vote -> vote.getUser().getId().equals(user.getId()));
    }

    public boolean isUserAlreadyVotedFor(Long contenderId) {
        Contender contender = contenderRepository.findById(contenderId).orElseThrow();
        User currentUser = userService.getCurrentUser();

        return contender.getVotes()
                .stream()
                .anyMatch(vote -> vote.getUser().getId().equals(currentUser.getId()));
    }
}
