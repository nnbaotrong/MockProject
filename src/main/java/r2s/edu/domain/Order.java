package r2s.edu.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.*;

/**
 * A Order.
 */
@Entity
@Table(name = "jhi_order")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "order_date")
    private ZonedDateTime orderDate;

    @Column(name = "ship_date")
    private ZonedDateTime shipDate;

    @Column(name = "ship_state")
    private Boolean shipState;

    @Column(name = "ship_address")
    private String shipAddress;

    @Column(name = "ship_cost")
    private Integer shipCost;

    @ManyToOne
    @JsonIgnoreProperties(value = { "customerLevels" }, allowSetters = true)
    private Customer customer;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Order id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getOrderDate() {
        return this.orderDate;
    }

    public Order orderDate(ZonedDateTime orderDate) {
        this.setOrderDate(orderDate);
        return this;
    }

    public void setOrderDate(ZonedDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public ZonedDateTime getShipDate() {
        return this.shipDate;
    }

    public Order shipDate(ZonedDateTime shipDate) {
        this.setShipDate(shipDate);
        return this;
    }

    public void setShipDate(ZonedDateTime shipDate) {
        this.shipDate = shipDate;
    }

    public Boolean getShipState() {
        return this.shipState;
    }

    public Order shipState(Boolean shipState) {
        this.setShipState(shipState);
        return this;
    }

    public void setShipState(Boolean shipState) {
        this.shipState = shipState;
    }

    public String getShipAddress() {
        return this.shipAddress;
    }

    public Order shipAddress(String shipAddress) {
        this.setShipAddress(shipAddress);
        return this;
    }

    public void setShipAddress(String shipAddress) {
        this.shipAddress = shipAddress;
    }

    public Integer getShipCost() {
        return this.shipCost;
    }

    public Order shipCost(Integer shipCost) {
        this.setShipCost(shipCost);
        return this;
    }

    public void setShipCost(Integer shipCost) {
        this.shipCost = shipCost;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Order customer(Customer customer) {
        this.setCustomer(customer);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Order)) {
            return false;
        }
        return id != null && id.equals(((Order) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Order{" +
            "id=" + getId() +
            ", orderDate='" + getOrderDate() + "'" +
            ", shipDate='" + getShipDate() + "'" +
            ", shipState='" + getShipState() + "'" +
            ", shipAddress='" + getShipAddress() + "'" +
            ", shipCost=" + getShipCost() +
            "}";
    }
}
