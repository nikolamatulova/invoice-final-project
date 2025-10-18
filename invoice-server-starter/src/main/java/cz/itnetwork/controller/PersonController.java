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
package cz.itnetwork.controller;

import cz.itnetwork.dto.PersonDTO;
import cz.itnetwork.dto.PersonStatisticsDTO;
import cz.itnetwork.service.person.PersonService;
import cz.itnetwork.service.person.PersonStatisticsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PersonController {

    @Autowired
    private PersonService personService;

    @Autowired
    private PersonStatisticsService personStatisticsService;

    /**
     * Adds a new person
     * @param personDTO the personDTO with details to create
     * @return the created personDTO
     */
    @PostMapping("/persons")
    public PersonDTO addPerson(@Valid @RequestBody PersonDTO personDTO) {

        return personService.addPerson(personDTO);
    }

    /**
     * Edits a person with a given ID
     * @param id the unique identifier of a person
     * @param personDTO the personDTO with details to edit
     * @return the created personDTO
     */
    @PutMapping({"/persons/{id}", "/persons/{id}/"})
    public PersonDTO editPerson(@PathVariable Long id, @Valid @RequestBody PersonDTO personDTO) {
        return personService.editPerson(id, personDTO);
    }

    /**
     * Gets a list of all people in the database
     * @return the list of all people in the database
     */
    @GetMapping("/persons")
    public List<PersonDTO> getPersons() {

        return personService.getAll();
    }

    /**
     * Deletes a person with a given ID
     * @param id the unique identifier of a person
     */
    @DeleteMapping("/persons/{id}")
    public void deletePerson(@PathVariable long id) {

        personService.removePerson(id);
    }

    /**
     * Retrieves a person by their unique ID
     * @param id the unique identifier of a peron
     * @return the PersonDTO with the specified ID
     */
    @GetMapping("/persons/{id}")
    public PersonDTO getPersonById(@PathVariable Long id) {
        return personService.getPerson(id);
    }

    /**
     * Gets a list of revenue statistics for all people
     * @return a list of PersonStatisticsDTO containing revenue data per person
     */
    @GetMapping({"/persons/statistics", "/persons/statistics/"})
    public List<PersonStatisticsDTO> getPersonsRevenue() {
        return personStatisticsService.getPersonsRevenue();
    }

}

