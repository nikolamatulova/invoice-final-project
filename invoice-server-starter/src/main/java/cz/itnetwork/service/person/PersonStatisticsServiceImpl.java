package cz.itnetwork.service.person;

import cz.itnetwork.dto.PersonStatisticsDTO;
import cz.itnetwork.entity.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonStatisticsServiceImpl implements PersonStatisticsService {

    @Autowired
    private PersonRepository personRepository;

    /**
     * Retrieves a list of person revenue statistics
     *
     * Calls a repository method which returns a list of object arrays,
     * where each array contains raw data representing:
     * Person ID, person name, revenue value
     * @return List of PersonStatisticsDTO containing person revenue statistics
     */
    @Override
    public List<PersonStatisticsDTO> getPersonsRevenue() {
        List<Object[]> result = personRepository.getPersonStatistics();

        // Transform the raw data into a list of DTOs using a stream
        return result.stream()
                .map(row -> new PersonStatisticsDTO(
                        ((Number) row[0]).longValue(),  // cast first element to long (person ID)
                        (String) row[1],                // second element is person name as String
                        ((Number) row[2]).longValue()   // third element to long (revenue)
                ))
                .collect(Collectors.toList());
    }
}
