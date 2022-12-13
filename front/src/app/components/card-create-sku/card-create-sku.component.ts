import { Component, Input, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ModalAddSkuComponent } from 'src/app/components/modal-add-sku/modal-add-sku.component';

@Component({
  selector: 'app-card-create-sku',
  templateUrl: './card-create-sku.component.html',
  styleUrls: ['./card-create-sku.component.css'],
  providers: [CardCreateSkuComponent],
})
export class CardCreateSkuComponent implements OnInit {
  @Input() component: any;
  constructor(public dialog: MatDialog) {}

  ngOnInit(): void {}

  openAddDialog(component: any) {
    const dialogRef = this.dialog.open(component, {
      width: '600px',
      height: '600px',
    });
  }
}
