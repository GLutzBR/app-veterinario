package br.com.lutztechnology.appveterinario.services;

import br.com.lutztechnology.appveterinario.model.Role;
import br.com.lutztechnology.appveterinario.repository.EmployeeRepository;
import br.com.lutztechnology.appveterinario.repository.RoleRepository;
import br.com.lutztechnology.appveterinario.exceptions.AppRoleNotFoundException;
import br.com.lutztechnology.appveterinario.exceptions.RoleHasEmployeesException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Role> searchAll() {
        return roleRepository.findAll();
    }

    public Page<Role> searchAll(Pageable pageable) {
        return roleRepository.findAll(pageable);
    }

    public Role searchById(Long id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new AppRoleNotFoundException(id));
    }

    public List<Role> searchByName(String name) {
        return roleRepository.findByName(name);
    }

    public Role insert(Role role) {
        return roleRepository.save(role);
    }

    public Role update(Role role, Long id) {
        searchById(id);

        return roleRepository.save(role);
    }

    public void deleteById(Long id) {
        Role roleFound = searchById(id);

        if (employeeRepository.findByRole(roleFound).isEmpty()) {
            roleRepository.delete(roleFound);
        } else {
            throw new RoleHasEmployeesException(id);
        }
    }
}
