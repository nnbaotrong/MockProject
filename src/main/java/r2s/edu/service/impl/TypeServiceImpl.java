package r2s.edu.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import r2s.edu.domain.Type;
import r2s.edu.repository.TypeRepository;
import r2s.edu.service.TypeService;
import r2s.edu.service.dto.TypeDTO;
import r2s.edu.service.mapper.TypeMapper;

/**
 * Service Implementation for managing {@link Type}.
 */
@Service
@Transactional
public class TypeServiceImpl implements TypeService {

    private final Logger log = LoggerFactory.getLogger(TypeServiceImpl.class);

    private final TypeRepository typeRepository;

    private final TypeMapper typeMapper;

    public TypeServiceImpl(TypeRepository typeRepository, TypeMapper typeMapper) {
        this.typeRepository = typeRepository;
        this.typeMapper = typeMapper;
    }

    @Override
    public TypeDTO save(TypeDTO typeDTO) {
        log.debug("Request to save Type : {}", typeDTO);
        Type type = typeMapper.toEntity(typeDTO);
        type = typeRepository.save(type);
        return typeMapper.toDto(type);
    }

    @Override
    public Optional<TypeDTO> partialUpdate(TypeDTO typeDTO) {
        log.debug("Request to partially update Type : {}", typeDTO);

        return typeRepository
            .findById(typeDTO.getId())
            .map(existingType -> {
                typeMapper.partialUpdate(existingType, typeDTO);

                return existingType;
            })
            .map(typeRepository::save)
            .map(typeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Types");
        return typeRepository.findAll(pageable).map(typeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TypeDTO> findOne(Long id) {
        log.debug("Request to get Type : {}", id);
        return typeRepository.findById(id).map(typeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Type : {}", id);
        typeRepository.deleteById(id);
    }
}
