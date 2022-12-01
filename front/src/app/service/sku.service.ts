import { HttpClient } from '@angular/common/http'
import { Injectable } from '@angular/core';
import ISku from '../interfaces/ISku';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class SkuService {

  constructor(private http: HttpClient, private auth: AuthService) { }

  getByLikeName(name:string){
    return this.http.get<ISku[]>(`http://localhost:8080/sku/search?term=${name}`, {
      headers: this.auth.buildHeader()
    })
  }

  get(){
    return this.http.get<ISku[]>("http://localhost:8080/sku", {
      headers: this.auth.buildHeader()
    })
  }

  getById(id:number){
    return this.http.get<ISku>(`http://localhost:8080/sku/${id}`, {
      headers: this.auth.buildHeader()
    })
  }

  delete(id:number){
    return this.http.delete(`http://localhost:8080/sku/${id}`, {
      headers: this.auth.buildHeader()
    })
  }

  update(id:number, data: ISku){
    console.log("json!",data)
    return this.http.put<ISku>(`http://localhost:8080/sku/${id}`, data, {
      headers: this.auth.buildHeader()
    })
  }

  create(data:ISku){
    return this.http.post<ISku>("http://localhost:8080/sku", data, {
      headers: this.auth.buildHeader()
    })
  }
}
