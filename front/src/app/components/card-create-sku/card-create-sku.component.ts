import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ModalAddSkuComponent } from 'src/app/components/modal-add-sku/modal-add-sku.component';

@Component({
  selector: 'app-card-create-sku',
  templateUrl: './card-create-sku.component.html',
  styleUrls: ['./card-create-sku.component.css']
})
export class CardCreateSkuComponent implements OnInit {

  constructor(public dialog: MatDialog) { }

  ngOnInit(): void {
  }

  openAddDialog() {
    const dialogRef = this.dialog.open(ModalAddSkuComponent,{
      width: "600px",
      height: "600px"
    });
  }


}
