package com.resourcify.resourcify_backend.dto;

import lombok.Data;
import java.util.List;

@Data
public class RequestStatsDto {
    private int totalResources;
    private int requestsMade;
    private int fulfilledRequests;
    private int pendingRequests;
    private int rejectedRequests;
    private List<RecentRequestDto> recentRequests;

}