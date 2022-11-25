import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-modal-confirm',
  templateUrl: './modal-confirm.component.html',
  styleUrls: ['./modal-confirm.component.css']
})
export class ModalConfirmComponent{

  constructor(@Inject(MAT_DIALOG_DATA)
  public obs: any) { }

  buttonPressed(){
    // console.log(this.obs.obs)
    this.obs.obs.subscribe((response:any) => { window.location.reload() }, (error:any) => { console.log("err!", error) })
  }
}
