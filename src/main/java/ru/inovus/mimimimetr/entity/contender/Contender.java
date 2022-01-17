package ru.inovus.mimimimetr.entity.contender;

import ru.inovus.mimimimetr.entity.Vote;

import java.util.List;

public class Contender {
    private Long id;
    private String name;
    private String image;
    private List<Vote> votes;
    private ContenderType type;
}
