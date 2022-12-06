import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-modal-confirm-delete',
  templateUrl: './modal-confirm-delete.component.html',
  styleUrls: ['./modal-confirm-delete.component.css'],
})
export class ModalConfirmDeleteComponent implements OnInit {
  entityName: string = 'object';
  public deletionConfirmed: boolean;
  constructor(private dialogRef: MatDialogRef<ModalConfirmDeleteComponent>) {}

  ngOnInit(): void {}

  onConfirmButtonPressed() {
    this.deletionConfirmed = true;
    this.dialogRef.close(this.deletionConfirmed);
  }

  onCancelButtonPressed() {
    this.deletionConfirmed = false;
    this.dialogRef.close(this.deletionConfirmed);
  }
}
