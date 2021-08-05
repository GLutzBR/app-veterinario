package br.com.lutztechnology.appveterinario.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter @Setter
public class AlertDTO {

    private String message;
    private String cssClass;
}
