import { HttpClient } from '@angular/common/http'
import { Injectable } from '@angular/core';
import ISku from '../interfaces/ISku';

@Injectable({
  providedIn: 'root'
})
export class SkuService {

  constructor(private http: HttpClient) { }

  get(){
    return this.http.get<ISku[]>("http://localhost:8080/sku")
  }

  getById(id:number){
    return this.http.get<ISku>(`http://localhost:8080/sku/${id}`)
  }

  delete(id:number){
    return this.http.delete(`http://localhost:8080/sku/${id}`)
  }

  update(id:number, data: ISku){
    return this.http.put<ISku>(`http://localhost:8080/sku/${id}`, data)
  }

  create(data:ISku){
    return this.http.post<ISku>("http://localhost:8080/sku",data)
  }
}
