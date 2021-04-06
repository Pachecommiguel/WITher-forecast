package com.pacheco.weatherchallenge.network;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.pacheco.weatherchallenge.db.entities.City;
import com.pacheco.weatherchallenge.db.entities.Clouds;
import com.pacheco.weatherchallenge.db.entities.Main;
import com.pacheco.weatherchallenge.db.entities.Wind;

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

        return new City(id, new Main(temp, pressure, humidity), new Wind(speed, deg), new Clouds(clouds), name);
    }
}
