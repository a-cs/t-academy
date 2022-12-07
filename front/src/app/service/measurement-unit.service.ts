import { HttpClient } from '@angular/common/http';
import { Injectable, EventEmitter } from '@angular/core';
import IMeasurementUnit from '../interfaces/IMeasurementUnit';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root',
})
export class MeasurementUnitService {
  public measurementUnitChanged = new EventEmitter<void>();

  constructor(private http: HttpClient, private auth: AuthService) {}

  get() {
    return this.http.get<IMeasurementUnit[]>(
      'http://localhost:8080/measurement-unit',
      {
        headers: this.auth.buildHeader(),
      }
    );
  }

  getPageable(page: number, size: number) {
    return this.http.get<IMeasurementUnit[]>(
      `http://localhost:8080/measurement-unit/pages?page=${page}&size=${size}`,
      {
        headers: this.auth.buildHeader(),
      }
    );
  }

  getById(id: number) {
    return this.http.get<IMeasurementUnit>(
      `http://localhost:8080/measurement-unit/${id}`,
      {
        headers: this.auth.buildHeader(),
      }
    );
  }

  getByLikeName(likeName: string) {
    return this.http.get<IMeasurementUnit[]>(
      `http://localhost:8080/measurement-unit/search?term=${likeName}`,
      {
        headers: this.auth.buildHeader(),
      }
    );
  }

  delete(id: number) {
    return this.http.delete(`http://localhost:8080/measurement-unit/${id}`, {
      headers: this.auth.buildHeader(),
    });
  }

  update(id: number, data: IMeasurementUnit) {
    return this.http.put<IMeasurementUnit>(
      `http://localhost:8080/measurement-unit/${id}`,
      data,
      {
        headers: this.auth.buildHeader(),
      }
    );
  }

  create(data: IMeasurementUnit) {
    return this.http.post<IMeasurementUnit>(
      'http://localhost:8080/measurement-unit',
      data,
      {
        headers: this.auth.buildHeader(),
      }
    );
  }
}
