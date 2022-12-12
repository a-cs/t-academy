import { HttpClient } from '@angular/common/http';
import { Injectable, EventEmitter } from '@angular/core';
import IClient from '../interfaces/IClient';
import { AuthService } from './auth.service';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class ClientService {
  public clientChanged = new EventEmitter<void>();

  constructor(private http:HttpClient, private auth:AuthService) { }

  getByLikeName(name: string, page: number, size: number) {
    return this.http.get<any>(
      `${environment.api}client/search?term=${name}&page=${page}&size=${size}`,
      {
        headers: this.auth.buildHeader(),
      }
    );
  }

  getPageable(page: number, size: number) {
    return this.http.get<any>(
      `${environment.api}client/pages?page=${page}&size=${size}`,
      {
        headers: this.auth.buildHeader(),
      }
    );
  }

  get() {
    return this.http.get<IClient[]>(`${environment.api}client`, {
      headers: this.auth.buildHeader(),
    });
  }

  getById(id: number) {
    return this.http.get<IClient>(`${environment.api}client/${id}`, {
      headers: this.auth.buildHeader(),
    });
  }

  delete(id: number) {
    return this.http.delete(`${environment.api}client/${id}`, {
      headers: this.auth.buildHeader(),
    });
  }

  update(id: number, data: IClient) {
    return this.http.put<IClient>(`${environment.api}client/${id}`, data, {
      headers: this.auth.buildHeader(),
    });
  }

  create(data: IClient) {
    console.log(data);
    return this.http.post<IClient>(`${environment.api}client`, data, {
      headers: this.auth.buildHeader(),
    });
  }
}
