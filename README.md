# FieldNode

**Resilient edge software for intermittently connected field environments.**

FieldNode is a portfolio and engineering lab focused on the kinds of problems encountered in forward-deployed and tactical systems engineering: unreliable WAN connectivity, local-first operation, edge services, field clients, telemetry, geospatial data, observability, and recovery from failure.

The project is intentionally built as a working system rather than a collection of isolated demos.

## Mission

Build a deployable edge platform that allows field clients to discover, report, and exchange operational state through a local Linux node even when upstream connectivity is degraded or unavailable.

When WAN connectivity returns, the edge node will reconcile and synchronize queued state upstream.

## v0.1 Objective

The first release proves a small but important capability:

> Multiple clients can register with a local edge node, publish status, receive live updates, and continue operating over the local network without internet access.

### v0.1 capabilities

- Client registration
- Node identity and last-seen tracking
- Status updates
- REST API
- Real-time updates
- Local persistence
- Health checks
- Linux / Docker deployment
- WAN-independent local operation

## Planned Architecture

```text
+------------------+        +------------------+
| Android Client A |        | Android Client B |
+--------+---------+        +---------+--------+
         |                            |
         |       Local LAN / Wi-Fi    |
         +-------------+--------------+
                       |
                       v
              +--------+---------+
              |   FieldNode Edge |
              |      Server      |
              +--------+---------+
                       |
        +--------------+---------------+
        |              |               |
        v              v               v
   REST / WS       PostgreSQL      Observability
        |
        | intermittent WAN
        v
  +-----+------+
  | Cloud Sync |
  |   (later)  |
  +------------+
```

## Technology Direction

### Edge Server
- Java 21
- Spring Boot 4.1
- PostgreSQL
- Docker
- Spring Actuator
- REST
- WebSockets

### Field Client
- Kotlin
- Android
- Jetpack Compose
- Room
- Retrofit / HTTP
- WebSockets

### Later Milestones
- Store-and-forward synchronization
- GPS / geospatial state
- Telemetry ingestion
- MQTT
- Prometheus / Grafana
- Failure injection and recovery testing
- TAK / ATAK interoperability

## Engineering Principles

FieldNode is built around a few constraints:

1. **Local operation must not depend on WAN availability.**
2. **Failures should be observable and diagnosable.**
3. **Deployment should be reproducible.**
4. **Interfaces should be documented.**
5. **Every milestone should result in a demonstrable working system.**
6. **Operational behavior matters as much as feature behavior.**

## Repository Layout

```text
FieldNode/
├── edge-server/        # Java / Spring Boot edge service
├── android-client/     # Kotlin field client
├── docs/               # Architecture, networking, API, and test documentation
├── docker/             # Deployment configuration
└── diagrams/           # Architecture and data-flow diagrams
```

## Roadmap

### v0.1 — Local Edge Presence
Register clients, update status, query known nodes, and distribute live state locally.

### v0.2 — Disconnected State
Persist client state and queue events safely through restarts and outages.

### v0.3 — Store-and-Forward
Synchronize queued state to an upstream service when WAN connectivity returns.

### v0.4 — Geospatial
Add location-aware clients, map visualization, and geospatial state.

### v0.5 — Telemetry
Ingest and normalize telemetry from benign simulated and physical sensors.

### v0.6 — Operational Hardening
Add metrics, dashboards, failure injection, recovery tests, and deployment hardening.

### v0.7 — TAK Interoperability
Explore interoperability with the public TAK / ATAK ecosystem.

## Status

**v0.1 — bootstrapping**
