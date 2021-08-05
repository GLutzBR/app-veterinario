package br.com.lutztechnology.appveterinario.domain.model;

import br.com.lutztechnology.appveterinario.domain.enums.State;
import br.com.lutztechnology.appveterinario.domain.serialization.UserSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

@JsonSerialize(using = UserSerializer.class)
@Entity
@NoArgsConstructor @AllArgsConstructor
@ToString
@Getter @Setter
public class Employee extends Person {

    @Size(min = 5, max = 255)
    @Column(nullable = false, length = 255)
    private String password;

    @NotNull
    @PastOrPresent
    @Column(name = "admission_date", nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate admissionDate;

    @PastOrPresent
    @Column(name = "resignation_date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate resignationDate;

    @NotNull
    @Column(nullable = false)
    private Boolean active = true;

    private String specialty;

    private State crmvState;

    private String crmv;

    @Valid
    @NotNull
    @ManyToOne
    @JoinColumn(name = "role_id_fk", nullable = false)
    private Role role;

    @JsonIgnore
    @OneToMany(mappedBy = "veterinarian")
    private List<MedicalRecord> medicalRecords;

}
