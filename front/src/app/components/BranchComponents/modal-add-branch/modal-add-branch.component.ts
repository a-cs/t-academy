import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { map, Observable, startWith } from 'rxjs';
import IBranch from 'src/app/interfaces/IBranch';
import { BranchService } from 'src/app/service/branch.service';

@Component({
  selector: 'app-modal-add-branch',
  templateUrl: './modal-add-branch.component.html',
  styleUrls: ['./modal-add-branch.component.css']
})
export class ModalAddBranchComponent implements OnInit {

  form: FormGroup
  branch: IBranch

  states=['AC','AL','AP','AM','BA','CE','DF','ES','GO','MA','MT','MS','MG','PA','PB','PR','PE','PI','RJ','RN','RS','RO','RR','SC','SP','SE','TO'];
  filteredStates:Observable<String[]>
  firstState:String; 

  aisleDigits: number
  bayDigits: number
  totalDigits: number

  constructor(private formBuilder: FormBuilder,
              private branchService: BranchService) { }

  ngOnInit(): void {
    this.configureForm()

    this.filteredStates = this.form.controls["state"].valueChanges.pipe(
      startWith(''),
      map((value:string) =>{
        return this._filterStates(value)
      })
    )
    this.filteredStates.subscribe(value => this.firstState=value[0])
  }

    configureForm() {
      this.form = this.formBuilder.group({
        name: ["", [Validators.required]],
        street: ["", [Validators.required]],
        number: ["", [Validators.required]],
        city: ["", [Validators.required]],
        state: ["", [Validators.required]],
        zip_code: ["", [Validators.required]],
        max_rows: ["", [Validators.required]],
        max_columns: ["", [Validators.required]]
      })
    }

    stateFallback() {
      if(this.states.includes(this.form.get('state')?.value.toUpperCase())){
        this.firstState = this.form.get('state')?.value.toUpperCase();
      }
  
      this.form.controls['state'].setValue(this.firstState)
    }
  
    stateOnCLick() {
      this.firstState = this.form.controls['state'].getRawValue(); 
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

    create() {
      const newBranch: IBranch = {
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

      this.aisleDigits = Math.floor(newBranch.max_columns/26)
      this.bayDigits = Math.floor(newBranch.max_rows/10)

      this.totalDigits = 2 + this.aisleDigits + this.bayDigits

      if(this.totalDigits > 5) {
        this.branchService.create(newBranch)
        .subscribe(response => {
          window.location.reload()
        })
      }     
    }

}
