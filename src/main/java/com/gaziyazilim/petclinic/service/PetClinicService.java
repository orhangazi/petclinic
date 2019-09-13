package com.gaziyazilim.petclinic.service;

import com.gaziyazilim.petclinic.exception.OwnerNotFoundException;
import com.gaziyazilim.petclinic.model.Owner;

import java.util.List;

public interface PetClinicService {
    List<Owner> findOwners();
    List<Owner> findOwners(String lastName);
    Owner findOwner(Long id) throws OwnerNotFoundException;
    void createOwner(Owner owner);
    void updateOwner(Owner owner);
    void deleteOwner(Long id);
}
