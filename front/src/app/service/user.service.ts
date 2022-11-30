import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import IAccessLevel from '../interfaces/IAccessLevel';
import IUser from '../interfaces/IUser';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) { }

  getByUsernameLike(name:string){
    return this.http.get<IUser[]>(`http://localhost:8080/user/search?username=${name}`)
  }

  get(){
    return this.http.get<IUser[]>("http://localhost:8080/user/all")
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

login() {
  const body = new HttpParams()
    .set("username", "operator_user")
    .set("password", "123456")
    .set("grant_type",  'password')
     
     // const body = new HttpParams().set("username", username).set("password", password)
     return this.http.post<any>(`
        http://localhost:8080/oauth/token`,
        body.toString(), {
          headers: new HttpHeaders()
          .set("Content-Type", "application/x-www-form-urlencoded")
          .set("Authorization", "Basic " + btoa("tsystems:tsPassword"))
        })
   }


}
