package com.resourcify.resourcify_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.resourcify.resourcify_backend.service.ResourceService;
import com.resourcify.resourcify_backend.dto.ResourceDto;

import java.util.List;

@RestController
@RequestMapping("/api/resources")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    @GetMapping
    public List<ResourceDto> getAllResources() {
        return resourceService.getAllResources();
    }
}
