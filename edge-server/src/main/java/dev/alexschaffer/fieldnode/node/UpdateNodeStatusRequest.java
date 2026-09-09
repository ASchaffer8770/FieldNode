package dev.alexschaffer.fieldnode.node;

import jakarta.validation.constraints.NotNull;

public record UpdateNodeStatusRequest(
        @NotNull NodeStatus status
) {
}
