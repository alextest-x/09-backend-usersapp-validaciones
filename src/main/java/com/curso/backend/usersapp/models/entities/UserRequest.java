package com.curso.backend.usersapp.models.entities;


/*

  clase que solo se utiliza para actualizar al usaurio
  solo actualiza el username y el email
 */


//import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class UserRequest {


    /*
      Aqui se pobla el json que viene en el cuerpo del request
      en vez que se pueble el entity

     */

    @NotBlank
    @Size(min = 4, max = 8)
    private String username;

    @NotEmpty
    @Email
    private String email;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
