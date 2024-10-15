package com.tumansh.Ecom_backend.services;

import com.tumansh.Ecom_backend.models.Product;
import com.tumansh.Ecom_backend.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepo repo;


    public List<Product> getProducts() {
        return repo.findAll();
    }

    public void addProductSingle(Product prod){
        repo.save(prod);
    }
    public void addProductList(List<Product> products){
            repo.saveAll(products);
    }
    public Product getProductbyId(int id){
        return  repo.findById(id).orElse(new Product());
    }

    public Product addProduct(Product product, MultipartFile imageFile) throws IOException {
        product.setImageName(imageFile.getOriginalFilename());
        product.setImageType(imageFile.getContentType());
        product.setImageData(imageFile.getBytes());
        return repo.save(product);
    }
    public void deleteProduct(int id){
        repo.deleteById(id);
    }

    public Product updateProduct(int id, Product product,MultipartFile imageFile) throws IOException {
        product.setImageData(imageFile.getBytes());
        product.setImageName(imageFile.getOriginalFilename());
        product.setImageType(imageFile.getContentType());
        return repo.save(product);
    }

    public List<Product> searchProduct(String search) {
        return repo.searchProducts(search);
    }

//    public RequestEntity<>
}
