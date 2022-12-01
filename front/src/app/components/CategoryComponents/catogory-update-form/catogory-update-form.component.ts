import { Component, Inject, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import {
  MatDialog,
  MatDialogConfig,
  MatDialogRef,
  MAT_DIALOG_DATA,
} from '@angular/material/dialog';
import ICategory from 'src/app/interfaces/ICategory';
import { CategoryService } from 'src/app/service/category.service';
import { ModalConfirmComponent } from '../../modal-confirm/modal-confirm.component';

@Component({
  selector: 'app-catogory-update-form',
  templateUrl: './catogory-update-form.component.html',
  styleUrls: ['./catogory-update-form.component.css'],
})
export class CategoryUpdateFormComponent implements OnInit {
  updateForm: FormGroup;
  formBuilder: FormBuilder = new FormBuilder();

  constructor(
    @Inject(MAT_DIALOG_DATA) public categoryToUpdate: ICategory,
    private dialogRef: MatDialogRef<CategoryUpdateFormComponent>,
    public confirmDialog: MatDialog,
    private categoryService: CategoryService
  ) {}

  ngOnInit(): void {
    this.configureForm();
  }

  onUpdate() {
    const formSubmmited: boolean = true;
    this.categoryService.categoryUpdatedOrDeleted.emit();
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
    let dialogConfig: MatDialogConfig = this.getDialogConfiguration();
    dialogConfig.data = {
      observable: this.categoryService.delete(
        this.categoryToUpdate.id as number
      ),
      object: 'category',
    };

    const confirmDialogRef = this.confirmDialog.open(
      ModalConfirmComponent,
      dialogConfig
    );
  }

  getDialogConfiguration(): MatDialogConfig {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.data = this.categoryToUpdate;
    dialogConfig.autoFocus = false;
    dialogConfig.disableClose = true;
    dialogConfig.width = '600px';
    dialogConfig.height = '600px';
    return dialogConfig;
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
}
