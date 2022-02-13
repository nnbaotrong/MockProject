import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './order-items.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const OrderItemsDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const orderItemsEntity = useAppSelector(state => state.orderItems.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="orderItemsDetailsHeading">
          <Translate contentKey="mockProjectApp.orderItems.detail.title">OrderItems</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{orderItemsEntity.id}</dd>
          <dt>
            <span id="quantity">
              <Translate contentKey="mockProjectApp.orderItems.quantity">Quantity</Translate>
            </span>
          </dt>
          <dd>{orderItemsEntity.quantity}</dd>
          <dt>
            <span id="unitPrice">
              <Translate contentKey="mockProjectApp.orderItems.unitPrice">Unit Price</Translate>
            </span>
          </dt>
          <dd>{orderItemsEntity.unitPrice}</dd>
          <dt>
            <Translate contentKey="mockProjectApp.orderItems.orderItemsOrder">Order Items Order</Translate>
          </dt>
          <dd>{orderItemsEntity.orderItemsOrder ? orderItemsEntity.orderItemsOrder.id : ''}</dd>
          <dt>
            <Translate contentKey="mockProjectApp.orderItems.orderItemsBook">Order Items Book</Translate>
          </dt>
          <dd>{orderItemsEntity.orderItemsBook ? orderItemsEntity.orderItemsBook.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/order-items" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/order-items/${orderItemsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default OrderItemsDetail;
