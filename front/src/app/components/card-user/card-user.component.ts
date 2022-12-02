import { Component, Input, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import IUser from 'src/app/interfaces/IUser';
import { ModalUpdateUserComponent } from 'src/app/components/modal-update-user/modal-update-user.component';

@Component({
  selector: 'app-card-user',
  templateUrl: './card-user.component.html',
  styleUrls: ['./card-user.component.css']
})
export class CardUserComponent implements OnInit {

  @Input() user: IUser

  constructor(public dialog: MatDialog) { }

  ngOnInit(): void {
  }

  openUpdateDialog(){
    const dialogRef = this.dialog.open(ModalUpdateUserComponent, {
      width: "650px",
      height: "500px",
      maxWidth: "96vw",
      autoFocus: false,
      data: this.user
    });
  }
}
