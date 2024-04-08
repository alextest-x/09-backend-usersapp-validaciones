package com.curso.backend.usersapp.services;

import com.curso.backend.usersapp.models.entities.User;
import com.curso.backend.usersapp.models.entities.UserRequest;
import com.curso.backend.usersapp.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

/*
aqui implementamos los metodos abstractos de la inteface UserService
e implementamos los metodos con @overrider lo hace el ide en automatico
y anotamos que es un componente @Service
inyectamos el UserRepository para hacer uso del repository.find() etc
*/


/*
  Los DTO o objeto de trnsferencia de datos ( en ingles data transfer object
  son un tipo de objetos que sirven unicamente para trasportar datos en la red, remotas
  (por ejemplo servicios web) datos formateados, modificados, simplificados, optimizados
  con cierta informacion de entidades que necesitamos incluir puede ser de un solo entity
  o mas de una combinadas en un solo DTO.
*/


@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserRepository repository;


    //el findAll(); es del tipo iterable hay que hacer un cast hay que convertirlo a un lista
    // en lugar de regresar un clase entity tambien se puede recibir el List<User> en un DTO
    // para regresar solo lo que necesitemos no el objeto completo
    //por ejemplo direccion solo la calle y el numero y no con todos sus atributos
    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return (List<User>) repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findById(Long id) {
        return repository.findById(id);

    }

    //el metodo save regresa el metodo guardado
    @Override
    @Transactional
    public User save(User user) {
        return repository.save(user);
    }

    /*
    //primera forma del update x-update
    @Override
    public Optional<User> update(User user, Long id) {
          //pasando la locica que teniamos en el controlador al service

        Optional<User> o = this.findById(id);
        if (o.isPresent()){
            //obteniendo el user de la base de datos con userDb para guardar ya actualizado
            User userDb = o.orElseThrow();

            //le modificamos los datos que solo usamos del RequestBody user
            userDb.setUsername(user.getUsername());
            userDb.setEmail(user.getEmail());
            //el .of encuantra el usuario
            return Optional.of(this.save(userDb));

        }

        return Optional.empty();
    }
    */


    //de otra forma el update
    @Override
    @Transactional
    public Optional<User> update(UserRequest user, Long id) {

    //comentamos porque se agrega el UserRequest para que solo valide en username y el email
    //tambien se agrego en la inteface de UserService
    //public Optional<User> update(User user, Long id) {

        Optional<User> o = this.findById(id);
        User userOptional = null;
        if (o.isPresent()){
            //obteniendo el user de la base de datos con userDb para guardar ya actualizado
            User userDb = o.orElseThrow();

            //le modificamos los datos que solo usamos del RequestBody user
            userDb.setUsername(user.getUsername());
            userDb.setEmail(user.getEmail());
            userOptional = this.save(userDb);

        }

        // ofNullable pregunta si el userOptional es nulo
        // regresa un optional empty
        // sino regresa un optional of con el valor userOptional
        return Optional.ofNullable(userOptional);
    }


    @Override
    @Transactional
    public void remove(Long id) {
        repository.deleteById(id);
    }
}
