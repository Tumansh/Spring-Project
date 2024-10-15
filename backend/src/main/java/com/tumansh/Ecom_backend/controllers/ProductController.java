package com.tumansh.Ecom_backend.controllers;

import com.tumansh.Ecom_backend.models.Product;
import com.tumansh.Ecom_backend.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
//@CrossOrigin
@RequestMapping("/api")
public class ProductController {
    @Autowired
    ProductService service;
    @RequestMapping("/")
    public String greet(){
        return "Hi Er.Tumansh";
    }
    @GetMapping("/products")
    public List<Product> getProducts(){

        return  service.getProducts();
    }
    @PostMapping("/products")
    public void addProduct( @RequestBody List<Product>products){
        service.addProductList(products);
    }

    @GetMapping("/product/{id}")
    public Product getProduct(@PathVariable int id){
        return service.getProductbyId(id);
    }

    @PostMapping("/product")
    public ResponseEntity<?>addProduct(@RequestPart Product product,
                                       @RequestPart MultipartFile imageFile){

        try {
            Product prod = service.addProduct(product, imageFile);
            return new ResponseEntity<>(prod, HttpStatus.CREATED);
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/product/{id}/image")
    public byte[] getImagebyProductId(@PathVariable("id") int id){
        Product prod=service.getProductbyId(id);
        return prod.getImageData();
    }
    @DeleteMapping("/product/{prodId}")
    public void deleteProduct(@PathVariable int prodId){
        service.deleteProduct(prodId);
    }
    @PutMapping("/product/{id}")
    public Product updateProduct(@PathVariable("id")int id,@RequestPart Product product,
                                 @RequestPart MultipartFile imageFile) throws IOException {
        if (product != null && id != 0) {
            String category = product.getCategory();
            System.out.println(category);
            if (category != null && !category.isEmpty()) {
                category = category.substring(0, 1).toUpperCase() + category.substring(1);
                product.setCategory(category);
            }
            System.out.println(category);

            return service.updateProduct(id, product, imageFile);
        } else return new Product();
    }
        @GetMapping("/products/search")
        public ResponseEntity<List<Product>> searchProduct(@RequestParam String search){
        List<Product> products=service.searchProduct(search);
        return new ResponseEntity<>(products,HttpStatus.OK);
    }
}
