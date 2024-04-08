package com.curso.backend.usersapp.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

//se anota con entity para indicar que es una clase de persistencia
@Entity
@Table(name="users")
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
/*
    //@Column(unique = true) campo unico no se puede repetir
    //este es cuando se crea la tabla de forma automatica con el ddl-auto
    //sino hay que ponerlo en el esquema de la base de datos mysql

    //reglas de validacion
    //para agregar la anotacio  @NotEmpty hay que agregar una dependencia en el pom.xml
    //I/O validation de java hibernate
    //@NotEmpty es para string valida solo que no sea vacio
    //@NotBlank es para string valida que no sea vacio y en espacios que no esten en blanco
    //@NotNull es para un no string o un objeto


*/

    @NotBlank
    @Size(min = 4, max = 8)
    @Column(unique = true)
    private String username;

    //@NotEmpty //lo cambiamos para que valide que no tenga espacios vacios y que no esten en blanco
    @NotBlank
    //@Size(min = 4, max = 8)
    private String password;


    @NotEmpty
    @Email
    @Column(unique = true)
    private String email;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
