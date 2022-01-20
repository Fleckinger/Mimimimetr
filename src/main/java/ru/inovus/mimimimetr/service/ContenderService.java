package ru.inovus.mimimimetr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.inovus.mimimimetr.entity.User;
import ru.inovus.mimimimetr.entity.Vote;
import ru.inovus.mimimimetr.entity.contender.Contender;
import ru.inovus.mimimimetr.entity.contender.ContendersPair;
import ru.inovus.mimimimetr.repository.ContenderRepository;
import ru.inovus.mimimimetr.repository.ContendersPairRepository;
import ru.inovus.mimimimetr.repository.VoteRepository;

import javax.persistence.NonUniqueResultException;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class ContenderService {

    private ContenderRepository contenderRepository;
    private ContendersPairRepository contendersPairRepository;
    private VoteRepository voteRepository;
    private UserService userService;

    @Autowired
    public ContenderService(ContenderRepository contenderRepository, ContendersPairRepository contendersPairRepository, VoteRepository voteRepository, UserService userService) {
        this.contenderRepository = contenderRepository;
        this.contendersPairRepository = contendersPairRepository;
        this.voteRepository = voteRepository;
        this.userService = userService;
    }

    public ContenderService() {
    }

    public Optional<Contender> findById(Long id) {
        return contenderRepository.findById(id);
    }

    public ContendersPair getPair() throws PairNotFoundException {

        User currentUser = userService.getCurrentUser();

        List<Contender> contenders = contenderRepository.findAllInRandomOrder();
        if (contenders.size() < 2) { throw new PairNotFoundException();}

        ContendersPair pair = new ContendersPair();

        for (int i = 0; i < contenders.size() - 1; i++) {
            pair.setFirstContender(contenders.get(i));
            pair.setSecondContender(contenders.get(i + 1));
            try {
                if (!isPairViewedByUser(pair, currentUser)) {
                    return pair;
                }
            } catch (NonUniqueResultException exception) {
                throw new PairNotFoundException();
            }

        }
        throw new PairNotFoundException();

    }

    public Vote saveVote(Vote vote) {
        return voteRepository.save(vote);
    }

    public ContendersPair saveContendersPair(ContendersPair pair) {
        return contendersPairRepository.save(pair);
    }

    public List<Contender> getTop(int number) {
        return contenderRepository.findTopByVotes(PageRequest.of(0, number));
    }

    public boolean isContenderVotedByUser(Contender contender, User user) {
        return contender.getVotes()
                .stream()
                .anyMatch(vote -> vote.getUser().getId().equals(user.getId()));
    }

    public boolean isPairViewedByUser(ContendersPair pair, User user) {
      return contendersPairRepository
              .findPairVotedByUser(pair.getFirstContender().getId(), pair.getSecondContender().getId(), user.getId())
              .size() > 0;
    }

    public boolean isUserAlreadyVotedFor(Long contenderId) {
        Contender contender = contenderRepository.findById(contenderId).orElseThrow();
        User currentUser = userService.getCurrentUser();

        return contender.getVotes()
                .stream()
                .anyMatch(vote -> vote.getUser().getId().equals(currentUser.getId()));
    }
}
