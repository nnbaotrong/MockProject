import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IOrder } from 'app/shared/model/order.model';
import { getEntities as getOrders } from 'app/entities/order/order.reducer';
import { IBook } from 'app/shared/model/book.model';
import { getEntities as getBooks } from 'app/entities/book/book.reducer';
import { getEntity, updateEntity, createEntity, reset } from './order-items.reducer';
import { IOrderItems } from 'app/shared/model/order-items.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const OrderItemsUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const orders = useAppSelector(state => state.order.entities);
  const books = useAppSelector(state => state.book.entities);
  const orderItemsEntity = useAppSelector(state => state.orderItems.entity);
  const loading = useAppSelector(state => state.orderItems.loading);
  const updating = useAppSelector(state => state.orderItems.updating);
  const updateSuccess = useAppSelector(state => state.orderItems.updateSuccess);
  const handleClose = () => {
    props.history.push('/order-items' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getOrders({}));
    dispatch(getBooks({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...orderItemsEntity,
      ...values,
      orderItemsOrder: orders.find(it => it.id.toString() === values.orderItemsOrder.toString()),
      orderItemsBook: books.find(it => it.id.toString() === values.orderItemsBook.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...orderItemsEntity,
          orderItemsOrder: orderItemsEntity?.orderItemsOrder?.id,
          orderItemsBook: orderItemsEntity?.orderItemsBook?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="mockProjectApp.orderItems.home.createOrEditLabel" data-cy="OrderItemsCreateUpdateHeading">
            <Translate contentKey="mockProjectApp.orderItems.home.createOrEditLabel">Create or edit a OrderItems</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="order-items-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('mockProjectApp.orderItems.quantity')}
                id="order-items-quantity"
                name="quantity"
                data-cy="quantity"
                type="text"
              />
              <ValidatedField
                label={translate('mockProjectApp.orderItems.unitPrice')}
                id="order-items-unitPrice"
                name="unitPrice"
                data-cy="unitPrice"
                type="text"
              />
              <ValidatedField
                id="order-items-orderItemsOrder"
                name="orderItemsOrder"
                data-cy="orderItemsOrder"
                label={translate('mockProjectApp.orderItems.orderItemsOrder')}
                type="select"
              >
                <option value="" key="0" />
                {orders
                  ? orders.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="order-items-orderItemsBook"
                name="orderItemsBook"
                data-cy="orderItemsBook"
                label={translate('mockProjectApp.orderItems.orderItemsBook')}
                type="select"
              >
                <option value="" key="0" />
                {books
                  ? books.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/order-items" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default OrderItemsUpdate;
