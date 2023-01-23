package com.game.entity;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import javax.persistence.Entity;

@JsonAutoDetect

public enum Race {
    HUMAN,
    DWARF,
    ELF,
    GIANT,
    ORC,
    TROLL,
    HOBBIT
}
