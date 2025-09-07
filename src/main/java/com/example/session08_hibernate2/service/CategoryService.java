package com.example.session08_hibernate2.service;

import com.example.session08_hibernate2.model.entity.Category;
import com.example.session08_hibernate2.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> findAll(int page , int size , String search) {
        int offset = (page - 1) * size;
        return categoryRepository.getAllCategories(offset,size,search);
    }

    public Category findById(Long id) {
        return categoryRepository.getCategoryById(id);
    }


    public Long countTotalElement(String search){
        return categoryRepository.countTotalElement(search);
    }


    public boolean saveCategory(Category category) {
        return categoryRepository.saveCategory(category);
    }


    public boolean updateCategory(long id ,Category category) {
        Category oldCategory = categoryRepository.getCategoryById(id);
        if (oldCategory != null) {
            category.setId(oldCategory.getId());
            return categoryRepository.updateCategory(category);
        }else {
            return false;
        }

    }


    public boolean deleteCategoryById(long id) {
        return categoryRepository.deleteCategoryById(id);
    }
}
