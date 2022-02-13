import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, openFile, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './book.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const BookDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const bookEntity = useAppSelector(state => state.book.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="bookDetailsHeading">
          <Translate contentKey="mockProjectApp.book.detail.title">Book</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{bookEntity.id}</dd>
          <dt>
            <span id="title">
              <Translate contentKey="mockProjectApp.book.title">Title</Translate>
            </span>
          </dt>
          <dd>{bookEntity.title}</dd>
          <dt>
            <span id="bookImage1">
              <Translate contentKey="mockProjectApp.book.bookImage1">Book Image 1</Translate>
            </span>
          </dt>
          <dd>
            {bookEntity.bookImage1 ? (
              <div>
                {bookEntity.bookImage1ContentType ? (
                  <a onClick={openFile(bookEntity.bookImage1ContentType, bookEntity.bookImage1)}>
                    <img src={`data:${bookEntity.bookImage1ContentType};base64,${bookEntity.bookImage1}`} style={{ maxHeight: '30px' }} />
                  </a>
                ) : null}
                <span>
                  {bookEntity.bookImage1ContentType}, {byteSize(bookEntity.bookImage1)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="bookImage2">
              <Translate contentKey="mockProjectApp.book.bookImage2">Book Image 2</Translate>
            </span>
          </dt>
          <dd>
            {bookEntity.bookImage2 ? (
              <div>
                {bookEntity.bookImage2ContentType ? (
                  <a onClick={openFile(bookEntity.bookImage2ContentType, bookEntity.bookImage2)}>
                    <img src={`data:${bookEntity.bookImage2ContentType};base64,${bookEntity.bookImage2}`} style={{ maxHeight: '30px' }} />
                  </a>
                ) : null}
                <span>
                  {bookEntity.bookImage2ContentType}, {byteSize(bookEntity.bookImage2)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="bookImage3">
              <Translate contentKey="mockProjectApp.book.bookImage3">Book Image 3</Translate>
            </span>
          </dt>
          <dd>
            {bookEntity.bookImage3 ? (
              <div>
                {bookEntity.bookImage3ContentType ? (
                  <a onClick={openFile(bookEntity.bookImage3ContentType, bookEntity.bookImage3)}>
                    <img src={`data:${bookEntity.bookImage3ContentType};base64,${bookEntity.bookImage3}`} style={{ maxHeight: '30px' }} />
                  </a>
                ) : null}
                <span>
                  {bookEntity.bookImage3ContentType}, {byteSize(bookEntity.bookImage3)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="cost">
              <Translate contentKey="mockProjectApp.book.cost">Cost</Translate>
            </span>
          </dt>
          <dd>{bookEntity.cost}</dd>
          <dt>
            <span id="pubDate">
              <Translate contentKey="mockProjectApp.book.pubDate">Pub Date</Translate>
            </span>
          </dt>
          <dd>{bookEntity.pubDate ? <TextFormat value={bookEntity.pubDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="inventoryNumber">
              <Translate contentKey="mockProjectApp.book.inventoryNumber">Inventory Number</Translate>
            </span>
          </dt>
          <dd>{bookEntity.inventoryNumber}</dd>
          <dt>
            <span id="productUpdate">
              <Translate contentKey="mockProjectApp.book.productUpdate">Product Update</Translate>
            </span>
          </dt>
          <dd>{bookEntity.productUpdate ? <TextFormat value={bookEntity.productUpdate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="discount">
              <Translate contentKey="mockProjectApp.book.discount">Discount</Translate>
            </span>
          </dt>
          <dd>{bookEntity.discount}</dd>
          <dt>
            <Translate contentKey="mockProjectApp.book.authorBook">Author Book</Translate>
          </dt>
          <dd>
            {bookEntity.authorBooks
              ? bookEntity.authorBooks.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {bookEntity.authorBooks && i === bookEntity.authorBooks.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>
            <Translate contentKey="mockProjectApp.book.type">Type</Translate>
          </dt>
          <dd>{bookEntity.type ? bookEntity.type.id : ''}</dd>
          <dt>
            <Translate contentKey="mockProjectApp.book.publisher">Publisher</Translate>
          </dt>
          <dd>{bookEntity.publisher ? bookEntity.publisher.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/book" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/book/${bookEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default BookDetail;
