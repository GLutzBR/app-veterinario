package br.com.lutztechnology.appveterinario.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "roles")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter @Setter
public class Role extends BaseEntity {

    @NotNull
    @Size(min = 3, max = 40)
    @Column(nullable = false, length = 40, unique = true)
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "role")
    private List<Employee> employees;

    public Role(String name) {
        this.name = name;
    }
}
