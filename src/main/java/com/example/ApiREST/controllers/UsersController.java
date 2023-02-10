package com.example.ApiREST.controllers;

import com.example.ApiREST.entities.User;
import com.example.ApiREST.repositories.UserRepository;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
public class UsersController {
    ArrayList<User> users = new ArrayList();
    private final UserRepository userRepository;
    public UsersController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/user")
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @PostMapping("/user")
    public ResponseEntity<String> createUser(@RequestBody User userDetails){
        Optional<User> user = userRepository.findByEmail(userDetails.getEmail());
        if(!user.isPresent()){
            userRepository.save(userDetails);
            return new ResponseEntity<>("El usuario ha sido añadido correctamente", HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>("El usuario ya existe", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id,@RequestBody User userDetails) {
        Optional<User> userOptional = userRepository.findById(id);
        if (!userOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        User user = userOptional.get();
        Class<?> userClass = user.getClass();
        Class<?> userDetailsClass = userDetails.getClass();

        for (Field field : userClass.getDeclaredFields()) {
            field.setAccessible(true);
            String fieldName = field.getName();
            try {
                Field userDetailsField = userDetailsClass.getDeclaredField(fieldName);
                userDetailsField.setAccessible(true);
                Object newValue = userDetailsField.get(userDetails);
                if (newValue != null && !newValue.equals(field.get(user))) {
                    field.set(user, newValue);
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                System.out.println("El error es " + e.getClass());
            }
        }
        User savedUser = userRepository.save(user);
        return ResponseEntity.ok(savedUser);
    }
    @DeleteMapping("/user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            return new ResponseEntity<>("El usuario no existe o ya ha sido borrado", HttpStatus.BAD_REQUEST);
        }
        userRepository.delete(user.get());
        return new ResponseEntity<>("El usuario ha sido borrado correctamente", HttpStatus.ACCEPTED);
    }
}
