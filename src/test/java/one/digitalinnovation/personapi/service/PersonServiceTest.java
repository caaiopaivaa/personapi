package one.digitalinnovation.personapi.service;

import one.digitalinnovation.personapi.builder.PersonDTOBuilder;
import one.digitalinnovation.personapi.dto.request.PersonDTO;
import one.digitalinnovation.personapi.dto.request.PhoneDTO;
import one.digitalinnovation.personapi.dto.response.MessageResponseDTO;
import one.digitalinnovation.personapi.entity.Person;
import one.digitalinnovation.personapi.exception.PersonNotFoundException;
import one.digitalinnovation.personapi.mapper.PersonMapper;
import one.digitalinnovation.personapi.repository.PersonRepository;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

    private static final long INVALID_PERSON_ID = 1L;

    private PersonMapper personMapper = PersonMapper.INSTANCE;

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private PersonService personService;

    @Test
    void whenPersonIsInformedThenItShouldBeCreated() {
        //given
        PersonDTO personDTOToSave = PersonDTOBuilder.builder().build().toPersonDTO();
        Person expectedSavedPerson = personMapper.toModel(personDTOToSave);
        MessageResponseDTO expectedMessage = new MessageResponseDTO("person saved. ID: "+ expectedSavedPerson.getId());
        //when
        Mockito.when(personRepository.save(expectedSavedPerson)).thenReturn(expectedSavedPerson);
        //then
        MessageResponseDTO messageResponseDTO = personService.createPerson(personDTOToSave);
        assertThat(expectedMessage.getMessage(), is(equalTo(messageResponseDTO.getMessage())));
    }

}
