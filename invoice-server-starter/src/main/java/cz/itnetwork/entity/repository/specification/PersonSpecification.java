package cz.itnetwork.entity.repository.specification;

import cz.itnetwork.entity.InvoiceEntity;
import cz.itnetwork.entity.InvoiceEntity_;
import cz.itnetwork.entity.PersonEntity;
import cz.itnetwork.entity.PersonEntity_;
import cz.itnetwork.entity.filter.PersonFilter;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class PersonSpecification implements Specification<InvoiceEntity> {

    private final PersonFilter filter;

    public Predicate toPredicate(Root<InvoiceEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        if (filter.getName() != null) {
            predicates.add(criteriaBuilder.equal(root.get(PersonEntity_.NAME), filter.getName()));
        }

        if (filter.getMail() != null) {
            predicates.add(criteriaBuilder.like(root.get(PersonEntity_.MAIL), filter.getMail()));
        }

        if (filter.getCity() != null) {
            predicates.add(criteriaBuilder.equal(root.get(PersonEntity_.CITY), filter.getCity()));
        }

        if (filter.getCountry() != null) {
            Expression<String> countryJoin = root.join(PersonEntity_.COUNTRY);
            predicates.add(countryJoin.in(filter.getCountry()));
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
