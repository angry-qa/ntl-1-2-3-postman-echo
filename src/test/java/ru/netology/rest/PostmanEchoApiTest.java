package ru.netology.rest;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

class PostmanEchoApiTest {
  private RequestSpecification requestSpec = new RequestSpecBuilder()
      .setBaseUri("https://postman-echo.com")
      //.setBasePath("/api/v1")
      //.setPort(9999)
      .setAccept(ContentType.JSON)
      .setContentType(ContentType.JSON)
      .log(LogDetail.ALL)
      .build();

    @Test
    void shouldStatusCode200() {
      // Given - When - Then
      // Предусловия
      given()
          .spec(requestSpec)
      // Выполняемые действия
      .when()
          .get("/get?foo1=bar1&foo2=bar2")
      // Проверки
      .then()
          .statusCode(200)
          .contentType(ContentType.JSON);
    }

  @Test
  void shouldBodyHasCorrectMessage() {
    // Given - When - Then
    // Предусловия
    given()
        .spec(requestSpec)
        //.contentType("text/plain; charset=UTF-8")
        .body("this test message")
        // Выполняемые действия
        .when()
        .post("/post")
        // Проверки
        .then()
        .statusCode(200)
        .contentType(ContentType.JSON)
        .body("data", equalTo("this test message"))
    ;
  }

  @Test
  void shouldBodyHasParametrs() {
    // Given - When - Then
    // Предусловия
    given()
        .spec(requestSpec)
        .queryParam("phone", "79000000000")
        .queryParam("mail", "mail@mail.ru")
        // Выполняемые действия
        .when()
        .post("/post")
        // Проверки
        .then()
        .statusCode(200)
        .contentType(ContentType.JSON)
        .body("args.phone", equalTo("79000000000"))
        .body("args.mail", equalTo("mail@mail.ru"))
    ;
  }

  @Test
  void shouldBodyHasFormData() {
    // Given - When - Then
    // Предусловия
    given()
        .spec(requestSpec)
        .formParam("phone", "79000000000")
        .formParam("mail", "mail@mail.ru")
        // Выполняемые действия
        .when()
        .post("/post")
        // Проверки
        .then()
        .statusCode(200)
        .contentType(ContentType.JSON)
        .body("data", equalTo("phone=79000000000&mail=mail%40mail.ru"))
    ;
  }

}
