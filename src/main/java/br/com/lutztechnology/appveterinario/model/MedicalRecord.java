package br.com.lutztechnology.appveterinario.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

@Entity
@Table(name = "medical_records")
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"animal", "veterinarian", "customer"})
@Getter @Setter
public class MedicalRecord extends BaseEntity {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @NotNull
    @PastOrPresent
    @Column(nullable = false, name = "service_date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate serviceDate;

    @JsonIgnore
    @NotNull
    @Column(nullable = false, columnDefinition = "TEXT")
    private String comments;

    // TODO: reavaliar garantia de parâmetro inicialmente false
    @Column(nullable = false)
    private Boolean archived = false;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id_fk", nullable = false)
    private Customer customer;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "animal_id_fk", nullable = false)
    private Animal animal;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_id_fk", nullable = false)
    private Employee veterinarian;
}
