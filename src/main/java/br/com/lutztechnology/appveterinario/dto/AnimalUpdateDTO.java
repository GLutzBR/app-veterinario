package br.com.lutztechnology.appveterinario.dto;

import br.com.lutztechnology.appveterinario.model.Animal;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class AnimalUpdateDTO {

    private List<Animal> animals;
}
