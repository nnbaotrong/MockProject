import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm, ValidatedBlobField } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IAuthor } from 'app/shared/model/author.model';
import { getEntities as getAuthors } from 'app/entities/author/author.reducer';
import { IType } from 'app/shared/model/type.model';
import { getEntities as getTypes } from 'app/entities/type/type.reducer';
import { IPublisher } from 'app/shared/model/publisher.model';
import { getEntities as getPublishers } from 'app/entities/publisher/publisher.reducer';
import { getEntity, updateEntity, createEntity, reset } from './book.reducer';
import { IBook } from 'app/shared/model/book.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const BookUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const authors = useAppSelector(state => state.author.entities);
  const types = useAppSelector(state => state.type.entities);
  const publishers = useAppSelector(state => state.publisher.entities);
  const bookEntity = useAppSelector(state => state.book.entity);
  const loading = useAppSelector(state => state.book.loading);
  const updating = useAppSelector(state => state.book.updating);
  const updateSuccess = useAppSelector(state => state.book.updateSuccess);
  const handleClose = () => {
    props.history.push('/book' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getAuthors({}));
    dispatch(getTypes({}));
    dispatch(getPublishers({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.pubDate = convertDateTimeToServer(values.pubDate);
    values.productUpdate = convertDateTimeToServer(values.productUpdate);

    const entity = {
      ...bookEntity,
      ...values,
      authorBooks: mapIdList(values.authorBooks),
      type: types.find(it => it.id.toString() === values.type.toString()),
      publisher: publishers.find(it => it.id.toString() === values.publisher.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          pubDate: displayDefaultDateTime(),
          productUpdate: displayDefaultDateTime(),
        }
      : {
          ...bookEntity,
          pubDate: convertDateTimeFromServer(bookEntity.pubDate),
          productUpdate: convertDateTimeFromServer(bookEntity.productUpdate),
          authorBooks: bookEntity?.authorBooks?.map(e => e.id.toString()),
          type: bookEntity?.type?.id,
          publisher: bookEntity?.publisher?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="mockProjectApp.book.home.createOrEditLabel" data-cy="BookCreateUpdateHeading">
            <Translate contentKey="mockProjectApp.book.home.createOrEditLabel">Create or edit a Book</Translate>
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
                  id="book-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField label={translate('mockProjectApp.book.title')} id="book-title" name="title" data-cy="title" type="text" />
              <ValidatedBlobField
                label={translate('mockProjectApp.book.bookImage1')}
                id="book-bookImage1"
                name="bookImage1"
                data-cy="bookImage1"
                isImage
                accept="image/*"
              />
              <ValidatedBlobField
                label={translate('mockProjectApp.book.bookImage2')}
                id="book-bookImage2"
                name="bookImage2"
                data-cy="bookImage2"
                isImage
                accept="image/*"
              />
              <ValidatedBlobField
                label={translate('mockProjectApp.book.bookImage3')}
                id="book-bookImage3"
                name="bookImage3"
                data-cy="bookImage3"
                isImage
                accept="image/*"
              />
              <ValidatedField label={translate('mockProjectApp.book.cost')} id="book-cost" name="cost" data-cy="cost" type="text" />
              <ValidatedField
                label={translate('mockProjectApp.book.pubDate')}
                id="book-pubDate"
                name="pubDate"
                data-cy="pubDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('mockProjectApp.book.inventoryNumber')}
                id="book-inventoryNumber"
                name="inventoryNumber"
                data-cy="inventoryNumber"
                type="text"
              />
              <ValidatedField
                label={translate('mockProjectApp.book.productUpdate')}
                id="book-productUpdate"
                name="productUpdate"
                data-cy="productUpdate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('mockProjectApp.book.discount')}
                id="book-discount"
                name="discount"
                data-cy="discount"
                type="text"
              />
              <ValidatedField
                label={translate('mockProjectApp.book.authorBook')}
                id="book-authorBook"
                data-cy="authorBook"
                type="select"
                multiple
                name="authorBooks"
              >
                <option value="" key="0" />
                {authors
                  ? authors.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="book-type" name="type" data-cy="type" label={translate('mockProjectApp.book.type')} type="select">
                <option value="" key="0" />
                {types
                  ? types.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="book-publisher"
                name="publisher"
                data-cy="publisher"
                label={translate('mockProjectApp.book.publisher')}
                type="select"
              >
                <option value="" key="0" />
                {publishers
                  ? publishers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/book" replace color="info">
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

export default BookUpdate;
