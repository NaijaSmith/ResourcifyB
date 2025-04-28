package com.resourcify.resourcify_backend.service;

import com.resourcify.resourcify_backend.dto.RecentRequestDto;
import com.resourcify.resourcify_backend.dto.RequestStatsDto;
import com.resourcify.resourcify_backend.model.Item;
import com.resourcify.resourcify_backend.model.Request;
import com.resourcify.resourcify_backend.repository.ItemRepository;
import com.resourcify.resourcify_backend.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import jakarta.servlet.http.HttpSession; // Import HttpSession

@Service
public class RequestService {

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private HttpSession httpSession; // Inject HttpSession

    @Transactional // Ensures atomicity: either all operations succeed or none do
    public void submitRequest(Request request) {
        // 1. Validate the request (you might already have this, but it's crucial)
        if (request.getQuantity() <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero.");
        }

        // 2. Find the item by its Name (name in the request)
        Item item = itemRepository.findById(request.getResourceId())
                .orElseThrow(() -> new IllegalArgumentException("Resource not found."));

        // 3. Check if there's enough quantity
        if (item.getQuantity() < request.getQuantity()) {
            throw new IllegalArgumentException("Requested quantity exceeds available quantity.");
        }

        // 4. Update the item's quantity
        item.setQuantity(item.getQuantity() - request.getQuantity());
        itemRepository.save(item);

        // 5. Save the request
        requestRepository.save(request);
    }

    public RequestStatsDto getRequestStats() {
        RequestStatsDto stats = new RequestStatsDto();
        stats.setTotalResources((int) itemRepository.count());
        stats.setRequestsMade((int) requestRepository.count());
        stats.setFulfilledRequests((int) requestRepository.countByStatus("Approved"));
        stats.setPendingRequests((int) requestRepository.countByStatus("Pending"));
        stats.setRejectedRequests((int) requestRepository.countByStatus("Rejected"));

        List<RecentRequestDto> recentRequests = requestRepository.findTop10ByOrderByRequestDateDesc()
                .stream()
                .map(request -> {
                    RecentRequestDto dto = new RecentRequestDto();
                    dto.setId(request.getId());

                    // Fetch resource name from items table based on resourceId in Request
                    Item item = itemRepository.findById(request.getResourceId()).orElse(null);
                    dto.setResourceName(item != null ? item.getName() : "Resource Not Found");

                    dto.setQuantity(request.getQuantity());
                    dto.setStatus(request.getStatus());
                    dto.setRequestDate(request.getRequestDate().toString()); // Convert LocalDateTime to String

                    return dto;
                })
                .collect(Collectors.toList());

        stats.setRecentRequests(recentRequests);
        return stats;
    }

    public void approveRequest(Long requestId) {
        String userRole = (String) httpSession.getAttribute("userRole");
        if (!"ADMIN".equals(userRole)) { // Use "ADMIN" (uppercase) to match your DB
            throw new IllegalStateException("Only admins can approve requests.");
        }
        // Implementation for approving a request
        // Example:
        Request request = requestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Request not found"));
        request.setStatus("Approved");
        requestRepository.save(request);
    }

    @Transactional
    public void rejectRequest(Long requestId) {
        String userRole = (String) httpSession.getAttribute("userRole");
        if (!"ADMIN".equals(userRole)) { // Use "ADMIN" (uppercase) to match your DB
            throw new IllegalStateException("Only admins can reject requests.");
        }
        // Implementation for rejecting a request
        // Example:
        Request request = requestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Request not found"));
        request.setStatus("Rejected");

        Item item = itemRepository.findById(request.getResourceId())
                .orElseThrow(() -> new IllegalArgumentException("Resource not found"));

        item.setQuantity(item.getQuantity() + request.getQuantity()); // Add quantity back
        itemRepository.save(item);
        requestRepository.delete(request);
    }
}