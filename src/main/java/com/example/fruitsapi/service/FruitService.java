package com.example.fruitsapi.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.fruitsapi.model.Fruit;
import com.example.fruitsapi.repository.FruitRepository;

@Service
public class FruitService {

    @Autowired
    private FruitRepository fruitRepository;

    // Get all active fruits
    public List<Fruit> getAllFruits() {
        return fruitRepository.findAllActive();
    }

    // Get fruit by ID (active only)
    public Fruit getFruitById(Long id) {
        return fruitRepository.findByIdActive(id)
                .orElseThrow(() -> new RuntimeException("Fruit not found or has been deleted"));
    }

    // Save or update fruit
    public Fruit saveFruit(Fruit fruit) {
        return fruitRepository.save(fruit);
    }

    // Soft delete fruit
    public void softDeleteFruit(Long id) {
        Fruit fruit = fruitRepository.findByIdActive(id)
                .orElseThrow(() -> new RuntimeException("Fruit not found or has been deleted"));

        fruit.setDeletedAt(LocalDateTime.now());
        fruitRepository.save(fruit);
    }
}
