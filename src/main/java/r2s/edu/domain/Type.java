package r2s.edu.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A Type.
 */
@Entity
@Table(name = "type")
public class Type implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "type_name")
    private String typeName;

    @OneToMany(mappedBy = "type")
    @JsonIgnoreProperties(value = { "authorBooks", "type", "publisher" }, allowSetters = true)
    private Set<Book> typeBooks = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Type id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTypeName() {
        return this.typeName;
    }

    public Type typeName(String typeName) {
        this.setTypeName(typeName);
        return this;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Set<Book> getTypeBooks() {
        return this.typeBooks;
    }

    public void setTypeBooks(Set<Book> books) {
        if (this.typeBooks != null) {
            this.typeBooks.forEach(i -> i.setType(null));
        }
        if (books != null) {
            books.forEach(i -> i.setType(this));
        }
        this.typeBooks = books;
    }

    public Type typeBooks(Set<Book> books) {
        this.setTypeBooks(books);
        return this;
    }

    public Type addTypeBook(Book book) {
        this.typeBooks.add(book);
        book.setType(this);
        return this;
    }

    public Type removeTypeBook(Book book) {
        this.typeBooks.remove(book);
        book.setType(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Type)) {
            return false;
        }
        return id != null && id.equals(((Type) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Type{" +
            "id=" + getId() +
            ", typeName='" + getTypeName() + "'" +
            "}";
    }
}
