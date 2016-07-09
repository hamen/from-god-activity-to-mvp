package com.ivanmorgillo.fromgodactivitytomvp.helpers;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;

import java.lang.reflect.Type;
import java.util.Date;

public class DateTimeSerializer implements JsonSerializer<DateTime>, JsonDeserializer<DateTime> {
	private DateTimeFormatter formatter;
	
	public DateTimeSerializer(DateTimeFormatter formatter) {
		this.formatter = formatter;
	}

	public DateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		try {
			return DateTime.parse(json.getAsString(), formatter);
		} catch (IllegalArgumentException e) {
			Date date = context.deserialize(json, Date.class);
			return new DateTime(date);
		}
	}

	public JsonElement serialize(DateTime src, Type typeOfSrc, JsonSerializationContext context) {
		return new JsonPrimitive(src.toString());
	}
}
