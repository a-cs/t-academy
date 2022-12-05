import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, ValidationErrors, ValidatorFn, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from 'src/app/service/user.service';

@Component({
  selector: 'app-confirmation',
  templateUrl: './confirmation.component.html',
  styleUrls: ['./confirmation.component.css']
})
export class ConfirmationComponent implements OnInit {

  msg:string;
  token:string;
  form: FormGroup
  showPassword: boolean = false
  
  constructor(private userService: UserService,
    private formBuilder: FormBuilder,
    private activateRoute: ActivatedRoute,
    public router: Router) { }

  ngOnInit(): void {
    this.activateRoute.queryParams
      .subscribe(params => {
        // this.userService.confirmEmail(params['token']).subscribe(res=>{
        //   console.log(res)
        //   this.msg=res;
        // }, err=>{
        //   this.msg=err.error.message})
        this.token = params['token']
      })

      this.form = this.formBuilder.group({
        password: ["", [Validators.required, Validators.minLength(5), Validators.maxLength(20)]],
        password_confirmation: ["", [Validators.required, this.matchValidator('password')]],
      })
  }
  
  changePassword(){
    let password = this.form.controls['password'].value
    let password_confirmation = this.form.controls['password_confirmation'].value
    // console.log({username, password})
    this.userService.changePassword(this.token, password).subscribe(
      response => {
        console.log("res!", response)
        // localStorage.setItem("T-WMS_token", response.access_token)
        // this.router.navigate(["login"])
      },
      error => {
        console.log("err!", error)
      })
  }

  togglePasswordVisibility(item: any) {
    this.showPassword = !this.showPassword
    item.focus()
    console.log(this.form.controls['password_confirmation'].errors)
  }
  
  matchValidator(
    matchTo: string, 
    reverse?: boolean
  ): ValidatorFn {
    return (control: AbstractControl): 
    ValidationErrors | null => {
      if (control.parent && reverse) {
        const c = (control.parent?.controls as any)[matchTo] as AbstractControl;
        if (c) {
          c.updateValueAndValidity();
        }
        return null;
      }
      return !!control.parent &&
        !!control.parent.value &&
        control.value === 
        (control.parent?.controls as any)[matchTo].value
        ? null
        : { matching: true };
    };
  }
}
