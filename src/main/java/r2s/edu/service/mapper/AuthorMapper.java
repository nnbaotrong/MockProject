package r2s.edu.service.mapper;

import java.util.Set;
import org.mapstruct.*;
import r2s.edu.domain.Author;
import r2s.edu.service.dto.AuthorDTO;

/**
 * Mapper for the entity {@link Author} and its DTO {@link AuthorDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AuthorMapper extends EntityMapper<AuthorDTO, Author> {
    @Named("idSet")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    Set<AuthorDTO> toDtoIdSet(Set<Author> author);
}
