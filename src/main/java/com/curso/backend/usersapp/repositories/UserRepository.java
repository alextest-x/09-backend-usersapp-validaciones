package com.curso.backend.usersapp.repositories;

import com.curso.backend.usersapp.models.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

    //ORM es un mapeo de objeto relacional como hibernate, jpa
    //enviamos el sql a objetos

}
