import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './publisher.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const PublisherDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const publisherEntity = useAppSelector(state => state.publisher.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="publisherDetailsHeading">
          <Translate contentKey="mockProjectApp.publisher.detail.title">Publisher</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{publisherEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="mockProjectApp.publisher.name">Name</Translate>
            </span>
          </dt>
          <dd>{publisherEntity.name}</dd>
          <dt>
            <span id="address">
              <Translate contentKey="mockProjectApp.publisher.address">Address</Translate>
            </span>
          </dt>
          <dd>{publisherEntity.address}</dd>
          <dt>
            <span id="phoneNumber">
              <Translate contentKey="mockProjectApp.publisher.phoneNumber">Phone Number</Translate>
            </span>
          </dt>
          <dd>{publisherEntity.phoneNumber}</dd>
        </dl>
        <Button tag={Link} to="/publisher" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/publisher/${publisherEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default PublisherDetail;
