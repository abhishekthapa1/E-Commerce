package project.ecommerce.service;

import org.apache.tika.exception.TikaException;
import org.springframework.stereotype.Service;
import project.ecommerce.component.FileStoreUtils;
import project.ecommerce.dto.ProductDto;
import project.ecommerce.model.Product;
import project.ecommerce.repository.ProductRepo;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepo productRepo;
    private final FileStoreUtils fileStoreUtils;

    public ProductServiceImpl(ProductRepo productRepo, FileStoreUtils fileStoreUtils) {
        this.productRepo = productRepo;
        this.fileStoreUtils = fileStoreUtils;
    }
    LocalDateTime localDateTime = LocalDateTime.now();
    DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/YYYY -- E H:m a");
    String currentDate = localDateTime.format(df);
    @Override
    public ProductDto createPost(ProductDto productDto) throws TikaException, IOException {

        Product product= Product.builder()
                .id(productDto.getId())
                .title(productDto.getTitle())
                .description(productDto.getDescription())
                .category(productDto.getCategory())
                .dateTime(currentDate)
                .imagePath(fileStoreUtils.saveMultipartFile(productDto.getMultipartFile()))
                .price(productDto.getPrice())
                .build();
        product = productRepo.save(product);
        return new ProductDto(product.getId());
    }

    @Override
    public List<ProductDto> getALlPost() {
        List<Product> allProduct = productRepo.findAll();
        return  allProduct.stream().map(product ->{
        try {
        return ProductDto.builder()
                        .id(product.getId())
                        .description(product.getDescription())
                        .title(product.getTitle())
                .category(product.getCategory())
                        .dateTime(product.getDateTime())
                        .price(product.getPrice())
                        .filePath(fileStoreUtils.getBase64FormFilePath(product.getImagePath())).build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }).collect(Collectors.toList());


    }

    @Override
    public void deletePost(Integer id) {
        productRepo.deleteById(id);
    }

    @Override
    public ProductDto getSinglePost(Integer id) throws IOException {
        Optional<Product> product = productRepo.findById(id);
        if(product.isPresent()){
            Product product1 = product.get();
            return ProductDto.builder()
                    .id(product1.getId())
                    .description(product1.getDescription())
                    .title(product1.getTitle())
                    .category(product1.getCategory())
                    .dateTime(product1.getDateTime())
                    .price(product1.getPrice())
                    .filePath(fileStoreUtils.getBase64FormFilePath(product1.getImagePath())).build();
        }
        return null;
    }
    @Override
    public List<ProductDto> searchProduct(String query) {
        List<Product> filteredRecipePostList = productRepo.filterPostByTitleAndDescription(query);
        return filteredRecipePostList.stream().map(post -> {
            try {
                return ProductDto.builder()
                        .id(post.getId())
                        .title(post.getTitle())
                        .description(post.getDescription())
                        .category(post.getCategory())
                        .dateTime(post.getDateTime())
                        .price(post.getPrice())
                        .filePath(fileStoreUtils.getBase64FormFilePath(post.getImagePath()))
                        .build();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());




    }
}
