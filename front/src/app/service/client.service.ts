import { HttpClient } from '@angular/common/http';
import { EventEmitter, Injectable } from '@angular/core';
import IClient from '../interfaces/IClient';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class ClientService {

  public clientChanged = new EventEmitter<void>();

  constructor(private http:HttpClient, private auth:AuthService) { }

  getByLikeName(name:string, page: number, size: number){
    return this.http.get<any>(`http://localhost:8080/client/search?term=${name}&page=${page}&size=${size}`, {

      headers: this.auth.buildHeader()

    })
  }

  getPageable(page: number, size: number){
    return this.http.get<any>(`http://localhost:8080/client/pages?page=${page}&size=${size}`, {

      headers: this.auth.buildHeader()

    })
  }

  get(){
    return this.http.get<IClient[]>(`http://localhost:8080/client`, {

      headers: this.auth.buildHeader()

    })
  }

  getById(id:number){
    return this.http.get<IClient>(`http://localhost:8080/client/${id}`, {

      headers: this.auth.buildHeader()

    })
  }

  delete(id:number){
    return this.http.delete(`http://localhost:8080/client/${id}`, {

      headers: this.auth.buildHeader()

    })
  }

  update(id:number, data: IClient){
    return this.http.put<IClient>(`http://localhost:8080/client/${id}`, data, {

      headers: this.auth.buildHeader()

    })
  }

  create(data:IClient){
    console.log(data)
    return this.http.post<IClient>("http://localhost:8080/client",data, {

      headers: this.auth.buildHeader()

    })
  }
}
