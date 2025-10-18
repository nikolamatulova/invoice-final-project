package cz.itnetwork.service.invoice;

import cz.itnetwork.dto.InvoiceDTO;
import cz.itnetwork.dto.mapper.InvoiceMapper;
import cz.itnetwork.entity.InvoiceEntity;
import cz.itnetwork.entity.filter.InvoiceFilter;
import cz.itnetwork.entity.repository.InvoiceRepository;
import cz.itnetwork.entity.repository.PersonRepository;
import cz.itnetwork.entity.repository.specification.InvoiceSpecification;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    @Autowired
    private InvoiceMapper invoiceMapper;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private PersonRepository personRepository;

    /**
     *Maps the buyer and seller from the Invoice DTO to the InvoiceEntity by setting
     * references to the corresponding Person entities taken from the database
     * @param invoice the InvoiceEntity to update with buyer and seller references
     * @param invoiceDTO the InvoiceDTO containing buyer and seller information
     */
    private void mapPeopleToInvoice(InvoiceEntity invoice, InvoiceDTO invoiceDTO) {
        invoice.setBuyer(personRepository.getReferenceById(invoiceDTO.getBuyer().getId()));
        invoice.setSeller(personRepository.getReferenceById(invoiceDTO.getSeller().getId()));
    }

    /**
     * Creates a new invoice into a database
     * Converts the provided InvoiceDTO to an InvoiceEntity, maps the buyer and seller
     * references, saves the entity, and returns the saved invoice as DTO
     * @param invoiceDTO the invoice data transfer object containing invoice details
     * @return the saved InvoiceDTO
     */
    public InvoiceDTO addInvoice(InvoiceDTO invoiceDTO) {
        InvoiceEntity invoice = invoiceMapper.toEntity(invoiceDTO);
        mapPeopleToInvoice(invoice, invoiceDTO);
        InvoiceEntity saved = invoiceRepository.save(invoice);
        return invoiceMapper.toDTO(saved);
    }

    /**
     *Gets a list of issued invoices by a seller with the given identification number
     * @param identificationNumber the identification number of a given seller
     * @return a list of InvoiceDTOs representing invoices issues by the specific seller
     */
    @Override
    public List<InvoiceDTO> getIssuedInvoicesByIdentificationNumber(String identificationNumber) {
        return invoiceRepository.findBySeller_IdentificationNumber(identificationNumber)
                .stream()
                .map(invoiceMapper::toDTO)
                .toList();
    }

    /**
     * Gets a list of received invoices by a buyer with the given identification number
     * @param identificationNumber the identification number of a given buyer
     * @return a lit of InvoiceDTOs representing invoices received by the specific buyer
     */
    @Override
    public List<InvoiceDTO> getReceivedInvoicesByIdentificationNumber(String identificationNumber) {
        return invoiceRepository.findByBuyer_IdentificationNumber(identificationNumber)
                .stream()
                .map(invoiceMapper::toDTO)
                .toList();
    }

    /**
     * Fetching an invoice by its given ID
     * @param id the unique identifier of the invoice
     * @return an InvoiceDTO corresponding to the given ID
     */
    public InvoiceDTO getInvoice(Long id) {
        InvoiceEntity invoice = invoiceRepository.getReferenceById(id);
        return invoiceMapper.toDTO(invoice);
    }

    /**
     * Gets a list of all invoices matching the filter criteria, limited by a specific number
     * @param invoiceFilter the filter object containing criteria to filter invoices
     * @return a list of invoices matching the filter criteria
     */
    public List<InvoiceDTO> getAllInvoices(InvoiceFilter invoiceFilter) {
        Specification<InvoiceEntity> spec = new InvoiceSpecification(invoiceFilter);
        Pageable pageable = PageRequest.of(0, invoiceFilter.getLimit()); // you could add page number too

        return invoiceRepository.findAll(spec, pageable)
                .stream()
                .map(invoiceMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Edits an existing invoice with a given ID
     * Checks if the invoice exists, maps buyer and seller, updates the invoice,
     * saves it, and returns the updated invoice as a DTO
     * @param id the unique identifier of the invoice to update
     * @param invoiceDTO the invoice data transfer object containing updated invoice details
     * @return the updated InvoiceDTO after saving changes
     * @throws EntityNotFoundException if no invoice with the given ID exists
     */
    public InvoiceDTO editInvoice(Long id, InvoiceDTO invoiceDTO) {
        if (!invoiceRepository.existsById(id)) {
            throw new EntityNotFoundException("Invoice with given id " + id + "was not found in the database.");
        }
        InvoiceEntity invoice = invoiceMapper.toEntity(invoiceDTO);
        mapPeopleToInvoice(invoice, invoiceDTO);
        invoice.setId(id);
        InvoiceEntity savedInvoice = invoiceRepository.save(invoice);
        return invoiceMapper.toDTO(savedInvoice);
    }

    /**
     * Deletes an invoice with the given ID
     * Checks if the invoice exists, converts it to a DTO, and returns the deleted invoice DTO
     * @param id the unique identifier of the invoice to delete
     * @return the InvoiceDTO of the deleted invoice
     * @throws EntityNotFoundException if no invoice with the given ID exists
     */
    public InvoiceDTO removeInvoice(Long id) {
        InvoiceEntity invoice = invoiceRepository.findById(id)
                .orElseThrow(EntityExistsException::new);
        InvoiceDTO deletedInvoice = invoiceMapper.toDTO(invoice);
        invoiceRepository.delete(invoice);
        return deletedInvoice;
    }

}
