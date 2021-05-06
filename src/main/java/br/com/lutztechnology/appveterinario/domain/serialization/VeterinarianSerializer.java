package br.com.lutztechnology.appveterinario.domain.serialization;

import br.com.lutztechnology.appveterinario.domain.model.Veterinarian;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class VeterinarianSerializer extends StdSerializer<Veterinarian> {

    public VeterinarianSerializer() {
        this(null);
    }

    protected VeterinarianSerializer(Class<Veterinarian> t) {
        super(t);
    }

    @Override
    public void serialize(
            Veterinarian veterinarian,
            JsonGenerator jsonGenerator,
            SerializerProvider serializerProvider) throws IOException {

        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("id", veterinarian.getId());
        jsonGenerator.writeStringField("specialty", veterinarian.getSpecialty());
        jsonGenerator.writeStringField("crmvState", veterinarian.getCrmvState());
        jsonGenerator.writeStringField("crmv", veterinarian.getCrmv());
        jsonGenerator.writeEndObject();
    }
}
