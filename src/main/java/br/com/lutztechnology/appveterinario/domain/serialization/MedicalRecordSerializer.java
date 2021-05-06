package br.com.lutztechnology.appveterinario.domain.serialization;

import br.com.lutztechnology.appveterinario.domain.model.MedicalRecord;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class MedicalRecordSerializer extends StdSerializer<MedicalRecord> {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public MedicalRecordSerializer() {
        this(null);
    }

    protected MedicalRecordSerializer(Class<MedicalRecord> t) {
        super(t);
    }

    @Override
    public void serialize(
            MedicalRecord medicalRecord,
            JsonGenerator jsonGenerator,
            SerializerProvider serializerProvider) throws IOException {

        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("id", medicalRecord.getId());
        jsonGenerator.writeStringField("serviceDate", formatter.format(medicalRecord.getServiceDate()));
        jsonGenerator.writeStringField("comments", medicalRecord.getComments());
        jsonGenerator.writeBooleanField("archived", medicalRecord.getArchived());
        jsonGenerator.writeObjectField("animal", medicalRecord.getAnimal());

        jsonGenerator.writeObjectFieldStart("user");
        jsonGenerator.writeNumberField("id", medicalRecord.getUser().getId());
        jsonGenerator.writeStringField("fullName",
                medicalRecord.getUser().getFirstName() + " " + medicalRecord.getUser().getLastName());
        jsonGenerator.writeEndObject();

        jsonGenerator.writeObjectField("veterinarian", medicalRecord.getUser().getVeterinarian());
        jsonGenerator.writeEndObject();
    }
}
