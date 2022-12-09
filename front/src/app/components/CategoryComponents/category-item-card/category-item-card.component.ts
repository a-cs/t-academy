import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import ICategory from 'src/app/interfaces/ICategory';
import { CategoryService } from 'src/app/service/category.service';
import { CategoryUpdateFormComponent } from '../catogory-update-form/catogory-update-form.component';

@Component({
  selector: 'app-category-item-card',
  templateUrl: './category-item-card.component.html',
  styleUrls: ['./category-item-card.component.css'],
})
export class CategoryItemCardComponent implements OnInit {
  @Input() category: ICategory;

  constructor(
    public dialogRef: MatDialog,
    private categoryService: CategoryService
  ) {}

  ngOnInit(): void {}

  //onUpdateButtonPressed(category: ICategory) {
  //const dialogConfig = this.getDialogConfiguration();

  //const dialogRef = this.dialogRef.open(
  //CategoryUpdateFormComponent,
  //dialogConfig
  //);

  //dialogRef.afterClosed().subscribe((formSubmitted) => {
  //if (formSubmitted) {
  //this.onItemUpdatedOrDeleted.emit();
  //}
  //});
  //}

  onUpdateButtonPressed(category: ICategory) {
    const dialogRef = this.dialogRef.open(CategoryUpdateFormComponent, {
      data: this.category,
      autoFocus: false,
      width: '600px',
      height: '600px',
    });
  }
}
