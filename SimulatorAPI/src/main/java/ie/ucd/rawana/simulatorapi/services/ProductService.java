package ie.ucd.rawana.simulatorapi.services;

import ie.ucd.rawana.simulatorapi.model.Product;

import java.util.List;



public interface ProductService {

    public List<Product> getAllProduct();

    public Product getProductDetails(long id);

    public String getItemWeight(long itemId);

    public String getItemLocation(long itemId);

    public void saveProduct(Product product);

    public List<Product> getProductById(long id);

    public void updateProduct(long id, Product product);

    public void deleteProduct(long id);
}
