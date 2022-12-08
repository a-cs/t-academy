import { Component, OnInit } from '@angular/core';
import IWarehouseSlot from 'src/app/interfaces/IWarehouseSlot';
import { AuthService } from 'src/app/service/auth.service';
import { WarehouseSlotService } from 'src/app/service/warehouse-slot.service';
import { PageEvent } from '@angular/material/paginator';

@Component({
  selector: 'app-warehouse-slots',
  templateUrl: './warehouse-slots.component.html',
  styleUrls: ['./warehouse-slots.component.css']
})
export class WarehouseSlotsComponent implements OnInit {
  
  warehouseSlots: IWarehouseSlot[]
  pageSize = 10;
  pageIndex = 0;
  length = 0;

  constructor(
    private warehouseSlotService: WarehouseSlotService,
    private authService: AuthService,
  ) { }

  ngOnInit(): void {
    this.warehouseSlotService.getByClientIdByBranchByProductName(this.branch, this.searchTextSKU, this.searchTextClient, this.pageIndex, this.pageSize)
    .subscribe(
      data => {
        console.log(data)
        this.warehouseSlots = data.content
        this.length = data.totalElements
        this.pageSize = data.size
        this.pageIndex = data.number
      }
    )
  }

  searchTextSKU: string = '';
  getSearchTextSKU() {
    return this.searchTextSKU;
  }
  searchTextClient: string = '';
  getSearchTextClient() {
    return this.searchTextClient;
  }

  branch: number = this.authService.getUserBranchId() || 0;

  onSearchTextEnteredSKUWareHouseSlot(searchValue: string){
    this.searchTextSKU = searchValue;
    this.warehouseSlotService.getByClientIdByBranchByProductName(this.branch, this.searchTextSKU, this.searchTextClient, this.pageIndex, this.pageSize)
    .subscribe(
      data => {
        console.log(data)
        this.warehouseSlots = data.content
        this.length = data.totalElements
        this.pageSize = data.size
        this.pageIndex = data.number
      }
    )
  }

  onSearchTextEnteredClient(searchValue: string){
    this.searchTextClient = searchValue;
    this.warehouseSlotService.getByClientIdByBranchByProductName(this.branch, this.searchTextSKU, this.searchTextClient, this.pageIndex, this.pageSize)
    .subscribe(
      data => {
        console.log(data)
        this.warehouseSlots = data.content
        this.length = data.totalElements
        this.pageSize = data.size
        this.pageIndex = data.number
      }
    )
  }

  handlePageEvent(e: PageEvent) {
    this.pageSize = e.pageSize;
    this.pageIndex = e.pageIndex;
    console.log("id = ", this.authService.getUserId())
    this.warehouseSlotService
      .getByClientIdByBranchByProductName(this.branch, this.searchTextSKU, this.searchTextClient, this.pageIndex, this.pageSize)
      .subscribe((data) => {
        this.warehouseSlots = data.content;
        this.length = data.totalPages;
      });
  }
}
