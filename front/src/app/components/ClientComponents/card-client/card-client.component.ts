import { Component, Input, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import IClient from 'src/app/interfaces/IClient';
import { ModalUpdateClientComponent } from '../modal-update-client/modal-update-client.component';

@Component({
  selector: 'app-card-client',
  templateUrl: './card-client.component.html',
  styleUrls: ['./card-client.component.css']
})
export class CardClientComponent implements OnInit {

  @Input() client: IClient 

  constructor(public dialog: MatDialog) { }

  ngOnInit(): void {
  }

  openUpdateDialog(client: IClient){
    this.dialog.open(ModalUpdateClientComponent, {
      autoFocus: false,
      width: "600px",
      height: "90%",
      data: client
    })
  }

}
