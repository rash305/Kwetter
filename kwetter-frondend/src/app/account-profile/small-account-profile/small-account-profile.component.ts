import {Component, Input, OnInit} from '@angular/core';
import {Tweet} from '../../models/tweet';
import {ProfileUser} from '../../models/ProfileUser';

@Component({
  selector: 'app-small-account-profile',
  templateUrl: './small-account-profile.component.html',
  styleUrls: ['./small-account-profile.component.css']
})
export class SmallAccountProfileComponent implements OnInit {

  @Input()
  user: ProfileUser;

  constructor() { }

  ngOnInit() {
  }

}
