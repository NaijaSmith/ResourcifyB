package com.resourcify.resourcify_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.resourcify.resourcify_backend.service.LocationService;

import java.util.List;

@RestController
@RequestMapping("/api/locations")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class LocationController {
    @Autowired
    private LocationService locationService;

    @GetMapping
    public List<String> getAllLocations() {
        return locationService.getDistinctLocations();
    }
}