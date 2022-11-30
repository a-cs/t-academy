import { Component, OnInit } from '@angular/core';
import IBranch from 'src/app/interfaces/IBranch';
import { BranchService } from 'src/app/service/branch.service';

@Component({
  selector: 'app-branch',
  templateUrl: './branch.component.html',
  styleUrls: ['./branch.component.css']
})
export class BranchComponent implements OnInit {

  branches: IBranch[]

  constructor(private branchService: BranchService) { }

  ngOnInit(): void {
    this.branchService.get().subscribe(
      data => {
        this.branches = data
        console.log(this.branches)
      }
    )
  }

}
