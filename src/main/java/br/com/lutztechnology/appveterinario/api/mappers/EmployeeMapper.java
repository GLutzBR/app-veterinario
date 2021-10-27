package br.com.lutztechnology.appveterinario.api.mappers;

import br.com.lutztechnology.appveterinario.api.dto.EmployeeDTO;
import br.com.lutztechnology.appveterinario.enums.State;
import br.com.lutztechnology.appveterinario.model.Employee;
import br.com.lutztechnology.appveterinario.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {

    @Autowired
    private RoleService roleService;

    public Employee convertToEntity(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();


        employee.setEmail(employeeDTO.getEmail());
        employee.setPassword(employeeDTO.getPassword());
        employee.setName(employeeDTO.getName());
        employee.setCpf(employeeDTO.getCpf());
        employee.setPhone(employeeDTO.getPhone());

        employee.setBirthDate(employeeDTO.getBirthDate());
        employee.setAdmissionDate(employeeDTO.getAdmissionDate());
        employee.setResignationDate(employeeDTO.getResignationDate());
        employee.setActive(employeeDTO.getActive());

        employee.setSpecialty(employeeDTO.getSpecialty());
        if (employeeDTO.getCrmvState() != null) {
            employee.setCrmvState(State.valueOf(employeeDTO.getCrmvState()));
        }
        employee.setCrmv(employeeDTO.getCrmv());

        employee.setRole(roleService.searchById(employeeDTO.getRoleId()));

        return employee;
    }
}
