package com.example.crudspringboot.controllers;

import com.example.crudspringboot.entities.Personne;
import com.example.crudspringboot.repositorys.PersonneRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController  // pour specifier a spring que c'est un controller
@RequestMapping("/api/personnes")// pour specifier le type de requete que va gerer le controller
public class PersonneController {

   final PersonneRepository personneRepository;

    public PersonneController(PersonneRepository personneRepository) {
        this.personneRepository = personneRepository;
    }

    @GetMapping
    public ResponseEntity<List<Personne>> getAllPersonnes(){
        return new ResponseEntity<>(personneRepository.findAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Personne> createPersonne(@RequestBody Personne personne){ // pour créer une personne on envoie des information(nom,city etc)
        // et RequestBody permet de recuperer le corps de la requete de type personne (ca va faire la conversion de se qu'on va recuperer en Personne et le nom de la variable qui va contenir cette information s'appelle personne egalement
        Personne personneCreated = personneRepository.save(personne);
        return new ResponseEntity<>(personneCreated, HttpStatus.CREATED);
    }
    @GetMapping("/{id}") // entre accolado veut dire que c'est une valeur qui peut changer
    public ResponseEntity<Personne> getPersonneById(@PathVariable Long id){ // @PathVaraible est une variable

        Optional<Personne> personne = personneRepository.findById(id); // un optional parce que FindById retourne un optional

        return personne.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }
    @PutMapping("/{id}")
    public ResponseEntity<Personne> updatePersonne(@PathVariable Long id, @RequestBody Personne personneDetails){
        Optional<Personne> personne = personneRepository.findById(id); // un optional parce que FindById retourne un optional
        if (personne.isPresent()){
            Personne existingPersonne = personne.get();
            existingPersonne.setCity(personneDetails.getCity());
            existingPersonne.setPhoneNumber(personneDetails.getPhoneNumber());

            Personne personneUpdated = personneRepository.save(existingPersonne);

            return new ResponseEntity<>(personneUpdated, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePersonne(@PathVariable Long id){
        Optional<Personne> personne = personneRepository.findById(id);

        if (personne.isPresent()){
            personneRepository.delete(personne.get());
            return new ResponseEntity<>(HttpStatus.OK);

        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
