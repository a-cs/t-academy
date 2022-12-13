import { Component, OnInit } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { ModalAddBranchComponent } from 'src/app/components/BranchComponents/modal-add-branch/modal-add-branch.component';
import IBranch from 'src/app/interfaces/IBranch';
import { AuthService } from 'src/app/service/auth.service';
import { BranchService } from 'src/app/service/branch.service';
import { buttonPermission } from 'src/app/utils/utils';

@Component({
  selector: 'app-branch',
  templateUrl: './branch.component.html',
  styleUrls: ['./branch.component.css'],
})
export class BranchComponent implements OnInit {
  component = ModalAddBranchComponent;
  branches: IBranch[];
  searchText: string;

  permissions = buttonPermission;

  isLoading: boolean = false;
  isError: boolean = false;
  hideSearchBar: boolean = false

  constructor(private branchService: BranchService, public auth: AuthService, private notification: ToastrService) {
    this.branchService.branchChanged.subscribe(() => {
      this.getData();
    });
  }

  ngOnInit(): void {
    this.getData();
  }

  getData() {
    this.isLoading = true
    this.hideSearchBar = true
    this.branchService.get().subscribe((data) => {
      this.branches = data;
      this.isLoading = false
      this.hideSearchBar = false
    }, error => {
      this.isLoading = false
      this.isError = true
      this.notification.error(error.error.message, 'Error: No server response', {
        tapToDismiss: true,
        disableTimeOut: true,
        closeButton: true,
      });
    })
  }

  onSearchTextEntered(searchValue: string) {
    this.isLoading = true
    this.hideSearchBar = false
    this.searchText = searchValue;
    this.branchService.getByLikeName(this.searchText).subscribe((data) => {
      this.branches = data;
      this.isLoading = false
    }, error => {
      this.isLoading = false
      this.isError = true
      this.notification.error(error.error.message, 'Error: No server response', {
        tapToDismiss: true,
        disableTimeOut: true,
        closeButton: true,
      });
    });
  }
}
