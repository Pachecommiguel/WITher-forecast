package com.pacheco.weatherchallenge.retrofit;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.pacheco.weatherchallenge.retrofit.response.City;

import java.lang.reflect.Type;

public class ResponseDeserializer  implements JsonDeserializer<City> {

    @Override
    public City deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        JsonElement main = jsonObject.get("main");
        double temp = main.getAsJsonObject().get("temp").getAsDouble();
        int pressure = main.getAsJsonObject().get("pressure").getAsInt();
        int humidity = main.getAsJsonObject().get("humidity").getAsInt();

        JsonElement wind = jsonObject.get("wind");
        double speed = wind.getAsJsonObject().get("speed").getAsDouble();
        int deg = wind.getAsJsonObject().get("deg").getAsInt();

        int clouds = jsonObject.get("clouds").getAsJsonObject().get("all").getAsInt();
        int id = jsonObject.get("id").getAsInt();
        String name = jsonObject.get("name").getAsString();

        return new City(id, name, temp, pressure, humidity, speed, deg, clouds);
    }
}
