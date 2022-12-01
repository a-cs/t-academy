import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { map, Observable } from 'rxjs';
import IAddress from 'src/app/interfaces/IAddress';
import IClient from 'src/app/interfaces/IClient';
import { ClientService } from 'src/app/service/client.service';

@Component({
  selector: 'app-modal-add-client',
  templateUrl: './modal-add-client.component.html',
  styleUrls: ['./modal-add-client.component.css'],
})
export class ModalAddClientComponent implements OnInit {
  createForm: FormGroup;

  states=['AC','AL','AP','AM','BA','CE','DF','ES','GO','MA','MT','MS','MG','PA','PB','PR','PE','PI','RJ','RN','RS','RO','RR','SC','SP','SE','TO'];
  filteredStates:Observable<String[]>
  firstState:String; 

  constructor(
    private clientService: ClientService,
    private dialogRef: MatDialogRef<ModalAddClientComponent>
  ) {}

  ngOnInit(): void {
    this.configureForm();
    this.filteredStates = this.createForm.controls["state"].valueChanges.pipe(
      map((value:string) =>{
        return this._filterStates(value)
      })
    )
    this.filteredStates.subscribe(value => this.firstState=value[0])
  }

  configureForm() {
    this.createForm = new FormGroup({
      name: new FormControl('', Validators.required),
      cnpj: new FormControl('', Validators.required),
      email: new FormControl('', Validators.required),
      street: new FormControl('', Validators.required),
      number: new FormControl('', Validators.required),
      city: new FormControl('', Validators.required),
      state: new FormControl(null, Validators.required),
      zipCode: new FormControl('', Validators.required)
      
    });
  }

  stateFallback() {
    if(this.states.includes(this.createForm.get('state')?.value.toUpperCase())){
      this.firstState = this.createForm.get('state')?.value.toUpperCase();
    }

    this.createForm.controls['state'].setValue(this.firstState)
  }

  stateOnCLick() {
    this.firstState = this.createForm.controls['state'].getRawValue(); 
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

  createNewClient() {
    const formSubmmited: boolean = true;
    const clientName = this.createForm.get('name')?.value;
    const clientCnpj = this.createForm.get('cnpj')?.value;
    const clientEmail = this.createForm.get('email')?.value;
    const clientStreet = this.createForm.get('street')?.value;
    const clientNumber = this.createForm.get('number')?.value;
    const clientCity = this.createForm.get('city')?.value;
    const clientZipCode = this.createForm.get('zipCode')?.value;
    const clientState = this.createForm.get('state')?.value;
    const newAddress: IAddress = {
      street: clientStreet,
      number: clientNumber,
      city: clientCity,
      zipCode: clientZipCode,
      state: clientState
    }
    const newClient: IClient = {
      name: clientName,
      cnpj: clientCnpj,
      email: clientEmail,
      address: newAddress
    };
    console.log(newClient)
    this.clientService.create(newClient).subscribe(response => { window.location.reload() }, error => { console.log("err!", error) });
    this.dialogRef.close(formSubmmited);
  }
}
