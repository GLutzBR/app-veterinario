package br.com.lutztechnology.appveterinario.api.mappers;

import br.com.lutztechnology.appveterinario.api.dto.CustomerDTO;
import br.com.lutztechnology.appveterinario.model.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    public Customer convertToEntity(CustomerDTO customerDTO) {
        Customer customer = new Customer();

        customer.setEmail(customerDTO.getEmail());
        customer.setName(customerDTO.getName());
        customer.setCpf(customerDTO.getCpf());
        customer.setPhone(customerDTO.getPhone());
        customer.setBirthDate(customerDTO.getBirthDate());

        return customer;
    }
}
