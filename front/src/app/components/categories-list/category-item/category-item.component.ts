import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import ICategory from 'src/app/interfaces/ICategory';
import { CategoryItemUpdateFormComponent } from '../catogory-item-update-form/catogory-item-update-form.component';

@Component({
  selector: 'app-category-item',
  templateUrl: './category-item.component.html',
  styleUrls: ['./category-item.component.css'],
})
export class CategoryItemComponent implements OnInit {
  @Output() onItemUpdatedOrDeleted = new EventEmitter<void>();
  @Input() category: ICategory;

  constructor(public dialogRef: MatDialog) {}

  ngOnInit(): void {}

  onUpdateButtonPressed(category: ICategory) {
    const dialogConfig = this.getDialogConfiguration();

    const dialogRef = this.dialogRef.open(
      CategoryItemUpdateFormComponent,
      dialogConfig
    );

    dialogRef.afterClosed().subscribe((formSubmitted) => {
      if (formSubmitted) {
        this.onItemUpdatedOrDeleted.emit();
      }
    });
  }

  getDialogConfiguration(): MatDialogConfig {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.data = this.category;
    dialogConfig.autoFocus = false;
    dialogConfig.disableClose = true;
    dialogConfig.width = '600px';
    dialogConfig.height = '600px';
    return dialogConfig;
  }
}
