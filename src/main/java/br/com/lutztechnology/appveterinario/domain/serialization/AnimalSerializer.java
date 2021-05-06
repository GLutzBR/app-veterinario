package br.com.lutztechnology.appveterinario.domain.serialization;

import br.com.lutztechnology.appveterinario.domain.model.Animal;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class AnimalSerializer extends StdSerializer<Animal> {

    public AnimalSerializer() {
        this(null);
    }

    protected AnimalSerializer(Class<Animal> t) {
        super(t);
    }

    @Override
    public void serialize(
            Animal animal,
            JsonGenerator jsonGenerator,
            SerializerProvider serializerProvider) throws IOException {

        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("id", animal.getId());
        jsonGenerator.writeStringField("name", animal.getName());
        jsonGenerator.writeNumberField("age", animal.getAge());
        jsonGenerator.writeStringField("breed", animal.getBreed());
        jsonGenerator.writeObjectFieldStart("owner");
        jsonGenerator.writeNumberField("id", animal.getOwner().getId());
        jsonGenerator.writeStringField("fullName", animal.getOwner().getFullName());
        jsonGenerator.writeEndObject();
        jsonGenerator.writeEndObject();
    }
}
