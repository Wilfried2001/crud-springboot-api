package com.example.crudspringboot.services;

import com.example.crudspringboot.entities.Personne;
import com.example.crudspringboot.repositorys.PersonneRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class PersonneServiceTest {
    @Mock
    private PersonneRepository personneRepository;

    @InjectMocks
    private PersonneService personneService;

    @Test
    void shouldReturnAllPersons() {
        Personne p1 = new Personne("John Doe", "New York", "123456789");
        Personne p2 = new Personne("Jane Doe", "Los Angeles", "987654321");

       when(personneRepository.findAll()).thenReturn(List.of(p1, p2));

       List<Personne> personnes = personneService.getAllPersonnes();
       assertThat(personnes).hasSize(2).containsExactly(p1, p2);

    }
    @Test
    void shouldReturnPersonById() {
        Personne p1 = new Personne("John Doe", "New York", "123456789");
        p1.setId(1L);

        when(personneRepository.findById(1L)).thenReturn(java.util.Optional.of(p1));

        Personne personne = personneService.getPersonneById(1L);
        assertThat(personne).isNotNull();
        assertThat(personne.getNom()).isEqualTo("John Doe");
    }
    @Test
    void shouldReturnPersonSaveOrUpdate(){
        Personne p1 = new Personne("John Doe", "New York", "123456789");
        p1.setId(1L);

        when(personneRepository.save(p1)).thenReturn(p1);

        Personne savedPersonne = personneService.createPersonne(p1);
        assertThat(savedPersonne).isNotNull();
        assertThat(savedPersonne.getNom()).isEqualTo("John Doe");
    }
    @Test
    void shouldDeletePerson(){
        personneService.deletePersonne(1L);
            // Ici, nous vérifions que la méthode deleteById a été appelée avec l'ID correct
       verify(personneRepository).deleteById(1L);
    }

}