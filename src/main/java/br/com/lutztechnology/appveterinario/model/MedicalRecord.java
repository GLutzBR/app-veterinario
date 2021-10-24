package br.com.lutztechnology.appveterinario.model;

//import br.com.lutztechnology.appveterinario.serialize.MedicalRecordSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

// TODO: reavaliar serialização
//@JsonSerialize(using = MedicalRecordSerializer.class)
@Entity
@Table(name = "medical_records")
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"animal", "veterinarian", "customer"})
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

    // TODO: reavaliar garantia de parâmetro inicialmente false
    @Column(nullable = false)
    private Boolean archived = false;

    @JsonIgnore
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id_fk", nullable = false)
    private Customer customer;

    @JsonIgnore
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "animal_id_fk", nullable = false)
    private Animal animal;

    @JsonIgnore
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id_fk", nullable = false)
    private Employee veterinarian;
}
