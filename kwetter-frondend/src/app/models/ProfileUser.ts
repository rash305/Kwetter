export class ProfileUser {

  id: number;
  username: string;
  location: string;
  avatarPath: string;
  website: string;
  bio: string;
  constructor(values: Object = {}) {
    Object.assign(this, values);

  }
}
