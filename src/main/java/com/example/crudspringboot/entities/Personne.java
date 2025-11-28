package com.example.crudspringboot.entities;

import jakarta.persistence.*;

@Entity // permet de montrer que cette classe est une entite
@Table(name = "personne") // pour le nom de la table dans la BD
public class Personne {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String city;
    private String phoneNumber;

    public Personne (){
        super();
    }
    public Personne (String nom, String city, String phoneNumber){
        this.nom = nom;
        this.city = city;
        this.phoneNumber = phoneNumber;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
