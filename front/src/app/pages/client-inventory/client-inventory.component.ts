import { Component, OnInit } from '@angular/core';
import IBranch from 'src/app/interfaces/IBranch';
import IWarehouseSlot from 'src/app/interfaces/IWarehouseSlot';
import { BranchService } from 'src/app/service/branch.service';
import { WarehouseSlotService } from 'src/app/service/warehouse-slot.service';
import { Observable } from 'rxjs';
import { FormControl, FormGroup} from '@angular/forms';
import { map } from 'rxjs/operators';
import {MatSelectModule} from '@angular/material/select';

@Component({
  selector: 'app-client-inventory',
  templateUrl: './client-inventory.component.html',
  styleUrls: ['./client-inventory.component.css']
})
export class ClientInventoryComponent implements OnInit {
  client_wareshouses_slots: IWarehouseSlot[] = []
  branches?: IBranch[]
  branchesList: IBranch[]
  constructor(private warehouseSlotService: WarehouseSlotService, private branchService: BranchService) { }

  ngOnInit(): void {
    this.warehouseSlotService.getByIdClient(1).subscribe(
      data => {
        this.client_wareshouses_slots = data
        console.log(this.warehouseSlotService)
      }
    )

    this.branchService.get().subscribe(
      res => {
        this.branches = res
      }
    )

    this.branchesForm.valueChanges.subscribe(branches => {
      this.branches = branches as unknown as IBranch[]
    }
    )
  }

  refreshWarehouseSlot(){

  }
  branchesForm = new FormControl('');
 
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
