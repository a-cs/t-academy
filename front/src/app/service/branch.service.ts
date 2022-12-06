import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import IBranch from '../interfaces/IBranch';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class BranchService {

  constructor(private http: HttpClient, private auth:AuthService) { }

  get(){
    return this.http.get<IBranch[]>("http://localhost:8080/branch", {

      headers: this.auth.buildHeader()

    })
  }

  getById(id:number){
    return this.http.get<IBranch>(`http://localhost:8080/branch/${id}`, {

      headers: this.auth.buildHeader()

    })
  }

  getByLikeName(likeName: string) {
    return this.http.get<IBranch[]>(`http://localhost:8080/branch/search?term=${likeName}`, {

      headers: this.auth.buildHeader()
  })
}

  delete(id:number){
    return this.http.delete(`http://localhost:8080/branch/${id}`, {

      headers: this.auth.buildHeader()

    })
  }

  update(id:number, data: IBranch){
    return this.http.put<IBranch>(`http://localhost:8080/branch/${id}`, data, {

      headers: this.auth.buildHeader()

    })
  }

  create(data:IBranch){
    return this.http.post<IBranch>("http://localhost:8080/branch/",data, {

      headers: this.auth.buildHeader()

    })
  }

}
