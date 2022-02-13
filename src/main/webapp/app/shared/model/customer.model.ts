import dayjs from 'dayjs';
import { IOrder } from 'app/shared/model/order.model';

export interface ICustomer {
  id?: number;
  firstName?: string | null;
  lastName?: string | null;
  birthday?: string | null;
  phoneNumber?: string | null;
  address?: string | null;
  information?: string | null;
  userName?: string | null;
  password?: string | null;
  customerLevels?: IOrder[] | null;
}

export const defaultValue: Readonly<ICustomer> = {};
