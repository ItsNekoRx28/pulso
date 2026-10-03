package es.nekaso.pulso.monitor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.LinkedList;

@RestController
@RequestMapping("/monitors")
class MonitorController {

    private final MonitorService monitorService;

    public MonitorController(MonitorService monitorService) {
        this.monitorService = monitorService;
    }

    @GetMapping("/")
    private ResponseEntity<LinkedList<Monitor>> all() {
        return ResponseEntity.ok(this.monitorService.get());
    }

    @GetMapping("/{requestedId}")
    private ResponseEntity<Monitor> findById(@PathVariable int requestedId) {
        Monitor monitorRequested = this.monitorService.get(requestedId);
        if (monitorRequested != null) {
            return ResponseEntity.ok(monitorRequested);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}