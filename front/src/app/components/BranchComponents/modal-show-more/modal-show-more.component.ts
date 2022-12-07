import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ToastrService } from 'ngx-toastr';
import { map, Observable, startWith } from 'rxjs';
import IBranch from 'src/app/interfaces/IBranch';
import { AuthService } from 'src/app/service/auth.service';
import { BranchService } from 'src/app/service/branch.service';
import { buttonPermission } from 'src/app/utils/utils';
import { ModalConfirmComponent } from '../../modal-confirm/modal-confirm.component';
import { MatDialogRef } from '@angular/material/dialog';
import { ModalConfirmDeleteComponent } from '../../modal-confirm-delete/modal-confirm-delete.component';

@Component({
  selector: 'app-modal-show-more',
  templateUrl: './modal-show-more.component.html',
  styleUrls: ['./modal-show-more.component.css'],
})
export class ModalShowMoreComponent implements OnInit {
  form: FormGroup;
  branch: IBranch;

  states = [
    'AC',
    'AL',
    'AP',
    'AM',
    'BA',
    'CE',
    'DF',
    'ES',
    'GO',
    'MA',
    'MT',
    'MS',
    'MG',
    'PA',
    'PB',
    'PR',
    'PE',
    'PI',
    'RJ',
    'RN',
    'RS',
    'RO',
    'RR',
    'SC',
    'SP',
    'SE',
    'TO',
  ];
  filteredStates: Observable<String[]>;
  firstState: String;

  showDeleteButton: boolean;
  showUpdateButton: boolean;
  showButtons: boolean;

  permissions = buttonPermission;

  isReadOnly: boolean;

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: IBranch,
    private formBuilder: FormBuilder,
    private branchService: BranchService,
    private dialog: MatDialog,
    private dialogRef: MatDialogRef<ModalShowMoreComponent>,
    private notification: ToastrService,
    public auth: AuthService
  ) {
    this.branch = Object.assign({}, this.data);
  }

  ngOnInit(): void {
    this.configureForm();
    this.filteredStates = this.form.controls['state'].valueChanges.pipe(
      startWith(''),
      map((value: string) => {
        return this._filterStates(value);
      })
    );
    this.firstState = this.branch.address.state || '';
    this.filteredStates.subscribe((value) => (this.firstState = value[0]));

    this.showDeleteButton = false;
    this.showUpdateButton = this.auth.validateRole(this.permissions.updateUnit);
    this.showButtons = false;

    this.isReadOnly = this.showUpdateButton;
  }

  configureForm() {
    this.form = this.formBuilder.group({
      name: [
        this.branch.name.charAt(0).toUpperCase() + this.branch.name.slice(1),
        [Validators.required],
      ],
      street: [this.branch.address.street, [Validators.required]],
      number: [this.branch.address.number, [Validators.required]],
      city: [this.branch.address.city, [Validators.required]],
      state: [this.branch.address.state, [Validators.required]],
      zip_code: [this.branch.address.zipCode, [Validators.required]],
      max_rows: [this.branch.max_rows, [Validators.required]],
      max_columns: [this.branch.max_columns, [Validators.required]],
    });
  }

  private _filterStates(name: string): String[] {
    console.log(
      this.states.filter((state) =>
        state?.toLowerCase().includes(name.toLowerCase())
      )
    );
    if (name != undefined) {
      const filterValue = name.toLowerCase();
      return this.states.filter((state) =>
        state?.toLowerCase().includes(filterValue)
      );
    } else {
      return this.states;
    }
  }

  stateFallback() {
    if (this.states.includes(this.form.get('state')?.value.toUpperCase())) {
      this.firstState = this.form.get('state')?.value.toUpperCase();
    }

    this.form.controls['state'].setValue(this.firstState);
  }

  stateOnCLick() {
    this.firstState = this.form.controls['state'].getRawValue();
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
        zipCode: this.form.value.zip_code,
      },
      max_rows: this.form.value.max_rows,
      max_columns: this.form.value.max_columns,
    };

    //this.branchService.update(this.branch.id as number, newBranch)
    //.subscribe(response => {
    //window.location.reload()
    //})
    this.branchService.update(this.branch.id as number, newBranch).subscribe(
      (response) => {},
      (error) => {
        this.notification.error(error.error.message, 'Error!', {
          progressBar: true,
        });
      },
      () => {
        this.notification.success(
          `Branch ${this.branch.name.toUpperCase()} was successfuly updated`,
          'Updated!',
          { progressBar: true }
        );
        this.dialogRef.close();
        this.branchService.branchChanged.emit();
      }
    );
  }

  //openConfirmDialog() {
  //this.dialog.open(ModalConfirmComponent, {
  //width: '600px',
  //height: '600px',
  //data: {
  //observable: this.branchService.delete(this.branch.id as number),
  //object: 'branch',
  //},
  //});
  //}

  openConfirmDialog() {
    const confirmDialogRef = this.dialog.open(ModalConfirmDeleteComponent, {
      width: '600px',
      height: '600px',
    });

    confirmDialogRef.afterClosed().subscribe((deletionConfirmed) => {
      if (deletionConfirmed) {
        this.branchService.delete(this.branch.id as number).subscribe(
          (response) => {},
          (error) => {
            this.notification.error(error.error.message, 'Error!', {
              progressBar: true,
            });
          },
          () => {
            this.notification.warning(
              `Branch ${this.branch.name.toUpperCase()} was successfuly deleted`,
              'Deleted!',
              { progressBar: true }
            );
            this.branchService.branchChanged.emit();
            this.dialogRef.close();
          }
        );
      }
    });
  }

  clickOnEdit() {
    this.showDeleteButton = true;
    this.showUpdateButton = false;
    this.showButtons = true;
    this.isReadOnly = this.showUpdateButton;
  }
}
