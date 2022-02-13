package r2s.edu.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link r2s.edu.domain.Type} entity.
 */
public class TypeDTO implements Serializable {

    private Long id;

    private String typeName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TypeDTO)) {
            return false;
        }

        TypeDTO typeDTO = (TypeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, typeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TypeDTO{" +
            "id=" + getId() +
            ", typeName='" + getTypeName() + "'" +
            "}";
    }
}
