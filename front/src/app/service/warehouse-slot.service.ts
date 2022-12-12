import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import IWarehouseSlot from '../interfaces/IWarehouseSlot';
import { AuthService } from './auth.service';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class WarehouseSlotService {
  constructor(private http: HttpClient, private auth: AuthService) { }

  //getByIdClient(id:number){
  //return this.http.get<IWarehouseSlot[]>(`${environment.api}warehouseSlot/client/${id}`,{
  //headers: this.auth.buildHeader()
  //})
  //}


  get() {
    return this.http.get<IWarehouseSlot[]>(`${environment.api}warehouseSlot`, {
      headers: this.auth.buildHeader()
    })
  }

  getByIdClient(id: number, pageIndex: number = 0, pageSize = 10) {
    return this.http.get<any>(
      `${environment.api}warehouseSlot/client/${id}?page=${pageIndex}&size=${pageSize}`,
      {
        headers: this.auth.buildHeader(),
      })
  }


  // getByClientIdByBranches(id: number) {
  // return this.http.get<IWarehouseSlot[]>(
  // `${environment.api}warehouseSlot/client/${id}/filterByBranches`,
  // {
  // headers: this.auth.buildHeader(),
  // }
  // );
  // }

  // getByClientIdByBranchesByProducts(id: number) {
  // return this.http.get<IWarehouseSlot[]>(
  // `${environment.api}warehouseSlot/client/${id}/filterByBranchesAndProducts`,
  // {
  // headers: this.auth.buildHeader(),
  // }
  // );
  // }

  // getByClientIdByBranchesByProductsName(
  // id: number,
  // term: string,
  // idsList: number[]
  // ) {
  // const body = { ids: idsList };
  // return this.http.post<IWarehouseSlot[]>(
  // `${environment.api}warehouseSlot/client/${id}/filterByBranches/searchProduct?term=${term}`,
  // body,
  // {
  // headers: this.auth.buildHeader(),
  // }
  // );
  // }

  getByClientIdByBranches(
    id: number,
    pageIndex: number = 0,
    pageSize: number = 10
  ) {
    return this.http.get<any>(
      `${environment.api}warehouseSlot/client/${id}/filterByBranches?page=${pageIndex}&size=${pageSize}`,
      {
        headers: this.auth.buildHeader(),
      }
    );
  }

  getByClientIdByBranchesByProducts(
    id: number,
    pageIndex: number = 0,
    pageSize: number = 10
  ) {
    return this.http.get<any>(
      `${environment.api}warehouseSlot/client/${id}/filterByBranchesAndProducts?page=${pageIndex}&size=${pageSize}`,
      {
        headers: this.auth.buildHeader(),
      }
    );
  }

  getByClientIdByBranchesByProductsName(
    id: number,
    term: string,
    idsList: number[],
    pageIndex: number = 0,
    pageSize: number = 10
  ) {
    const body = { ids: idsList };
    return this.http.post<any>(
      `${environment.api}warehouseSlot/client/${id}/filterByBranches/searchProduct?term=${term}&page=${pageIndex}&size=${pageSize}`,
      body,
      {
        headers: this.auth.buildHeader(),
      }
    );
  }

  getByClientIdByBranchByProductName(
    branchId: number,
    sku: string,
    client: string,
    pageIndex: number = 0,
    pageSize: number = 10
  ) {
    return this.http.get<any>(
      `${environment.api}warehouseSlot/client/filtered?branch=${branchId}&sku=${sku}&client=${client}&page=${pageIndex}&size=${pageSize}`,
      {
        headers: this.auth.buildHeader(),
      }
    )
  }
}
