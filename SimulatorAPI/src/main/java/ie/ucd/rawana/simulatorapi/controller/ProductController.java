package ie.ucd.rawana.simulatorapi.controller;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import ie.ucd.rawana.simulatorapi.DTO.ItemDetails;
import ie.ucd.rawana.simulatorapi.DTO.ItemIdDto;
import ie.ucd.rawana.simulatorapi.DTO.ProductDto;
import ie.ucd.rawana.simulatorapi.model.Product;
import ie.ucd.rawana.simulatorapi.services.ProductService;


@RestController
@CrossOrigin(origins="http://localhost:3000")
public class ProductController {

    @Autowired
    private ProductService productservice;


    @GetMapping("/getProduct")
    public Set<ProductDto> getProduct() {


        Set<ProductDto> setProDto = new HashSet<>();

        List<Product> product =  productservice.getAllProduct();

        for(Product pro : product) {

            ProductDto proDto = new ProductDto();

            proDto.setId(pro.getId());
            proDto.setImagePath(pro.getImagePath());
            proDto.setProductName(pro.getProductName());
            proDto.setPrice(pro.getPrice());
            proDto.setWeight(pro.getWeight());
            proDto.setLocation(pro.getLocation());
            proDto.setCount("");

            setProDto.add(proDto);


        }

        return setProDto;

    }


    @GetMapping("/items")
    public Set<ItemIdDto> getItems() {

        Set<ItemIdDto> setProDto = new HashSet<>();

        List<Product> product =  productservice.getAllProduct();

        for(Product pro : product) {


            ItemIdDto proId = new ItemIdDto();

            proId.setId(pro.getId());


            setProDto.add(proId);

        }

        return setProDto;

    }


    @GetMapping("/items/{id}")
    public ItemDetails getItemDetails(@PathVariable(value = "id") Long id) {

        Product product =  productservice.getProductDetails(id);

        ItemDetails proDto = new ItemDetails();

        proDto.setId(product.getId());
        proDto.setName(product.getProductName());
        proDto.setSupplier(product.getSupplier());
        proDto.setWeight(product.getWeight());
        proDto.setLocation(product.getLocation());


        return proDto;

    }


    @GetMapping("/getItemWeight/{itemId}")
    public String getItemWeight(@PathVariable(value = "itemId") Long itemId) {

        Product product =  productservice.getProductDetails(itemId);

        String weight = product.getWeight();

        return weight;

    }

    @GetMapping("/getItemLocation/{itemId}")
    public String getItemLocation(@PathVariable(value = "itemId") Long itemId) {

        String location =  productservice.getItemLocation(itemId);

        return location;

    }


    //DonPlex
    @PostMapping("/saveProduct")
    public boolean saveProduct(@RequestBody ProductDto model) {

        boolean feedBack = false;
        Product product = new Product();

        product.setId(model.getId());
        product.setProductName(model.getProductName());
        product.setImagePath(model.getImagePath());
        product.setPrice(model.getPrice());
        product.setWeight(model.getWeight());
        product.setLocation(model.getLocation());

        try {
            productservice.saveProduct(product);
            feedBack = true;
        } catch (Exception e) {
            feedBack = false;
        }
        return feedBack;
    }

    @GetMapping("/getProductById/{id}")
    public List<Product> getProductById(@PathVariable long id) {
        return productservice.getProductById(id);
    }

    @PutMapping("/updateProduct/{id}")
    public boolean updateProduct(@RequestBody ProductDto model, @PathVariable long id) {

        boolean feedBack = false;
        Product product = new Product();
        if (model.getId().equals(id)) {
            product.setId(model.getId());
            product.setProductName(model.getProductName());
            product.setPrice(model.getPrice());
            product.setImagePath(model.getImagePath());

            try {
                feedBack = true;
                productservice.updateProduct(id, product);
            } catch (Exception e) {
                feedBack = false;
            }
        } else {
            return feedBack = false;
        }
        return feedBack;
    }

    @DeleteMapping("/deleteProduct/{id}")
    public void deleteProduct(@PathVariable long id) {
        productservice.deleteProduct(id);
    }



}
