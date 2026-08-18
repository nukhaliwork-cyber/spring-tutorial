package com.rest_api.demo.common.response;

import lombok.*;
import org.springframework.data.domain.Page;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaginationMeta {
    private int currentPage;
    private int lastPage;
    private int perPage;
    private long total;

    public static PaginationMeta fromPage(Page<?> page) {
        return PaginationMeta.builder()
                .currentPage(page.getNumber() + 1)
                .lastPage(page.getTotalPages())
                .perPage(page.getSize())
                .total(page.getTotalElements())
                .build();
    }
}
