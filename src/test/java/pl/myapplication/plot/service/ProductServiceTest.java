package pl.myapplication.plot.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.myapplication.plot.PlotApplication;
import pl.myapplication.plot.model.Product;
import static org.junit.Assert.assertEquals;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= PlotApplication.class)
public class ProductServiceTest {
    @Autowired
    ProductService productService;

    @Test
    public void findAll() {
        ResponseEntity<String> test = this.productService.findAll();
        assertEquals(HttpStatus.OK, test.getStatusCode());
    }

    @Test
    public void save() {
        Product testProduct = new Product("Masło");
        Product result = this.productService.save(testProduct);
        assertEquals(testProduct, result);
    }

    @Test
    public void addUserToProduct() {
        ResponseEntity<String> test = this.productService.addUserToProduct(Long.valueOf(1), "loginTest");
        assertEquals(HttpStatus.BAD_REQUEST, test.getStatusCode());
    }

    @Test
    public void deleteUserFromProduct() {
        ResponseEntity<String> test = this.productService.deleteUserFromProduct(Long.valueOf(1));
        assertEquals(HttpStatus.BAD_REQUEST, test.getStatusCode());
    }

    @Test
    public void delete() {
        ResponseEntity<String> test = this.productService.delete(Long.valueOf(1));
        assertEquals(HttpStatus.BAD_REQUEST, test.getStatusCode());
    }

    @Test
    public void changeName() {
        ResponseEntity<String> test = this.productService.changeName(Long.valueOf(1), "login1");
        assertEquals(HttpStatus.BAD_REQUEST, test.getStatusCode());
    }
}
