package cz.itnetwork.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceDTO {

    /**
     * The unique identifier of the invoice
     */
    @JsonProperty("_id")
    @NotNull
    private long id;

    /**
     * Sequential number of the invoice for the given year or period.
     */
    @NotNull
    private int invoiceNumber;

    /**
     * Date when the invoice was issued
     */
    @NotNull
    private LocalDate issued;

    /**
     * Date when the payment of the invoice is due
     */
    @NotNull
    private LocalDate dueDate;

    /**
     * Description or name of the invoiced product or service
     */
    @NotBlank
    private String product;

    /**
     * NET price of the invoice (before VAT)
     */
    @NotNull
    private Long price;

    /**
     * VAT percentage applied to the price
     */
    @NotNull
    private int vat;

    /**
     * Optional comments related to the invoice
     */
    private String note;

    /**
     * Buyer information
     * Must be a valid PersonDTO
     */
    @Valid
    @NotNull
    private PersonDTO buyer;

    /**
     * Seller information
     * Must be a valid PersonDTO
     */
    @Valid
    @NotNull
    private PersonDTO seller;
}
