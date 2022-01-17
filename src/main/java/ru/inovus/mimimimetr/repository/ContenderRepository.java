package ru.inovus.mimimimetr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.inovus.mimimimetr.entity.contender.Contender;

@Repository
public interface ContenderRepository extends JpaRepository<Contender, Long> {
}
