import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
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
  isLogin: boolean = true;

  constructor(
    private formBuilder: FormBuilder,
    private userService: UserService,
    public router: Router,
    private notificationService: ToastrService
  ) { }

  ngOnInit(): void {
    this.configureForm();
  }

  configureForm() {
    this.form = this.formBuilder.group({
      email: ['', [Validators.required, Validators.email]],
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

  submit() {
    let email = this.form.controls['email'].value;
    if (this.isLogin) {
      let password = this.form.controls['password'].value;
      this.userService.login(email, password).subscribe(
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
    } else {
      this.userService.forgotPassword(email).subscribe(
        (response) => { },
        (error) => {

          this.notificationService.error(error.error.message, 'Error!', {
            progressBar: true,
          });
        },

        () => {
          this.notificationService.success(`En email was sent to your email address`,
            'Success!',
            { progressBar: true }
          );
        }
      );
    }
  }

    togglePasswordVisibility(item: any) {
      this.showPassword = !this.showPassword;
      item.focus();
    }

    toggleLogin(){
      this.isLogin = !this.isLogin
      if (this.isLogin) {
        this.form.addControl('password', new FormControl("",
          [
            Validators.required,
            Validators.minLength(5),
            Validators.maxLength(20),
          ],
        ))
      } else {
        this.form.removeControl('password');
      }
    }
  }
