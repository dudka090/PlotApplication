package pl.myapplication.plot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.myapplication.plot.model.Product;
import pl.myapplication.plot.service.ProductService;


import java.util.List;

@RestController
@RequestMapping("/productList")
public class ProductController {
    ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<String> findAll() {
        return this.productService.findAll();
    }

    @GetMapping("/getByLogin")
    public List<String> findAllByUser_Login(@RequestParam String login) {
        return this.productService.findAllByUser_Login(login);
    }

    @PostMapping("/save")
    public Product save(@RequestBody Product product) {
        return this.productService.save(product);
    }

    @PostMapping("/addUserToProduct")
    public ResponseEntity<String> addUserToProduct(@RequestParam Long id, @RequestParam String login) {
        return this.productService.addUserToProduct(id, login);
    }

    @PostMapping("/deleteUserFromProduct")
    public ResponseEntity<String> deleteUserFromProduct(@RequestParam Long id) {
        return this.productService.deleteUserFromProduct(id);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        return this.productService.delete(id);
    }

    @PostMapping("/changeName/{id}/{name}")
    public ResponseEntity<String> changeName(@PathVariable Long id, @PathVariable String name) {
        return this.productService.changeName(id, name);
    }

}