/**
 * Footer component
 * @author : Jesus Lising <jess.lising@gmail.com>
 */

import {Component, OnInit } from '@angular/core';
import { AppGlobal } from '../../app.global';

@Component({
	selector : 'app-footer',
	templateUrl :'./footer.component.html'
})
export class FooterComponent {
    constructor(public _appGlobal : AppGlobal){}
}
