import { HttpClient } from '@angular/common/http';
import { EventEmitter, Injectable } from '@angular/core';
import ICategory from '../interfaces/ICategory';
import { AuthService } from './auth.service';
import { ToastrService } from 'ngx-toastr';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class CategoryService {
  public categoryUpdatedOrDeleted = new EventEmitter<void>();

  constructor(
    private http: HttpClient,
    private auth: AuthService,
    private notification: ToastrService
  ) {}

  //get() {
  //return this.http.get<ICategory[]>('${environment.api}category', {
  //headers: this.auth.buildHeader(),
  //});
  //}

  get() {
    return this.http.get<ICategory[]>(`${environment.api}category`, {
      headers: this.auth.buildHeader(),
    });
  }

  //getPageable(page = 0, size = 10) {
  //let categoryPage = {
  //content: [],
  //length: 0,
  //};

  //return this.http.get<any>(
  //`${environment.api}category/pageable?page=${page}&size=${size}`,
  //{
  //headers: this.auth.buildHeader(),
  //}
  //);
  //}

  getPageable(page = 0, size = 10) {
    return this.http.get<any>(
      `${environment.api}category/pageable?page=${page}&size=${size}`,
      {
        headers: this.auth.buildHeader(),
      }
    );
  }

  getById(id: number) {
    return this.http.get<ICategory>(`${environment.api}category/${id}`, {
      headers: this.auth.buildHeader(),
    });
  }

  delete(id: number): void {
    this.http
      .delete(`${environment.api}category/${id}`, {
        headers: this.auth.buildHeader(),
      })
      .subscribe(
        (response) => {},
        (error) => {
          this.notification.error(error.error.message, 'Error!', {
            progressBar: true,
          });
        },
        () => {
          this.notification.warning(
            'Category was successfully deleted',
            'Deleted!',
            { progressBar: true }
          );
          this.categoryUpdatedOrDeleted.emit();
        }
      );
  }

  update(id: number, data: ICategory) {
    return this.http
      .put<ICategory>(`${environment.api}category/${id}`, data, {
        headers: this.auth.buildHeader(),
      })
      .subscribe(
        (response) => {},
        (error) => {
          this.notification.error(error.error.message, 'Error!', {
            progressBar: true,
          });
        },
        () => {
          this.notification.success(
            'Category was successfully updated',
            'Updated!',
            { progressBar: true }
          );
          this.categoryUpdatedOrDeleted.emit();
        }
      );
  }

  create(data: ICategory): void {
    this.http
      .post<ICategory>('${environment.api}category', data, {
        headers: this.auth.buildHeader(),
      })
      .subscribe(
        (respose) => {},
        (error) => {
          this.notification.error(error.error.message, 'Error', {
            progressBar: true,
          });
        },
        () => {
          this.notification.success(
            'Category successfully created',
            'Created!',
            {
              progressBar: true,
            }
          );
        }
      );
  }

  getByLikeName(searchString: string) {
    return this.http.get<any>(
      `${environment.api}category/search?term=${searchString}`,
      {
        headers: this.auth.buildHeader(),
      }
    );
  }
}
