package ru.vtb.opms.driven.adapter;

import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import ru.vtb.opms.driven.dto.ServiceStatusDto;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GetServiceStatusEndpointTest {
    private final int serverPort;

    public GetServiceStatusEndpointTest(@LocalServerPort int serverPort) {
        this.serverPort = serverPort;
    }

    @Test
    public void test_endpointReturnsCorrectData() {
        String url = String.format("http://127.0.0.1:%d/status", serverPort);
        System.out.println(url);
        RestAssured.given()
                .when().get(url)
                .then().assertThat()
                .statusCode(200)
                .body("status", Matchers.is(ServiceStatusDto.Status.OK.toString()));
    }
}