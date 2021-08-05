package br.com.lutztechnology.appveterinario.model;

import br.com.lutztechnology.appveterinario.enums.State;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Entity
@NoArgsConstructor
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

    public Employee(String email,
                    String name,
                    String cpf,
                    String phone,
                    LocalDate birthDate,
                    Address address,
                    String password,
                    LocalDate admissionDate,
                    LocalDate resignationDate,
                    Boolean active,
                    String specialty,
                    State crmvState,
                    String crmv,
                    Role role,
                    List<MedicalRecord> medicalRecords) {
        super(email, name, cpf, phone, birthDate, address);
        this.password = password;
        this.admissionDate = admissionDate;
        this.resignationDate = resignationDate;
        this.active = active;
        this.specialty = specialty;
        this.crmvState = crmvState;
        this.crmv = crmv;
        this.role = role;
        this.medicalRecords = medicalRecords;
    }
}
