package br.com.lutztechnology.appveterinario.domain.model;

import br.com.lutztechnology.appveterinario.domain.serialization.AnimalSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@JsonSerialize(using = AnimalSerializer.class)
@Entity
@Table(name = "animals")
@NoArgsConstructor @AllArgsConstructor
@ToString(exclude = {"owner", "medicalRecords"})
@Getter @Setter
public class Animal extends BaseEntity {

    @NotNull
    @Size(min = 3, max = 50)
    @Column(nullable = false, length = 50)
    private String name;

    @PastOrPresent
    @Column(nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate age;

    // TODO: Criar model de raças para organização e padronização das raças que são cadastradas no sistema
    @Column(nullable = false, length = 20)
    private String breed;

    @Valid
    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "owner_id_fk", nullable = false)
    private Customer owner;

    @OneToMany(mappedBy = "animal", fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private List<MedicalRecord> medicalRecords;
}
