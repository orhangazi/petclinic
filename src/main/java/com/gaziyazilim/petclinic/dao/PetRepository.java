package com.gaziyazilim.petclinic.dao;

import com.gaziyazilim.petclinic.model.Pet;

import java.util.List;

public interface PetRepository {
    Pet findById(Long id);
    List<Pet> findByOwnerId(Long ownerId);
    void create(Pet pet);
    Pet update(Pet pet);
    void delete(Long id);
    void deleteByOwnerId(Long ownerId);
}
