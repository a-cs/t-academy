import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import ITransaction from '../interfaces/ITransaction';
import ITransactionPayload from '../interfaces/ITransactionPayload';
import { AuthService } from './auth.service';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class TransactionService {

  constructor(private http: HttpClient, private auth: AuthService) {}

  getAll() {
    return this.http.get<ITransaction[]>(`${environment.api}transaction`, {
      headers: this.auth.buildHeader(),
    });
  }

  getAllPageable(page: number, size: number) {
    return this.http.get<any>(`${environment.api}transaction/prettify?page=${page}&size=${size}`, {
      headers: this.auth.buildHeader(),
    });
  }

  create(data: ITransactionPayload) {
    return this.http.post<ITransaction[]>(`${environment.api}transaction`, data, {
      headers: this.auth.buildHeader(),
    });
  }
}
