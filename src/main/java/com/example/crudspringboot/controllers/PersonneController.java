package com.example.crudspringboot.controllers;

import com.example.crudspringboot.entities.Personne;
import com.example.crudspringboot.repositorys.PersonneRepository;
import com.example.crudspringboot.services.PersonneService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController  // pour specifier a spring que c'est un controller
@RequestMapping("/api/personnes")// pour specifier le type de requete que va gerer le controller
public class PersonneController {

   final PersonneService personneService;

    public PersonneController(PersonneService personneService) {
        this.personneService = personneService;
    }

    @GetMapping
    public ResponseEntity<List<Personne>> getAllPersonnes(){
        return new ResponseEntity<>(personneService.getAllPersonnes(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Personne> createPersonne(@RequestBody Personne personne){ // pour créer une personne on envoie des information(nom,city etc)
        // et RequestBody permet de recuperer le corps de la requete de type personne (ca va faire la conversion de se qu'on va recuperer en Personne et le nom de la variable qui va contenir cette information s'appelle personne egalement
        Personne personneCreated = personneService.createPersonne(personne);
        return new ResponseEntity<>(personneCreated, HttpStatus.CREATED);
    }
    @GetMapping("/{id}") // entre accolado veut dire que c'est une valeur qui peut changer
    public ResponseEntity<Personne> getPersonneById(@PathVariable Long id){ // @PathVaraible sert à récupérer une valeur qui se trouve dans l’URL et la mettre dans une variable Java ici la variable est id et est de type Long
        Personne personne = personneService.getPersonneById(id);
        if (personne != null){
            return new ResponseEntity<>(personne, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }
    @PutMapping("/{id}")
    public ResponseEntity<Personne> updatePersonne(@PathVariable Long id, @RequestBody Personne personneDetails){
        Personne updatedPersonne = personneService.updatePersonne(id, personneDetails);
        if (updatedPersonne != null){
            return new ResponseEntity<>(updatedPersonne, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePersonne(@PathVariable Long id){
        Personne personne = personneService.getPersonneById(id);
        if (personne != null){
            personneService.deletePersonne(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
