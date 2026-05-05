package com.example.crudspringboot.repositorys;

import com.example.crudspringboot.entities.Personne;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PersonneRepositoryTest {

    private final PersonneRepository personneRepository;

    @Autowired
    public PersonneRepositoryTest(PersonneRepository personneRepository) {
        this.personneRepository = personneRepository;
    }
    @Test
    void shouldGetAllPersons() {
        // Act
        List<Personne> personnes = personneRepository.findAll();

        // Assert
        assertEquals(4, personnes.size());
        assertEquals("John Doe", personnes.get(0).getNom());

    }
    @Test
    void shouldGetPersonById() {
        // Act
        Personne personne = personneRepository.findById(1L).orElse(null);

        // Assert
        assertEquals("John Doe", personne.getNom());
    }
    @Test
    void shouldSavePerson(){
        Personne personne = new Personne();
        personne.setNom("wizzy");
        personne.setCity("Douala");
        personne.setPhoneNumber("123456789");

        Personne savedPersonne = personneRepository.save(personne);

        assertNotNull(savedPersonne.getId());
        assertEquals("wizzy", savedPersonne.getNom());
        assertEquals("Douala", savedPersonne.getCity());
        assertEquals("123456789", savedPersonne.getPhoneNumber());
    }
    @Test
    void shouldUpdatePerson(){
        Personne personne = personneRepository.findById(1L).orElse(null);
        personne.setCity("Yaoundé");

        Personne updatedPersonne = personneRepository.save(personne);

        assertEquals("Yaoundé", updatedPersonne.getCity());
    }
    @Test
    void shouldDeletePerson(){
        personneRepository.deleteById(2L);

        Optional<Personne> deletedPersonne = personneRepository.findById(2L);
        assertFalse(deletedPersonne.isPresent());
    }

}