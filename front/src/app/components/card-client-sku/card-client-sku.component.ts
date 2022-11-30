import { Component, OnInit, Input} from '@angular/core';
import IWarehouseSlot from 'src/app/interfaces/IWarehouseSlot';

@Component({
  selector: 'app-card-client-sku',
  templateUrl: './card-client-sku.component.html',
  styleUrls: ['./card-client-sku.component.css']
})
export class CardClientSkuComponent implements OnInit {
  @Input() warehouseSlot: IWarehouseSlot
  constructor() { }

  ngOnInit(): void {
  }

}
