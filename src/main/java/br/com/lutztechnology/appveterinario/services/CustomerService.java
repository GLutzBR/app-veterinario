package br.com.lutztechnology.appveterinario.services;

import br.com.lutztechnology.appveterinario.api.dto.CustomerDTO;
import br.com.lutztechnology.appveterinario.api.mappers.AddressMapper;
import br.com.lutztechnology.appveterinario.api.mappers.CustomerMapper;
import br.com.lutztechnology.appveterinario.exceptions.CustomerHasMedicalRecord;
import br.com.lutztechnology.appveterinario.exceptions.CustomerNotFoundException;
import br.com.lutztechnology.appveterinario.model.Address;
import br.com.lutztechnology.appveterinario.model.Customer;
import br.com.lutztechnology.appveterinario.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private AddressMapper addressMapper;

    public List<Customer> searchAll() {
        return customerRepository.findAll();
    }

    public Page<Customer> searchAll(Pageable pageable) {
        return customerRepository.findAll(pageable);
    }

    public Customer searchById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));
    }

    public List<Customer> searchByName(String name) {
        return customerRepository.findByName(name);
    }

    public Customer insert(Customer customer) {
        return customerRepository.save(customer);
    }

    public Customer insert(CustomerDTO customerDTO) {
        Customer customer = customerMapper.convertToEntity(customerDTO);
        customer.setAddress(addressMapper.convertToEntity(customerDTO.getAddress()));

        return customerRepository.save(customer);
    }

    public Customer update(Customer customer, Long id) {
        searchById(id);

        return customerRepository.save(customer);
    }

    public Customer update(CustomerDTO customerDTO, Long id) {
        Customer customer = searchById(id);
        Address address;

        if (customerDTO.getAddress() == null) {
            address = customer.getAddress();
        } else {
            address = addressMapper.convertToEntity(customerDTO.getAddress());
            address.setId(customer.getAddress().getId());
        }

        customer = customerMapper.convertToEntity(customerDTO);
        customer.setAddress(address);
        customer.setId(id);

        return customerRepository.save(customer);
    }

    public void deleteById(Long id) {
        Customer customer = searchById(id);
        if (customer.getMedicalRecords().isEmpty()) {
            customerRepository.delete(customer);
        } else {
            throw new CustomerHasMedicalRecord(id);
        }
    }
}
