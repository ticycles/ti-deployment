package com.trackandtrail.dto.notification;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data

public class PushNotificationRequest {
    private String title;
    private String message;
    private Map<String, String> data;
    private String image;
    private String token;
    private String topic;
}

