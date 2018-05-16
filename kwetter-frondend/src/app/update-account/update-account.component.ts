import { Component, OnInit } from '@angular/core';
import {ProfileDataService} from '../services/profile-data.service';
import {UserService} from '../services/user.service';
import {ProfileUser} from '../models/ProfileUser';
import {PrivateUser} from '../models/PrivateUser';
import {Router} from '@angular/router';

@Component({
  selector: 'app-update-account',
  templateUrl: './update-account.component.html',
  styleUrls: ['./update-account.component.css']
})
export class UpdateAccountComponent implements OnInit {

  constructor(private profileDataService: ProfileDataService, private userService: UserService, private router: Router) { }
  publicDetails: ProfileUser = new ProfileUser();
  privateDetails: PrivateUser;
  ngOnInit() {
    this.profileDataService.getUserProfile(this.userService.getLoggedInID()).subscribe((user) => {
      this.publicDetails = user;
    });
  }

  update() {
    this.profileDataService.updatePublicUser(this.publicDetails).subscribe((user) => {
      this.router.navigate(['/']);

    });
  }

}
