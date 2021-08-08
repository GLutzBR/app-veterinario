package br.com.lutztechnology.appveterinario.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "customers")
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"pets", "medicalRecords"})
@Getter @Setter
public class Customer extends Person {

    @JsonIgnore
    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER)
    private List<Animal> pets;

    @JsonIgnore
    @OneToMany(mappedBy = "customer")
    private List<MedicalRecord> medicalRecords;

}
