import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { map, Observable } from 'rxjs';
import IAccessLevel from 'src/app/interfaces/IAccessLevel';
import IUser from 'src/app/interfaces/IUser';
import { UserService } from 'src/app/service/user.service';

@Component({
  selector: 'app-modal-update-user',
  templateUrl: './modal-update-user.component.html',
  styleUrls: ['./modal-update-user.component.css']
})
export class ModalUpdateUserComponent implements OnInit {

  form: FormGroup
  userCopy: IUser
  rolesList: IAccessLevel[]
  filteredRoles: Observable<IAccessLevel[]>
  firstRole: IAccessLevel

  constructor(@Inject(MAT_DIALOG_DATA)
    public user: IUser,
    private formBuilder: FormBuilder,
    private userService:UserService) { 
      this.userCopy = Object.assign({}, this.user)
    }

  ngOnInit(): void {
    this.userService.getRoles().subscribe(roles => this.rolesList = roles)

    this.firstRole = this.user.accessLevel
    this.configureForm();
    this.filteredRoles= this.form.controls["role"].valueChanges.pipe(
      map((value: any) => {
        const name = value = typeof value === 'string' ? value : value?.authority;
        return name ? this._filterRoles(name as string) : this.rolesList.slice();
      }),
    );
    
    this.filteredRoles.subscribe(role => this.firstRole = role[0])
  }

  configureForm() {
    this.form = this.formBuilder.group({
      role: [this.firstRole, [Validators.required]]
    })
  }

  private _filterRoles(name: string): IAccessLevel[] {
    const filterValue = name.toLowerCase();
    return this.rolesList.filter(role => role.authority.toLowerCase().includes(filterValue));
  }


  displayRole(role: IAccessLevel): string {
    return role && role.authority ? role.authority : '';
  }

  roleFallback() {
    setTimeout(() => {
      this.form.controls['role'].setValue(this.firstRole)
  }, 200);
  }

  optionSelected(roleSelected:IAccessLevel){
    this.firstRole = roleSelected;
  }

  enableUser(){
    this.userCopy.enabled = !this.userCopy.enabled
  }

  update() {
    const userUpdated: IUser = {
      id: this.userCopy.id,
      username: this.userCopy.username,
      email: this.userCopy.email,
      enabled: this.userCopy.enabled,
      accessLevel: this.form.value.role
    }
    this.userService.updateUser( userUpdated).subscribe(
      response => { window.location.reload() }, error => { console.log("err!", error) }
      )
  }

}
