package com.gaziyazilim.petclinic.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

//jpa hibernate olacağı için entity olarak işaretliyoruz
@Entity
@Table(name = "t_vet")
public class Vet extends BaseEntity{
    @NotEmpty
    @Column(name = "first_name")
    private String firstName;

    @NotEmpty
    @Column(name = "last_name")
    private String lastName;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
