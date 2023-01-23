package com.game.entity;


import com.fasterxml.jackson.annotation.JsonAutoDetect;

import javax.persistence.Entity;

@JsonAutoDetect
public enum Profession {
    WARRIOR,
    ROGUE,
    SORCERER,
    CLERIC,
    PALADIN,
    NAZGUL,
    WARLOCK,
    DRUID
}
