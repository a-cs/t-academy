import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import IClient from '../interfaces/IClient';

@Injectable({
  providedIn: 'root'
})
export class ClientService {

  constructor(private http:HttpClient) { }

  getByLikeName(name:string){
    return this.http.get<IClient[]>(`http://localhost:8080/client/search?term=${name}`)
  }

  get(){
    return this.http.get<IClient[]>("http://localhost:8080/client")
  }

  getById(id:number){
    return this.http.get<IClient>(`http://localhost:8080/client/${id}`)
  }

  delete(id:number){
    return this.http.delete(`http://localhost:8080/client/${id}`)
  }

  update(id:number, data: IClient){
    return this.http.put<IClient>(`http://localhost:8080/client/${id}`, data)
  }

  create(data:IClient){
    console.log(data)
    return this.http.post<IClient>("http://localhost:8080/client",data)
  }
}
