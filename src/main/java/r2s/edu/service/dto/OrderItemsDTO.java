package r2s.edu.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link r2s.edu.domain.OrderItems} entity.
 */
public class OrderItemsDTO implements Serializable {

    private Long id;

    private Integer quantity;

    private Integer unitPrice;

    private OrderDTO orderItemsOrder;

    private BookDTO orderItemsBook;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Integer unitPrice) {
        this.unitPrice = unitPrice;
    }

    public OrderDTO getOrderItemsOrder() {
        return orderItemsOrder;
    }

    public void setOrderItemsOrder(OrderDTO orderItemsOrder) {
        this.orderItemsOrder = orderItemsOrder;
    }

    public BookDTO getOrderItemsBook() {
        return orderItemsBook;
    }

    public void setOrderItemsBook(BookDTO orderItemsBook) {
        this.orderItemsBook = orderItemsBook;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderItemsDTO)) {
            return false;
        }

        OrderItemsDTO orderItemsDTO = (OrderItemsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, orderItemsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrderItemsDTO{" +
            "id=" + getId() +
            ", quantity=" + getQuantity() +
            ", unitPrice=" + getUnitPrice() +
            ", orderItemsOrder=" + getOrderItemsOrder() +
            ", orderItemsBook=" + getOrderItemsBook() +
            "}";
    }
}
