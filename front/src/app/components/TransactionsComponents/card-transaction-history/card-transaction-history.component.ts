import { Component, OnInit, Input, Inject } from '@angular/core';
import ITransaction from 'src/app/interfaces/ITransaction';
import { MatDialog, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { FormBuilder } from '@angular/forms';
import { AuthService } from 'src/app/service/auth.service';
import { ModalShowMoreTransactionHistoryComponent } from '../modal-show-more-transaction-history/modal-show-more-transaction-history.component';

@Component({
  selector: 'app-card-transaction-history',
  templateUrl: './card-transaction-history.component.html',
  styleUrls: ['./card-transaction-history.component.css']
})
export class CardTransactionHistoryComponent implements OnInit {

  @Input() transaction: ITransaction
  transactionType: string
  constructor(public dialog: MatDialog){}

  ngOnInit(): void {
    console.log(this.transaction)
    this.transactionType = this.transaction.type
  }

  openShowMoreDialog(transaction: ITransaction) {
    this.dialog.open(ModalShowMoreTransactionHistoryComponent, {
      autoFocus: false,
      width: "600px",
      height: "600px",
      data: transaction
    })
  }

}
