package ru.inovus.mimimimetr.service;

import org.springframework.stereotype.Service;
import ru.inovus.mimimimetr.entity.contender.ContenderView;
import ru.inovus.mimimimetr.repository.ContenderViewRepository;

import java.util.Optional;

@Service
public class ContenderViewService {

    private final ContenderViewRepository contenderViewRepository;

    public ContenderViewService(ContenderViewRepository contenderViewRepository) {
        this.contenderViewRepository = contenderViewRepository;
    }

    public ContenderView save(ContenderView contenderView) {
        return contenderViewRepository.save(contenderView);
    }

    public Optional<ContenderView> findById(Long id) {
        return contenderViewRepository.findById(id);
    }

    public ContenderView update(ContenderView view) {
        return contenderViewRepository.save(view);
    }

    public void delete(ContenderView view) {
        contenderViewRepository.delete(view);
    }

}
