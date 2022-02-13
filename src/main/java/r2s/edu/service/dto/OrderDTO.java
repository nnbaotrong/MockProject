package r2s.edu.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link r2s.edu.domain.Order} entity.
 */
public class OrderDTO implements Serializable {

    private Long id;

    private ZonedDateTime orderDate;

    private ZonedDateTime shipDate;

    private Boolean shipState;

    private String shipAddress;

    private Integer shipCost;

    private CustomerDTO customer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(ZonedDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public ZonedDateTime getShipDate() {
        return shipDate;
    }

    public void setShipDate(ZonedDateTime shipDate) {
        this.shipDate = shipDate;
    }

    public Boolean getShipState() {
        return shipState;
    }

    public void setShipState(Boolean shipState) {
        this.shipState = shipState;
    }

    public String getShipAddress() {
        return shipAddress;
    }

    public void setShipAddress(String shipAddress) {
        this.shipAddress = shipAddress;
    }

    public Integer getShipCost() {
        return shipCost;
    }

    public void setShipCost(Integer shipCost) {
        this.shipCost = shipCost;
    }

    public CustomerDTO getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDTO customer) {
        this.customer = customer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderDTO)) {
            return false;
        }

        OrderDTO orderDTO = (OrderDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, orderDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrderDTO{" +
            "id=" + getId() +
            ", orderDate='" + getOrderDate() + "'" +
            ", shipDate='" + getShipDate() + "'" +
            ", shipState='" + getShipState() + "'" +
            ", shipAddress='" + getShipAddress() + "'" +
            ", shipCost=" + getShipCost() +
            ", customer=" + getCustomer() +
            "}";
    }
}
