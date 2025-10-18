package cz.itnetwork.entity.filter;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonFilter {

    private String name;

    private String mail;

    private String city;

    private String country;

    private Integer limit = 10;


}
