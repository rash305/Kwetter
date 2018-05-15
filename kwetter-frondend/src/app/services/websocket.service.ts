import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { Observer } from 'rxjs/Observer';
import { Subject } from 'rxjs/Subject';

import * as socketIo from 'socket.io-client';
import {Data} from '@angular/router';
import {TOKEN_NAME} from './auth.constant';


@Injectable()
export class WebSocketService {
  private socket: Subject<MessageEvent>;


  public connect(url): Subject<MessageEvent> {
    if (!this.socket) {
      this.socket = this.create(url);
    }
    return this.socket;
  }

  private create(url): Subject<MessageEvent> {
    // const ws = new WebSocket(this.buildUrl(url) );
    const token = localStorage.getItem(TOKEN_NAME) ;
    const options = {
      headers: {
        'Authorization' : 'JWT ' + token
      }
    };
    const ws = new WebSocket(this.buildUrl(url) );

    const observable = Observable.create(
      (obs: Observer<MessageEvent>) => {
        ws.onmessage = obs.next.bind(obs);
        ws.onerror = obs.error.bind(obs);
        ws.onclose = obs.complete.bind(obs);
        return ws.close.bind(ws);
      }
    );
    const observer = {
      next: (data: Object) => {
        if (ws.readyState === WebSocket.OPEN) {
          ws.send(JSON.stringify(data));
        }
      },
    };

    return Subject.create(observer, observable);
  }

  private buildUrl(url): string {
    return 'ws://' + url + '/' + localStorage.getItem(TOKEN_NAME) ;
  }

}

