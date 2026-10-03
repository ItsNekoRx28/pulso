package es.nekaso.pulso.monitor;

import java.time.LocalDate;
public record Monitor(int id, String name, String url, LocalDate date) {}
