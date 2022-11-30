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
  
}
