import { HttpClient } from '@angular/common/http';
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
}
