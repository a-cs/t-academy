import { Component, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import {MatDialog} from '@angular/material/dialog';
import ISku from 'src/app/interfaces/ISku';
import { ModalUpdateSkuComponent } from 'src/app/modal-update-sku/modal-update-sku.component';
@Component({
  selector: 'app-card-sku',
  templateUrl: './card-sku.component.html',
  styleUrls: ['./card-sku.component.css']
})
export class CardSkuComponent {


 @Input() sku: ISku ={
  name: "",
  category: {
    id: 0,
    name: ""
  },
  measurementUnit: {
    id: 0,
    description: "",
    symbol: ""
  }

}

constructor(public dialog: MatDialog) {}

openDialog(sku : ISku) {
  console.log(sku)
  const dialogRef = this.dialog.open(ModalUpdateSkuComponent,{
    width: "600px",
    height: "600px",
    data:sku
  });

}


}
