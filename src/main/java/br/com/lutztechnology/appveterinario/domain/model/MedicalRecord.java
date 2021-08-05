package br.com.lutztechnology.appveterinario.domain.model;

import br.com.lutztechnology.appveterinario.domain.serialization.MedicalRecordSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

@JsonSerialize(using = MedicalRecordSerializer.class)
@Entity
@Table(name = "medical_records")
@NoArgsConstructor @AllArgsConstructor
@ToString(exclude = {"animal", "veterinarian"})
@Getter @Setter
public class MedicalRecord extends BaseEntity {

    @NotNull
    @PastOrPresent
    @Column(nullable = false, name = "service_date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate serviceDate;

    @NotNull
    @Column(nullable = false, columnDefinition = "TEXT")
    private String comments;

    @Column(nullable = false)
    private Boolean archived = false;

    @Valid
    @NotNull
    @ManyToOne
    @JoinColumn(name = "customer_id_fk", nullable = false)
    private Customer customer;

    @Valid
    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "animal_id_fk", nullable = false)
    private Animal animal;

    @Valid
    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_id_fk")
    private Employee veterinarian;
}
