package com.rest_api.demo.category.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateCategoryRequest {

    @NotBlank(message = "Kateqoriya başlığı mütləq daxil edilməlidir")
    @Size(min = 2, max = 150, message = "Başlıq 2 ilə 150 simvol arasında olmalıdır")
    private String title;

    private String description;

    private Long parentId;

    @NotNull(message = "Sıralama (rank) boş ola bilməz")
    @PositiveOrZero(message = "Rank 0 və ya müsbət ədəd olmalıdır")
    private Integer rank;
}
