import { Component, Inject, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import IWarehouseSlot from 'src/app/interfaces/IWarehouseSlot';
import { MatDialog, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-modal-show-client-inventory',
  templateUrl: './modal-show-client-inventory.component.html',
  styleUrls: ['./modal-show-client-inventory.component.css']
})
export class ModalShowClientInventoryComponent implements OnInit {
  form: FormGroup
  wareHouseSlot: IWarehouseSlot

  constructor(@Inject(MAT_DIALOG_DATA) public data: IWarehouseSlot) { }

  ngOnInit(): void {
  }


}
