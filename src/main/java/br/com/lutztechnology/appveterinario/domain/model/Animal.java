package br.com.lutztechnology.appveterinario.domain.model;

import br.com.lutztechnology.appveterinario.domain.serialization.AnimalSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@JsonSerialize(using = AnimalSerializer.class)
@Entity
@Table(name = "animals")
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(exclude = {"owner", "medicalRecords"})
@ToString(exclude = {"owner", "medicalRecords"})
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Getter @Setter
    private Long id;

    @Column(nullable = false, length = 20)
    @Getter @Setter
    private String name;

    @Column(nullable = false)
    @Getter @Setter
    private Integer age;

    // TODO: Criar model de raças para organização e padronização das raças que são cadastradas no sistema
    @Column(nullable = false, length = 20)
    @Getter @Setter
    private String breed;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "owner_id", referencedColumnName = "id", nullable = false)
    @Getter @Setter
    private Customer owner;

    @OneToMany(mappedBy = "animal", fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @Getter @Setter
    private Set<MedicalRecord> medicalRecords;

    public Animal(String name, Integer age, String breed, Customer owner) {
        this.name = name;
        this.age = age;
        this.breed = breed;
        this.owner = owner;
    }
}
