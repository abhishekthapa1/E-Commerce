package project.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductDto {
    private Integer id;
    private String title;
    private String description;
    private MultipartFile multipartFile;
    private String category;
    private String filePath;
    private String dateTime;
    private Double price;

    public ProductDto(Integer id) {
        this.id = id;
    }
}
