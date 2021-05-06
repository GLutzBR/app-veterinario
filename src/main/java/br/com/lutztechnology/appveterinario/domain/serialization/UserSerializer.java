package br.com.lutztechnology.appveterinario.domain.serialization;

import br.com.lutztechnology.appveterinario.domain.model.User;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class UserSerializer extends StdSerializer<User> {

    public UserSerializer() {
        this(null);
    }

    protected UserSerializer(Class<User> t) {
        super(t);
    }

    @Override
    public void serialize(
            User user,
            JsonGenerator jsonGenerator,
            SerializerProvider serializerProvider) throws IOException {

        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("id", user.getId());
        jsonGenerator.writeStringField("username", user.getUsername());
        jsonGenerator.writeStringField("firstName", user.getFirstName());
        jsonGenerator.writeStringField("lastName", user.getLastName());
        jsonGenerator.writeStringField("fullName", user.getFirstName() + ' ' + user.getLastName());
        jsonGenerator.writeBooleanField("active", user.getActive());
        jsonGenerator.writeObjectField("roles", user.getRoles());
        jsonGenerator.writeEndObject();
    }
}
