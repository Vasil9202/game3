package com.game.repository;


import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.Entity;
import java.util.Date;
import java.util.List;
import java.util.Optional;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete
//@Repository
@Repository
public interface PlayerRepository extends JpaRepository<Player,Long> {

    @Query("SELECT p FROM Player p WHERE " +
            "((p.name IS NOT NULL AND :name IS NULL) OR (p.name LIKE %:name%)) AND " +
            "((p.title IS NOT NULL AND :title IS NULL) OR (p.title LIKE %:title%)) AND " +
            "((p.race IS NOT NULL AND :race IS NULL) OR (p.race = :race)) AND " +
            "((p.profession IS NOT NULL AND :profession IS NULL) OR (p.profession = :profession)) AND " +
            "((p.birthday IS NOT NULL AND :after IS NULL) OR (p.birthday >= :after)) AND " +
            "((p.birthday IS NOT NULL AND :before IS NULL) OR (p.birthday <= :before)) AND " +
            "((p.banned IS NOT NULL AND :banned IS NULL) OR (p.banned = :banned)) AND " +
            "((p.experience IS NOT NULL AND :minExperience IS NULL) OR (p.experience >= :minExperience)) AND " +
            "((p.experience IS NOT NULL AND :maxExperience IS NULL) OR (p.experience <= :maxExperience)) AND " +
            "((p.level IS NOT NULL AND :minLevel IS NULL) OR (p.level >= :minLevel)) AND " +
            "((p.level IS NOT NULL AND :maxLevel IS NULL) OR (p.level <= :maxLevel))")
    List<Player> filterList(@Param("name") String name,
                            @Param("title") String title,
                            @Param("race") Race race,
                            @Param("profession") Profession profession,
                            @Param("after") Date after,
                            @Param("before") Date before,
                            @Param("banned") Boolean banned,
                            @Param("minExperience") Integer minExperience,
                            @Param("maxExperience") Integer maxExperience,
                            @Param("minLevel") Integer minLevel,
                            @Param("maxLevel") Integer maxLevel);



}
