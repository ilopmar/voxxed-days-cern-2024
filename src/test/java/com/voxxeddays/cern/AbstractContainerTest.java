package com.voxxeddays.cern;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

@SpringBootTest(
    webEnvironment = WebEnvironment.RANDOM_PORT
)
public abstract class AbstractContainerTest {

  protected RequestSpecification requestSpecification;

  @LocalServerPort
  int port;

  @BeforeEach
  void setup() {
    requestSpecification = new RequestSpecBuilder()
        .setPort(port)
        .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .build();
  }

}
