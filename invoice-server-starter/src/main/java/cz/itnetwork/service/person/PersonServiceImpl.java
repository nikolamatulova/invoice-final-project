/*  _____ _______         _                      _
 * |_   _|__   __|       | |                    | |
 *   | |    | |_ __   ___| |___      _____  _ __| | __  ___ ____
 *   | |    | | '_ \ / _ \ __\ \ /\ / / _ \| '__| |/ / / __|_  /
 *  _| |_   | | | | |  __/ |_ \ V  V / (_) | |  |   < | (__ / /
 * |_____|  |_|_| |_|\___|\__| \_/\_/ \___/|_|  |_|\_(_)___/___|
 *                                _
 *              ___ ___ ___ _____|_|_ _ _____
 *             | . |  _| -_|     | | | |     |  LICENCE
 *             |  _|_| |___|_|_|_|_|___|_|_|_|
 *             |_|
 *
 *   PROGRAMOVÁNÍ  <>  DESIGN  <>  PRÁCE/PODNIKÁNÍ  <>  HW A SW
 *
 * Tento zdrojový kód je součástí výukových seriálů na
 * IT sociální síti WWW.ITNETWORK.CZ
 *
 * Kód spadá pod licenci prémiového obsahu a vznikl díky podpoře
 * našich členů. Je určen pouze pro osobní užití a nesmí být šířen.
 * Více informací na http://www.itnetwork.cz/licence
 */
package cz.itnetwork.service.person;

import cz.itnetwork.dto.PersonDTO;
import cz.itnetwork.dto.mapper.PersonMapper;
import cz.itnetwork.entity.PersonEntity;
import cz.itnetwork.entity.repository.PersonRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonServiceImpl implements cz.itnetwork.service.person.PersonService {

    @Autowired
    private PersonMapper personMapper;

    @Autowired
    private PersonRepository personRepository;

    /**
     * Creates a new person into the database
     * Converts the PersonDTO to an PersonEntity, saves the entity and
     * returns the saved entity as a PersonDTO
     * @param personDTO the PersonDTO containing the details of the person
     * @return the saved PersonDTO
     */
    public PersonDTO addPerson(PersonDTO personDTO) {
        PersonEntity entity = personMapper.toEntity(personDTO);
        entity = personRepository.save(entity);

        return personMapper.toDTO(entity);
    }

    /**
     * Edits an existing person with a given ID
     * Checks if the person exists, updates the person, saves it and returns
     * the updated person as a PersonDTO
     * @param id the unique identifier of the person to update
     * @param personDTO the person data transfer object containing updated details
     * @return the updated PersonDTO after saving changes
     * @throws EntityNotFoundException if no person with the give ID was found
     */
    public PersonDTO editPerson(Long id, PersonDTO personDTO) {
        if(!personRepository.existsById(id)) {
            throw new EntityNotFoundException("Person with given id " + id + " was not found in the database.");
        }
        PersonEntity entity = personMapper.toEntity(personDTO);
        entity.setId(id);
        PersonEntity saved = personRepository.save(entity);
        return personMapper.toDTO(saved);
    }

    /**
     * Deletes a person based on their ID
     * Checks if the person exists, converts it to a DTO, and returns the deleted person DTO
     * @param id the unique identifier of the person to delete
     * @throws EntityNotFoundException if no person with the given ID exists
     */
    @Override
    public void removePerson(long id) {
        try {
            PersonEntity person = fetchPersonById(id);
            person.setHidden(true);

            personRepository.save(person);
        } catch (NotFoundException ignored) {
            // The contract in the interface states, that no exception is thrown, if the entity is not found.
        }
    }

    /**
     * Gets all people who are not marked as hidden and maps them to DTOs
     * @return list of PersonDTO for all visible people
     */
    @Override
    public List<PersonDTO> getAll() {
        return personRepository.findByHidden(false)
                .stream()
                .map(i -> personMapper.toDTO(i))
                .collect(Collectors.toList());
    }

    /**
     * Gets a single person based on their ID and maps it to DTO
     * @param id the unique identifier of the person
     * @return the corresponding PersonDTO
     */
    @Override
    public PersonDTO getPerson(long id) {
        PersonEntity personEntity = fetchPersonById(id);
        return personMapper.toDTO(personEntity);
    }

    // region: Private methods
    /**
     * <p>Attempts to fetch a person.</p>
     * <p>In case a person with the passed [id] doesn't exist a [{@link org.webjars.NotFoundException}] is thrown.</p>
     *
     * @param id Person to fetch
     * @return Fetched entity
     * @throws org.webjars.NotFoundException In case a person with the passed [id] isn't found
     */
    private PersonEntity fetchPersonById(long id) {
        return personRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Person with id " + id + " wasn't found in the database."));
    }
    // endregion


}
