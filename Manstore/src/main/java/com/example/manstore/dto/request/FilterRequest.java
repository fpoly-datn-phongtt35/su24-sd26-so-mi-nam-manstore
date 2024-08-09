package com.example.manstore.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class FilterRequest {
    private List<Integer> listColor;
    private List<String> listSize;
}
