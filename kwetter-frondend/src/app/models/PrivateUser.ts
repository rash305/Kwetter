export class PrivateUser {

  id: number;
  email: string;
  username: string;
  password: string;
  constructor(values: Object = {}) {
    Object.assign(this, values);

  }
}
