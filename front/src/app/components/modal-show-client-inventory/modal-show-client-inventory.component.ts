import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import IWarehouseSlot from 'src/app/interfaces/IWarehouseSlot';
import { MatDialog, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { WarehouseSlotService } from 'src/app/service/warehouse-slot.service';

@Component({
  selector: 'app-modal-show-client-inventory',
  templateUrl: './modal-show-client-inventory.component.html',
  styleUrls: ['./modal-show-client-inventory.component.css']
})
export class ModalShowClientInventoryComponent implements OnInit {
  form: FormGroup
  wareHouseSlot: IWarehouseSlot

  constructor(@Inject(MAT_DIALOG_DATA) public data: IWarehouseSlot,
    private formBuilder: FormBuilder,
    private warehouseSlotService: WarehouseSlotService,
    private dialog: MatDialog
  ) {
    this.wareHouseSlot = Object.assign({}, this.data)
  }

  ngOnInit(): void {
    this.configureForm()
  }

  configureForm() {
    console.log(this.wareHouseSlot.skuName)
    console.log(this.wareHouseSlot)
    this.form = this.formBuilder.group({
      name: [this.wareHouseSlot.skuName, [Validators.required]],
      branchName: [this.wareHouseSlot.branchName, [Validators.required]],
      quantity: [this.wareHouseSlot.quantity, [Validators.required]],
      aisleBay: [this.wareHouseSlot.aisle + this.wareHouseSlot.bay, [Validators.required]],
      measurementUnity: [this.wareHouseSlot.symbol, [Validators.required]],
      arrivalDate: [this.wareHouseSlot.arrivalDate, [Validators.required]]
    })
  }

}
