import { IOrder } from 'app/shared/model/order.model';
import { IBook } from 'app/shared/model/book.model';

export interface IOrderItems {
  id?: number;
  quantity?: number | null;
  unitPrice?: number | null;
  orderItemsOrder?: IOrder | null;
  orderItemsBook?: IBook | null;
}

export const defaultValue: Readonly<IOrderItems> = {};
