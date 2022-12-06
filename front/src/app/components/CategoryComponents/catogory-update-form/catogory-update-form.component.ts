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
import { AuthService } from 'src/app/service/auth.service';
import { buttonPermission } from 'src/app/utils/utils';
import { ModalConfirmComponent } from '../../modal-confirm/modal-confirm.component';
import { ModalConfirmDeleteComponent } from 'src/app/components/modal-confirm-delete/modal-confirm-delete.component';
import { CategoryService } from 'src/app/service/category.service';

@Component({
  selector: 'app-catogory-update-form',
  templateUrl: './catogory-update-form.component.html',
  styleUrls: ['./catogory-update-form.component.css'],
})
export class CategoryUpdateFormComponent implements OnInit {
  updateForm: FormGroup;

  showDeleteButton: boolean
  showUpdateButton:boolean
  showButtons: boolean
  
  permissions = buttonPermission

  isReadOnly: boolean

  constructor(
    @Inject(MAT_DIALOG_DATA) public categoryToUpdate: ICategory,
    private updateDialogRef: MatDialogRef<CategoryUpdateFormComponent>,
    public confirmDialog: MatDialog,
    private categoryService: CategoryService,
    public auth: AuthService
  ) {}

  ngOnInit(): void {

    this.showDeleteButton = false
    this.showUpdateButton = this.auth.validateRole(this.permissions.updateCategory)
    this.showButtons = false

    this.isReadOnly = this.showUpdateButton

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

  clickOnEdit() {
    this.showDeleteButton = true
    this.showUpdateButton = false
    this.showButtons = true
    this.isReadOnly = this.showUpdateButton
  }
}
