import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './order.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const OrderDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const orderEntity = useAppSelector(state => state.order.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="orderDetailsHeading">
          <Translate contentKey="mockProjectApp.order.detail.title">Order</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{orderEntity.id}</dd>
          <dt>
            <span id="orderDate">
              <Translate contentKey="mockProjectApp.order.orderDate">Order Date</Translate>
            </span>
          </dt>
          <dd>{orderEntity.orderDate ? <TextFormat value={orderEntity.orderDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="shipDate">
              <Translate contentKey="mockProjectApp.order.shipDate">Ship Date</Translate>
            </span>
          </dt>
          <dd>{orderEntity.shipDate ? <TextFormat value={orderEntity.shipDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="shipState">
              <Translate contentKey="mockProjectApp.order.shipState">Ship State</Translate>
            </span>
          </dt>
          <dd>{orderEntity.shipState ? 'true' : 'false'}</dd>
          <dt>
            <span id="shipAddress">
              <Translate contentKey="mockProjectApp.order.shipAddress">Ship Address</Translate>
            </span>
          </dt>
          <dd>{orderEntity.shipAddress}</dd>
          <dt>
            <span id="shipCost">
              <Translate contentKey="mockProjectApp.order.shipCost">Ship Cost</Translate>
            </span>
          </dt>
          <dd>{orderEntity.shipCost}</dd>
          <dt>
            <Translate contentKey="mockProjectApp.order.customer">Customer</Translate>
          </dt>
          <dd>{orderEntity.customer ? orderEntity.customer.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/order" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/order/${orderEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default OrderDetail;
