package com.curso.backend.usersapp.controllers;

import com.curso.backend.usersapp.models.entities.User;
import com.curso.backend.usersapp.models.entities.UserRequest;
import com.curso.backend.usersapp.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@CrossOrigin(originPatterns = "*")
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService service;

    //lo mapeamos si nada
    @GetMapping
    public List<User> list(){
        return  service.findAll();
    }


    /*
       cuando ponemos una ruta /{id}
       hay que poner el @PathVariable

       en caso de venir con otro nombre
       @GetMapping(name="/{id}")
       User show(@PathVariable(name="id") Long idUser)

     */


   /*

    @GetMapping(name="/{id}")
    public  User show(@PathVariable Long id){
        //return service.findById(id); //error
        return service.findById(id).orElseThrow();
    }
  */


    // ResponseEntity para dar una repuesta de un ok o no encontrado

    @GetMapping("/{id}")
    public ResponseEntity<?> show(@PathVariable Long id){

        // regresa un optional
        Optional<User> userOptional = service.findById(id);
        if (userOptional.isPresent()) {
            return ResponseEntity.ok(userOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    /*
    //el user viene del request
    //anotamos @RequestBody que viene con un json
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public  User create(@RequestBody User user) {
        return service.save(user);
    }
    */








    // se agrega la validacion en el metodo create
    // en el objeto se agrega como un interceptor programacion orientada a aspectos
    // valida antes de  entrar al metodo cada campo y ponemos la anotacion @Valid
    // valida el objeto usuario user que viene en el requestBody del json que enviamos desde un postman o angular
    // y poner otro argumento como segundo argumento en el metodo y tiene que ir junto  del objeto que se va a validar
    // poner BindingResult result



    //de otra forma el post
    //.body se guarda el objeto usuario de la respuesta  que se crea en base de datos
    @PostMapping
    public  ResponseEntity<?> create(@Valid @RequestBody User user, BindingResult result) {

        //validar campo tiene errores
        if(result.hasErrors()){
            return validation(result);
        }

        //en una sola linea
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(user));

        // de otra forma
        //User userDb= service.save(user);
        //return ResponseEntity.status(HttpStatus.CREATED).body(service.save(userDb));

    }


    /*
    //primera forma del update x-update
    //implmenatdo en el controlado
    //se comenta porque la logica se puso en el service UserServiceImpl
    //pero solo el if y se mantiene el metodo PutMapping 1.1
    //hay que poner @RequestBody, @PathVariable
    //update(@RequestBody User user, @PathVariable Long id)
    //donde el user son los datos del json
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody User user, @PathVariable Long id){

        //validar en la bd que exista para poder modificarlo
         Optional<User> o = service.findById(id);
         if (o.isPresent()){
             //obteniendo el user de la base de datos con userDb para guardar ya actualizado
             User userDb = o.orElseThrow(); //orElseThrow() en caso de ser null

             //le modificamos los datos que solo usamos del RequestBody user
             userDb.setUsername(user.getUsername());
             userDb.setEmail(user.getEmail());
             return ResponseEntity.status(HttpStatus.CREATED).body(service.save(userDb));

         }
         //sino encuentra regresa un not found
         // con build genera la respuesta en el ResponseEntity
        return ResponseEntity.notFound().build();
    }
    */


    //PutMapping 1.1
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody UserRequest user, BindingResult result, @PathVariable Long id){

        //se comenta porque hay error en el User porque pusimos el UserRequest para que solo actualize el
        //username y email entonces solo agregamos UserRequest en el metodo update
        //public ResponseEntity<?> update(@Valid @RequestBody User user, BindingResult result, @PathVariable Long id){


        //validar campo tiene errores
        if(result.hasErrors()){
            return validation(result);
        }

        Optional <User> o = service.update(user, id);
        if (o.isPresent()){
            //.body(o. pasamos el objeto que viene en el update
            return ResponseEntity.status(HttpStatus.CREATED).body(o.orElseThrow());
        }
       return ResponseEntity.notFound().build();
    }


    //eliminamos por id
    @DeleteMapping("/{id}")
    public ResponseEntity<?> remove(@PathVariable Long id){

        Optional<User> o = service.findById(id);

        //validar si se encuentra el usaurio en bd
        if (o.isPresent()){
            service.remove(id);
            return ResponseEntity.noContent().build();
        }
          return ResponseEntity.notFound().build();
    }

    //metodo helper
    private ResponseEntity<?> validation(BindingResult result) {
        //el mapa se convierte en un json
        Map<String, String> errors = new HashMap<>();

        //como es una lista usamos el forEach()
        result.getFieldErrors().forEach(err -> {
            errors.put(err.getField(), "El campo " + err.getField() + " " +
            err.getDefaultMessage());
        });

        return ResponseEntity.badRequest().body(errors);
    }

}
