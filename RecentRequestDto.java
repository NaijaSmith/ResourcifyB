package com.resourcify.resourcify_backend.dto;

import lombok.Data;

@Data
public class RecentRequestDto {
    private Long id;
    private String resourceName;
    private int quantity;
    private String status;
    private String requestDate; // Fixed naming
}
