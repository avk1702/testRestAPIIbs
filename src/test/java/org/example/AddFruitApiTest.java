package org.example;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
public class AddFruitApiTest extends AbstractApiTest {

    @Test
    public void testAddFood() {
        // Шаг 1: Сброс тестовых данных
        resetTestData();

        // Шаг 2: Подготовка данных для добавления товара
        String requestBody = "{\n" +
                "  \"name\": \"Авокадо\",\n" +
                "  \"type\": \"FRUIT\",\n" +
                "  \"exotic\": true\n" +
                "}";

        // Шаг 3: Отправка POST запроса для добавления товара
        Response postResponse = sendPostRequest(requestBody);

        // Проверка, что статус код ответа на POST запрос равен 200
        assertResponseStatus(postResponse, 200);

        // Шаг 4: Отправка GET запроса для получения списка товаров
        Response getResponse = sendGetRequest();

        // Проверка, что статус код ответа на GET запрос равен 200
        assertResponseStatus(getResponse, 200);

        // Проверка, что добавленный товар присутствует в списке
        String addedFoodName = getResponse.jsonPath().getString("name[-1]");
        String addedFoodType = getResponse.jsonPath().getString("type[-1]");
        Boolean addedFoodExotic = getResponse.jsonPath().getBoolean("exotic[-1]");

        assertEquals("Авокадо", addedFoodName, "Имя добавленного товара не соответствует ожидаемому значению 'Авокадо'");
        assertEquals("FRUIT", addedFoodType, "Тип добавленного товара не соответствует ожидаемому значению 'FRUIT'");
        assertEquals(true, addedFoodExotic, "Чекбокс 'Экзотический' не должен быть установлен.");
    }
}



