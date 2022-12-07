import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import IBranch from 'src/app/interfaces/IBranch';
import { BranchService } from 'src/app/service/branch.service';
import { MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-modal-add-branch',
  templateUrl: './modal-add-branch.component.html',
  styleUrls: ['./modal-add-branch.component.css'],
})
export class ModalAddBranchComponent implements OnInit {
  form: FormGroup;
  branch: IBranch;

  constructor(
    private formBuilder: FormBuilder,
    private branchService: BranchService,
    private notification: ToastrService,
    private dialogRef: MatDialogRef<ModalAddBranchComponent>
  ) {}

  ngOnInit(): void {
    this.configureForm();
  }

  configureForm() {
    this.form = this.formBuilder.group({
      name: ['', [Validators.required]],
      street: ['', [Validators.required]],
      number: ['', [Validators.required]],
      city: ['', [Validators.required]],
      state: ['', [Validators.required]],
      zip_code: ['', [Validators.required]],
      max_rows: ['', [Validators.required]],
      max_columns: ['', [Validators.required]],
    });
  }

  create() {
    const newBranch: IBranch = {
      name: this.form.value.name.toLowerCase(),
      address: {
        street: this.form.value.street.toLowerCase(),
        number: this.form.value.number,
        city: this.form.value.city.toLowerCase(),
        state: this.form.value.state,
        zipCode: this.form.value.zip_code,
      },
      max_rows: this.form.value.max_rows,
      max_columns: this.form.value.max_columns,
    };

    this.branchService.create(newBranch).subscribe(
      (response) => {},
      (error) => {
        this.notification.error(error.error.message, 'Error!', {
          progressBar: true,
        });
      },
      () => {
        this.notification.success(
          `Branch ${newBranch.name.toUpperCase()} was successfuly updated`,
          'Created!',
          { progressBar: true }
        );
        this.branchService.branchChanged.emit();
        this.dialogRef.close();
      }
    );
  }
}
