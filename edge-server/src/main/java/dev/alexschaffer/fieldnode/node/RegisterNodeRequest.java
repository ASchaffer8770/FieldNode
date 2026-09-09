package dev.alexschaffer.fieldnode.node;

import jakarta.validation.constraints.NotBlank;

public record RegisterNodeRequest(
        @NotBlank String displayName,
        @NotBlank String clientType
) {
}
