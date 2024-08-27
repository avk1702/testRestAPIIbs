package org.example;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
public class AddVegetableTest extends AbstractApiTest {

    @Test
    public void testAddVegetable() {
        // Шаг 1: Сброс тестовых данных
        resetTestData();

        // Шаг 2: Подготовка данных для добавления товара
        String requestBody = "{\n" +
                "  \"name\": \"Огурец\",\n" +
                "  \"type\": \"VEGETABLE\",\n" +
                "  \"exotic\": false\n" +
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

        assertEquals("Огурец", addedFoodName, "Имя добавленного товара не соответствует ожидаемому значению 'Огурец'");
        assertEquals("VEGETABLE", addedFoodType, "Тип добавленного товара не соответствует ожидаемому значению 'VEGETABLE'");
        assertEquals(false, addedFoodExotic, "Чекбокс 'Экзотический' должен быть установлен.");
    }
}

///

