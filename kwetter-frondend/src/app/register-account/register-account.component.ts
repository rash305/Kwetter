import { Component, OnInit } from '@angular/core';
import {UserService} from '../services/user.service';
import {AuthenticationService} from '../services/authentication.service';
import {ActivatedRoute, Router} from '@angular/router';
import {PrivateUser} from '../models/PrivateUser';


@Component({
  selector: 'app-register-account',
  templateUrl: './register-account.component.html',
  styleUrls: ['./register-account.component.css']
})
export class RegisterAccountComponent implements OnInit {
  model: PrivateUser = new PrivateUser();
  repeatPassword$: string;
  loading = false;
  error = '';
  redirectUrl: string;

  constructor(private router: Router,
              private activatedRoute: ActivatedRoute,
              private authenticationService: AuthenticationService,
              private userService: UserService) {
    this.redirectUrl = this.activatedRoute.snapshot.queryParams['redirectTo'];
  }
  ngOnInit() {
  }

  create() {
    this.loading = true;

    if (!(this.repeatPassword$ === this.model.password)) {
      this.error = 'Passwords do not match!';
      return;
    }
    this.error = '';
    this.authenticationService.createUser(this.model)
      .subscribe(
        result => {
          this.loading = false;

          if (result) {
            this.login(this.model.username, this.model.password);
          } else {
            this.error = 'User is not created';
          }
        },
        error => {
          this.error = error;
          this.loading = false;
        }
      );
  }

  private navigateAfterSuccess() {
    if (this.redirectUrl) {
      this.router.navigateByUrl(this.redirectUrl);
    } else {
      this.router.navigate(['/']);
    }
  }

  login(username: string, password: string) {
    this.authenticationService.login(this.model.username, this.model.password)
      .subscribe(
        result => {
          this.loading = false;

          if (result) {
            this.userService.login(result);
            this.router.navigate(['/']);
            this.navigateAfterSuccess();
          } else {
            this.error = 'Username or password is incorrect';
          }
        },
        error => {
          this.error = 'Username or password is incorrect';
          this.loading = false;
        }
      );
  }

}
