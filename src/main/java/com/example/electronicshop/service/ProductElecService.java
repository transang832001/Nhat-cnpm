package com.example.electronicshop.service;

import com.example.electronicshop.communication.request.ProductElecRequest;
import com.example.electronicshop.communication.response.ProductElecResponse;
import com.example.electronicshop.map.ProductElecMap;
import com.example.electronicshop.models.ResponseObject;
import com.example.electronicshop.models.enity.Product;
import com.example.electronicshop.models.enity.ProductElec;
import com.example.electronicshop.notification.AppException;
import com.example.electronicshop.repository.BrandRepository;
import com.example.electronicshop.repository.CategoryRepository;
import com.example.electronicshop.repository.ProductElecRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class ProductElecService {
    private final ProductElecRepository productElecRepository;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;
    private final ProductElecMap productElecMap;
    public ResponseEntity<?> addProduct(ProductElecRequest req) {
        if (req != null) {
            ProductElec product = productElecMap.toProduct(req);
            try {
                productElecRepository.save(product);
            } catch (Exception e) {
                throw new AppException(HttpStatus.CONFLICT.value(), "Product name already exists");
            }
            ProductElecResponse res = productElecMap.toProductRes(product);
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    new ResponseObject("true", "Add product successfully ", res)
            );
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ResponseObject("false", "Request is null", "")
        );
    }
}
