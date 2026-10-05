package es.nekaso.pulso.model.monitor;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "monitors")
public class Monitor {

    public Monitor() {
    }

    public Monitor(Long id, String name, String url, LocalDate date) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.date = date;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String url;

    @Column
    private LocalDate date;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Monitor monitor = (Monitor) o;
        return Objects.equals(id, monitor.id) &&
                Objects.equals(name, monitor.name) &&
                Objects.equals(url, monitor.url) &&
                Objects.equals(date, monitor.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, url, date);
    }
}