import { HttpClient } from '@angular/common/http'
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class SkuService {

  constructor(private http: HttpClient) { }

  get(){
    return this.http.get<[any]>("http://localhost:8080/sku")
  }

  getById(id:number){
    return this.http.get<any>(`http://localhost:8080/sku/${id}`)
  }

  delete(id:number){
    return this.http.delete(`http://localhost:8080/sku/${id}`)
  }
  
  update(id:number, dados: any){
    return this.http.put<any>(`http://localhost:8080/sku/${id}`, dados)
  }
  
  create(dados:any){
    return this.http.post<any>("http://localhost:8080/sku",dados)
  }
}
