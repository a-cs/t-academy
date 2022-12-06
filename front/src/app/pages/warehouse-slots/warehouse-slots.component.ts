import { Component, OnInit } from '@angular/core';
import IWarehouseSlot from 'src/app/interfaces/IWarehouseSlot';
import { WarehouseSlotService } from 'src/app/service/warehouse-slot.service';

@Component({
  selector: 'app-warehouse-slots',
  templateUrl: './warehouse-slots.component.html',
  styleUrls: ['./warehouse-slots.component.css']
})
export class WarehouseSlotsComponent implements OnInit {

  warehouseSlots: IWarehouseSlot[]

  constructor(private warehouseSlotService: WarehouseSlotService) { }

  ngOnInit(): void {
    this.warehouseSlotService.get().subscribe(
      data => {
        this.warehouseSlots = data
        console.log(this.warehouseSlots)
      }
    )
  }

}
