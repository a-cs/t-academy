import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import ITransaction from '../interfaces/ITransaction';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class TransactionService {

  constructor(private http: HttpClient, private auth: AuthService) { }

  getAll() {
    return this.http.get<ITransaction[]>('http://localhost:8080/transaction', {
      headers: this.auth.buildHeader(),
    });
  }

}
