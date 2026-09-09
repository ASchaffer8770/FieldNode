# FieldNode Architecture

## Context

FieldNode is an edge-first system intended to remain useful when upstream connectivity is intermittent or unavailable.

The initial system has three logical layers:

1. **Field clients** — mobile applications connected to a local network.
2. **Edge node** — a Linux-hosted service that owns local operational state.
3. **Upstream service** — a future cloud service used for synchronization, aggregation, and remote visibility.

## v0.1 Architecture

```text
Android Client A            Android Client B
       |                            |
       +---------- LAN ------------+
                    |
                    v
          +-------------------+
          | FieldNode Edge    |
          | Spring Boot       |
          +-------------------+
          | REST API          |
          | WebSocket broker  |
          | Node registry     |
          | Health endpoint   |
          +-------------------+
                    |
             local persistence
               (next step)
```

## Design Constraints

### WAN independence

The local edge service must not require internet connectivity to:

- register clients;
- update local client status;
- query known clients;
- distribute live status changes.

### Observable behavior

The service must expose health information and structured application logs so a field engineer can determine whether failure is occurring in the client, network, application, or persistence layer.

### Replaceable interfaces

Networking and persistence boundaries should remain explicit so that later versions can introduce MQTT, PostgreSQL, store-and-forward synchronization, and TAK interoperability without rewriting the entire application.

## Interfaces

### REST

Base path:

```text
/api/v1
```

Initial endpoints:

```text
POST /api/v1/nodes
GET  /api/v1/nodes
GET  /api/v1/nodes/{id}
PUT  /api/v1/nodes/{id}/status
```

### WebSocket

Handshake endpoint:

```text
/ws
```

Node update topic:

```text
/topic/nodes
```

Clients can use REST for commands and initial state, then subscribe to the WebSocket topic for live changes.

## Future Components

- PostgreSQL persistence
- event/outbox queue
- upstream synchronization worker
- geospatial state
- telemetry adapters
- Prometheus metrics
- deployment health diagnostics
- Android local cache
- TAK / ATAK adapter
