package one.digitalinnovation.personapi.builder;

import lombok.Builder;
import one.digitalinnovation.personapi.dto.request.PersonDTO;
import one.digitalinnovation.personapi.dto.request.PhoneDTO;

import java.util.ArrayList;
import java.util.List;

@Builder
public class PersonDTOBuilder {
    @Builder.Default
    private Long id = 1l;

    @Builder.Default
    private String firstName = "Fulano";

    @Builder.Default
    private String lastName = "De Tal";

    @Builder.Default
    private String cpf = "557.270.380-06";

    @Builder.Default
    private String birthDate = "24-05-1988";

    @Builder.Default
    private List<PhoneDTO> phones = new ArrayList<>();

    public PersonDTO toPersonDTO(){
        return new PersonDTO(
                this.id,
                this.firstName,
                this.lastName,
                this.cpf,
                this.birthDate,
                this.phones);
    }

}
