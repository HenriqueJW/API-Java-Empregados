package com.henrique.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controlador {

    private Map<Integer, Empregado> empregados;
    private int proxId = 4;

    public Controlador() {
        empregados = new HashMap<Integer, Empregado>();

        Empregado c1 = new Empregado(1, "Empregado 1", 10);
        Empregado c2 = new Empregado(2, "Empregado 2", 20);
        Empregado c3 = new Empregado(3, "Empregado 3", 30);

        empregados.put(1, c1);
        empregados.put(2, c2);
        empregados.put(3, c3);
    }

    //http://localhost:8080/employees           retornar a lista de empregados employees
    @RequestMapping(value = "/employees", method = RequestMethod.GET)
    public ResponseEntity<List<Empregado>> listar() {
        return new ResponseEntity<List<Empregado>>(new ArrayList<Empregado>(empregados.values()), HttpStatus.OK);
    }

    //http://localhost:8080/employee/1           retorna o empregado com o id 1
    @RequestMapping(value = "/employee/{id}", method = RequestMethod.GET)
    public ResponseEntity<Empregado> buscar(@PathVariable("id") Integer id) {
        Empregado empregado = empregados.get(id);

        if (empregado == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Empregado>(empregado, HttpStatus.OK);
    }

    //http://localhost:8080/delete/1           deleta o empregado com id 1
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deletar(@PathVariable("id") int id) {
        Empregado empregado = empregados.remove(id);

        if (empregado == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Empregado>(empregado, HttpStatus.OK);

    }

    //http://localhost:8080/update/1           atualiza o empregado com id 1
    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT, consumes = "application/json")
    public ResponseEntity<Empregado> atualizar(@RequestBody Empregado empregado, @PathVariable("id") int id) {

        if (empregado == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        empregado.setId(id);
        empregados.put(id, empregado);
        return new ResponseEntity<Empregado>(empregados.get(id), HttpStatus.OK);
    }

    //http://localhost:8080/create           cria um novo empregado
    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<Empregado> criar(@RequestBody Empregado empregado) {
        
        if (empregado == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        empregado.setId(proxId);
        proxId++;

        empregados.put(empregado.getId(), empregado);

        return new ResponseEntity<Empregado>(empregados.get(empregado.getId()), HttpStatus.OK);
    }

}
