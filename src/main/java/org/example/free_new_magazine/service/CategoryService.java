package org.example.free_new_magazine.service;

import lombok.RequiredArgsConstructor;
import org.example.free_new_magazine.dto.CategoryDTO;
import org.example.free_new_magazine.entity.Category;
import org.example.free_new_magazine.entity.Role;
import org.example.free_new_magazine.entity.User;
import org.example.free_new_magazine.exception.ResourceNotFoundException;
import org.example.free_new_magazine.mapper.CategoryMapper;
import org.example.free_new_magazine.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.security.access.AccessDeniedException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final CurrentUserService currentUserService;
    private final AuditLogService auditLogService;


    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::toDTO)
                .collect(Collectors.toList());
    }

    public CategoryDTO getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
        return categoryMapper.toDTO(category);
    }

    public CategoryDTO createCategory(CategoryDTO categoryDTO)  {
        User user = currentUserService.getCurrentUser();
        if(user.getRole() != Role.ROLE_ADMIN) {
            throw  new AccessDeniedException("Only ADMIN can create category");
        }

        Category category = categoryMapper.toEntity(categoryDTO);
        Category savedCategory = categoryRepository.save(category);
        auditLogService.log("CREATE_CATEGORY","/categories");
        return categoryMapper.toDTO(savedCategory);
    }

    public CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO) {
        User user = currentUserService.getCurrentUser();
        if(user.getRole() != Role.ROLE_ADMIN) {
            throw  new AccessDeniedException("Only ADMIN can create category");
        }
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
        category.setName(categoryDTO.getName());
        category.setDescription(categoryDTO.getDescription());

        Category updated = categoryRepository.save(category);
        auditLogService.log("UPDATE_CATEGORY","/categories" + id);
        return categoryMapper.toDTO(updated);
    }

    public void deleteCategory(Long id) {
        User user = currentUserService.getCurrentUser();
        if(user.getRole() != Role.ROLE_ADMIN) {
            throw  new AccessDeniedException("Only ADMIN can delete category");
        }

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
        categoryRepository.delete(category);
        auditLogService.log("DELETE_CATEGORY","/categories" + id);
    }
}
