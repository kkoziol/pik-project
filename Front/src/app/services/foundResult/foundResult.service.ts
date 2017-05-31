import {AuthorizationHttp} from "../authorizationHttp/authorizationHttp";
import {Injectable} from "@angular/core";
import {AuthorizationService} from "../authorization/authorization.service";
import {repeat} from "rxjs/operator/repeat";

type NotificationPermission = "default" | "denied" | "granted";

type NotificationDirection = "auto" | "ltr" | "rtl";

export interface Listening {
  active:boolean;
}

@Injectable()
export class FoundResultService {

  //foundOrders: Order[];
  listening: Listening;
  constructor(private authorizationHttp: AuthorizationHttp, private authotrizationService: AuthorizationService) {
    this.startListening();
    this.listening =  {active: false};
    if(authotrizationService.authorize.active)
      this.listening.active = true;
  }

  startListening() {
    this.liste();
  }

  liste() {
    if(this.listening)
    this.authorizationHttp.get("/foundresults/async/" + this.authotrizationService.username).map(res => res.json()).subscribe(data => {
      this.notifyMe(); this.liste();},error2 => {if(error2 === 504)this.liste()})
  }


  notifyMe() {
    // Let's check if the browser supports notifications
    if (!("Notification" in window)) {
      alert("This browser does not support desktop notification");
    }

    // Let's check whether notification permissions have already been granted
    else if (Notification.permission === "granted") {
      // If it's okay let's create a notification
      var notification = new Notification("Masz nową ofertę!");
      notification.onclick = function(){
        window.location.href = "http://localhost:4200/result";
        this.cancel();
      };
    }

    // Otherwise, we need to ask the user for permission
    else if (Notification.permission !== "denied") {
      Notification.requestPermission(function (permission) {
        // If the user accepts, let's create a notification
        if (permission === "granted") {
          var notification = new Notification("Masz nową ofertę!");
          notification.onclick = function(){
            window.location.href = "localhost:4200/result";
            this.cancel();
          };
        }
      });
    }

    // At last, if the user has denied notifications, and you
    // want to be respectful there is no need to bother them any more.
  }
}



interface NotificationPermissionCallback {
  (permission: NotificationPermission): void;
}

interface NotificationOptions {
  dir?: NotificationDirection;
  lang?: string;
  body?: string;
  tag?: string;
  image?: string;
  icon?: string;
  badge?: string;
  sound?: string;
  vibrate?: number | number[],
  timestamp?: number,
  renotify?: boolean;
  silent?: boolean;
  requireInteraction?: boolean;
  data?: any;
  actions?: NotificationAction[]
}

interface NotificationAction {
  action: string;
  title: string;
  icon?: string;
}

declare class Notification extends EventTarget {
  constructor(title: string, options?: NotificationOptions);

  static readonly permission: NotificationPermission;
  static requestPermission(): Promise<NotificationPermission>;
  static requestPermission(deprecatedCallback: (permission: NotificationPermission) => void): void;

  static readonly maxActions: number;

  onclick: EventListenerOrEventListenerObject;
  onerror: EventListenerOrEventListenerObject;

  close(): void;

  readonly title: string;
  readonly dir: NotificationDirection;
  readonly lang: string;
  readonly body: string;
  readonly tag: string;
  readonly image: string;
  readonly icon: string;
  readonly badge: string;
  readonly sound: string;
  readonly vibrate: number[];
  readonly timestamp: number;
  readonly renotify: boolean;
  readonly silent: boolean;
  readonly requireInteraction: boolean;
  readonly data: any;
  readonly actions: NotificationAction[]
}

