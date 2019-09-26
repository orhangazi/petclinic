package com.gaziyazilim.petclinic.web;

import com.gaziyazilim.petclinic.exception.InternalServerException;
import com.gaziyazilim.petclinic.exception.OwnerNotFoundException;
import com.gaziyazilim.petclinic.model.Owner;
import com.gaziyazilim.petclinic.service.PetClinicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.ConstraintViolationException;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/rest")
public class PetClinicRestController {
    @Autowired
    private PetClinicService petClinicService;

    @RequestMapping(method = RequestMethod.POST, value="/owner")
    public ResponseEntity<URI> createOwner(@RequestBody Owner owner){
        try {
            petClinicService.createOwner(owner);
            Long id = owner.getId();
            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
            return ResponseEntity.created(location).build();
        }
        catch(ConstraintViolationException ex){
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).build();
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Cacheable("allOwners")
    @RequestMapping(method = RequestMethod.GET,value = "/owners")
    public ResponseEntity<List<Owner>> getOwners(){

        try {
            System.out.println("getOwners called. Test for method level caching");
            List<Owner> owners = petClinicService.findOwners();
            return ResponseEntity.ok(owners);
        }
        catch (Exception ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @RequestMapping(method = RequestMethod.GET,value = "/owner")
    public ResponseEntity<List<Owner>> getOwner(@RequestParam("ln") String lastName){
        try {
            List<Owner> owners = petClinicService.findOwners(lastName);
            return ResponseEntity.ok(owners);
        }
        catch (Exception ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @RequestMapping(method = RequestMethod.GET,value = "/owner/{id}")
    public ResponseEntity<Owner> getOwner(@PathVariable("id") Long id){
        try {
            Owner owner = petClinicService.findOwner(id);
            return ResponseEntity.ok(owner);
        }
        catch (OwnerNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @RequestMapping(method=RequestMethod.PUT,value="/owner/{id}")
    public ResponseEntity<?> updateOwner(@PathVariable Long id, @RequestBody Owner owner2){

        try {
            petClinicService.updateOwner(owner2);
            return ResponseEntity.ok().build();
        }
        catch (OwnerNotFoundException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @RequestMapping(method=RequestMethod.DELETE,value = "/owner/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> deleteOwner(@PathVariable Long id){
        try {
            petClinicService.findOwner(id);
            petClinicService.deleteOwner(id);
            return ResponseEntity.ok().build();
        }
        catch(OwnerNotFoundException e){
            throw e;
        }
        catch(Exception e){
            throw new InternalServerException(e);
        }
    }

    @RequestMapping(method = RequestMethod.GET,value = "/owner/{id}", produces = "application/json")
    public ResponseEntity<?> getOwnerAsHateoasResoursce(@PathVariable("id") Long id){
        try {
            Owner owner = petClinicService.findOwner(id);
            Link self = ControllerLinkBuilder.linkTo(PetClinicRestController.class).slash("/owner/"+id).withSelfRel();
            Link create = ControllerLinkBuilder.linkTo(PetClinicRestController.class).slash("/owner").withRel("create");
            Link update = ControllerLinkBuilder.linkTo(PetClinicRestController.class).slash("/owner/"+id).withRel("update");
            Link delete = ControllerLinkBuilder.linkTo(PetClinicRestController.class).slash("/owner/"+id).withRel("delete");

            Resource<Owner> resource = new Resource<Owner>(owner,self,create,update,delete);

            return ResponseEntity.ok(resource);
        }
        catch (OwnerNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}