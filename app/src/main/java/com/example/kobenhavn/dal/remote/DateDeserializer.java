package com.example.kobenhavn.dal.remote;


import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.logging.Logger;

public class DateDeserializer implements JsonDeserializer<Date> {

    private static final String EASY_DECODE_TIMESTAMP = ("yyyy-MM-dd-HH-mm-ss-SSS");
    private static final SimpleDateFormat formatter = new SimpleDateFormat(EASY_DECODE_TIMESTAMP);

    @Override
    public Date deserialize(JsonElement element, Type arg1, JsonDeserializationContext arg2) throws JsonParseException {
        String date = element.getAsString().trim();
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        return new Date(new BigDecimal(date).multiply(new BigDecimal(1000)).longValue());
    }
}