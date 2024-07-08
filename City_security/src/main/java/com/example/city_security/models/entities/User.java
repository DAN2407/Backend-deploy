package com.example.city_security.models.entities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String nombre;
    private String contrasena;
    private String correo;
    private String dui;
    private String telefono;

    //Llave de role-> se creara otro campo llamado role relacionado con tabla Role
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private Role role;


    //Llave de peticiones
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Peticiones> peticiones;

    //Llave de codigoQR
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<CodigoQR> codigoQRS;

    //Creando relacion de Hogares N:N
    @ManyToMany(fetch = FetchType.EAGER)
         @JoinTable(
                 //Nombre de la tabla intermedia
                 name = "UserXHogar",
                 joinColumns = @JoinColumn(name = "user_id"), // Llave foranea de la tabla actual
                 inverseJoinColumns = @JoinColumn(name = "hogar_id") // Llave foranea de la tabla roles
         )
    private List<Hogar> hogares;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(() -> role.getRole());
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return "";
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

}
