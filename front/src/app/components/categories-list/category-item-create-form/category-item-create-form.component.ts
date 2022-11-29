import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import ICategory from 'src/app/interfaces/ICategory';
import { CategoryService } from 'src/app/service/category.service';

@Component({
  selector: 'app-category-item-create-form',
  templateUrl: './category-item-create-form.component.html',
  styleUrls: ['./category-item-create-form.component.css'],
})
export class CategoryItemCreateFormComponent implements OnInit {
  createForm: FormGroup;

  constructor(
    private categoryService: CategoryService,
    private dialogRef: MatDialogRef<CategoryItemCreateFormComponent>
  ) {}

  ngOnInit(): void {
    this.configureForm();
  }

  configureForm() {
    this.createForm = new FormGroup({
      name: new FormControl('', Validators.required),
    });
  }

  createNewCategory() {
    const formSubmmited: boolean = true;
    const categoryName = this.createForm.get('name')?.value;
    const newCategory: ICategory = {
      name: categoryName,
    };
    this.categoryService.create(newCategory).subscribe();
    this.dialogRef.close(formSubmmited);
  }
}
