package cz.itnetwork.entity.filter;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvoiceFilter {

    /**
     * Filter by buyer's person ID
     */
    private Long buyer;

    /**
     * Filter by seller's person ID
     */
    private Long seller;

    /**
     * Filter by product name or description
     */
    private String product;

    /**
     * Minimum price (inclusive) filter
     */
    private Long minPrice;

    /**
     * Maximum price (inclusive) filter
     */
    private Long maxPrice;

    /**
     * Maximum number of results to return. Defaults to 10.
     */
    private Integer limit = 10;
}
