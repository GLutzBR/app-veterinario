package br.com.lutztechnology.appveterinario.model;

import lombok.*;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.time.LocalDate;

@MappedSuperclass
@NoArgsConstructor
@Getter @Setter
public abstract class Person extends BaseEntity {

    @NotNull
    @Size(min = 10, max = 80)
    @Email
    @Column(nullable = false, length = 80, unique = true)
    private String email;

    @NotNull
    @Size(min = 3, max = 80)
    @Column(nullable = false, length = 80)
    private String name;

    @NotNull
    @Size(min = 14, max = 14)
    @CPF
    @Column(nullable = false, length = 14, unique = true)
    private String cpf;

    @NotNull
    @Size(min = 15, max = 15)
    @Pattern(
            regexp = "^\\([1-9]{2}\\) (?:[2-8]|9[1-9])[0-9]{3}\\-[0-9]{4}$",
            message = "o telefone deve estar no formato (99) 99999-9999"
    )
    @Column(nullable = false, length = 15)
    private String phone;

    @NotNull
    @Past
    @Column(name = "birth_date", nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate birthDate;

    @Valid
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id_fk", nullable = false)
    private Address address;

    public Person(String email,
                  String name,
                  String cpf,
                  String phone,
                  LocalDate birthDate,
                  Address address) {
        this.email = email;
        this.name = name;
        this.cpf = cpf;
        this.phone = phone;
        this.birthDate = birthDate;
        this.address = address;
    }
}
