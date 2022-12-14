import { Component, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import ISku from 'src/app/interfaces/ISku';
import { ModalUpdateSkuComponent } from 'src/app/components/modal-update-sku/modal-update-sku.component';
@Component({
  selector: 'app-card-sku',
  templateUrl: './card-sku.component.html',
  styleUrls: ['./card-sku.component.css']
})
export class CardSkuComponent {
  @Input() sku: ISku

  constructor(public dialog: MatDialog,
    private router: Router) { }

  openUpdateDialog(sku: ISku) {
    console.log(sku)
    const dialogRef = this.dialog.open(ModalUpdateSkuComponent, {
      width: "600px",
      height: "600px",
      autoFocus: false,
      data: sku
    });

  }


}
