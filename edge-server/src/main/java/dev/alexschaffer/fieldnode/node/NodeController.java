package dev.alexschaffer.fieldnode.node;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/nodes")
public class NodeController {

    private final NodeRegistryService nodeRegistryService;

    public NodeController(NodeRegistryService nodeRegistryService) {
        this.nodeRegistryService = nodeRegistryService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NodeRecord register(@Valid @RequestBody RegisterNodeRequest request) {
        return nodeRegistryService.register(request);
    }

    @GetMapping
    public List<NodeRecord> findAll() {
        return nodeRegistryService.findAll();
    }

    @GetMapping("/{id}")
    public NodeRecord findById(@PathVariable UUID id) {
        return nodeRegistryService.findById(id);
    }

    @PutMapping("/{id}/status")
    public NodeRecord updateStatus(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateNodeStatusRequest request
    ) {
        return nodeRegistryService.updateStatus(id, request);
    }
}
