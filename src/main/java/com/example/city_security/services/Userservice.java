package com.example.city_security.services;

import com.example.city_security.models.dtos.*;
import com.example.city_security.models.entities.Token;
import com.example.city_security.models.entities.User;
import jakarta.validation.constraints.NotNull;

import java.util.List;


public interface Userservice {
    void registerUser(CreateUserDTO userRegisterDTO);
    List<User> findAll();
    User findUserByCorreo(String correo);
    void updateUserRole(UpdateRoleDTO updateRoleDTO);
    List<User> findByRole(FindUserByRole role);
    void deleteUser(FindUserDTO correo);
    User findByIdentifier(String identifier);
    Token registerToken(User user) throws Exception;
    Boolean isTokenValid(User user, String token);
    void cleanTokens(User user) throws Exception;
    //Agregando HogarXUsuario
    void AddHogarToUser(User info, @NotNull List<String> hogar);
}
