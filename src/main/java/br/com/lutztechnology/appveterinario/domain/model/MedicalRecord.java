package br.com.lutztechnology.appveterinario.domain.model;

import br.com.lutztechnology.appveterinario.domain.serialization.MedicalRecordSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@JsonSerialize(using = MedicalRecordSerializer.class)
@Entity
@Table(name = "medical_records")
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(exclude = {"animal", "user"})
@ToString(exclude = {"animal", "user"})
public class MedicalRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Getter @Setter
    private Long id;

    @Column(nullable = false, name = "service_date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Getter @Setter
    private LocalDate serviceDate;

    @Column(nullable = false, length = 200)
    @Getter @Setter
    private String comments;

    @Column(nullable = false)
    @Getter @Setter
    private Boolean archived = false;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "animal_id", referencedColumnName = "id")
    @Getter @Setter
    private Animal animal;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @Getter @Setter
    private User user;

    public MedicalRecord(LocalDate serviceDate, String comments, Animal animal, User user) {
        this.serviceDate = serviceDate;
        this.comments = comments;
        this.animal = animal;
        this.user = user;
    }
}
