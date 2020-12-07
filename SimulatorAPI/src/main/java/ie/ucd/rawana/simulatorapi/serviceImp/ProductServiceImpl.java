package ie.ucd.rawana.simulatorapi.serviceImp;

import java.util.List;

import ie.ucd.rawana.simulatorapi.model.Product;
import ie.ucd.rawana.simulatorapi.repository.ProductRepository;
import ie.ucd.rawana.simulatorapi.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> getAllProduct() {

        return productRepository.findAll();
    }

    @Override
    public Product getProductDetails(long id) {

        return productRepository.findById(id).get();
    }

    @Override
    public String getItemWeight(long itemId) {

        return productRepository.getItemWeight(itemId);
    }

    @Override
    public String getItemLocation(long itemId) {

        return productRepository.getItemLocation(itemId);
    }

    @Override
    public void saveProduct(Product product) {
        productRepository.save(product);

    }

    @Override
    public void deleteProduct(long id) {
        productRepository.deleteById(id);
    }

    @Override
    public List<Product> getProductById(long id) {
        return productRepository.findProductById(id);
    }

    @Override
    public void updateProduct(long id, Product product) {
        productRepository.save(product);
    }


}
