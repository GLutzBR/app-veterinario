package br.com.lutztechnology.appveterinario.domain.model;

import br.com.lutztechnology.appveterinario.domain.serialization.RoleSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@JsonSerialize(using = RoleSerializer.class)
@Entity
@Table(name = "roles")
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(exclude = "users")
@ToString(exclude = "users")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    private Long id;

    @Column(nullable = false, length = 20, unique = true)
    @Getter @Setter
    private String type;

    @Column(nullable = false, length = 20)
    @Getter @Setter
    private String name;

    // TODO: Adicionar campo de descrição do nível de acesso

    @ManyToMany(mappedBy = "roles", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @Getter @Setter
    private Set<User> users;

    public Role(String type, String name) {
        this.type = type;
        this.name = name;
    }
}
