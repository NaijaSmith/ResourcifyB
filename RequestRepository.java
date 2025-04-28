package com.resourcify.resourcify_backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.resourcify.resourcify_backend.model.Request;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {

    int countByStatus(String status);

    List<Request> findTop10ByOrderByRequestDateDesc(); // Fixed camelCase

    @SuppressWarnings("null")
    Optional<Request> findById(Long id);
}
