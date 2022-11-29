import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { CategoryCreateFormComponent } from '../category-create-form/category-create-form.component';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';

@Component({
  selector: 'app-category-item-create-card',
  templateUrl: './category-create-card.component.html',
  styleUrls: ['./category-create-card.component.css'],
})
export class CategoryCreateCardComponent implements OnInit {
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
      CategoryCreateFormComponent,
      dialogConfig
    );

    dialogRef.afterClosed().subscribe((formSubmmited) => {
      if (formSubmmited) {
        this.onItemAdded.emit();
      }
    });
  }
}
