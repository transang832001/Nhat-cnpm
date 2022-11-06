package com.example.electronicshop.service;

import com.example.electronicshop.communication.request.ProductElecRequest;
import com.example.electronicshop.communication.response.ProductElecResponse;
import com.example.electronicshop.config.CloudinaryConfig;
import com.example.electronicshop.map.ProductElecMap;
import com.example.electronicshop.models.ResponseObject;
import com.example.electronicshop.models.enity.Product;
import com.example.electronicshop.models.enity.ProductElec;
import com.example.electronicshop.models.enity.ProductImage;
import com.example.electronicshop.notification.AppException;
import com.example.electronicshop.repository.BrandRepository;
import com.example.electronicshop.repository.CategoryRepository;
import com.example.electronicshop.repository.ProductElecRepository;
import com.mongodb.MongoWriteException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class ProductElecService {
    private final ProductElecRepository productElecRepository;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;
    private final ProductElecMap productElecMap;
    private final CloudinaryConfig cloudinary;

    public ResponseEntity<?> addProduct(ProductElecRequest req) {
        List<ProductImage> images = new ArrayList<>();
        if (req != null) {
            ProductElec product = productElecMap.toProduct(req);
            try {
                processUploadImage(req.getImages(), product);
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

    public List<ProductImage> processUploadImage (List<MultipartFile> images, ProductElec product) {
        if (images == null || images.isEmpty()) throw new AppException(HttpStatus.BAD_REQUEST.value(), "images is empty");
        for (int i = 0; i < images.size(); i++) {
            try {
                String url = cloudinary.uploadImage(images.get(i), null);
                if (i == 0) product.getImages().add(new ProductImage(UUID.randomUUID().toString(), url));
                else product.getImages().add(new ProductImage(UUID.randomUUID().toString(), url));
            } catch (IOException e) {
                log.error(e.getMessage());
                throw new AppException(HttpStatus.EXPECTATION_FAILED.value(), "Error when upload images");
            }
            productElecRepository.save(product);
        }
        return product.getImages();
    }

}
