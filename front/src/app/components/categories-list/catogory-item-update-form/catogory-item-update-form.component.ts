import { Component, Inject, Output, EventEmitter, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import ICategory from 'src/app/interfaces/ICategory';
import { CategoryService } from 'src/app/service/category.service';

@Component({
  selector: 'app-catogory-item-update-form',
  templateUrl: './catogory-item-update-form.component.html',
  styleUrls: ['./catogory-item-update-form.component.css'],
})
export class CategoryItemUpdateFormComponent implements OnInit {
  updateForm: FormGroup;
  formBuilder: FormBuilder = new FormBuilder();

  constructor(
    @Inject(MAT_DIALOG_DATA) public categoryToUpdate: ICategory,
    private dialogRef: MatDialogRef<CategoryItemUpdateFormComponent>,
    private categoryService: CategoryService
  ) {}

  ngOnInit(): void {
    this.configureForm();
  }

  configureForm() {
    this.updateForm = new FormGroup({
      id: new FormControl(this.categoryToUpdate.id, Validators.nullValidator),
      name: new FormControl(
        this.categoryToUpdate.name,
        Validators.nullValidator
      ),
    });
  }

  onUpdate() {
    const formSubmmited: boolean = true;
    const newCategoryName = this.updateForm.get('name')?.value;
    const newCategory: ICategory = {
      id: this.categoryToUpdate.id,
      name: newCategoryName,
    };
    this.categoryService
      .update(newCategory.id as number, newCategory)
      .subscribe();
    this.dialogRef.close(formSubmmited);
  }

  onDeleteItem() {
    const formSubmmited: boolean = true;
    this.categoryService.delete(this.categoryToUpdate.id as number).subscribe();
    this.dialogRef.close(formSubmmited);
  }
}
