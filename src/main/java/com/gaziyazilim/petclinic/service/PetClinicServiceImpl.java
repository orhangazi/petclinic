package com.gaziyazilim.petclinic.service;

import com.gaziyazilim.petclinic.dao.OwnerRepository;
import com.gaziyazilim.petclinic.dao.PetRepository;
import com.gaziyazilim.petclinic.dao.jpa.VetRepository;
import com.gaziyazilim.petclinic.exception.OwnerNotFoundException;
import com.gaziyazilim.petclinic.exception.VetNotFoundException;
import com.gaziyazilim.petclinic.model.Owner;
import com.gaziyazilim.petclinic.model.Vet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;

@Validated
@Service
@Transactional(rollbackFor = Exception.class)
public class PetClinicServiceImpl implements PetClinicService {

    private OwnerRepository ownerRepository;
    private PetRepository petRepository;
    private VetRepository vetRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    public void setOwnerRepository(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    @Autowired
    public void setPetRepository(PetRepository petRepository){
        this.petRepository = petRepository;
    }

    @Autowired
    public void setVetRepository(VetRepository setVetRepository){
        this.vetRepository = setVetRepository;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly=true)
    @Secured(value = {"ROLE_USER","ROLE_EDITOR"})
    public List<Owner> findOwners() {
        return ownerRepository.findAll();
    }

    @Override
    @Transactional(propagation=Propagation.SUPPORTS,readOnly = true)
    public List<Owner> findOwners(String lastName) {
        return ownerRepository.findByLastName(lastName);
    }

    @Override
    @Transactional(propagation=Propagation.SUPPORTS,readOnly = true)
    public Owner findOwner(Long id) throws OwnerNotFoundException {
        Owner owner = ownerRepository.findById(id);

        if (owner == null) throw new OwnerNotFoundException("Owner not found with id: " + id);

        return owner;
    }

    @CacheEvict(cacheNames = "allOwners",allEntries = true)
    @Override
    public void createOwner(@Valid Owner owner) {
        ownerRepository.create(owner);

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("o@g");
        simpleMailMessage.setTo("o@k");
        simpleMailMessage.setSubject("New owner created");
        simpleMailMessage.setText("New owner created with id: " + owner.getId());

        javaMailSender.send(simpleMailMessage);
    }

    @Override
    public void updateOwner(Owner owner) {
        ownerRepository.update(owner);
    }

    @Override
    public void deleteOwner(Long id)
    {
        petRepository.deleteByOwnerId(id);
        ownerRepository.delete(id);

        //if (true) throw new RuntimeException("testing rolling back");
    }

    @Override
    public List<Vet> findVets() {
        return vetRepository.findAll();
    }

    @Override
    public Vet findVet(Long id) throws VetNotFoundException {
        return vetRepository.findById(id).orElseThrow(()->{return new VetNotFoundException("Vet not found by id: "+id); });
    }
}
