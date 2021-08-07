package br.com.lutztechnology.appveterinario.services;

import br.com.lutztechnology.appveterinario.exceptions.EmployeeNotFoundException;
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

    public Employee insert(Employee employee) {
        String encryptedPassword = passwordEncoder.encode(employee.getPassword());

        employee.setPassword(encryptedPassword);

        return employeeRepository.save(employee);
    }

    public Employee update(Employee employee, Long id) {
        Employee employeeFound = searchById(id);

        employee.setPassword(employeeFound.getPassword());

        return employeeRepository.save(employee);
    }

    public void changeAvailability(Long id) {
        Employee employee = searchById(id);

        employee.setActive(!employee.getActive());

        employeeRepository.save(employee);
    }

//    public void deleteById(Long id) {
//        Employee employee = searchById(id);
//        if (employee.getMedicalRecords().isEmpty()) {
//            employeeRepository.delete(employee);
//        } else {
//            throw new EmployeeHasMedicalRecord(id);
//        }
//    }
}
