package com.example.fruitsapi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.fruitsapi.model.Fruit;

public interface FruitRepository extends JpaRepository<Fruit, Long> {

    // Ambil data yang belum dihapus (deleted_at = NULL)
    @Query("SELECT f FROM Fruit f WHERE f.deletedAt IS NULL")
    List<Fruit> findAllActive();

    @Query("SELECT f FROM Fruit f WHERE f.id = :id AND f.deletedAt IS NULL")
    Optional<Fruit> findByIdActive(Long id);
}
