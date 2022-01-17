package ru.inovus.mimimimetr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.inovus.mimimimetr.entity.Vote;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
}
