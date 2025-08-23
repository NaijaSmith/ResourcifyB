package com.resourcify.resourcify_backend.model; 

import lombok.Data;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "requests")
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Resource_id")
    private Long resourceId;

    @Column(name = "User_id")
    private Long userId;

    private String status;

    @Column(name = "Request_date")
    private LocalDateTime requestDate; // Fixed camelCase

    private String location;
    private int quantity;
    private String name;
}
