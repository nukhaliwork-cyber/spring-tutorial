package com.example.demo.common.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaginationMeta {

    private int currentPage;
    private int lastPage;
    private int perPage;
    private long total;

    /**
     * Converts Spring Data JPA 'Page' object to pagination metadata.
     * Note: Spring Data page numbers are 0-indexed, converted here to 1-indexed (Laravel style).
     */
    public static PaginationMeta fromPage(Page<?> page) {
        return PaginationMeta.builder()
                .currentPage(page.getNumber() + 1)
                .lastPage(page.getTotalPages())
                .perPage(page.getSize())
                .total(page.getTotalElements())
                .build();
    }
}
