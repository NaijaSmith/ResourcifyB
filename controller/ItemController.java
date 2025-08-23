package com.resourcify.resourcify_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import com.resourcify.resourcify_backend.service.ItemService;
import com.resourcify.resourcify_backend.model.Item;

@RestController
@RequestMapping("/api/items")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class ItemController {
    @Autowired
    private ItemService itemService;
    

    @GetMapping
    public List<Item> getAllItems() {
        return itemService.getAllItems();
    }
}