package com.backend.girnartour.repository;

import com.backend.girnartour.models.IdSequence;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IdSequenceRepository extends JpaRepository<IdSequence,Integer> {

    IdSequence findByEntityNameIgnoreCase(String name);
}
