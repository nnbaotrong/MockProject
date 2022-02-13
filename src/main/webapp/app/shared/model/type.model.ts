import { IBook } from 'app/shared/model/book.model';

export interface IType {
  id?: number;
  typeName?: string | null;
  typeBooks?: IBook[] | null;
}

export const defaultValue: Readonly<IType> = {};
