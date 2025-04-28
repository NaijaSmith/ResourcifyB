package com.resourcify.resourcify_backend.service;

import com.resourcify.resourcify_backend.repository.ItemRepository;
import com.resourcify.resourcify_backend.model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LocationService {

    @Autowired
    private ItemRepository itemRepository;

    public List<String> getDistinctLocations() {
        return itemRepository.findAll().stream()
                .map(Item::getLocation)
                .distinct()
                .collect(Collectors.toList());
    }
}