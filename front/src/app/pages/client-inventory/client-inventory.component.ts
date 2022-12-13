import { Component, OnInit } from '@angular/core';
import IBranch from 'src/app/interfaces/IBranch';
import IWarehouseSlot from 'src/app/interfaces/IWarehouseSlot';
import { BranchService } from 'src/app/service/branch.service';
import { WarehouseSlotService } from 'src/app/service/warehouse-slot.service';
import { FormControl, FormGroup } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { ModalShowClientInventoryComponent } from 'src/app/components/modal-show-client-inventory/modal-show-client-inventory.component';
import { PageEvent } from '@angular/material/paginator';
import { AuthService } from 'src/app/service/auth.service';
import { ToastrService } from 'ngx-toastr';

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

  length = 50;
  pageSize = 6;
  pageIndex = 0;
  pageSizeOptions = [5, 10, 25];

  hidePageSize = true;
  showPageSizeOptions = false;
  showFirstLastButtons = true;
  disabled = false;

  isLoading: boolean = false;
  isError: boolean = false;
  hideSearchBar: boolean = false

  pageEvent: PageEvent;

  constructor(
    private warehouseSlotService: WarehouseSlotService,
    private branchService: BranchService,
    private authService: AuthService,
    public dialog: MatDialog,
    private notification: ToastrService
  ) { }

  ngOnInit(): void {
    this.isLoading = true
    this.hideSearchBar = true
    this.warehouseSlotService
      .getByIdClient(this.authService.getUserId() || 0, this.pageIndex, this.pageSize)
      .subscribe((data) => {
        this.client_wareshouses_slots = data.content;
        this.length = data.totalElements
        this.pageSize = data.size
        this.pageIndex = data.number
        this.isLoading = false
        this.hideSearchBar = false
      }, error => {
        this.isLoading = false
        this.isError = true
        this.notification.error(error.error.message, 'Error: No server response', {
          tapToDismiss: true,
          disableTimeOut: true,
          closeButton: true,
        });
      })

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
    this.isLoading = true
    this.hideSearchBar = false
    if (this.idList.length >= 0 == false) {
   
      this.warehouseSlotService
        .getByClientIdByBranchesByProductsName(
          this.authService.getUserId() || 0,
          this.searchText,
          [],
          this.pageIndex,
          this.pageSize
        )
        .subscribe((client_wareshouses_slots) => {
          this.client_wareshouses_slots =
            (client_wareshouses_slots.content as unknown as IWarehouseSlot[]) ||
            [];
          this.length = client_wareshouses_slots.totalElements;
          this.pageSize = client_wareshouses_slots.size;
          this.pageIndex = client_wareshouses_slots.number;
          this.isLoading = false
        }, error => {
          this.isLoading = false
          this.isError = true
          this.notification.error(error.error.message, 'Error: No server response', {
            tapToDismiss: true,
            disableTimeOut: true,
            closeButton: true,
          });
        });
    } else {
      
      this.searchText = searchValue;
      
      this.warehouseSlotService
        .getByClientIdByBranchesByProductsName(
          this.authService.getUserId() || 0,
          this.searchText,
          this.idList,
          this.pageIndex,
          this.pageSize
        )
        .subscribe((client_wareshouses_slots) => {
          
          this.client_wareshouses_slots =
            (client_wareshouses_slots.content as unknown as IWarehouseSlot[]) ||
            [];
          this.length = client_wareshouses_slots.totalElements;
          this.pageSize = client_wareshouses_slots.size;
          this.pageIndex = client_wareshouses_slots.number;
          this.isLoading = false
        }, error => {
          this.isLoading = false
          this.isError = true
          this.notification.error(error.error.message, 'Error: No server response', {
            tapToDismiss: true,
            disableTimeOut: true,
            closeButton: true,
          });
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
    this.isLoading = true
    this.length = e.length;
    this.pageSize = e.pageSize;
    this.pageIndex = e.pageIndex;
    
    this.warehouseSlotService
      .getByIdClient(this.authService.getUserId() || 0, this.pageIndex, this.pageSize)
      .subscribe((data) => {
        this.client_wareshouses_slots = data.content;
        this.length = data.totalElements;
          this.pageSize = data.size;
          this.pageIndex = data.number;
          this.isLoading = false
      }, error => {
        this.isLoading = false
        this.isError = true
        this.notification.error(error.error.message, 'Error: No server response', {
          tapToDismiss: true,
          disableTimeOut: true,
          closeButton: true,
        });
      });
  }
}
