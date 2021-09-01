package br.com.lutztechnology.appveterinario.services;

import br.com.lutztechnology.appveterinario.api.dto.EmployeeDTO;
import br.com.lutztechnology.appveterinario.api.mappers.AddressMapper;
import br.com.lutztechnology.appveterinario.api.mappers.EmployeeMapper;
import br.com.lutztechnology.appveterinario.exceptions.EmployeeNotFoundException;
import br.com.lutztechnology.appveterinario.model.Address;
import br.com.lutztechnology.appveterinario.model.Employee;
import br.com.lutztechnology.appveterinario.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private AddressMapper addressMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Employee> searchAll() {
        return employeeRepository.findAll();
    }

    public Page<Employee> searchAll(Pageable pageable) {
        return employeeRepository.findAll(pageable);
    }

    public Employee searchById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
    }

    public List<Employee> searchByNameOrEmail(String search) {
        return employeeRepository.findByNameOrEmail(search);
    }

    public Employee searchByEmail(String email) {
        return employeeRepository.findByEmail(email)
                .orElseThrow(() -> new EmployeeNotFoundException(email));
    }

    public Employee insert(Employee employee) {
        String encryptedPassword = passwordEncoder.encode(employee.getPassword());

        employee.setPassword(encryptedPassword);

        return employeeRepository.save(employee);
    }

    public Employee insert(EmployeeDTO employeeDTO) {
        Employee employee = employeeMapper.convertToEntity(employeeDTO);
        employee.setPassword(passwordEncoder.encode(employee.getPassword()));

        Address address = addressMapper.convertToEntity(employeeDTO.getAddress());
        employee.setAddress(address);

        return employeeRepository.save(employee);
    }

    public Employee update(Employee employee, Long id) {
        Employee employeeFound = searchById(id);

        employee.setPassword(employeeFound.getPassword());

        return employeeRepository.save(employee);
    }

    public Employee update(EmployeeDTO employeeDTO, Long id) {
        Employee employee = searchById(id);
        Address address;

        // TODO: não receber a senha e tratar no front
        if (employeeDTO.getPassword() == null) {
            employeeDTO.setPassword(employee.getPassword());
        } else {
            employeeDTO.setPassword(passwordEncoder.encode(employeeDTO.getPassword()));
        }

        if (employeeDTO.getAddress() == null) {
            address = employee.getAddress();
        } else {
            address = addressMapper.convertToEntity(employeeDTO.getAddress());
            address.setId(employee.getAddress().getId());
        }

        employee = employeeMapper.convertToEntity(employeeDTO);

        employee.setAddress(address);
        employee.setId(id);

        return employeeRepository.save(employee);
    }

    public Employee changeAvailability(Long id) {
        Employee employee = searchById(id);

        employee.setActive(!employee.getActive());

        return employeeRepository.save(employee);
    }
}
