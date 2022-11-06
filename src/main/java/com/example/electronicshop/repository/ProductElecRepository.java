package com.example.electronicshop.repository;


import com.example.electronicshop.models.enity.ProductElec;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductElecRepository extends MongoRepository<ProductElec, String> {
    Page<ProductElec> findAllByState(String state, Pageable pageable);
}
