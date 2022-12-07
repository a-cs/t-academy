import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import IAccessLevel from '../interfaces/IAccessLevel';
import IOAuthResponse from '../interfaces/IOAuthResponse';
import IUser from '../interfaces/IUser';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  constructor(private http: HttpClient, private auth: AuthService) {}

  getByUsernameLike(name: string, pageSize: number, pageIdx: number) {
    return this.http.get<any>(
      `http://localhost:8080/user/search?username=${name}&size=${pageSize}&page=${pageIdx}`,
      {
        headers: this.auth.buildHeader(),
      }
    );
  }

  get() {
    console.log(this.auth.buildHeader());
    return this.http.get<IUser[]>('http://localhost:8080/user/all', {
      headers: this.auth.buildHeader(),
    });
  }

  getById(id: number) {
    return this.http.get<IUser>(`http://localhost:8080/user/${id}`, {
      headers: this.auth.buildHeader(),
    });
  }

  updateUser(data: IUser) {
    console.log('json!', data);
    return this.http.put<IUser>(`http://localhost:8080/user/${data.id}`, data, {
      headers: this.auth.buildHeader(),
    });
  }

  getRoles() {
    return this.http.get<IAccessLevel[]>(`http://localhost:8080/roles`, {
      headers: this.auth.buildHeader()
    })
  }

  changePassword(token:string, password:string){
    const body = new HttpParams()
    .set("token", token)
    .set("password", password)
    return this.http.put<any>(`http://localhost:8080/user/setpassword`, body)

  }

  createUser(data: IUser){
    console.log('json!', data);
    return this.http.post<IUser>(`http://localhost:8080/user/signup`, data, {
      headers: this.auth.buildHeader()
    });
  }

  login(username: string, password: string) {
    const body = new HttpParams()
      .set('username', username)
      .set('password', password)
      .set('grant_type', 'password');

    return this.http.post<IOAuthResponse>(
      `
        http://localhost:8080/oauth/token`,
      body.toString(),
      {
        headers: new HttpHeaders()
          .set('Content-Type', 'application/x-www-form-urlencoded')
          .set('Authorization', 'Basic ' + btoa('tsystems:tsPassword')),
      }
    );
  }
}
