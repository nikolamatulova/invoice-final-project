package cz.itnetwork.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Entity(name = "invoice")
@Getter
@Setter
public class InvoiceEntity {

    /**
     * Primary key - unique identifier of the invoice
     * Generated automatically by the database
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /**
     * Sequential number of the invoice for the given year or period.
     */
    private int invoiceNumber;

    /**
     * Date when the invoice was issued
     */
    private LocalDate issued;

    /**
     * Due date for payment of the invoice
     */
    private LocalDate dueDate;

    /**
     * Description or name of the invoiced product or service
     */
    private String product;

    /**
     * NET price of the invoice (before VAT)
     */
    private Long price;

    /**
     * VAT percentage applied to the price
     */
    private int vat;

    /**
     * Optional comments related to the invoice
     */
    private String note;

    /**
     * Reference to the buyer entity
     */
    @ManyToOne
    private PersonEntity buyer;

    /**
     * Reference to the seller entity
     */
    @ManyToOne
    private PersonEntity seller;

}
