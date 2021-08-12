package br.com.lutztechnology.appveterinario.model;

import br.com.lutztechnology.appveterinario.serialize.AnimalSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@JsonSerialize(using = AnimalSerializer.class)
@Entity
@Table(name = "animals")
@NoArgsConstructor
@AllArgsConstructor
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
    @NotNull
    @Size(min = 3, max = 20)
    @Column(nullable = false, length = 20)
    private String breed;

    @Valid
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id_fk", nullable = false)
    private Customer owner;

    @OneToMany(mappedBy = "animal", fetch = FetchType.LAZY)
    private List<MedicalRecord> medicalRecords;

    public int getAgeYears() {
        if (this.age != null) {
            return Period.between(age, LocalDate.now()).getYears();
        } else {
            return 0;
        }
    }
}
