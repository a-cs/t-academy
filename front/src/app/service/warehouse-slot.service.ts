import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http'
import IWarehouseSlot from '../interfaces/IWarehouseSlot';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class WarehouseSlotService {

  constructor(private http: HttpClient, private auth: AuthService){}

  get() {
     return this.http.get<IWarehouseSlot[]>(`http://localhost:8080/warehouseSlot`, {
      headers: this.auth.buildHeader()
     })
  }

  getByIdClient(id:number){
    return this.http.get<IWarehouseSlot[]>(`http://localhost:8080/warehouseSlot/client/${id}`,{
      headers: this.auth.buildHeader()
    })
  }

  getByClientIdByBranches(id:number){
    return this.http.get<IWarehouseSlot[]>(`http://localhost:8080/warehouseSlot/client/${id}/filterByBranches`,{
      headers: this.auth.buildHeader()
    })
  }

  getByClientIdByBranchesByProducts(id:number){
    return this.http.get<IWarehouseSlot[]>(`http://localhost:8080/warehouseSlot/client/${id}/filterByBranchesAndProducts`,{
      headers: this.auth.buildHeader()
    })
  }

  getByClientIdByBranchesByProductsName(id:number, term:string, idsList: number[]){
    const body = { ids: idsList}
    return this.http.post<IWarehouseSlot[]>
    (`http://localhost:8080/warehouseSlot/client/${id}/filterByBranches/searchProduct?term=${term}`, body,{
      headers: this.auth.buildHeader()
    }) 
  }
}
