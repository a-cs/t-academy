import { Component, Input, OnInit } from '@angular/core';
import IWarehouseSlot from 'src/app/interfaces/IWarehouseSlot';

@Component({
  selector: 'app-card-warehouse-slot',
  templateUrl: './card-warehouse-slot.component.html',
  styleUrls: ['./card-warehouse-slot.component.css']
})
export class CardWarehouseSlotComponent implements OnInit {

  @Input() warehouseSlot: IWarehouseSlot
  
  aisleBay: string

  constructor() { }

  ngOnInit(): void {
    this.aisleBay = this.warehouseSlot.aisle + this.warehouseSlot.bay
    console.log(this.aisleBay)
  }

}
