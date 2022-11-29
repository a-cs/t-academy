import { Component, OnInit } from '@angular/core';
import IWarehouseSlot from 'src/app/interfaces/IWarehouseSlot';
import { WarehouseSlotService } from 'src/app/service/warehouse-slot.service';

@Component({
  selector: 'app-client-inventory',
  templateUrl: './client-inventory.component.html',
  styleUrls: ['./client-inventory.component.css']
})
export class ClientInventoryComponent implements OnInit {
  client_wareshouses_slots: IWarehouseSlot[] = []
  constructor(private warehouseSlotService: WarehouseSlotService) { }

  ngOnInit(): void {
    this.warehouseSlotService.getByIdClient(1).subscribe(
      data => {
        this.client_wareshouses_slots = data
        console.log(this.warehouseSlotService)
      }
    )
  }

  searchText: string = "";
  branch: string = "";

  onSearchTextEnteredSKUWareHouse(searchValue: string){
    this.searchText = searchValue;
    //this.warehouseSlotService.(this.searchText).subscribe(data => this.skus = data)
  }

  onSearchTextEnteredByBranch(searchValue: string){
    this.searchText = searchValue;
    //this.warehouseSlotService.(this.searchText).subscribe(data => this.skus = data)
  }

}
