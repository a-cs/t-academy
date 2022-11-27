import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import IMeasurementUnit from '../interfaces/IMeasurementUnit';

@Injectable({
  providedIn: 'root'
})
export class MeasurementUnitService {

  constructor(private http: HttpClient) { }

  get(){
    return this.http.get<IMeasurementUnit[]>("http://localhost:8080/measurement-unit")
  }

  getById(id:number){
    return this.http.get<IMeasurementUnit>(`http://localhost:8080/measurement-unit/${id}`)
  }

  getByLikeName(likeName: string) {
    return this.http.get<IMeasurementUnit[]>(`http://localhost:8080/measurement-unit/search?term=${likeName}`)
  }

  delete(id:number){
    return this.http.delete(`http://localhost:8080/measurement-unit/${id}`)
  }

  update(id:number, data: IMeasurementUnit){
    return this.http.put<IMeasurementUnit>(`http://localhost:8080/measurement-unit/${id}`, data)
  }

  create(data:IMeasurementUnit){
    return this.http.post<IMeasurementUnit>("http://localhost:8080/measurement-unit",data)
  }
}
