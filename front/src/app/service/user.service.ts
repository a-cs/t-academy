import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import IAccessLevel from '../interfaces/IAccessLevel';
import IOAuthResponse from '../interfaces/IOAuthResponse';
import IUser from '../interfaces/IUser';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient, private auth: AuthService) { }

  getByUsernameLike(name:string){
    return this.http.get<IUser[]>(`http://localhost:8080/user/search?username=${name}`)
  }

  get(){
    console.log(this.auth.buildHeader())
    return this.http.get<IUser[]>("http://localhost:8080/user/all", {
      headers: this.auth.buildHeader()
      // .set("Authorization", "Basic " + btoa("tsystems:tsPassword"))
    })
  }

  getById(id:number){
    return this.http.get<IUser>(`http://localhost:8080/user/${id}`)
  }

  // delete(id:number){
  //   return this.http.delete(`http://localhost:8080/user/${id}`)
  // }

  updateUser(data: IUser){
    console.log("json!",data)
    return this.http.put<IUser>(`http://localhost:8080/user/${data.id}`, data)
  }

  getRoles(){
    return this.http.get<IAccessLevel[]>(`http://localhost:8080/roles`)
  }

login(username: string, password: string) {
  const body = new HttpParams()
    .set("username", username)
    .set("password", password)
    .set("grant_type",  'password')

     // const body = new HttpParams().set("username", username).set("password", password)
     return this.http.post<IOAuthResponse>(`
        http://localhost:8080/oauth/token`,
        body.toString(), {
          headers: new HttpHeaders()
          .set("Content-Type", "application/x-www-form-urlencoded")
          .set("Authorization", "Basic " + btoa("tsystems:tsPassword"))
        })
   }


}
