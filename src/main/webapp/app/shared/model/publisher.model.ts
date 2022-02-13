import { IBook } from 'app/shared/model/book.model';

export interface IPublisher {
  id?: number;
  name?: string | null;
  address?: string | null;
  phoneNumber?: number | null;
  publisherBooks?: IBook[] | null;
}

export const defaultValue: Readonly<IPublisher> = {};
