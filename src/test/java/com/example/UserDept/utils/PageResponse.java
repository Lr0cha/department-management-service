package com.example.UserDept.utils;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class PageResponse<T> {

    // getters e setters
    private List<T> content;
    private int totalPages;
    private long totalElements;
    private boolean last;

}

