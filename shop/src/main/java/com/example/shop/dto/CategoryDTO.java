package com.example.shop.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDTO {
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String slug;
}
