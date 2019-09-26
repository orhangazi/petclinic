package com.gaziyazilim.petclinic.dao.jpa;

import com.gaziyazilim.petclinic.model.Vet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VetRepository extends JpaRepository<Vet,Long> {

}