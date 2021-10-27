package br.com.lutztechnology.appveterinario.api.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

@Setter
@Getter
public class MedicalRecordDTO {

    @NotNull
    @PastOrPresent
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate serviceDate;

    @NotNull
    private String comments;

    private Boolean archived = false;

    @NotNull
    private Long customerId;

    @NotNull
    private Long animalId;

    @NotNull
    private Long veterinarianId;
}
