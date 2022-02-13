package r2s.edu.service.mapper;

import java.util.Set;
import org.mapstruct.*;
import r2s.edu.domain.Book;
import r2s.edu.service.dto.BookDTO;

/**
 * Mapper for the entity {@link Book} and its DTO {@link BookDTO}.
 */
@Mapper(componentModel = "spring", uses = { AuthorMapper.class, TypeMapper.class, PublisherMapper.class })
public interface BookMapper extends EntityMapper<BookDTO, Book> {
    @Mapping(target = "authorBooks", source = "authorBooks", qualifiedByName = "idSet")
    @Mapping(target = "type", source = "type", qualifiedByName = "id")
    @Mapping(target = "publisher", source = "publisher", qualifiedByName = "id")
    BookDTO toDto(Book s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    BookDTO toDtoId(Book book);

    @Mapping(target = "removeAuthorBook", ignore = true)
    Book toEntity(BookDTO bookDTO);
}
