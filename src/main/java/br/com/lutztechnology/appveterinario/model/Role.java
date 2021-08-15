package br.com.lutztechnology.appveterinario.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
}
