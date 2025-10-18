package cz.itnetwork.entity.repository.specification;

import cz.itnetwork.entity.InvoiceEntity;
import cz.itnetwork.entity.InvoiceEntity_;
import cz.itnetwork.entity.PersonEntity;
import cz.itnetwork.entity.PersonEntity_;
import cz.itnetwork.entity.filter.InvoiceFilter;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

/**
 * Specification implementation for filtering InvoiceEntity instances
 * based on the provided InvoiceFilter criteria
 *
 * Constructs a dynamic query predicate according to non-null filter fields
 */
@RequiredArgsConstructor
public class InvoiceSpecification implements Specification<InvoiceEntity> {

    /**
     * The filter criteria used to build the predicates
     */
    private final InvoiceFilter filter;

    /**
     * Builds a Predicate based on the InvoiceFilter
     *
     * If present, applies the following filters:
     * SellerID match, BuyerID match, Price range, product name search
     *
     * @param root the root type in the form clause
     * @param criteriaQuery the criteria query
     * @param criteriaBuilder the criteria builder
     * @return a conjunction of all predicates matching the filter criteria
     */
    @Override
    public Predicate toPredicate(Root<InvoiceEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        if (filter.getSeller() != null) {
            Join<PersonEntity, InvoiceEntity> sellerJoin = root.join(InvoiceEntity_.SELLER);
            predicates.add(criteriaBuilder.equal(sellerJoin.get(PersonEntity_.ID), filter.getSeller()));
        }

        if (filter.getBuyer() != null) {
            Join<PersonEntity, InvoiceEntity> buyerJoin = root.join(InvoiceEntity_.BUYER);
            predicates.add(criteriaBuilder.equal(buyerJoin.get(PersonEntity_.ID), filter.getBuyer()));
        }

        if (filter.getMinPrice() != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(InvoiceEntity_.PRICE), filter.getMinPrice()));
        }

        if (filter.getMaxPrice() != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(InvoiceEntity_.PRICE), filter.getMaxPrice()));
        }

        if (filter.getProduct() != null) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get(InvoiceEntity_.PRODUCT)),
                    "%" + filter.getProduct().toLowerCase() + "%"));
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
