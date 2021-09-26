package one.digitalinnovation.personapi.service;


import one.digitalinnovation.personapi.dto.request.PersonDTO;
import one.digitalinnovation.personapi.dto.request.PhoneDTO;
import one.digitalinnovation.personapi.dto.response.MessageResponseDTO;
import one.digitalinnovation.personapi.entity.Person;
import one.digitalinnovation.personapi.entity.Phone;
import one.digitalinnovation.personapi.exception.PersonNotFoundException;
import one.digitalinnovation.personapi.mapper.PersonMapper;
import one.digitalinnovation.personapi.mapper.PhoneMapper;
import one.digitalinnovation.personapi.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonService {
    private PersonRepository personRepository;

    private final PersonMapper personMapper = PersonMapper.INSTANCE;
    private final PhoneMapper phoneMapper = PhoneMapper.INSTANCE;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public MessageResponseDTO createPerson(PersonDTO personDTO){
        Person savedPerson = savePerson(personDTO);
        return createMessageResponse(savedPerson.getId(), "person saved. ID: ");
    }

    public List<PersonDTO> listAll() {
        List<Person> allPeople = personRepository.findAll();
        return allPeople.stream()
                .map(personMapper::toDTO)
                .collect(Collectors.toList());
    }

    public PersonDTO findById(Long id) throws PersonNotFoundException {
        Person person = verifyIfExists(id);
        return personMapper.toDTO(person);
    }

    public PersonDTO delete(Long id) throws PersonNotFoundException {
        PersonDTO personDTO =  personMapper.toDTO(verifyIfExists(id));
        personRepository.deleteById(id);
        return personDTO;
    }

    public MessageResponseDTO update(Long id, PersonDTO personDTO) throws PersonNotFoundException {
        verifyIfExists(id);
        Person updatedPerson = savePerson(personDTO);
        return createMessageResponse(updatedPerson.getId(), "person updated. ID: ");
    }

    private Person savePerson(PersonDTO personDTO){
        Person personToSave = personMapper.toModel(personDTO);
        return personRepository.save(personToSave);
    }

    private Person verifyIfExists(Long id) throws PersonNotFoundException {
        return personRepository.findById(id)
                .orElseThrow(() -> new PersonNotFoundException(id));
    }

    private MessageResponseDTO createMessageResponse(Long id, String message) {
        return MessageResponseDTO
                .builder()
                .message(message + id)
                .build();
    }

    public PersonDTO addPhone(Long idPerson, PhoneDTO phoneDTO) throws PersonNotFoundException {
        Phone phoneToAdd = phoneMapper.toModel(phoneDTO);
        Person person = verifyIfExists(idPerson);
        person.getPhones().add(phoneToAdd);
        return personMapper.toDTO(personRepository.save(person));
    }
}
