package com.resourcify.resourcify_backend.controller;

import com.resourcify.resourcify_backend.dto.RequestStatsDto;
import com.resourcify.resourcify_backend.model.Request;
import com.resourcify.resourcify_backend.service.RequestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
@RequestMapping("/api/requests")
@CrossOrigin(origins = "http://127.0.0.1:5500", allowCredentials = "true")
public class RequestController {

    private final RequestService requestService;
    private static final Logger LOGGER = Logger.getLogger(RequestController.class.getName());

    public RequestController(RequestService requestService) {
        this.requestService = requestService;
    }

    /**
     * Submit a new resource request
     */
    @PostMapping("/submit")
    public ResponseEntity<String> submitRequest(@RequestBody Request request) {
        try {
            requestService.submitRequest(request);
            return ResponseEntity.ok("Request submitted successfully.");
        } catch (IllegalArgumentException e) {
            LOGGER.warning("Validation failed: " + e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            LOGGER.severe("Error submitting request: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while processing the request.");
        }
    }

    /**
     * Get request statistics
     */
    @GetMapping("/stats")
    public ResponseEntity<RequestStatsDto> getRequestStats() {
        try {
            return ResponseEntity.ok(requestService.getRequestStats());
        } catch (Exception e) {
            LOGGER.severe("Failed to retrieve request stats: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Approve a resource request (admin-only)
     */
    @PutMapping("/{requestId}/approve")
    public ResponseEntity<String> approveRequest(@PathVariable Long requestId) {
        try {
            requestService.approveRequest(requestId);
            return ResponseEntity.ok("Request approved successfully.");
        } catch (Exception e) {
            LOGGER.severe("Failed to approve request ID " + requestId + ": " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to approve request: " + e.getMessage());
        }
    }

    /**
     * Reject a resource request (admin-only)
     */
    @DeleteMapping("/{requestId}/reject")
    public ResponseEntity<String> rejectRequest(@PathVariable Long requestId) {
        try {
            requestService.rejectRequest(requestId);
            return ResponseEntity.ok("Request rejected successfully.");
        } catch (Exception e) {
            LOGGER.severe("Failed to reject request ID " + requestId + ": " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to reject request: " + e.getMessage());
        }
    }
}
