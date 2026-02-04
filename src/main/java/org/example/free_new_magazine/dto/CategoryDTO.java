package org.example.free_new_magazine.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {

    @NotNull
    private Long id;

    @NotBlank(message = "Category name cannot be blank")
    private String name;

    @NotBlank(message = "Category description cannot be blank")
    private String description;
}
