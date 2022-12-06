import { Component, OnInit } from '@angular/core';
import { TransactionService } from 'src/app/service/transaction.service';
import ITransaction from 'src/app/interfaces/ITransaction';

@Component({
  selector: 'app-transaction-history',
  templateUrl: './transaction-history.component.html',
  styleUrls: ['./transaction-history.component.css']
})
export class TransactionHistoryComponent implements OnInit {

  transactions: ITransaction[]
  constructor(private transactionService: TransactionService) { }

  ngOnInit(): void {
    this.transactionService.getAll().subscribe(
      data => {
        this.transactions = data
      }
    )
  }

}
