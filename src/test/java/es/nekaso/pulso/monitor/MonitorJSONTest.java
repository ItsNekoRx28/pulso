package es.nekaso.pulso.monitor;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.io.IOException;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class MonitorJsonTest {

    @Autowired
    private JacksonTester<Monitor> json;

    private LocalDate now = LocalDate.parse("2023-10-27");

    @Test
    void monitorSerializationTest() throws IOException {
        Monitor monitor = new Monitor(1L, "Nekaso", "https://nekaso.es", this.now);
        assertThat(json.write(monitor)).hasJsonPathNumberValue("@.id");
        assertThat(json.write(monitor)).extractingJsonPathNumberValue("@.id")
                .isEqualTo(1);
        assertThat(json.write(monitor)).hasJsonPathStringValue("@.name");
        assertThat(json.write(monitor)).extractingJsonPathStringValue("@.name")
                .isEqualTo("Nekaso");
        assertThat(json.write(monitor)).hasJsonPathStringValue("@.url");
        assertThat(json.write(monitor)).extractingJsonPathStringValue("@.url")
                .isEqualTo("https://nekaso.es");
        assertThat(json.write(monitor)).hasJsonPathStringValue("@.date");
        assertThat(json.write(monitor)).extractingJsonPathStringValue("@.date")
                .isEqualTo("2023-10-27");
    }

    @Test
    void monitorDeserializationTest() throws IOException {
        String expected = """
                {
                    "id": 1,
                    "name": "Nekaso",
                    "url": "https://nekaso.es",
                    "date": "2023-10-27"
                }
                """;
        assertThat(json.parse(expected))
                .isEqualTo(new Monitor(1L, "Nekaso", "https://nekaso.es", this.now));
        assertThat(json.parseObject(expected).id()).isEqualTo(1);
        assertThat(json.parseObject(expected).name()).isEqualTo("Nekaso");
        assertThat(json.parseObject(expected).url()).isEqualTo("https://nekaso.es");
        assertThat(json.parseObject(expected).date()).isEqualTo(this.now);
    }
}
