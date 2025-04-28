package com.resourcify.resourcify_backend.dto;

public class ResourceDto {

    private Long id;
    private String name;
    private double latitude;
    private double longitude;

    public ResourceDto(Long id, String name, double latitude, double longitude) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }
    
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }
}
