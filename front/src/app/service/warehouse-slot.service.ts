import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http'
import IWarehouseSlot from '../interfaces/IWarehouseSlot';

@Injectable({
  providedIn: 'root'
})
export class WarehouseSlotService {

  constructor(private http: HttpClient) { }

  getByIdClient(id:number){
    return this.http.get<IWarehouseSlot[]>(`http://localhost:8080/warehouseSlot/client/${id}`)
  }
}
