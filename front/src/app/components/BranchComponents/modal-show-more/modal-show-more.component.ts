import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog, MAT_DIALOG_DATA } from '@angular/material/dialog';
import IBranch from 'src/app/interfaces/IBranch';
import { BranchService } from 'src/app/service/branch.service';
import { ModalConfirmComponent } from '../../modal-confirm/modal-confirm.component';

@Component({
  selector: 'app-modal-show-more',
  templateUrl: './modal-show-more.component.html',
  styleUrls: ['./modal-show-more.component.css']
})
export class ModalShowMoreComponent implements OnInit {

  form: FormGroup
  branch: IBranch

  showDeleteButton: boolean
  showUpdateButton:boolean
  showButtons: boolean

  constructor(@Inject(MAT_DIALOG_DATA) public data: IBranch,
    private formBuilder: FormBuilder,
    private branchService: BranchService,
    private dialog: MatDialog) {
    this.branch = Object.assign({}, this.data)
  }

  ngOnInit(): void {
    this.configureForm()
    this.showDeleteButton = false
    this.showUpdateButton = true
    this.showButtons = false
  }

  configureForm() {
    this.form = this.formBuilder.group({
      name: [this.branch.name.charAt(0).toUpperCase() + this.branch.name.slice(1), [Validators.required]],
      street: [this.branch.address.street, [Validators.required]],
      number: [this.branch.address.number, [Validators.required]],
      city: [this.branch.address.city, [Validators.required]],
      state: [this.branch.address.state, [Validators.required]],
      zip_code: [this.branch.address.zipCode, [Validators.required]],
      max_rows: [this.branch.max_rows, [Validators.required]],
      max_columns: [this.branch.max_columns, [Validators.required]]
    })
  }

  update() {
    const newBranch: IBranch = {
      id: this.branch.id,
      name: this.form.value.name.toLowerCase(),
      address: {
        street: this.form.value.street.toLowerCase(),
        number: this.form.value.number,
        city: this.form.value.city.toLowerCase(),
        state: this.form.value.state,
        zipCode: this.form.value.zip_code
      },
      max_rows: this.form.value.max_rows,
      max_columns: this.form.value.max_columns
    }

    this.branchService.update(this.branch.id as number, newBranch)
      .subscribe(response => {
        window.location.reload()
      })
  }

  openConfirmDialog() {
    this.dialog.open(ModalConfirmComponent, {
      width: "600px",
      height: "600px",
      data: {
        observable: this.branchService.delete(this.branch.id as number),
        object: "branch"
      }
    })
  }

  clickOnEdit() {
    this.showDeleteButton = true
    this.showUpdateButton = false
    this.showButtons = true
  }

}
