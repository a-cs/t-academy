import { Injectable, EventEmitter } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import IBranch from '../interfaces/IBranch';
import { AuthService } from './auth.service';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class BranchService {
  public branchChanged = new EventEmitter<void>();

  constructor(private http: HttpClient, private auth: AuthService) {}

  get() {
    return this.http.get<IBranch[]>(`${environment.api}branch`, {
      headers: this.auth.buildHeader(),
    });
  }

  getById(id: number) {
    return this.http.get<IBranch>(`${environment.api}branch/${id}`, {
      headers: this.auth.buildHeader(),
    });
  }

  getByLikeName(likeName: string) {
    return this.http.get<IBranch[]>(
      `${environment.api}branch/search?term=${likeName}`,
      {
        headers: this.auth.buildHeader(),
      }
    );
  }

  delete(id: number) {
    return this.http.delete(`${environment.api}branch/${id}`, {
      headers: this.auth.buildHeader(),
    });
  }

  update(id: number, data: IBranch) {
    return this.http.put<IBranch>(`${environment.api}branch/${id}`, data, {
      headers: this.auth.buildHeader(),
    });
  }

  create(data: IBranch) {
    return this.http.post<IBranch>(`${environment.api}branch/`, data, {
      headers: this.auth.buildHeader(),
    });
  }
}
