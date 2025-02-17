package com.dairyproject.services;

import java.util.Set;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dairyproject.entities.ProductDetails;
import com.dairyproject.exceptions.ProductNotFoundException;
import com.dairyproject.repositories.ProductRepository;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository proRepo;

    @Override
    public Set<ProductDetails> getAllProductDetails() {
        return proRepo.findAllProductDetails();
    }

    @Override
    public ProductDetails getProductDetailsByName(String name) {
        return proRepo.findProductDetailsByNameOnly(name);
    }

    @Override
    public ProductDetails getProductDetailsByPid(Integer PID) {
        return proRepo.findProductDetailByPid(PID);
    }

    @Override
    public String insertNewProductDetails(ProductDetails productDetails) {
        if (proRepo.save(productDetails) != null) {
            return "Product Added...";
        }
        return "Failed to add product. Something went wrong !";
    }

    @Override
    public String updateProductDetails(ProductDetails productDetails) {
        if (proRepo.save(productDetails) != null) {
            return "Product Updated Successfully.";
        }
        return "Product update failed.";
    }

    @Override
    public String removeProductByPID(Integer pid) throws ProductNotFoundException {
        if (proRepo.deleteProductDetailsByPID(pid) == 1) {
            return "Product removed successfully...";
        }
        throw new ProductNotFoundException("Product not found !");
    }
}
