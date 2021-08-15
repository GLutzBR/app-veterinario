package br.com.lutztechnology.appveterinario.serialize;

import br.com.lutztechnology.appveterinario.model.MedicalRecord;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class MedicalRecordSerializer extends StdSerializer<MedicalRecord> {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

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

        jsonGenerator.writeObjectFieldStart("animal");
        jsonGenerator.writeNumberField("id", medicalRecord.getAnimal().getId());
        jsonGenerator.writeStringField("name", medicalRecord.getAnimal().getName());
        jsonGenerator.writeEndObject();

        jsonGenerator.writeObjectFieldStart("customer");
        jsonGenerator.writeNumberField("id", medicalRecord.getCustomer().getId());
        jsonGenerator.writeStringField("name", medicalRecord.getCustomer().getName());
        jsonGenerator.writeEndObject();

        jsonGenerator.writeObjectFieldStart("veterinarian");
        jsonGenerator.writeNumberField("id", medicalRecord.getVeterinarian().getId());
        jsonGenerator.writeStringField("name", medicalRecord.getVeterinarian().getName());
        jsonGenerator.writeEndObject();

        jsonGenerator.writeEndObject();
    }
}
