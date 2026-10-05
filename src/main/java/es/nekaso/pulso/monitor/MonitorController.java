package es.nekaso.pulso.monitor;

import es.nekaso.pulso.model.monitor.Monitor;
import es.nekaso.pulso.repository.monitor.MonitorRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/monitors")
class MonitorController {

    private final MonitorRepository monitorRepository;

    public MonitorController(MonitorRepository monitorRepository) {
        this.monitorRepository = monitorRepository;
    }

    @GetMapping("/")
    private ResponseEntity<Iterable<Monitor>> all() {
        return ResponseEntity.ok(monitorRepository.findAll());
    }

    @GetMapping("/{requestedId}")
    private ResponseEntity<Monitor> findById(@PathVariable Long requestedId) {
        Optional<Monitor> monitorRequested = this.monitorRepository.findById(requestedId);
        if (monitorRequested.isPresent()) {
            return ResponseEntity.ok(monitorRequested.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    private ResponseEntity<Void> createMonitor(@RequestBody Monitor newMonitorRequest, UriComponentsBuilder ucb) {
        Monitor savedMonitor = monitorRepository.save(newMonitorRequest);
        URI locationOfNewMonitor = ucb
                .path("monitors/{id}")
                .buildAndExpand(savedMonitor.getId())
                .toUri();
        return ResponseEntity.created(locationOfNewMonitor).build();
    }

    @DeleteMapping("/{requestedId}")
    private ResponseEntity<Void> deleteMonitor(@PathVariable Long requestedId) {
        if (monitorRepository.existsById(requestedId)) {
            monitorRepository.deleteById(requestedId);
        }
        return ResponseEntity.status(204).build();
    }
}