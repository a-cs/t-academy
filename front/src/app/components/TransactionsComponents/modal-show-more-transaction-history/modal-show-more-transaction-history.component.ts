import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { TransactionService } from 'src/app/service/transaction.service';
import ITransaction from 'src/app/interfaces/ITransaction';

@Component({
  selector: 'app-modal-show-more-transaction-history',
  templateUrl: './modal-show-more-transaction-history.component.html',
  styleUrls: ['./modal-show-more-transaction-history.component.css']
})
export class ModalShowMoreTransactionHistoryComponent implements OnInit {

  form: FormGroup
  transaction: ITransaction

  constructor(@Inject(MAT_DIALOG_DATA) public data: ITransaction,
    private formBuilder: FormBuilder,
    private transactionService: TransactionService,
    private dialog: MatDialog
  ) {
    this.transaction = Object.assign({}, this.data)
  }

  ngOnInit(): void {
    this.configureForm()
  }

  configureForm() {
    console.log("sku", this.transaction.sku)
    this.form = this.formBuilder.group({
      sku: [this.transaction.sku, [Validators.required]],
      quantity: [this.transaction.quantity, [Validators.required]],
      client: [this.transaction.client, [Validators.required]],
      branch: [this.transaction.branch, [Validators.required]],
      type: [this.transaction.type, [Validators.required]],
      aisleBay: [this.transaction.warehouseSlot, [Validators.required]],
      arrivalDate: [this.transaction.date, [Validators.required]]
    })
  }
}
