package br.com.lutztechnology.appveterinario.domain.model;

import br.com.lutztechnology.appveterinario.domain.serialization.VeterinarianSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@JsonSerialize(using = VeterinarianSerializer.class)
@Entity
@Table(name = "veterinarians")
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(exclude = "user")
@ToString(exclude = "user")
public class Veterinarian {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    private Long id;

    // TODO: Criar tabela de controle de especialidades
    @Column(nullable = false, length = 20)
    @Getter @Setter
    private String specialty;

    // TODO: Criar tabela de registro de estados de CRMV
    @Column(nullable = false, name = "crmv_state", length = 6)
    @Getter @Setter
    private String crmvState;

    @Column(nullable = false, length = 10, unique = true)
    @Getter @Setter
    private String crmv;

    @OneToOne(mappedBy = "veterinarian")
    @Getter @Setter
    private User user;

    public Veterinarian(String specialty, String crmvState, String crmv) {
        this.specialty = specialty;
        this.crmvState = crmvState;
        this.crmv = crmv;
    }
}
