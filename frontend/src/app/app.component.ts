import { environment } from '../environments/environment';
import { Component } from '@angular/core';
import { Title }     from '@angular/platform-browser';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  constructor(private _titleService: Title ) {
     this._titleService.setTitle( environment.appTitle + " " + environment.appVersion );
  }
}
