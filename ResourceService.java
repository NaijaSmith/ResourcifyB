package com.resourcify.resourcify_backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

import com.resourcify.resourcify_backend.repository.ItemRepository;
import com.resourcify.resourcify_backend.dto.ResourceDto;


@Service
public class ResourceService {

    @Autowired
    private ItemRepository itemRepository;

    public List<ResourceDto> getAllResources() {
        return itemRepository.findAll()
                .stream()
                .map(item -> new ResourceDto(item.getId(), item.getName(), item.getLatitude(),
                item.getLongitude()))
                .collect(Collectors.toList());
    }
}
