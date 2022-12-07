import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import {  MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ToastrService } from 'ngx-toastr';
import { map, Observable, startWith } from 'rxjs';
import IAccessLevel from 'src/app/interfaces/IAccessLevel';
import IBranch from 'src/app/interfaces/IBranch';
import IUser from 'src/app/interfaces/IUser';
import { BranchService } from 'src/app/service/branch.service';
import { UserService } from 'src/app/service/user.service';

@Component({
  selector: 'app-modal-create-user',
  templateUrl: './modal-create-user.component.html',
  styleUrls: ['./modal-create-user.component.css']
})

export class ModalCreateUserComponent implements OnInit {

  form: FormGroup
  rolesList: IAccessLevel[]
  firstRole: IAccessLevel
  filteredRoles: Observable<IAccessLevel[]>
  branchList: IBranch[]
  firstBranch: IBranch
  filteredBranches: Observable<IBranch[]>

  constructor(@Inject(MAT_DIALOG_DATA)
  public user: IUser,
  private formBuilder: FormBuilder,
  private branchService: BranchService,
  private userService: UserService,
  private dialogRef: MatDialogRef<ModalCreateUserComponent>,
  private notificationService: ToastrService
  ){}

  ngOnInit(): void {
    this.userService.getRoles().subscribe(roles => this.onGetRoles(roles))
    this.branchService.get().subscribe(branches => this.onGetBranches(branches))

    this.configureForm()

  }

  configureForm(){
    this.form = this.formBuilder.group({
      username: ["", [Validators.required]],
      email: ["", [Validators.required, Validators.email]],
      branch: ["", Validators.required],
      role: ["", Validators.required]
    })
  }

  private _filterRoles(name: string): IAccessLevel[] {
    const filterValue = name.toLowerCase()
    return this.rolesList.filter(role => role.authority.toLowerCase().includes(filterValue));
  }

  private _filterBranches(name: string): IBranch[] {
    const filterValue = name.toLowerCase();
    return this.branchList.filter(branch => branch.name.toLowerCase().includes(filterValue));
  }

  onGetRoles(data: IAccessLevel[]){
    this.rolesList = data;

    this.filteredRoles= this.form.controls["role"].valueChanges.pipe(
      startWith(''),
      map((value: any) => {
        const name = value = typeof value === 'string' ? value : value?.authority;
        return name ? this._filterRoles(name as string) : this.rolesList.slice();
      }),
    );
    this.filteredRoles.subscribe(role => {
      this.firstRole = role[0]})
  }
  onGetBranches(data: IBranch[]){
    this.branchList = data;
    this.filteredBranches= this.form.controls["branch"].valueChanges.pipe(
      startWith(''),
      map((value: any) => {
        const name = value = typeof value === 'string' ? value : value?.name;
        return name ? this._filterBranches(name as string) : this.branchList.slice();
      }),
    );
    this.filteredBranches.subscribe(branch => {
      this.firstBranch = branch[0]
      })
    
  }


  create(){

    const userInfo: IUser = {
      username: this.form.value.username,
      email: this.form.value.email,
      enabled: this.form.value.enabled,
      accessLevel: this.form.value.role
    }
    this.userService.createUser(userInfo).subscribe(
      response => {
       },
      (error) => {
        this.notificationService.error(error.error.message, 'Error!', {
          progressBar: true,
        });
      },

      () => {
        this.notificationService.success(`User created successfully.`,
          'Success!',
          { progressBar: true }
        )
        this.userService.userChanged.emit()
        this.dialogRef.close()
      }
      )
  }

  roleOnCLick() {
   this.firstRole = this.form.controls['role'].getRawValue(); 
  }
  branchOnCLick() {
    this.firstBranch = this.form.controls['branch'].getRawValue(); 
  }

  roleFallback(){
    this.form.controls['role'].setValue(this.firstRole)
  }

  branchFallback(){
  
    this.form.controls['branch'].setValue(this.firstBranch)
  }

  displayRole(role: IAccessLevel): string {
    return role && role.authority ? role.authority : '';
  }

  displayBranch(branch: IBranch): string {
    return branch && branch.name ? branch.name : '';
  }

  optionSelected(roleSelected:IAccessLevel){
    this.firstRole = roleSelected;
  }
}
