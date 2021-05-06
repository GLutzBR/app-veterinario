package br.com.lutztechnology.appveterinario.domain.model;

import br.com.lutztechnology.appveterinario.domain.serialization.UserSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@JsonSerialize(using = UserSerializer.class)
@Entity
@Table(name = "users")
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(exclude = {"roles", "veterinarian"})
@ToString(exclude = {"roles", "veterinarian"})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    private Long id;

    @Column(nullable = false, length = 30, unique = true)
    @Getter @Setter
    private String username;

    @Column(nullable = false, length = 150)
    @Getter @Setter
    private String password;

    @Column(nullable = false, length = 20)
    @Getter @Setter
    private String firstName;

    @Column(nullable = false, length = 50)
    @Getter @Setter
    private String lastName = "";

    @Column(nullable = false)
    @Getter @Setter
    private Boolean active = true;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    @Getter @Setter
    private Set<Role> roles;

    @OneToOne(cascade = CascadeType.MERGE, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "veterinarian_id", referencedColumnName = "id")
    @Getter @Setter
    private Veterinarian veterinarian;

    public User(String username, String password, String firstName, Set<Role> roles, Veterinarian veterinarian) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.roles = roles;
        this.veterinarian = veterinarian;
    }
}
