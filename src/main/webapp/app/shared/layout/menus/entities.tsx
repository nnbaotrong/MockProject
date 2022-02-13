import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { Translate, translate } from 'react-jhipster';
import { NavDropdown } from './menu-components';

export const EntitiesMenu = props => (
  <NavDropdown
    icon="th-list"
    name={translate('global.menu.entities.main')}
    id="entity-menu"
    data-cy="entity"
    style={{ maxHeight: '80vh', overflow: 'auto' }}
  >
    <>{/* to avoid warnings when empty */}</>
    <MenuItem icon="asterisk" to="/book">
      <Translate contentKey="global.menu.entities.book" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/type">
      <Translate contentKey="global.menu.entities.type" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/publisher">
      <Translate contentKey="global.menu.entities.publisher" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/customer">
      <Translate contentKey="global.menu.entities.customer" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/order">
      <Translate contentKey="global.menu.entities.order" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/author">
      <Translate contentKey="global.menu.entities.author" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/order-items">
      <Translate contentKey="global.menu.entities.orderItems" />
    </MenuItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
