package com.resourcify.resourcify_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.resourcify.resourcify_backend.model.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByLocation(String location);
}
