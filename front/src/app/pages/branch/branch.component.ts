import { Component, OnInit } from '@angular/core';
import { ModalAddBranchComponent } from 'src/app/components/BranchComponents/modal-add-branch/modal-add-branch.component';
import IBranch from 'src/app/interfaces/IBranch';
import { BranchService } from 'src/app/service/branch.service';

@Component({
  selector: 'app-branch',
  templateUrl: './branch.component.html',
  styleUrls: ['./branch.component.css']
})
export class BranchComponent implements OnInit {

  component = ModalAddBranchComponent
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
