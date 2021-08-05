package br.com.lutztechnology.appveterinario.services;

import br.com.lutztechnology.appveterinario.model.Employee;
import br.com.lutztechnology.appveterinario.repository.EmployeeRepository;
import br.com.lutztechnology.appveterinario.model.AppUserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AppUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Employee employee = employeeRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found."));


        return new AppUserDetailsImpl(employee);
    }
}
