package com.gaziyazilim.petclinic.dao.mem;

import com.gaziyazilim.petclinic.dao.OwnerRepository;
import com.gaziyazilim.petclinic.model.Owner;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class OwnerRepositoryInMemoryImpl implements OwnerRepository {

    private Map<Long,Owner> ownersMap = new HashMap<>();

    public OwnerRepositoryInMemoryImpl(){
        Owner owner1 = new Owner();
        owner1.setId(1L);
        owner1.setFirstName("Fadime");
        owner1.setLastName("Kılıç");

        Owner owner2 = new Owner();
        owner2.setId(2L);
        owner2.setFirstName("Osman");
        owner2.setLastName("Kılıç");

        Owner owner3 = new Owner();
        owner3.setId(3L);
        owner3.setFirstName("Oğuzhan");
        owner3.setLastName("Kılıç");

        Owner owner4 = new Owner();
        owner4.setId(4L);
        owner4.setFirstName("Bilal");
        owner4.setLastName("Uslu");

        Owner owner5 = new Owner();
        owner5.setId(5L);
        owner5.setFirstName("Hümeyra");
        owner5.setLastName("Kılıç");

        Owner owner6 = new Owner();
        owner6.setId(6L);
        owner6.setFirstName("Meral");
        owner6.setLastName("Doğan");

        Owner owner7 = new Owner();
        owner7.setId(7L);
        owner7.setFirstName("Orhan Gazi");
        owner7.setLastName("Kılıç");

        ownersMap.put(owner1.getId(),owner1);
        ownersMap.put(owner2.getId(),owner2);
        ownersMap.put(owner3.getId(),owner3);
        ownersMap.put(owner4.getId(),owner4);
        ownersMap.put(owner5.getId(),owner5);
        ownersMap.put(owner6.getId(),owner6);
        ownersMap.put(owner7.getId(),owner7);
    }

    @Override
    public List<Owner> findAll() {
        return new ArrayList<>(ownersMap.values());
    }

    @Override
    public Owner findById(Long id) {
        return ownersMap.get(id);
    }

    @Override
    public List<Owner> findByLastName(String lastName) {
        return ownersMap.values().stream().filter(owner -> owner.getLastName().equals(lastName)).collect(Collectors.toList());
    }

    @Override
    public void create(Owner owner) {
        owner.setId(new Date().getTime());
        ownersMap.put(owner.getId(),owner);
    }

    @Override
    public Owner update(Owner owner) {
        ownersMap.replace(owner.getId(),owner);
        return owner;
    }

    @Override
    public void delete(Long id) {
        ownersMap.remove(id);
    }
}
