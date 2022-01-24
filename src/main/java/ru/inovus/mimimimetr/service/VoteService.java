package ru.inovus.mimimimetr.service;

import org.springframework.stereotype.Service;
import ru.inovus.mimimimetr.entity.Vote;
import ru.inovus.mimimimetr.repository.VoteRepository;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class VoteService {

    private final VoteRepository voteRepository;

    public VoteService(VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    public Vote save(Vote vote) {
        return voteRepository.save(vote);
    }

    public Optional<Vote> findById(Long id) {
        return voteRepository.findById(id);
    }

    public Vote update(Vote vote) {
        return save(vote);
    }

    public void delete(Vote vote) {
        voteRepository.delete(vote);
    }

}
