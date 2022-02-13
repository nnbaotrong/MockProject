package r2s.edu.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Lob;

/**
 * A DTO for the {@link r2s.edu.domain.Book} entity.
 */
public class BookDTO implements Serializable {

    private Long id;

    private String title;

    @Lob
    private byte[] bookImage1;

    private String bookImage1ContentType;

    @Lob
    private byte[] bookImage2;

    private String bookImage2ContentType;

    @Lob
    private byte[] bookImage3;

    private String bookImage3ContentType;
    private String cost;

    private ZonedDateTime pubDate;

    private Integer inventoryNumber;

    private ZonedDateTime productUpdate;

    private Integer discount;

    private Set<AuthorDTO> authorBooks = new HashSet<>();

    private TypeDTO type;

    private PublisherDTO publisher;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public byte[] getBookImage1() {
        return bookImage1;
    }

    public void setBookImage1(byte[] bookImage1) {
        this.bookImage1 = bookImage1;
    }

    public String getBookImage1ContentType() {
        return bookImage1ContentType;
    }

    public void setBookImage1ContentType(String bookImage1ContentType) {
        this.bookImage1ContentType = bookImage1ContentType;
    }

    public byte[] getBookImage2() {
        return bookImage2;
    }

    public void setBookImage2(byte[] bookImage2) {
        this.bookImage2 = bookImage2;
    }

    public String getBookImage2ContentType() {
        return bookImage2ContentType;
    }

    public void setBookImage2ContentType(String bookImage2ContentType) {
        this.bookImage2ContentType = bookImage2ContentType;
    }

    public byte[] getBookImage3() {
        return bookImage3;
    }

    public void setBookImage3(byte[] bookImage3) {
        this.bookImage3 = bookImage3;
    }

    public String getBookImage3ContentType() {
        return bookImage3ContentType;
    }

    public void setBookImage3ContentType(String bookImage3ContentType) {
        this.bookImage3ContentType = bookImage3ContentType;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public ZonedDateTime getPubDate() {
        return pubDate;
    }

    public void setPubDate(ZonedDateTime pubDate) {
        this.pubDate = pubDate;
    }

    public Integer getInventoryNumber() {
        return inventoryNumber;
    }

    public void setInventoryNumber(Integer inventoryNumber) {
        this.inventoryNumber = inventoryNumber;
    }

    public ZonedDateTime getProductUpdate() {
        return productUpdate;
    }

    public void setProductUpdate(ZonedDateTime productUpdate) {
        this.productUpdate = productUpdate;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public Set<AuthorDTO> getAuthorBooks() {
        return authorBooks;
    }

    public void setAuthorBooks(Set<AuthorDTO> authorBooks) {
        this.authorBooks = authorBooks;
    }

    public TypeDTO getType() {
        return type;
    }

    public void setType(TypeDTO type) {
        this.type = type;
    }

    public PublisherDTO getPublisher() {
        return publisher;
    }

    public void setPublisher(PublisherDTO publisher) {
        this.publisher = publisher;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BookDTO)) {
            return false;
        }

        BookDTO bookDTO = (BookDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, bookDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BookDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", bookImage1='" + getBookImage1() + "'" +
            ", bookImage2='" + getBookImage2() + "'" +
            ", bookImage3='" + getBookImage3() + "'" +
            ", cost='" + getCost() + "'" +
            ", pubDate='" + getPubDate() + "'" +
            ", inventoryNumber=" + getInventoryNumber() +
            ", productUpdate='" + getProductUpdate() + "'" +
            ", discount=" + getDiscount() +
            ", authorBooks=" + getAuthorBooks() +
            ", type=" + getType() +
            ", publisher=" + getPublisher() +
            "}";
    }
}
