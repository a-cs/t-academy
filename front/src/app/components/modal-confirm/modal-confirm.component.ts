import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-modal-confirm',
  templateUrl: './modal-confirm.component.html',
  styleUrls: ['./modal-confirm.component.css']
})
export class ModalConfirmComponent{

  constructor(@Inject(MAT_DIALOG_DATA)
  public data: any) { }

  buttonPressed(){
    // console.log(this.data)
    this.data.observable.subscribe((response:any) => { window.location.reload() }, (error:any) => { console.log("err!", error) })
  }
}
