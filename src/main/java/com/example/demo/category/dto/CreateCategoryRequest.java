package com.example.demo.category.dto;

import com.example.demo.category.entity.Category;
import com.example.demo.common.validation.Exists;
import com.example.demo.common.validation.Unique;
import com.example.demo.common.validation.ValidFile;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateCategoryRequest {

    @NotBlank(message = "Başlıq mütləq doldurulmalıdır")
    @Size(min = 2, max = 150, message = "Başlıq 2 ilə 150 simvol arasında olmalıdır")
    private String title;

    @NotBlank(message = "Slug mütləq doldurulmalıdır")
    @Size(max = 180, message = "Slug maksimum 180 simvol ola bilər")
    @Unique(entity = Category.class, field = "slug", message = "Bu slug artıq başqa kateqoriyada istifadə olunur")
    private String slug;

    private String description;

    @Exists(entity = Category.class, field = "id", message = "Seçilən ana kateqoriya (parent_id) mövcud deyil")
    private Long parentId;

    @Builder.Default
    private Integer rank = 0;

    @ValidFile(maxSizeInMb = 2, allowedTypes = {"image/jpeg", "image/png", "image/webp", "image/svg+xml"})
    private MultipartFile icon;
}
