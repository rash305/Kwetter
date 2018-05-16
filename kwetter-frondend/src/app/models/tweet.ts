import {ProfileUser} from './ProfileUser';
import moment = require('moment');
import {forEach} from '@angular/router/src/utils/collection';

export class Tweet {

  id: number;
  message: string;
  tweetedBy: ProfileUser;
  tweetedDate: String;
  likedCount: number;
  liked: ProfileUser[];
  constructor(values: Object = {}) {
    Object.assign(this, values);

  }

  public getTweetedAgo() {
    const newDate = new Date(this.tweetedDate.replace('[UTC]', ''));

    return moment(newDate).fromNow();
  }

  public getLikedString() {
    let returnString = '';
    this.liked.forEach((item, index) => {
      returnString += item.username;
      returnString += ', ';
    });
    return returnString;
  }
}

