import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from 'src/app/service/user.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent implements OnInit {
  form: FormGroup;
  showPassword: boolean = false;

  constructor(
    private formBuilder: FormBuilder,
    private userService: UserService,
    public router: Router,
    private notificationService: ToastrService
  ) {}

  ngOnInit(): void {
    this.configureForm();
  }

  configureForm() {
    this.form = this.formBuilder.group({
      username: ['', [Validators.required]],
      password: [
        '',
        [
          Validators.required,
          Validators.minLength(5),
          Validators.maxLength(20),
        ],
      ],
    });
  }

  login() {
    // console.log(this.form)
    let username = this.form.controls['username'].value;
    let password = this.form.controls['password'].value;
    // console.log({username, password})
    this.userService.login(username, password).subscribe(
      (response) => {
        console.log('res!', response);
        localStorage.setItem('T-WMS_token', response.access_token);
        this.router.navigate(['']);
      },
      (error) => {
        this.notificationService.error('Login or password invalid', 'Error!', {
          progressBar: true,
        });
      }
    );
  }

  togglePasswordVisibility(item: any) {
    this.showPassword = !this.showPassword;
    item.focus();
  }
}
