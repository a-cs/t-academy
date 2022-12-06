import { Component, Inject, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import {
  MatDialog,
  MatDialogRef,
  MAT_DIALOG_DATA,
} from '@angular/material/dialog';
import ICategory from 'src/app/interfaces/ICategory';
import { ModalConfirmDeleteComponent } from 'src/app/components/modal-confirm-delete/modal-confirm-delete.component';
import { CategoryService } from 'src/app/service/category.service';

@Component({
  selector: 'app-catogory-update-form',
  templateUrl: './catogory-update-form.component.html',
  styleUrls: ['./catogory-update-form.component.css'],
})
export class CategoryUpdateFormComponent implements OnInit {
  updateForm: FormGroup;

  constructor(
    @Inject(MAT_DIALOG_DATA) public categoryToUpdate: ICategory,
    private updateDialogRef: MatDialogRef<CategoryUpdateFormComponent>,
    public confirmDialog: MatDialog,
    private categoryService: CategoryService
  ) {}

  ngOnInit(): void {
    this.updateForm = new FormGroup({
      id: new FormControl(this.categoryToUpdate.id, Validators.nullValidator),
      name: new FormControl(
        this.categoryToUpdate.name,
        Validators.nullValidator
      ),
    });
  }

  onUpdate() {
    const newCategoryName = this.updateForm.get('name')?.value;

    const newCategory: ICategory = {
      id: this.categoryToUpdate.id,
      name: newCategoryName,
    };

    this.categoryService.update(newCategory.id as number, newCategory);

    this.updateDialogRef.close();
  }

  onDelete() {
    const confirmDialogRef = this.confirmDialog.open(
      ModalConfirmDeleteComponent,
      {
        width: '600px',
        height: '600px',
        autoFocus: false,
      }
    );

    confirmDialogRef.afterClosed().subscribe((isConfirmed) => {
      if (isConfirmed) {
        this.categoryService.delete(this.categoryToUpdate.id as number);
        this.updateDialogRef.close();
      }
    });
  }
}
