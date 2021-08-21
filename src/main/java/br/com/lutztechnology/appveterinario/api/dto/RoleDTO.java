package br.com.lutztechnology.appveterinario.api.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class RoleDTO {

    @NotNull
    @Size(min = 3, max = 40)
    @Getter @Setter
    private String name;
}
