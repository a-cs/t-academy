import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialog, MatDialogConfig, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { map, Observable } from 'rxjs';
import IAddress from 'src/app/interfaces/IAddress';
import IClient from 'src/app/interfaces/IClient';
import { ClientService } from 'src/app/service/client.service';
import { ModalConfirmComponent } from '../../modal-confirm/modal-confirm.component';

@Component({
  selector: 'app-modal-update-client',
  templateUrl: './modal-update-client.component.html',
  styleUrls: ['./modal-update-client.component.css']
})
export class ModalUpdateClientComponent implements OnInit {

  updateForm: FormGroup;
  formBuilder: FormBuilder = new FormBuilder();
  states:String[]=['AC','AL','AP','AM','BA','CE','DF','ES','GO','MA','MT','MS','MG','PA','PB','PR','PE','PI','RJ','RN','RS','RO','RR','SC','SP','SE','TO'];
  filteredStates:Observable<String[]>
  firstState:String; 
  showDeleteButton: boolean
  showUpdateButton:boolean
  showButtons: boolean

  constructor(
    @Inject(MAT_DIALOG_DATA) public clientToUpdate: IClient,
    private dialogRef: MatDialogRef<ModalUpdateClientComponent>,
    public confirmDialog: MatDialog,
    private clientService: ClientService,
    private dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.configureForm();
    this.filteredStates = this.updateForm.controls["state"].valueChanges.pipe(
      map((value:string) =>{
        return this._filterStates(value)
      })
    )
    this.firstState = this.clientToUpdate.address.state||""
    this.filteredStates.subscribe(value => this.firstState=value[0])
    this.showDeleteButton = false
    this.showUpdateButton = true
    this.showButtons = false
  }

  configureForm() {
    this.updateForm = new FormGroup({
      id: new FormControl(this.clientToUpdate.id, Validators.nullValidator),
      name: new FormControl(
        this.clientToUpdate.name,
        Validators.nullValidator
      ),
      cnpj: new FormControl(
        this.clientToUpdate.cnpj, 
        Validators.required),
      street: new FormControl(
        this.clientToUpdate.address.street, 
        Validators.required),
      number: new FormControl(
        this.clientToUpdate.address.number, 
        Validators.required),
      city: new FormControl(
        this.clientToUpdate.address.city, 
        Validators.required),
      state: new FormControl(
        this.clientToUpdate.address.state, 
        Validators.required),
      zipCode: new FormControl(
        this.clientToUpdate.address.zipCode,
        Validators.required)
    });
  }

  private _filterStates(name: string): String[] {
    console.log(this.states.filter(state => state?.toLowerCase().includes(name.toLowerCase())))
    if(name!=undefined){
      const filterValue = name.toLowerCase();
      return this.states.filter(state => state?.toLowerCase().includes(filterValue));
    }
    else{
      return this.states 
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
      state: newClientState
    }
    const newClient: IClient = {
      id: this.clientToUpdate.id,
      name: newClientName,
      cnpj: newClientCnpj,
      address: newAddress
    };
    this.clientService
      .update(newClient.id as number, newClient)
      .subscribe(response => { window.location.reload() }, error => { console.log("err!", error) });
    this.dialogRef.close(formSubmmited);
  }

  stateFallback() {
    if(this.states.includes(this.updateForm.get('state')?.value.toUpperCase())){
      this.firstState = this.updateForm.get('state')?.value.toUpperCase();
    }

    this.updateForm.controls['state'].setValue(this.firstState)
  }

  stateOnCLick() {
    this.firstState = this.updateForm.controls['state'].getRawValue(); 
  }

  onDeleteItem() {
    const dialogRef = this.dialog.open(ModalConfirmComponent, {
      width: "600px",
      height: "600px",
      data: {
        observable: this.clientService.delete(this.clientToUpdate.id as number),
        object: "Client"
      }

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
    this.showDeleteButton = true
    this.showUpdateButton = false
    this.showButtons = true
  }

}
