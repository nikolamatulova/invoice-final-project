package cz.itnetwork.service.invoice;

import cz.itnetwork.dto.InvoiceDTO;
import cz.itnetwork.entity.filter.InvoiceFilter;

import java.util.List;

public interface InvoiceService {

    /**
     * Creates a new invoice
     *
     * @param invoiceDTO Invoice to create
     * @return newly created invoice
     */
    InvoiceDTO addInvoice(InvoiceDTO invoiceDTO);

    List<InvoiceDTO> getIssuedInvoicesByIdentificationNumber(String identificationNumber);

    List<InvoiceDTO> getReceivedInvoicesByIdentificationNumber(String identificationNumber);

    InvoiceDTO getInvoice(Long id);

    List<InvoiceDTO> getAllInvoices(InvoiceFilter invoiceFilter);

    InvoiceDTO editInvoice(Long id, InvoiceDTO invoiceDTO);

    InvoiceDTO removeInvoice(Long id);
}
