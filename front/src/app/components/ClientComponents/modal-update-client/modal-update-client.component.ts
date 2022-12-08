import { TOUCH_BUFFER_MS } from '@angular/cdk/a11y/input-modality/input-modality-detector';
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
import { ToastrService } from 'ngx-toastr';
import { map, Observable } from 'rxjs';
import IAddress from 'src/app/interfaces/IAddress';
import IClient from 'src/app/interfaces/IClient';
import { AuthService } from 'src/app/service/auth.service';
import { ClientService } from 'src/app/service/client.service';
import { buttonPermission } from 'src/app/utils/utils';
import { ModalConfirmDeleteComponent } from '../../modal-confirm-delete/modal-confirm-delete.component';

@Component({
  selector: 'app-modal-update-client',
  templateUrl: './modal-update-client.component.html',
  styleUrls: ['./modal-update-client.component.css'],
})
export class ModalUpdateClientComponent implements OnInit {
  updateForm: FormGroup;
  formBuilder: FormBuilder = new FormBuilder();
  states: String[] = [
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

  isReadOnly: boolean;

  permissions = buttonPermission;

  constructor(
    @Inject(MAT_DIALOG_DATA) public clientToUpdate: IClient,
    private dialogRef: MatDialogRef<ModalUpdateClientComponent>,
    public confirmDialog: MatDialog,
    private clientService: ClientService,
    private dialog: MatDialog,
    public auth: AuthService,
    private notification: ToastrService
  ) {}

  ngOnInit(): void {
    this.configureForm();
    this.filteredStates = this.updateForm.controls['state'].valueChanges.pipe(
      map((value: string) => {
        return this._filterStates(value);
      })
    );
    this.firstState = this.clientToUpdate.address.state || '';
    this.filteredStates.subscribe((value) => (this.firstState = value[0]));
    this.showDeleteButton = false;
    this.showUpdateButton = this.auth.validateRole(
      this.permissions.selectClient
    );
    this.showButtons = false;
    this.isReadOnly = this.showUpdateButton;
  }

  configureForm() {
    this.updateForm = new FormGroup({
      id: new FormControl(this.clientToUpdate.id, Validators.nullValidator),
      name: new FormControl(this.clientToUpdate.name, Validators.nullValidator),
      cnpj: new FormControl(this.clientToUpdate.cnpj, Validators.required),
      street: new FormControl(
        this.clientToUpdate.address.street,
        Validators.required
      ),
      number: new FormControl(
        this.clientToUpdate.address.number,
        Validators.required
      ),
      city: new FormControl(
        this.clientToUpdate.address.city,
        Validators.required
      ),
      state: new FormControl(
        this.clientToUpdate.address.state,
        Validators.required
      ),
      zipCode: new FormControl(
        this.clientToUpdate.address.zipCode,
        Validators.required
      ),
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

  onUpdate() {
    const formSubmmited: boolean = true;
    const newClientName = this.updateForm.get('name')?.value;
    const newClientCnpj = this.updateForm.get('cnpj')?.value;
    const newClientStreet = this.updateForm.get('street')?.value;
    const newClientNumber = this.updateForm.get('number')?.value;
    const newClientCity = this.updateForm.get('city')?.value;
    const newClientZipCode = this.updateForm.get('zipCode')?.value;
    const newClientState = this.updateForm.get('state')?.value;
    const newAddress: IAddress = {
      street: newClientStreet,
      number: newClientNumber,
      city: newClientCity,
      zipCode: newClientZipCode,
      state: newClientState,
    };
    const newClient: IClient = {
      id: this.clientToUpdate.id,
      name: newClientName,
      cnpj: newClientCnpj,
      address: newAddress,
    };
    
    this.clientService.update(newClient.id as number, newClient).subscribe(
      (response) => {},
      (error) => {
        this.notification.error(error.error.message, 'Error!', {
          progressBar: true,
        });
      },
      () => {
        this.notification.success(
          `Client ${newClient.name?.toUpperCase()} was successfully updated`,
          'Updated!',
          { progressBar: true }
        );
        this.clientService.clientChanged.emit();
        this.dialogRef.close();
      }
    );

  }

  stateFallback() {
    if (
      this.states.includes(this.updateForm.get('state')?.value.toUpperCase())
    ) {
      this.firstState = this.updateForm.get('state')?.value.toUpperCase();
    }

    this.updateForm.controls['state'].setValue(this.firstState);
  }

  stateOnCLick() {
    this.firstState = this.updateForm.controls['state'].getRawValue();
  }

  onDeleteItem() {
    const dialogRefConfirmDelete = this.dialog.open(
      ModalConfirmDeleteComponent,
      {
        width: '600px',
        height: '600px',
      }
    );

    dialogRefConfirmDelete.afterClosed().subscribe((deletionConfirmed) => {
      this.clientService.delete(this.clientToUpdate.id as number).subscribe(
        (response) => {},
        (error) => {
          this.notification.error(error.error.message, 'Error!', {
            progressBar: true,
          });
        },
        () => {
          this.notification.warning(
            `Client ${this.clientToUpdate.name} was successfully deleted`,
            'Deleted!',
            { progressBar: true }
          );
          this.clientService.clientChanged.emit();
          this.dialogRef.close();
        }
      );
    });
  }

  getDialogConfiguration(): MatDialogConfig {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.data = this.clientToUpdate;
    dialogConfig.autoFocus = false;
    dialogConfig.disableClose = true;
    dialogConfig.width = '600px';
    dialogConfig.height = '600px';
    return dialogConfig;
  }

  clickOnEdit() {
    this.showDeleteButton = true;
    this.showUpdateButton = false;
    this.showButtons = true;
    this.isReadOnly = this.showUpdateButton;
  }
}
