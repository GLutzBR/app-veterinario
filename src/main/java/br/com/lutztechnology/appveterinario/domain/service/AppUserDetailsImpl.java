package br.com.lutztechnology.appveterinario.domain.service;

import br.com.lutztechnology.appveterinario.domain.enums.Role;
import br.com.lutztechnology.appveterinario.domain.model.Employee;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AppUserDetailsImpl implements UserDetails {

    private final Employee employee;

    public AppUserDetailsImpl(Employee employee) {
        this.employee = employee;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Role role;
        switch (employee.getRole().getName()) {
            case "Gerente":
                role = Role.ADMIN;
                break;
            case "Veterinário":
                role = Role.VET;
                break;
            default:
                role = Role.USER;
        }

        return AuthorityUtils.createAuthorityList(role.name());
    }

    @Override
    public String getPassword() {
        return this.employee.getPassword();
    }

    @Override
    public String getUsername() {
        return this.employee.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return employee.getResignationDate() == null;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.employee.getActive();
    }

    public Long getId() {
        return this.employee.getId();
    }
}
