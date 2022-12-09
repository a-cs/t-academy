import { HttpClient } from '@angular/common/http';
import { Injectable, EventEmitter } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import ISku from '../interfaces/ISku';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root',
})
export class SkuService {
  public skuChanged = new EventEmitter<void>();

  constructor(private http: HttpClient, private auth: AuthService) {}

  getByLikeName(name: string, page: number, size: number) {
    return this.http.get<any>(
      `http://localhost:8080/sku/search?term=${name}&page=${page}&size=${size}`,
      {
        headers: this.auth.buildHeader(),
      }
    );
  }

  getPageable(page: number, size: number) {
    return this.http.get<any>(
      `http://localhost:8080/sku/pages?page=${page}&size=${size}`,
      {
        headers: this.auth.buildHeader(),
      }
    );
  }
  get() {
    return this.http.get<any>(`http://localhost:8080/sku`, {
      headers: this.auth.buildHeader(),
    });
  }

  getById(id: number) {
    return this.http.get<ISku>(`http://localhost:8080/sku/${id}`, {
      headers: this.auth.buildHeader(),
    });
  }

  delete(id: number) {
    return this.http.delete(`http://localhost:8080/sku/${id}`, {
      headers: this.auth.buildHeader(),
    });
  }

  update(id: number, data: ISku) {
    return this.http.put<ISku>(`http://localhost:8080/sku/${id}`, data, {
      headers: this.auth.buildHeader(),
    });
  }

  create(data: ISku) {
    return this.http.post<ISku>('http://localhost:8080/sku', data, {
      headers: this.auth.buildHeader(),
    });
  }
}
