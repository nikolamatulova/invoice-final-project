package cz.itnetwork.controller;

import cz.itnetwork.dto.InvoiceDTO;
import cz.itnetwork.entity.filter.InvoiceFilter;
import cz.itnetwork.service.invoice.InvoiceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    /**
     * Adds a new invoice
     * @param invoiceDTO the invoiceDTO with the details to create
     * @return the created invoiceDTO
     */
    @PostMapping({"/invoices", "/invoices/"})
    InvoiceDTO addInvoice(@Valid @RequestBody InvoiceDTO invoiceDTO) {
        return invoiceService.addInvoice(invoiceDTO);
    }

    /**
     * Retrieves invoices issued by a person or entity identified
     * by their identification number
     * @param identificationNumber the identification number of the issuer
     * @return list of issued invoices as InvoiceDTO
     */
    @GetMapping({"/identification/{identificationNumber}/issued", "/identification/{identificationNumber}/issued/" })
    public List<InvoiceDTO> getIssuedInvoicesByIdentificationNumber(@PathVariable String identificationNumber) {
        return invoiceService.getIssuedInvoicesByIdentificationNumber(identificationNumber);
    }

    /**
     * Retrieves invoices received by a person or entity identified
     * by their identification number.
     * @param identificationNumber the identification number of the receiver
     * @return list of received invoices as InvoiceDTO
     */
    @GetMapping({"/identification/{identificationNumber}/received", "/identification/{identificationNumber}/received/"})
    public List<InvoiceDTO> getReceivedInvoicesByIdentificationNumber(@PathVariable String identificationNumber) {
        return invoiceService.getReceivedInvoicesByIdentificationNumber(identificationNumber);
    }

    /**
     * Gets a single invoice by its ID
     * @param id the unique identifier of the invoice
     * @return the invoice as InvoiceDTO
     */
    @GetMapping({"/invoices/{id}", "/invoices/{id}/"})
    public InvoiceDTO getInvoice(@PathVariable Long id) {
        return invoiceService.getInvoice(id);
    }

    /**
     * Gets a list of all invoices, filtered or not
     * @param invoiceFilter filter criteria to apply (can be empty)
     * @return list of invoices as InvoiceDTO
     */
    @GetMapping({"/invoices", "/invoices/"})
    public List<InvoiceDTO> getAllInvoices(InvoiceFilter invoiceFilter){
        return invoiceService.getAllInvoices(invoiceFilter);
    }

    /**
     * Updates an existing invoice by ID with new data
     * @param id the ID of the invoice to update
     * @param invoiceDTO the new invoice data
     * @return teh updated invoice as InvoiceDTO
     */
    @PutMapping({"/invoices/{id}", "/invoices/{id}/"})
    public InvoiceDTO editInvoice(@Valid @PathVariable Long id, @RequestBody InvoiceDTO invoiceDTO){
        return invoiceService.editInvoice(id, invoiceDTO);
    }

    /**
     * Deletes an invoice by its ID
     * @param id the unique identifier if the invoice
     * @return the deleted invoice as InvoiceDTO
     */
    @DeleteMapping({"/invoices/{id}", "invoices/{id}/"})
    public InvoiceDTO removeInvoice(@PathVariable Long id){

        return invoiceService.removeInvoice(id);
    }
}
