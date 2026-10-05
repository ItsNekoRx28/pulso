package es.nekaso.pulso.monitor;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.net.URI;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureTestRestTemplate
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class MonitorTests {

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    void shouldReturnAMonitorWhenDataIsSaved() {
        ResponseEntity<String> response = restTemplate.getForEntity("/monitors/99", String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(response.getBody());

        Number id = documentContext.read("$.id");
        assertThat(id).isEqualTo(99);

        String name = documentContext.read("$.name");
        assertThat(name).isNotNull();
    }

    @Test
    void shouldNotReturnAMonitorWithAnUnknownId() {
        ResponseEntity<String> response = restTemplate.getForEntity("/monitors/1000", String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isBlank();
    }

    @Test
    void shouldCreateANewMonitor() {
        Monitor newMonitor = new Monitor(null, "Nekaso Test", "https://nekaso.es", LocalDate.now());

        ResponseEntity<Void> createResponse = restTemplate.postForEntity("/monitors", newMonitor, Void.class);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        URI locationOfNewMonitor = createResponse.getHeaders().getLocation();

        ResponseEntity<String> getResponse = restTemplate.getForEntity(locationOfNewMonitor, String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(getResponse.getBody());
        Number id = documentContext.read("$.id");
        String name = documentContext.read("$.name");
        String url = documentContext.read("$.url");

        assertThat(id).isNotNull();
        assertThat(name).isEqualTo("Nekaso Test");
        assertThat(url).isEqualTo("https://nekaso.es");
    }

    @Test
    void shouldDeleteAMonitor() {
        Monitor newMonitor = new Monitor(null, "Nekaso Test", "https://nekaso.es", LocalDate.now());

        ResponseEntity<Void> createResponse = restTemplate.postForEntity("/monitors", newMonitor, Void.class);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        URI locationOfNewMonitor = createResponse.getHeaders().getLocation();

        ResponseEntity<String> getResponse = restTemplate.getForEntity(locationOfNewMonitor, String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(getResponse.getBody());
        Number id = documentContext.read("$.id");
        String name = documentContext.read("$.name");
        String url = documentContext.read("$.url");

        assertThat(id).isNotNull();
        assertThat(name).isEqualTo("Nekaso Test");
        assertThat(url).isEqualTo("https://nekaso.es");

        String uri = "/monitors/" + id;

        ResponseEntity<Void> deleteResponse = restTemplate.exchange(uri, HttpMethod.DELETE, null, Void.class);

        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.valueOf(204));

        ResponseEntity<String> getResponseAfterDelete = restTemplate.getForEntity(uri, String.class);

        assertThat(getResponseAfterDelete.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}