package com.resourcify.resourcify_backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.resourcify.resourcify_backend.repository.ItemRepository;
import com.resourcify.resourcify_backend.model.Item;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    public List<Item> getItemsByLocation(String location) {
        return itemRepository.findByLocation(location);
    }

    public Item addItem(Item item) {
        try {
            return itemRepository.save(item);
        } catch (Exception e) {
            // Log the exception or handle it appropriately
            throw new RuntimeException("Failed to add item.", e);
        }
    }

    public Item updateExistingItem(Item item) {
        Optional<Item> existingItem = itemRepository.findById(item.getId());
        if (existingItem.isPresent()) {
            return itemRepository.save(item);
        } else {
            // Log the error or handle it appropriately
            throw new RuntimeException("Item not found with id: " + item.getId());
        }
    }
}