package com.services.impl;

import com.dtos.LoginRequestDTO;
import com.dtos.LoginResponseDTO;
import com.dtos.UserDTO;
import com.entities.Student;
import com.entities.User;
import com.repositories.StudentRepository;
import com.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

/**
 * Service d'authentification
 */
@Service
public class AuthServiceImpl {

    private final UserRepository userRepository;
    private final StudentRepository studentRepository;

    @Autowired
    public AuthServiceImpl(UserRepository userRepository,
                           StudentRepository studentRepository) {
        this.userRepository = userRepository;
        this.studentRepository = studentRepository;
    }

    /**
     * Authentification d'un utilisateur
     */
    public LoginResponseDTO login(LoginRequestDTO loginRequest) {
        // Chercher l'utilisateur par email
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("Email ou mot de passe incorrect"));

        // Vérifier le mot de passe (en clair)
        if (!user.getPassword().equals(loginRequest.getPassword())) {
            throw new RuntimeException("Email ou mot de passe incorrect");
        }

        // Créer la réponse
        LoginResponseDTO response = new LoginResponseDTO();
        response.setUserId(user.getId());
        response.setEmail(user.getEmail());



        return response;
    }

    /**
     * Enregistre un nouvel utilisateur
     */
    @Transactional
    public UserDTO register(UserDTO userDTO) {
        // Vérifier si l'email existe déjà
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new RuntimeException("Cet email est déjà utilisé");
        }

        // Créer le nouvel utilisateur
        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setId(userDTO.getId());
        // Sauvegarder l'utilisateur
        User savedUser = userRepository.save(user);

        // Convertir en DTO pour le retour
        UserDTO savedDTO = new UserDTO();
        savedDTO.setId(savedUser.getId());
        savedDTO.setEmail(savedUser.getEmail());

        return savedDTO;
    }

    /**
     * Vérifie si un email existe déjà
     */
    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }

    /**
     * Récupère un utilisateur par son ID
     */
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
    }
}