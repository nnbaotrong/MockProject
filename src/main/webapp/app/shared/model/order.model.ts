import dayjs from 'dayjs';
import { ICustomer } from 'app/shared/model/customer.model';

export interface IOrder {
  id?: number;
  orderDate?: string | null;
  shipDate?: string | null;
  shipState?: boolean | null;
  shipAddress?: string | null;
  shipCost?: number | null;
  customer?: ICustomer | null;
}

export const defaultValue: Readonly<IOrder> = {
  shipState: false,
};
