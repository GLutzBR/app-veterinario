package br.com.lutztechnology.appveterinario.api.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.time.LocalDate;

@Getter
@Setter
public class CustomerDTO {

    @NotNull
    @Size(min = 10, max = 80)
    @Email
    private String email;

    @NotNull
    @Size(min = 3, max = 80)
    private String name;

    @NotNull
    @Size(min = 14, max = 14)
    @CPF
    private String cpf;

    @NotNull
    @Size(min = 15, max = 15)
    @Pattern(
            regexp = "^\\([1-9]{2}\\) (?:[2-8]|9[1-9])[0-9]{3}\\-[0-9]{4}$",
            message = "O telefone deve estar no formato (99) 99999-9999"
    )
    private String phone;

    @NotNull
    @Past
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate birthDate;

    @Valid
    private AddressDTO address;
}
