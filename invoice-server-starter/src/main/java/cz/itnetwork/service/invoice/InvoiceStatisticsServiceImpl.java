package cz.itnetwork.service.invoice;

import cz.itnetwork.dto.InvoiceStatisticsDTO;
import cz.itnetwork.entity.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Year;

@Service
public class InvoiceStatisticsServiceImpl implements InvoiceStatisticsService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    /**
     * Gets summary statistics for invoices
     * Statistics include:
     * Total sum of all invoices for the current year, total sum of all invoices (all time)
     * Total count of all invoices
     * @return InvoiceStatisticsDTO containing the calculated statistics
     */
    public InvoiceStatisticsDTO getInvoiceStatistics() {
        int currentYear = Year.now().getValue();            //gets the current year

        Long currentYearSum = invoiceRepository.getCurrentYearSum(currentYear);
        Long allTimeSum = invoiceRepository.getAllTimeSum();
        Long invoiceCount = invoiceRepository.getInvoiceCount();

        return new InvoiceStatisticsDTO(currentYearSum, allTimeSum, invoiceCount);
    }


}
