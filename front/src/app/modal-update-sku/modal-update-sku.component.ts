import { Component, Inject, OnInit } from '@angular/core';
import {MAT_DIALOG_DATA} from "@angular/material/dialog"
import ISku from '../interfaces/ISku';

@Component({
  selector: 'app-modal-update-sku',
  templateUrl: './modal-update-sku.component.html',
  styleUrls: ['./modal-update-sku.component.css']
})
export class ModalUpdateSkuComponent{
  sku: ISku

  constructor(@Inject(MAT_DIALOG_DATA) public data: ISku) {

    this.sku = Object.assign({}, this.data)
   }

  update() {
    console.log(this.sku)
  }

}
