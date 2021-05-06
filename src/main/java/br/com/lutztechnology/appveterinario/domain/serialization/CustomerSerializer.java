package br.com.lutztechnology.appveterinario.domain.serialization;

import br.com.lutztechnology.appveterinario.domain.model.Animal;
import br.com.lutztechnology.appveterinario.domain.model.Customer;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class CustomerSerializer extends StdSerializer<Customer> {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public CustomerSerializer() {
        this(null);
    }

    protected CustomerSerializer(Class<Customer> t) {
        super(t);
    }

    @Override
    public void serialize(
            Customer customer,
            JsonGenerator jsonGenerator,
            SerializerProvider serializerProvider) throws IOException {

        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("id", customer.getId());
        jsonGenerator.writeStringField("fullName", customer.getFullName());
        jsonGenerator.writeStringField("birthDate", formatter.format(customer.getBirthDate()));
        jsonGenerator.writeStringField("address", customer.getAddress());
        jsonGenerator.writeStringField("phone", customer.phoneToString());
        jsonGenerator.writeObjectField("pets", customer.getPets());
        jsonGenerator.writeEndObject();
    }
}
