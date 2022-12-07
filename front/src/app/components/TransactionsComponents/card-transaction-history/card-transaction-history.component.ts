import { Component, OnInit, Input } from '@angular/core';
import ITransaction from 'src/app/interfaces/ITransaction';

@Component({
  selector: 'app-card-transaction-history',
  templateUrl: './card-transaction-history.component.html',
  styleUrls: ['./card-transaction-history.component.css']
})
export class CardTransactionHistoryComponent implements OnInit {

  @Input() transaction: ITransaction

  constructor() { }

  ngOnInit(): void {
  }

}
