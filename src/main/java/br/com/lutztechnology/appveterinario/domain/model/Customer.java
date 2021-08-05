package br.com.lutztechnology.appveterinario.domain.model;

import br.com.lutztechnology.appveterinario.domain.serialization.CustomerSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@JsonSerialize(using = CustomerSerializer.class)
@Entity
@Table(name = "customers")
@NoArgsConstructor @AllArgsConstructor
@ToString(exclude = "pets")
@Getter @Setter
public class Customer extends Person {

    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY, cascade = CascadeType.MERGE, orphanRemoval = true)
    private List<Animal> pets;

}
