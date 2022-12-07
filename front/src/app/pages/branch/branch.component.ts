import { Component, OnInit } from '@angular/core';
import { ModalAddBranchComponent } from 'src/app/components/BranchComponents/modal-add-branch/modal-add-branch.component';
import IBranch from 'src/app/interfaces/IBranch';
import { AuthService } from 'src/app/service/auth.service';
import { BranchService } from 'src/app/service/branch.service';
import { buttonPermission } from 'src/app/utils/utils';

@Component({
  selector: 'app-branch',
  templateUrl: './branch.component.html',
  styleUrls: ['./branch.component.css']
})
export class BranchComponent implements OnInit {

  component = ModalAddBranchComponent
  branches: IBranch[]
  searchText: string

  permissions = buttonPermission

  constructor(private branchService: BranchService, public auth: AuthService) { }

  ngOnInit(): void {
    this.branchService.get().subscribe(
      data => {
        this.branches = data
        console.log(this.branches)
      }
    )
  }

  onSearchTextEntered(searchValue: string) {
    this.searchText = searchValue
    this.branchService.getByLikeName(this.searchText).subscribe(
      data => {this.branches = data
        console.log(this.branches)
      }
      )
  }

}
