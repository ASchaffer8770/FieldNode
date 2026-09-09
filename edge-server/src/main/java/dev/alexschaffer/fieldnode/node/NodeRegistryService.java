package dev.alexschaffer.fieldnode.node;

import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class NodeRegistryService {

    private static final String NODE_TOPIC = "/topic/nodes";

    private final Map<UUID, NodeRecord> nodes = new ConcurrentHashMap<>();
    private final SimpMessagingTemplate messagingTemplate;

    public NodeRegistryService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public NodeRecord register(RegisterNodeRequest request) {
        NodeRecord node = new NodeRecord(
                UUID.randomUUID(),
                request.displayName(),
                request.clientType(),
                NodeStatus.UNKNOWN,
                Instant.now()
        );

        nodes.put(node.id(), node);
        publish(node);
        return node;
    }

    public List<NodeRecord> findAll() {
        return nodes.values().stream()
                .sorted(Comparator.comparing(NodeRecord::displayName))
                .toList();
    }

    public NodeRecord findById(UUID id) {
        NodeRecord node = nodes.get(id);

        if (node == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Node not found");
        }

        return node;
    }

    public NodeRecord updateStatus(UUID id, UpdateNodeStatusRequest request) {
        NodeRecord current = findById(id);

        NodeRecord updated = new NodeRecord(
                current.id(),
                current.displayName(),
                current.clientType(),
                request.status(),
                Instant.now()
        );

        nodes.put(id, updated);
        publish(updated);
        return updated;
    }

    private void publish(NodeRecord node) {
        messagingTemplate.convertAndSend(NODE_TOPIC, node);
    }
}
