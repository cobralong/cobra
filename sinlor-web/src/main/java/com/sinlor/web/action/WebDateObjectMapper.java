package com.sinlor.web.action;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.ser.CustomSerializerFactory;

public class WebDateObjectMapper extends ObjectMapper {

    public WebDateObjectMapper() {
        CustomSerializerFactory factory = new CustomSerializerFactory();
        factory.addGenericMapping(Date.class, new JsonSerializer<Date>() {
            @Override
            public void serialize(Date value, JsonGenerator jsonGenerator, SerializerProvider provider)
                    throws IOException, JsonProcessingException {
                jsonGenerator.writeString(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(value));
            }
        });
        this.setSerializerFactory(factory);
    }
}
