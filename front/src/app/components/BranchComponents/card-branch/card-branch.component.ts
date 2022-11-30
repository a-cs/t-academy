import { Component, Input, OnInit } from '@angular/core';
import IBranch from 'src/app/interfaces/IBranch';

@Component({
  selector: 'app-card-branch',
  templateUrl: './card-branch.component.html',
  styleUrls: ['./card-branch.component.css']
})
export class CardBranchComponent implements OnInit {

  @Input() branch: IBranch

  constructor() { }

  ngOnInit(): void {
  }

}
