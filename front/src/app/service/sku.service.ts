import { HttpClient } from '@angular/common/http';
import { Injectable, EventEmitter } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import ISku from '../interfaces/ISku';
import { AuthService } from './auth.service';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class SkuService {
  public skuChanged = new EventEmitter<void>();

  constructor(private http: HttpClient, private auth: AuthService) {}

  getByLikeName(name: string, page: number, size: number) {
    return this.http.get<any>(
      `${environment.api}sku/search?term=${name}&page=${page}&size=${size}`,
      {
        headers: this.auth.buildHeader(),
      }
    );
  }

  getPageable(page: number, size: number) {
    return this.http.get<any>(
      `${environment.api}sku/pages?page=${page}&size=${size}`,
      {
        headers: this.auth.buildHeader(),
      }
    );
  }
  get() {
    return this.http.get<any>(`${environment.api}sku`, {
      headers: this.auth.buildHeader(),
    });
  }

  getById(id: number) {
    return this.http.get<ISku>(`${environment.api}sku/${id}`, {
      headers: this.auth.buildHeader(),
    });
  }

  delete(id: number) {
    return this.http.delete(`${environment.api}sku/${id}`, {
      headers: this.auth.buildHeader(),
    });
  }

  update(id: number, data: ISku) {
    return this.http.put<ISku>(`${environment.api}sku/${id}`, data, {
      headers: this.auth.buildHeader(),
    });
  }

  create(data: ISku) {
    return this.http.post<ISku>(`${environment.api}sku`, data, {
      headers: this.auth.buildHeader(),
    });
  }
}
