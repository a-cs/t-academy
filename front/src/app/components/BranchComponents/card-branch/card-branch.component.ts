import { Component, Input, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import IBranch from 'src/app/interfaces/IBranch';
import { ModalShowMoreComponent } from '../modal-show-more/modal-show-more.component';

@Component({
  selector: 'app-card-branch',
  templateUrl: './card-branch.component.html',
  styleUrls: ['./card-branch.component.css']
})
export class CardBranchComponent implements OnInit {

  @Input() branch: IBranch

  constructor(public dialog: MatDialog) { }

  ngOnInit(): void {
  }

  openShowMoreDialog(branch: IBranch) {
    this.dialog.open(ModalShowMoreComponent, {
      autoFocus: false,
      width: "600px",
      height: "600px",
      data: branch
    })
  }

}
