package com.example.electronicshop.controller;

import com.example.electronicshop.communication.request.ProductElecRequest;
import com.example.electronicshop.service.ProductElecService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class ProductElecController {
    private final ProductElecService productElecService;
    @PostMapping("/product/add")
//    public ResponseEntity<?> addProduct(@Valid @RequestBody ProductElecRequest req) {
//        return productElecService.addProduct(req);
//    }
    public ResponseEntity<?> addProduct(@Valid @ModelAttribute ProductElecRequest req) {
        return productElecService.addProduct(req);
    }
}
