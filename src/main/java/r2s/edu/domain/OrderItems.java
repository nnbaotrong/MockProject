package r2s.edu.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;

/**
 * A OrderItems.
 */
@Entity
@Table(name = "order_items")
public class OrderItems implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "unit_price")
    private Integer unitPrice;

    @ManyToOne
    @JsonIgnoreProperties(value = { "customer" }, allowSetters = true)
    private Order orderItemsOrder;

    @ManyToOne
    @JsonIgnoreProperties(value = { "authorBooks", "type", "publisher" }, allowSetters = true)
    private Book orderItemsBook;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public OrderItems id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public OrderItems quantity(Integer quantity) {
        this.setQuantity(quantity);
        return this;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getUnitPrice() {
        return this.unitPrice;
    }

    public OrderItems unitPrice(Integer unitPrice) {
        this.setUnitPrice(unitPrice);
        return this;
    }

    public void setUnitPrice(Integer unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Order getOrderItemsOrder() {
        return this.orderItemsOrder;
    }

    public void setOrderItemsOrder(Order order) {
        this.orderItemsOrder = order;
    }

    public OrderItems orderItemsOrder(Order order) {
        this.setOrderItemsOrder(order);
        return this;
    }

    public Book getOrderItemsBook() {
        return this.orderItemsBook;
    }

    public void setOrderItemsBook(Book book) {
        this.orderItemsBook = book;
    }

    public OrderItems orderItemsBook(Book book) {
        this.setOrderItemsBook(book);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderItems)) {
            return false;
        }
        return id != null && id.equals(((OrderItems) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrderItems{" +
            "id=" + getId() +
            ", quantity=" + getQuantity() +
            ", unitPrice=" + getUnitPrice() +
            "}";
    }
}
