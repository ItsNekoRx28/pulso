package es.nekaso.pulso.monitor;

import java.time.LocalDate;

public record Monitor(Long id, String name, String url, LocalDate date) {
}
