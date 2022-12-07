import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ModalCreateUserComponent } from '../modal-create-user/modal-create-user.component';

@Component({
  selector: 'app-card-create-user',
  templateUrl: './card-create-user.component.html',
  styleUrls: ['./card-create-user.component.css']
})
export class CardCreateUserComponent implements OnInit {

  constructor(public dialog: MatDialog) { }

  ngOnInit(): void {
  }

  openUpdateDialog(){
    const dialogRef = this.dialog.open(ModalCreateUserComponent, {
      width: "650px",
      height: "500px",
      maxWidth: "96vw",
      autoFocus: false
    });
  }
}
