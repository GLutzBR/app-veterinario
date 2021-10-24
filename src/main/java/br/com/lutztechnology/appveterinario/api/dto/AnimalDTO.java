package br.com.lutztechnology.appveterinario.api.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
public class AnimalDTO {

    @NotNull
    @Size(min = 3, max = 50)
    private String name;

    @PastOrPresent
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate age;

    @NotNull
    @Size(min = 3, max = 20)
    private String breed;

    @NotNull
    @Positive
    private Long ownerId;
}
