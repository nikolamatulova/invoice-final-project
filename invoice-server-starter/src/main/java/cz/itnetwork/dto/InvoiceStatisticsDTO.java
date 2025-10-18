package cz.itnetwork.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceStatisticsDTO {

    /**
     * Total sum of invoices in the current year
     */
    private Long currentYearSum;

    /**
     * Total sum of all invoices issued (all time)
     */
    private Long allTimeSum;

    /**
     * Total count of all invoices issued
     */
    private Long invoicesCount;

}
