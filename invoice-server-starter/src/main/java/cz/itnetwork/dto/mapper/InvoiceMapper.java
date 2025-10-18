package cz.itnetwork.dto.mapper;

import cz.itnetwork.dto.InvoiceDTO;
import cz.itnetwork.entity.InvoiceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface InvoiceMapper {

    /**
     * Maps an InvoiceDTO to an InvoiceEntity
     * @param source the DTO to map
     * @return the mapped entity
     */
    InvoiceEntity toEntity(InvoiceDTO source);

    /**
     * Maps an InvoiceEntity to an InvoiceDTO
     * @param source the entity to map
     * @return the mapped DTO
     */
    InvoiceDTO toDTO(InvoiceEntity source);

}
