package r2s.edu.service.mapper;

import org.mapstruct.*;
import r2s.edu.domain.Publisher;
import r2s.edu.service.dto.PublisherDTO;

/**
 * Mapper for the entity {@link Publisher} and its DTO {@link PublisherDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PublisherMapper extends EntityMapper<PublisherDTO, Publisher> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PublisherDTO toDtoId(Publisher publisher);
}
