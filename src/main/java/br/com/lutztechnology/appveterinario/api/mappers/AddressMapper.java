package br.com.lutztechnology.appveterinario.api.mappers;

import br.com.lutztechnology.appveterinario.api.dto.AddressDTO;
import br.com.lutztechnology.appveterinario.enums.State;
import br.com.lutztechnology.appveterinario.model.Address;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {

    public Address convertToEntity(AddressDTO addressDTO) {
        Address address = new Address();
        State state = State.valueOf(addressDTO.getState());

        address.setState(state);
        address.setCity(addressDTO.getCity());
        address.setDistrict(addressDTO.getDistrict());
        address.setPublicPlace(addressDTO.getPublicPlace());
        address.setCep(addressDTO.getCep());
        address.setNumber(addressDTO.getNumber());
        address.setComplement(addressDTO.getComplement());

        return address;
    }
}
