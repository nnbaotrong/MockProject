package r2s.edu.service.mapper;

import org.mapstruct.*;
import r2s.edu.domain.OrderItems;
import r2s.edu.service.dto.OrderItemsDTO;

/**
 * Mapper for the entity {@link OrderItems} and its DTO {@link OrderItemsDTO}.
 */
@Mapper(componentModel = "spring", uses = { OrderMapper.class, BookMapper.class })
public interface OrderItemsMapper extends EntityMapper<OrderItemsDTO, OrderItems> {
    @Mapping(target = "orderItemsOrder", source = "orderItemsOrder", qualifiedByName = "id")
    @Mapping(target = "orderItemsBook", source = "orderItemsBook", qualifiedByName = "id")
    OrderItemsDTO toDto(OrderItems s);
}
