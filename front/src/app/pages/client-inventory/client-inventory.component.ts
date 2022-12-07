import { Component, OnInit } from '@angular/core';
import IBranch from 'src/app/interfaces/IBranch';
import IWarehouseSlot from 'src/app/interfaces/IWarehouseSlot';
import { BranchService } from 'src/app/service/branch.service';
import { WarehouseSlotService } from 'src/app/service/warehouse-slot.service';
import { FormControl, FormGroup } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { ModalShowClientInventoryComponent } from 'src/app/components/modal-show-client-inventory/modal-show-client-inventory.component';
import { PageEvent } from '@angular/material/paginator';

@Component({
  selector: 'app-client-inventory',
  templateUrl: './client-inventory.component.html',
  styleUrls: ['./client-inventory.component.css'],
})
export class ClientInventoryComponent implements OnInit {
  client_wareshouses_slots: IWarehouseSlot[] = [];
  branches?: IBranch[];
  branchesList: IBranch[];
  firstBranch: string;
  idList: number[] = [];
  pageSize = 10;
  pageIndex = 0;
  length = 0;
  constructor(
    private warehouseSlotService: WarehouseSlotService,
    private branchService: BranchService,
    public dialog: MatDialog
  ) { }

  ngOnInit(): void {
    this.warehouseSlotService
      .getByIdClient(1, this.pageIndex, this.pageSize)
      .subscribe((data) => {
        this.client_wareshouses_slots = data.content;
        this.length = data.totalPages;
      });

    this.branchService.get().subscribe((res) => {
      this.branches = res;
    });

    this.branchesForm.valueChanges.subscribe((branches) => {
      this.branchesList = branches as unknown as IBranch[];

      if (this.branchesList[0] != undefined) {
        this.firstBranch = this.branchesList[0].name as unknown as '';
      }

      this.idList = [];
      if (this.branchesList.length > 0) {
        for (let i = 0; i < this.branchesList.length; i++) {
          this.idList.push(this.branchesList[i].id!);
        }
      }

      this.onSearchTextEnteredSKUWareHouse(this.getSearchText());
    });
  }

  refreshWarehouseSlot() { }
  branchesForm = new FormControl('');

  searchText: string = '';
  branch: string = '';

  getSearchText() {
    return this.searchText;
  }

  onSearchTextEnteredSKUWareHouse(searchValue: string) {
    // if(searchValue == "" && this.branchesList == null){
    //   console.log("oi")
    // }

    if (this.idList.length >= 0 == false) {
      this.warehouseSlotService
        .getByClientIdByBranchesByProductsName(
          1,
          this.searchText,
          [],
          this.pageIndex,
          this.pageSize
        )
        .subscribe((client_wareshouses_slots) => {
          this.client_wareshouses_slots =
            (client_wareshouses_slots.content as unknown as IWarehouseSlot[]) ||
            [];
          this.length = client_wareshouses_slots.totalPages;
        });
    } else {
      console.log(this.idList)
      this.searchText = searchValue;
      this.warehouseSlotService
        .getByClientIdByBranchesByProductsName(
          1,
          this.searchText,
          this.idList,
          this.pageIndex,
          this.pageSize
        )
        .subscribe((client_wareshouses_slots) => {
          console.log(client_wareshouses_slots);
          this.client_wareshouses_slots =
            (client_wareshouses_slots.content as unknown as IWarehouseSlot[]) ||
            [];
          this.length = client_wareshouses_slots.totalPages;
        });
    }
  }

  openAddDialog(warehouseSlot: IWarehouseSlot) {
    const dialogRef = this.dialog.open(ModalShowClientInventoryComponent, {
      width: '600px',
      height: '600px',
      data: warehouseSlot,
    });
  }

  handlePageEvent(e: PageEvent) {
    this.pageSize = e.pageSize;
    this.pageIndex = e.pageIndex;

    this.warehouseSlotService
      .getByIdClient(1, this.pageIndex, this.pageSize)
      .subscribe((data) => {
        this.client_wareshouses_slots = data.content;
        this.length = data.totalPages;
      });
  }
}
