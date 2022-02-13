package r2s.edu.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link r2s.edu.domain.Publisher} entity.
 */
public class PublisherDTO implements Serializable {

    private Long id;

    private String name;

    private String address;

    private Integer phoneNumber;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Integer phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PublisherDTO)) {
            return false;
        }

        PublisherDTO publisherDTO = (PublisherDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, publisherDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PublisherDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", address='" + getAddress() + "'" +
            ", phoneNumber=" + getPhoneNumber() +
            "}";
    }
}
