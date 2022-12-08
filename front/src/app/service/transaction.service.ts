import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import ITransaction from '../interfaces/ITransaction';
import ITransactionPayload from '../interfaces/ITransactionPayload';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root',
})
export class TransactionService {

  constructor(private http: HttpClient, private auth: AuthService) {}

  getAll() {
    return this.http.get<ITransaction[]>('http://localhost:8080/transaction', {
      headers: this.auth.buildHeader(),
    });
  }

  getAllPageable(page: number, size: number) {
    return this.http.get<any>(`http://localhost:8080/transaction/prettify?page=${page}&size=${size}`, {
      headers: this.auth.buildHeader(),
    });
  }

  create(data: ITransactionPayload) {
    return this.http.post<ITransaction[]>('http://localhost:8080/transaction', data, {
      headers: this.auth.buildHeader(),
    });
  }
}
