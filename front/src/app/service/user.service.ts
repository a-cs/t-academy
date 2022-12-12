import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { EventEmitter, Injectable } from '@angular/core';
import IAccessLevel from '../interfaces/IAccessLevel';
import IOAuthResponse from '../interfaces/IOAuthResponse';
import IUser from '../interfaces/IUser';
import { AuthService } from './auth.service';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class UserService {

  public userChanged = new EventEmitter<void>()

  constructor(private http: HttpClient, private auth: AuthService) {}

  getByUsernameLike(name: string, pageSize: number, pageIdx: number) {
    return this.http.get<any>(
      `${environment.api}user/search?username=${name}&size=${pageSize}&page=${pageIdx}`,
      {
        headers: this.auth.buildHeader(),
      }
    );
  }

  get() {
    console.log(this.auth.buildHeader());
    return this.http.get<IUser[]>('${environment.api}user/all', {
      headers: this.auth.buildHeader(),
    });
  }

  getById(id: number) {
    return this.http.get<IUser>(`${environment.api}user/${id}`, {
      headers: this.auth.buildHeader(),
    });
  }

  updateUser(data: IUser) {
    console.log('json!', data);
    return this.http.put<IUser>(`${environment.api}user/${data.id}`, data, {
      headers: this.auth.buildHeader(),
    });
  }

  getRoles() {
    return this.http.get<IAccessLevel[]>(`${environment.api}roles`, {
      headers: this.auth.buildHeader()
    })
  }

  changePassword(token:string, password:string){
    const body = new HttpParams()
    .set("token", token)
    .set("password", password)
    return this.http.put<any>(`${environment.api}user/setpassword`, body)

  }

  forgotPassword(email:string){
    return this.http.get<any>(`${environment.api}user/resetpassword?email=${email}`)
  }

  createUser(data: IUser){
    console.log('json!', data);
    return this.http.post<IUser>(`${environment.api}user/signup`, data, {
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
        ${environment.api}oauth/token`,
      body.toString(),
      {
        headers: new HttpHeaders()
          .set('Content-Type', 'application/x-www-form-urlencoded')
          .set('Authorization', 'Basic ' + btoa('tsystems:tsPassword')),
      }
    );
  }
}
