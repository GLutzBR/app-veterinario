package br.com.lutztechnology.appveterinario.api.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
public class AddressDTO {

    @NotNull
    @Size(min = 2, max = 2)
    private String state;

    @NotNull
    @Size(min = 3, max = 255)
    private String city;

    @NotNull
    @Size(min = 3, max = 255)
    private String district;

    @NotNull
    @Size(min = 3, max = 255)
    private String publicPlace;

    @NotNull
    @Size(min = 9, max = 9)
    @Pattern(regexp = "^\\d{5}-\\d{3}$", message = "o cep deve estar no formato 99999-999")
    private String cep;

    @NotNull
    @Size(min = 1, max = 20)
    private String number;

    private String complement;
}
