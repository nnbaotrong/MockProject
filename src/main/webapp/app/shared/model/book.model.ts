import dayjs from 'dayjs';
import { IAuthor } from 'app/shared/model/author.model';
import { IType } from 'app/shared/model/type.model';
import { IPublisher } from 'app/shared/model/publisher.model';

export interface IBook {
  id?: number;
  title?: string | null;
  bookImage1ContentType?: string | null;
  bookImage1?: string | null;
  bookImage2ContentType?: string | null;
  bookImage2?: string | null;
  bookImage3ContentType?: string | null;
  bookImage3?: string | null;
  cost?: string | null;
  pubDate?: string | null;
  inventoryNumber?: number | null;
  productUpdate?: string | null;
  discount?: number | null;
  authorBooks?: IAuthor[] | null;
  type?: IType | null;
  publisher?: IPublisher | null;
}

export const defaultValue: Readonly<IBook> = {};
