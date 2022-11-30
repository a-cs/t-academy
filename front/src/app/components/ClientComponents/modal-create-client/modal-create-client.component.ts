import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { ModalAddClientComponent } from '../modal-add-client/modal-add-client.component';

@Component({
  selector: 'app-modal-create-client',
  templateUrl: './modal-create-client.component.html',
  styleUrls: ['./modal-create-client.component.css']
})
export class ModalCreateClientComponent implements OnInit {

  @Output() onItemAdded = new EventEmitter<void>();

  constructor(public dialogRef: MatDialog) {}

  ngOnInit(): void {}

  openAddDialog() {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.autoFocus = false;
    dialogConfig.disableClose = true;
    dialogConfig.width = '600px';
    dialogConfig.height = '90%';

    const dialogRef = this.dialogRef.open(
      ModalAddClientComponent,
      dialogConfig
    );

    dialogRef.afterClosed().subscribe((formSubmmited) => {
      if (formSubmmited) {
        this.onItemAdded.emit();
      }
    });
  }

}
