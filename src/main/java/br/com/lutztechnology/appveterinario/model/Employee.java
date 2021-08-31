package br.com.lutztechnology.appveterinario.model;

import br.com.lutztechnology.appveterinario.enums.State;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
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
    @Column(nullable = false)
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

    // TODO: validar se valores vazios estão sendo salvos como nulos
//    @Size(min = 5, max = 30)
    @Size(max = 30)
    @Column(length = 30)
    private String specialty;

//    @Size(min = 2, max = 2)
//    @Size(max = 2)
    // TODO: buscar como fazer validação deste campo
    @Enumerated(EnumType.STRING)
    private State crmvState;

//    @Size(min = 7, max = 7)
    @Size(max = 7)
    @Column(length = 7)
    private String crmv;

    @Valid
    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
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
