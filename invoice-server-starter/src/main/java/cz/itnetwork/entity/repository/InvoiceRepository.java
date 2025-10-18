package cz.itnetwork.entity.repository;

import cz.itnetwork.entity.InvoiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InvoiceRepository extends JpaRepository<InvoiceEntity, Long>,
        JpaSpecificationExecutor<InvoiceEntity> {
    /**
     * Finds all invoices where the seller's identification number matches the given one
     * @param identificationNumber the identification number of the seller
     * @return list of invoices sold by the specified seller
     */
    List<InvoiceEntity> findBySeller_IdentificationNumber(String identificationNumber);

    /**
     * Finds all invoices where the buyer's identification number matches the given one
     * @param identificationNumber the identification number of the buyer
     * @return list of invoices purchased by the specified buyer
     */
    List<InvoiceEntity> findByBuyer_IdentificationNumber(String identificationNumber);

    /**
     * Calculates the total sum of invoice prices for invoices issued in the specified year
     */
    @Query("SELECT COALESCE(SUM(i.price), 0) FROM invoice i WHERE YEAR(i.issued) = :year")
    Long getCurrentYearSum(@Param("year") int year);

    /**
     * Calculates the total sum of invoice prices for all invoices ever issued
     */
    @Query("SELECT COALESCE(SUM(i.price), 0) FROM invoice i")
    Long getAllTimeSum();

    /**
     * Counts the total number of invoices ever issued
     */
    @Query("SELECT COUNT(i) FROM invoice i")
    Long getInvoiceCount();

}
