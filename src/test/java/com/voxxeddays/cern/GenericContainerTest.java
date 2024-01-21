package com.voxxeddays.cern;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
class GenericContainerTest {

  private static final Logger LOG = LoggerFactory.getLogger(GenericContainerTest.class);

  RestTemplate restTemplate = new RestTemplate();

  @Container
  static GenericContainer container = new GenericContainer(
      DockerImageName.parse("traefik/whoami:v1.10")
  ).withExposedPorts(80);

  @Test
  public void tryAccessPort80() {
    var url = "http://localhost:80";

    ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
    LOG.info("response body: {}", response.getBody());

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
  }

  @Test
  public void useContainerHostAndPort() {
    var url = "http://%s:%s".formatted(container.getHost(), container.getFirstMappedPort());
    LOG.info("Server URL: {}", url);

    ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

    LOG.info("response body: {}", response.getBody());
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
  }

}

