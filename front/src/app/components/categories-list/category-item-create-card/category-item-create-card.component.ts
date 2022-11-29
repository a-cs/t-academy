import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { CategoryItemCreateFormComponent } from '../category-item-create-form/category-item-create-form.component';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { interval } from 'rxjs';

@Component({
  selector: 'app-category-item-create-card',
  templateUrl: './category-item-create-card.component.html',
  styleUrls: ['./category-item-create-card.component.css'],
})
export class CategoryItemCreateCardComponent implements OnInit {
  @Output() onItemAdded = new EventEmitter<void>();

  constructor(public dialogRef: MatDialog) {}

  ngOnInit(): void {}

  openAddDialog() {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.autoFocus = false;
    dialogConfig.disableClose = true;
    dialogConfig.width = '600px';
    dialogConfig.height = '600px';

    const dialogRef = this.dialogRef.open(
      CategoryItemCreateFormComponent,
      dialogConfig
    );

    dialogRef.afterClosed().subscribe((formSubmmited) => {
      if (formSubmmited) {
        this.onItemAdded.emit();
      }
    });
  }
}
