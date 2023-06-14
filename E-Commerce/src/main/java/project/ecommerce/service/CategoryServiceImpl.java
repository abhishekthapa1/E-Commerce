package project.ecommerce.service;

import org.springframework.stereotype.Service;
import project.ecommerce.dto.CategoryDto;
import project.ecommerce.model.Category;
import project.ecommerce.repository.CategoryRepo;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepo categoryRepo;

    public CategoryServiceImpl(CategoryRepo categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    @Override
    public CategoryDto create(CategoryDto categoryDto) {
        Category category = Category
                .builder()
                .id(null)
                .categoryName(categoryDto.getCategoryName())
                .description(categoryDto.getDescription())
                .build();
        categoryRepo.save(category);
        return new CategoryDto(category.getId());
    }

    @Override
    public List<CategoryDto> getAllCategory() {
        List<Category> categoryList = categoryRepo.findAll();
        return categoryList.stream().map(category -> CategoryDto.builder()
                .id(category.getId())
                .categoryName(category.getCategoryName())
                .description(category.getDescription())
                .build()).collect(Collectors.toList());


    }

    @Override
    public void delete(Integer id) {
        categoryRepo.deleteById(id);
    }

}
