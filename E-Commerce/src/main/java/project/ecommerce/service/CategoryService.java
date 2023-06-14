package project.ecommerce.service;

import project.ecommerce.dto.CategoryDto;

import java.util.List;

public interface CategoryService {
    CategoryDto create(CategoryDto categoryDto);

    List<CategoryDto> getAllCategory();

    void delete(Integer id);



}
