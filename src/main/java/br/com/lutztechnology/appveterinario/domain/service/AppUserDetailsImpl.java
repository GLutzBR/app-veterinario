package br.com.lutztechnology.appveterinario.domain.service;

import br.com.lutztechnology.appveterinario.domain.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AppUserDetailsImpl implements UserDetails {

    private final User user;

    public AppUserDetailsImpl(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();

        this.user.getRoles().forEach(u -> {
            GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + u.getType());
            authorities.add(authority);
        });

        return authorities;
    }

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.user.getActive();
    }

    public String getFullName() {
        return this.user.getFirstName() + " " + this.user.getLastName();
    }

    public Long getId() {
        return this.user.getId();
    }
}
