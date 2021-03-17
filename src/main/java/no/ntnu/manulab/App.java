package no.ntnu.manulab;

import com.github.cliftonlabs.json_simple.JsonException;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        System.out.println("Hello World!");

        String adamString = "{\"name\": \"Adam\", \"age\": 23}";

        try {
            JsonObject adam = (JsonObject) Jsoner.deserialize(adamString);

            System.out.println(adam.toJson());
        } catch (JsonException e) {
            e.printStackTrace();
        }
    }
}
