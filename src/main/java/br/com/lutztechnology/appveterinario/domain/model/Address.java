package br.com.lutztechnology.appveterinario.domain.model;

import br.com.lutztechnology.appveterinario.domain.enums.State;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Address extends BaseEntity{

    @NotNull
    @Column(nullable = false, length = 2)
    @Enumerated(EnumType.STRING)
    private State state;

    @NotNull
    @Size(min = 3, max = 255)
    @Column(nullable = false)
    private String city;

    @NotNull
    @Size(min = 3, max = 255)
    @Column(nullable = false)
    private String district;

    @NotNull
    @Size(min = 3, max = 255)
    @Column(nullable = false)
    public String publicPlace;

    @NotNull
    @Size(min = 9, max = 9)
    @Pattern(regexp = "^\\d{5}-\\d{3}$", message = "o cep deve estar no formato 99999-999")
    @Column(nullable = false, length = 9)
    private String cep;

    @NotNull
    @Size(min = 1, max = 20)
    @Column(nullable = false)
    private String number;

    private String complement;
}
