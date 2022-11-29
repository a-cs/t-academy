import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  form: FormGroup
  showPassword: boolean = false

  constructor(private formBuilder: FormBuilder) { }

  ngOnInit(): void {
    this.configureForm()
  }

  configureForm() {
    this.form = this.formBuilder.group({
      email: ["", [Validators.required, Validators.email]],
      password: ["", [Validators.required, Validators.minLength(5), Validators.maxLength(20)]],
    })
  }

  login(){
    // console.log(this.form)
    let email = this.form.controls['email'].value
    let password = this.form.controls['password'].value
    console.log({email, password})
  }


  togglePasswordVisibility(item: any) {
    this.showPassword = !this.showPassword
    item.focus()
  }
}
