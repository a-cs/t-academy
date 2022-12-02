import { HttpClient } from '@angular/common/http';
import { EventEmitter, Injectable } from '@angular/core';
import ICategory from '../interfaces/ICategory';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root',
})
export class CategoryService {
  public categoryUpdatedOrDeleted = new EventEmitter<void>();
  constructor(private http: HttpClient, private auth: AuthService) {}

  get() {
    return this.http.get<ICategory[]>('http://localhost:8080/category', {
      headers: this.auth.buildHeader(),
    });
  }

  getById(id: number) {
    return this.http.get<ICategory>(`http://localhost:8080/category/${id}`, {
      headers: this.auth.buildHeader(),
    });
  }

  delete(id: number) {
    return this.http.delete(`http://localhost:8080/category/${id}`, {
      headers: this.auth.buildHeader(),
    });
  }

  update(id: number, data: ICategory) {
    return this.http.put<ICategory>(
      `http://localhost:8080/category/${id}`,
      data,
      { headers: this.auth.buildHeader() }
    );
  }

  create(data: ICategory) {
    return this.http.post<ICategory>('http://localhost:8080/category', data, {
      headers: this.auth.buildHeader(),
    });
  }

  getByLikeName(searchString: string) {
    return this.http.get<ICategory[]>(
      `http://localhost:8080/category/search?term=${searchString}`,
      {
        headers: this.auth.buildHeader(),
      }
    );
  }
}
