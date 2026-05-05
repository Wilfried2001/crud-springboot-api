package com.example.crudspringboot.services;

import com.example.crudspringboot.entities.Personne;
import com.example.crudspringboot.repositorys.PersonneRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonneService {
    private final PersonneRepository personneRepository;

    public PersonneService(PersonneRepository personneRepository) {
        this.personneRepository = personneRepository;
     }
     public List<Personne> getAllPersonnes() {
         return personneRepository.findAll();
     }
        public Personne getPersonneById(Long id) {
            return personneRepository.findById(id).orElse(null);
        }
        public Personne createPersonne(Personne personne) {
            return personneRepository.save(personne);
        }
        public Personne updatePersonne(Long id, Personne personneDetails) {
            return personneRepository.findById(id).map(personne -> {
                personne.setCity(personneDetails.getCity());
                personne.setPhoneNumber(personneDetails.getPhoneNumber());
                return personneRepository.save(personne);
            }).orElse(null);
        }
        public void deletePersonne(Long id) {
            personneRepository.deleteById(id);
        }
}
