package com.rest_api.demo.common.response;

import org.springframework.data.domain.Page;

public class PaginationMeta {
    private int currentPage;
    private int lastPage;
    private int perPage;
    private long total;

    public PaginationMeta() {
    }

    public PaginationMeta(int currentPage, int lastPage, int perPage, long total) {
        this.currentPage = currentPage;
        this.lastPage = lastPage;
        this.perPage = perPage;
        this.total = total;
    }

    public static PaginationMeta fromPage(Page<?> page) {
        return new PaginationMeta(
                page.getNumber() + 1,
                page.getTotalPages(),
                page.getSize(),
                page.getTotalElements()
        );
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getLastPage() {
        return lastPage;
    }

    public void setLastPage(int lastPage) {
        this.lastPage = lastPage;
    }

    public int getPerPage() {
        return perPage;
    }

    public void setPerPage(int perPage) {
        this.perPage = perPage;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
