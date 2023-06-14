package project.ecommerce.service;

import org.apache.tika.exception.TikaException;
import project.ecommerce.dto.ProductDto;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    ProductDto createPost(ProductDto postDto) throws TikaException, IOException;

    List<ProductDto> getALlPost();

    void deletePost(Integer id);

    ProductDto getSinglePost(Integer id) throws IOException;
    List<ProductDto> searchProduct(String query);

//    ProductDto postView(short id) throws IOException;
}
