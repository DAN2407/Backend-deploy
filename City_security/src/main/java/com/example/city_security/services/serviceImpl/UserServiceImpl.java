package com.example.city_security.services.serviceImpl;

import com.example.city_security.Repository.HogarRepository;
import com.example.city_security.Repository.RoleRepository;
import com.example.city_security.Repository.TokenRepository;
import com.example.city_security.Repository.UserRepository;
import com.example.city_security.models.dtos.*;
import com.example.city_security.models.entities.Hogar;
import com.example.city_security.models.entities.Role;
import com.example.city_security.models.entities.Token;
import com.example.city_security.models.entities.User;
import com.example.city_security.services.Userservice;
import com.example.city_security.utils.JWTTools;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements Userservice {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private HogarRepository hogarRepository;
    @Autowired
    private JWTTools jwtTools;
    @Autowired
    private TokenRepository tokenRepository;

    @Override
    public void registerUser(CreateUserDTO userRegisterDTO) {
        synchronized(this) { // Añadir sincronización para evitar problemas de concurrencia
            User existingUser = userRepository.findByCorreo(userRegisterDTO.getCorreo());
            if(existingUser != null){
                throw new EntityNotFoundException("User already exists with correo: " + userRegisterDTO.getCorreo());
            }
            User user = new User();
            user.setNombre(userRegisterDTO.getNombre());
            user.setContrasena(null);
            user.setCorreo(userRegisterDTO.getCorreo());
            user.setDui(null);
            user.setTelefono(null);
            user.setRole(roleRepository.findByRole("USER"));
            userRepository.save(user);
        }
    }


    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findUserByCorreo(String correo) {
        return userRepository.findByCorreo(correo);
    }

    @Override
    public void updateUserRole(UpdateRoleDTO updateRoleDTO) {
        User user = userRepository.findByCorreo(updateRoleDTO.getCorreo());
        if (user == null) {
            throw new EntityNotFoundException("User not found with correo: " + updateRoleDTO.getCorreo());
        }
        Role newRole = roleRepository.findByRole(updateRoleDTO.getNewRole());
        if (newRole == null) {
            throw new EntityNotFoundException("Role not found: " + updateRoleDTO.getNewRole());
        }
        user.setRole(newRole);
        userRepository.save(user);
    }

    @Override
    public List<User> findByRole(FindUserByRole roleDTO) {
        Role role = roleRepository.findByRole(roleDTO.getRole());
        if (role == null) {
            throw new EntityNotFoundException("Role not found: " + roleDTO.getRole());
        }
        return userRepository.findByRole(role);
    }

    @Override
    public void deleteUser(FindUserDTO info) {
        User user = userRepository.findByCorreo(info.getCorreo());
        if (user == null) {
            throw new EntityNotFoundException("User not found with correo: " + info.getCorreo());
        }
        userRepository.delete(user);
    }

    @Override
    public User findByIdentifier(String identifier) {
        return userRepository.findByNombreOrCorreo(identifier, identifier).orElse(null);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public Token registerToken(User user) throws Exception {
        cleanTokens(user);

        String tokenString = jwtTools.generateToken(user);
        Token token = new Token(tokenString, user);

        tokenRepository.save(token);

        return token;
    }

    @Override
    public Boolean isTokenValid(User user, String token) {
        try {
            cleanTokens(user);
            List<Token> tokens = tokenRepository.findByUserAndActive(user, true);

            tokens.stream()
                    .filter(tk -> tk.getContent().equals(token))
                    .findAny()
                    .orElseThrow(() -> new Exception());

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void cleanTokens(User user) throws Exception {
        List<Token> tokens = tokenRepository.findByUserAndActive(user, true);

        tokens.forEach(token -> {
            if(!jwtTools.verifyToken(token.getContent())) {
                token.setActive(false);
                tokenRepository.save(token);
            }
        });
    }


    @Override
    public void AddHogarToUser(User info, List<String> hogar) {
        List<Hogar> hogares = new ArrayList<>();
        hogar.forEach(h -> {
            Hogar hogar1 = hogarRepository.findByDireccion(h);
            if(hogar1 != null) {
                hogares.add(hogar1); //Agregando a la lista de hogares
            }
        });
        info.setHogares(hogares);
        userRepository.save(info);
    }

}
