package br.com.lutztechnology.appveterinario.domain.serialization;

import br.com.lutztechnology.appveterinario.domain.model.Role;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class RoleSerializer extends StdSerializer<Role> {

    public RoleSerializer() {
        this(null);
    }

    protected RoleSerializer(Class<Role> t) {
        super(t);
    }

    @Override
    public void serialize(
            Role role,
            JsonGenerator jsonGenerator,
            SerializerProvider serializerProvider) throws IOException {

        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("id", role.getId());
        jsonGenerator.writeStringField("type", role.getType());
        jsonGenerator.writeStringField("name", role.getName());
        jsonGenerator.writeEndObject();
    }
}
