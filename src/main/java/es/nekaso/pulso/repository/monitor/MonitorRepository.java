package es.nekaso.pulso.repository.monitor;

import es.nekaso.pulso.model.monitor.Monitor;
import org.springframework.data.repository.CrudRepository;

public interface MonitorRepository extends CrudRepository<Monitor, Long> {
}