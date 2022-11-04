package com.example.electronicshop.repository;

import com.example.electronicshop.models.enity.Brand;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BrandRepository extends MongoRepository<Brand, String> {
    List<Brand> findAllByState(String state);
    Optional<Brand> findBrandByIdAndState(String id, String state);
}
