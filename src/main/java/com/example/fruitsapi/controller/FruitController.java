package com.example.fruitsapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.fruitsapi.model.Fruit;
import com.example.fruitsapi.service.FruitService;

@RestController
@RequestMapping("/api/fruits")
public class FruitController {

    @Autowired
    private FruitService fruitService;

    @GetMapping
    public List<Fruit> getAllFruits() {
        return fruitService.getAllFruits();
    }

    @GetMapping("/{id}")
    public Fruit getFruitById(@PathVariable Long id) {
        return fruitService.getFruitById(id);
    }

    @PostMapping
    public Fruit createFruit(@RequestBody Fruit fruit) {
        return fruitService.saveFruit(fruit);
    }

    @PutMapping("/{id}")
    public Fruit updateFruit(@PathVariable Long id, @RequestBody Fruit fruit) {
        fruit.setId(id);
        return fruitService.saveFruit(fruit);
    }

    @DeleteMapping("/{id}")
    public String softDeleteFruit(@PathVariable Long id) {
        fruitService.softDeleteFruit(id);
        return "Fruit has been soft deleted successfully!";
    }
}
