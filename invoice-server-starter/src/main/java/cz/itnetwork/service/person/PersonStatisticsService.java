package cz.itnetwork.service.person;

import cz.itnetwork.dto.PersonStatisticsDTO;

import java.util.List;

public interface PersonStatisticsService {

    List<PersonStatisticsDTO> getPersonsRevenue();
}
