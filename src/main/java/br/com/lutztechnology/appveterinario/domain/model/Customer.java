package br.com.lutztechnology.appveterinario.domain.model;

import br.com.lutztechnology.appveterinario.domain.serialization.CustomerSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@JsonSerialize(using = CustomerSerializer.class)
@Entity
@Table(name = "customers")
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(exclude = "pets")
@ToString(exclude = "pets")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    private Long id;

    @Column(name = "full_name", nullable = false, length = 50)
    @Getter @Setter
    private String fullName;

    @Column(name = "birth_date", nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Getter @Setter
    private LocalDate birthDate;

    // TODO: Criar model de endereço que estende esta classe para controlar cadastro de endereços
    @Column(nullable = false)
    @Getter @Setter
    private String address;

    // TODO: Criar model de contato que estende esta classe para controlar canais de contato com o cliente
    @Column(nullable = false, length = 11)
    @Getter @Setter
    private String phone;

    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY, cascade = CascadeType.MERGE, orphanRemoval = true)
    @Getter @Setter
    private Set<Animal> pets;

    public Customer(String fullName, LocalDate birthDate, String address, String phone) {
        this.fullName = fullName;
        this.birthDate = birthDate;
        this.address = address;
        this.phone = phone;
    }

    public void addPets(Animal pet) {
        this.pets.add(pet);
    }

    public String phoneToString() {
        StringBuilder phoneMask = null;
        int counter = 0;
        if (this.phone.length() == 11) {
            phoneMask = new StringBuilder("(");
            for (char number : this.phone.toCharArray()) {
                phoneMask.append(number);
                if (counter == 1) {
                    phoneMask.append(") ");
                }
                if (counter == 6) {
                    phoneMask.append("-");
                }
                counter++;
            }
        }

        if (phoneMask == null) {
            return this.phone;
        }

        return phoneMask.toString();
    }
}
