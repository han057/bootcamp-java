package com.example.demo.messaging;

import java.time.Instant;

public record DemoMessage(String content, Instant sentAt) {
}
