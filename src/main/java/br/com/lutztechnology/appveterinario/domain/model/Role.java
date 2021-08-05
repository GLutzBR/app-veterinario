package br.com.lutztechnology.appveterinario.domain.model;

import br.com.lutztechnology.appveterinario.domain.serialization.RoleSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@JsonSerialize(using = RoleSerializer.class)
@Entity
@Table(name = "roles")
@NoArgsConstructor @AllArgsConstructor
@ToString
@Getter @Setter
public class Role extends BaseEntity {

    @NotNull
    @Size(min = 3, max = 40)
    @Column(nullable = false, length = 40, unique = true)
    private String name;
}
