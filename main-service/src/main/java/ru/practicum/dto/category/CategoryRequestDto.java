package ru.practicum.dto.category;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
@Schema(description = "Данные для добавления новой категории")
public class CategoryRequestDto {
    @Schema(description = "Название категории", example = "Концерты")
    @NotBlank
    @Length(max = 50, min = 1)
    private String name;
}
