import { Component, OnInit } from '@angular/core';
import {Tweet} from '../models/tweet';
import {TweetDataService} from '../services/tweet-data.service';
import {ProfileDataService} from '../services/profile-data.service';
import {ProfileUser} from '../models/ProfileUser';
import {TOKEN_NAME} from '../services/auth.constant';
import {JwtHelper} from 'angular2-jwt';
import {ActivatedRoute, Params, Router} from '@angular/router';
import {WebSocketService} from '../services/websocket.service';
import {Subject} from 'rxjs/Subject';

@Component({
  selector: 'app-account-profile',
  templateUrl: './account-profile.component.html',
  styleUrls: ['./account-profile.component.css']
})
export class AccountProfileComponent implements OnInit {
  jwtHelper: JwtHelper = new JwtHelper();

  username$: string = null;
  tweets$ = [];
  loggedinUserId: number = null;
  userPage$: ProfileUser = new ProfileUser();
  followingList: ProfileUser[] = [];
  followersList: ProfileUser[] = [];

  constructor(private tweetSocketService: WebSocketService, private profileDataService: ProfileDataService, private tweetDataService: TweetDataService, private route: ActivatedRoute, private router: Router) {
    this.route.params.subscribe( params => this.setOtherUserId(params) );

  }


  ngOnInit() {

    const decodedToken = this.jwtHelper.decodeToken(localStorage.getItem(TOKEN_NAME));
    if (decodedToken.userid == null || decodedToken.userid === undefined) {
      this.router.navigateByUrl('/login');
    } else {
      this.loggedinUserId = decodedToken.userid;
    }

    if (this.username$ !== null) {
      this.profileDataService.getUsernameProfile(this.username$).subscribe((user) => {
        this.userPage$ = user;
        console.log(user);
        this.GetFollowingAndFollowers(user.id);
      });
    } else {
      this.profileDataService.getUserProfile(this.loggedinUserId).subscribe((user) => {
        this.userPage$ = user;
        this.GetFollowingAndFollowers(user.id);
      });
    }

    this.UpdateTweetsSocket();
  }
  GetFollowingAndFollowers(userid: number) {
    this.profileDataService.getFollowing(userid).subscribe((userlist) => {
      this.followingList = userlist;
    });
    this.profileDataService.getFollowers(userid).subscribe((userlist) => {
      this.followersList = userlist;
    });
    this.tweetDataService.getTweets(userid).subscribe((tweets) => {
      this.tweets$ = tweets;
    });
    }

  private setOtherUserId(params: Params) {
    if (params.username !== undefined) {
      this.username$ = params.username;
    }
  }

  Follow() {
    this.profileDataService.followUser(this.userPage$.id, this.loggedinUserId).subscribe((Message) => {
      alert(Message);
    });
  }

    Unfollow() {
    this.profileDataService.unfollowUser(this.userPage$.id, this.loggedinUserId).subscribe((Message) => {
      alert(Message);
    });
  }


  private UpdateTweetsSocket(): void {
    const websocket: Subject<MessageEvent> = this.tweetSocketService.connect('localhost:8080/kwetter-1.0-SNAPSHOT/websocket');


    websocket.subscribe((response: MessageEvent) => {
      const tweet: Tweet = new Tweet(JSON.parse(response.data));

      if (tweet.tweetedBy.id !== this.userPage$.id) {
        return;
      }
      this.tweets$.unshift(tweet);
    });
  }
}
