import { loadingBarReducer as loadingBar } from 'react-redux-loading-bar';

import locale from './locale';
import authentication from './authentication';
import applicationProfile from './application-profile';

import administration from 'app/modules/administration/administration.reducer';
import userManagement from 'app/modules/administration/user-management/user-management.reducer';
import register from 'app/modules/account/register/register.reducer';
import activate from 'app/modules/account/activate/activate.reducer';
import password from 'app/modules/account/password/password.reducer';
import settings from 'app/modules/account/settings/settings.reducer';
import passwordReset from 'app/modules/account/password-reset/password-reset.reducer';
// prettier-ignore
import book from 'app/entities/book/book.reducer';
// prettier-ignore
import type from 'app/entities/type/type.reducer';
// prettier-ignore
import publisher from 'app/entities/publisher/publisher.reducer';
// prettier-ignore
import customer from 'app/entities/customer/customer.reducer';
// prettier-ignore
import order from 'app/entities/order/order.reducer';
// prettier-ignore
import author from 'app/entities/author/author.reducer';
// prettier-ignore
import orderItems from 'app/entities/order-items/order-items.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const rootReducer = {
  authentication,
  locale,
  applicationProfile,
  administration,
  userManagement,
  register,
  activate,
  passwordReset,
  password,
  settings,
  book,
  type,
  publisher,
  customer,
  order,
  author,
  orderItems,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
  loadingBar,
};

export default rootReducer;
