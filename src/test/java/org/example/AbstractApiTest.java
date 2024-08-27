package org.example;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class AbstractApiTest {

    protected String sessionId;

    static {
        // Установка базового URI для всех запросов
        RestAssured.baseURI = "http://localhost:8080";
    }

    protected void resetTestData() {
        Response resetResponse = given()
                .contentType("application/json")
                .when()
                .post("/api/data/reset");

        assertEquals(200, resetResponse.getStatusCode(), "Статус код сброса данных не равен 200");
        sessionId = resetResponse.getCookie("JSESSIONID");
    }

    protected Response sendPostRequest(String requestBody) {
        return given()
                .contentType("application/json")
                .cookie("JSESSIONID", sessionId)
                .body(requestBody)
                .when()
                .post("/api/food");
    }

    protected Response sendGetRequest() {
        return given()
                .cookie("JSESSIONID", sessionId)
                .get("/api/food");
    }

    protected void assertResponseStatus(Response response, int expectedStatusCode) {
        assertEquals(expectedStatusCode, response.getStatusCode(), "Статус код не соответствует ожидаемому значению");
    }
}

