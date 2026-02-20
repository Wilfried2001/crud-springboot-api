package com.example.crudspringboot.services;

import com.example.crudspringboot.entities.User;
import com.example.crudspringboot.repositorys.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

  //  public CustomUserDetailsService(UserRepository userRepository) {
      //  this.userRepository = userRepository;
    //}

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { // spring security appelle cette methode automatiquement pour verifier si l'utilisateur existe et recupérer ses informations de sécurité ( mot de passe + role)
        // Recherche de l'utilisateur en base de données à partir du username
        User user = userRepository.findByUsername(username);
        if(user == null) {
            throw new UsernameNotFoundException("User not found with username :" + username);
        }
        // Conversion de l'utilisateur (entité User) en UserDetails
        // Format compréhensible par Spring Security
        return new org.springframework.security.core.userdetails.User(
                // Nom d'utilisateur utilisé pour l'authentification
                user.getUsername(),
                // Mot de passe (déjà chiffré en base, ex: BCrypt)
                user.getPassword(),
                Collections.singletonList(
                        new SimpleGrantedAuthority(user.getRole()))
        );
    }
    // en gros on transforme le User en UserDetails(spring security)

    // cette méthode cherche l'utilisateur en BD
    // Refuse la connexion s'il n'existe pas
    // transforme l'utilisateur en format comprehensible par spring security
    // Retourne : -username , mot de passe , role(s)
}
