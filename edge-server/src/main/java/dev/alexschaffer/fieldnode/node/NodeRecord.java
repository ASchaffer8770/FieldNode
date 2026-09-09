package dev.alexschaffer.fieldnode.node;

import java.time.Instant;
import java.util.UUID;

public record NodeRecord(
        UUID id,
        String displayName,
        String clientType,
        NodeStatus status,
        Instant lastSeen
) {
}
