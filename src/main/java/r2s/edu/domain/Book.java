package r2s.edu.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A Book.
 */
@Entity
@Table(name = "book")
public class Book implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Lob
    @Column(name = "book_image_1")
    private byte[] bookImage1;

    @Column(name = "book_image_1_content_type")
    private String bookImage1ContentType;

    @Lob
    @Column(name = "book_image_2")
    private byte[] bookImage2;

    @Column(name = "book_image_2_content_type")
    private String bookImage2ContentType;

    @Lob
    @Column(name = "book_image_3")
    private byte[] bookImage3;

    @Column(name = "book_image_3_content_type")
    private String bookImage3ContentType;

    @Column(name = "cost")
    private String cost;

    @Column(name = "pub_date")
    private ZonedDateTime pubDate;

    @Column(name = "inventory_number")
    private Integer inventoryNumber;

    @Column(name = "product_update")
    private ZonedDateTime productUpdate;

    @Column(name = "discount")
    private Integer discount;

    @ManyToMany
    @JoinTable(
        name = "rel_book__author_book",
        joinColumns = @JoinColumn(name = "book_id"),
        inverseJoinColumns = @JoinColumn(name = "author_book_id")
    )
    @JsonIgnoreProperties(value = { "jobs" }, allowSetters = true)
    private Set<Author> authorBooks = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "typeBooks" }, allowSetters = true)
    private Type type;

    @ManyToOne
    @JsonIgnoreProperties(value = { "publisherBooks" }, allowSetters = true)
    private Publisher publisher;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Book id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public Book title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public byte[] getBookImage1() {
        return this.bookImage1;
    }

    public Book bookImage1(byte[] bookImage1) {
        this.setBookImage1(bookImage1);
        return this;
    }

    public void setBookImage1(byte[] bookImage1) {
        this.bookImage1 = bookImage1;
    }

    public String getBookImage1ContentType() {
        return this.bookImage1ContentType;
    }

    public Book bookImage1ContentType(String bookImage1ContentType) {
        this.bookImage1ContentType = bookImage1ContentType;
        return this;
    }

    public void setBookImage1ContentType(String bookImage1ContentType) {
        this.bookImage1ContentType = bookImage1ContentType;
    }

    public byte[] getBookImage2() {
        return this.bookImage2;
    }

    public Book bookImage2(byte[] bookImage2) {
        this.setBookImage2(bookImage2);
        return this;
    }

    public void setBookImage2(byte[] bookImage2) {
        this.bookImage2 = bookImage2;
    }

    public String getBookImage2ContentType() {
        return this.bookImage2ContentType;
    }

    public Book bookImage2ContentType(String bookImage2ContentType) {
        this.bookImage2ContentType = bookImage2ContentType;
        return this;
    }

    public void setBookImage2ContentType(String bookImage2ContentType) {
        this.bookImage2ContentType = bookImage2ContentType;
    }

    public byte[] getBookImage3() {
        return this.bookImage3;
    }

    public Book bookImage3(byte[] bookImage3) {
        this.setBookImage3(bookImage3);
        return this;
    }

    public void setBookImage3(byte[] bookImage3) {
        this.bookImage3 = bookImage3;
    }

    public String getBookImage3ContentType() {
        return this.bookImage3ContentType;
    }

    public Book bookImage3ContentType(String bookImage3ContentType) {
        this.bookImage3ContentType = bookImage3ContentType;
        return this;
    }

    public void setBookImage3ContentType(String bookImage3ContentType) {
        this.bookImage3ContentType = bookImage3ContentType;
    }

    public String getCost() {
        return this.cost;
    }

    public Book cost(String cost) {
        this.setCost(cost);
        return this;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public ZonedDateTime getPubDate() {
        return this.pubDate;
    }

    public Book pubDate(ZonedDateTime pubDate) {
        this.setPubDate(pubDate);
        return this;
    }

    public void setPubDate(ZonedDateTime pubDate) {
        this.pubDate = pubDate;
    }

    public Integer getInventoryNumber() {
        return this.inventoryNumber;
    }

    public Book inventoryNumber(Integer inventoryNumber) {
        this.setInventoryNumber(inventoryNumber);
        return this;
    }

    public void setInventoryNumber(Integer inventoryNumber) {
        this.inventoryNumber = inventoryNumber;
    }

    public ZonedDateTime getProductUpdate() {
        return this.productUpdate;
    }

    public Book productUpdate(ZonedDateTime productUpdate) {
        this.setProductUpdate(productUpdate);
        return this;
    }

    public void setProductUpdate(ZonedDateTime productUpdate) {
        this.productUpdate = productUpdate;
    }

    public Integer getDiscount() {
        return this.discount;
    }

    public Book discount(Integer discount) {
        this.setDiscount(discount);
        return this;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public Set<Author> getAuthorBooks() {
        return this.authorBooks;
    }

    public void setAuthorBooks(Set<Author> authors) {
        this.authorBooks = authors;
    }

    public Book authorBooks(Set<Author> authors) {
        this.setAuthorBooks(authors);
        return this;
    }

    public Book addAuthorBook(Author author) {
        this.authorBooks.add(author);
        author.getJobs().add(this);
        return this;
    }

    public Book removeAuthorBook(Author author) {
        this.authorBooks.remove(author);
        author.getJobs().remove(this);
        return this;
    }

    public Type getType() {
        return this.type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Book type(Type type) {
        this.setType(type);
        return this;
    }

    public Publisher getPublisher() {
        return this.publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public Book publisher(Publisher publisher) {
        this.setPublisher(publisher);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Book)) {
            return false;
        }
        return id != null && id.equals(((Book) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Book{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", bookImage1='" + getBookImage1() + "'" +
            ", bookImage1ContentType='" + getBookImage1ContentType() + "'" +
            ", bookImage2='" + getBookImage2() + "'" +
            ", bookImage2ContentType='" + getBookImage2ContentType() + "'" +
            ", bookImage3='" + getBookImage3() + "'" +
            ", bookImage3ContentType='" + getBookImage3ContentType() + "'" +
            ", cost='" + getCost() + "'" +
            ", pubDate='" + getPubDate() + "'" +
            ", inventoryNumber=" + getInventoryNumber() +
            ", productUpdate='" + getProductUpdate() + "'" +
            ", discount=" + getDiscount() +
            "}";
    }
}
