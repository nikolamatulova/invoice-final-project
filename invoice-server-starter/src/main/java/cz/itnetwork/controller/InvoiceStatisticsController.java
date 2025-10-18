package cz.itnetwork.controller;

import cz.itnetwork.dto.InvoiceStatisticsDTO;
import cz.itnetwork.service.invoice.InvoiceStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class InvoiceStatisticsController {

    @Autowired
    private InvoiceStatisticsService invoiceStatisticsService;

    /**
     * Gets invoice statistics
     * @return InvoiceStatisticsDTO containing aggregated invoice data
     */
    @GetMapping({"/statistics", "/statistics/"})
    public InvoiceStatisticsDTO getInvoiceStatistics() {
        return invoiceStatisticsService.getInvoiceStatistics();
    }
}
