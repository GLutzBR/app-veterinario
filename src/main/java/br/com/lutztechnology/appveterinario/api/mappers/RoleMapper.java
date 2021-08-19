package br.com.lutztechnology.appveterinario.api.mappers;

import br.com.lutztechnology.appveterinario.api.RoleDTO;
import br.com.lutztechnology.appveterinario.model.Role;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper {

    public Role convertToEntity(RoleDTO roleDTO) {
        Role role = new Role();

        role.setName(roleDTO.getName());

        return role;
    }
}
