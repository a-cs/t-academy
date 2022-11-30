import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import IBranch from '../interfaces/IBranch';

@Injectable({
  providedIn: 'root'
})
export class BranchService {

  constructor(private http: HttpClient) { }

  get(){
    return this.http.get<IBranch[]>("http://localhost:8080/branch")
  }

  getById(id:number){
    return this.http.get<IBranch>(`http://localhost:8080/branch/${id}`)
  }

  delete(id:number){
    return this.http.delete(`http://localhost:8080/branch/${id}`)
  }

  update(id:number, data: IBranch){
    return this.http.put<IBranch>(`http://localhost:8080/branch/${id}`, data)
  }

  create(data:IBranch){
    return this.http.post<IBranch>("http://localhost:8080/branch",data)
  }

}
