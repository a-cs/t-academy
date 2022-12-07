import { Component, OnInit } from '@angular/core';
import { TransactionService } from 'src/app/service/transaction.service';
import ITransaction from 'src/app/interfaces/ITransaction';
import { PageEvent } from '@angular/material/paginator';

@Component({
  selector: 'app-transaction-history',
  templateUrl: './transaction-history.component.html',
  styleUrls: ['./transaction-history.component.css']
})
export class TransactionHistoryComponent implements OnInit {

  transactions: ITransaction[]

  idList: number[] = [];
  length = 50;
  pageSize = 6;
  pageIndex = 0;
  pageSizeOptions = [5, 10, 25];

  hidePageSize = true;
  showPageSizeOptions = false;
  showFirstLastButtons = true;
  disabled = false;

  pageEvent: PageEvent;

  constructor(private transactionService: TransactionService) { }

  ngOnInit(): void {
    this.transactionService.getAllPageable(this.pageIndex, this.pageSize).subscribe(
      data => {
        console.log(data)
        this.transactions = data.content
        this.length = data.totalElements
        this.pageSize = data.size
        this.pageIndex = data.number
      }
    )
  }

  handlePageEvent(e: PageEvent) {
    this.pageEvent = e;
    this.length = e.length;
    this.pageSize = e.pageSize;
    this.pageIndex = e.pageIndex;

    this.transactionService.getAllPageable(this.pageIndex, this.pageSize).subscribe((data) => {
      this.transactions = data.content
      this.length = data.totalElements
      this.pageSize = data.size
      this.pageIndex = data.number
    });


  }
}
