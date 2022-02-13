package r2s.edu.service.mapper;

import org.mapstruct.*;
import r2s.edu.domain.Type;
import r2s.edu.service.dto.TypeDTO;

/**
 * Mapper for the entity {@link Type} and its DTO {@link TypeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TypeMapper extends EntityMapper<TypeDTO, Type> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TypeDTO toDtoId(Type type);
}
