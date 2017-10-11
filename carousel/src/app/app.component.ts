import { Component } from '@angular/core';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {

  items: Array<any> = []

  constructor() {
    this.items = [
      { name: 'assets/images/thumb1.jpg' },
      { name: 'assets/images/thumb2.jpg' },
      { name: 'assets/images/thumb3.jpg' },
      { name: 'assets/images/thumb4.jpg' },
      { name: 'assets/images/thumb5.jpg' },
      { name: 'assets/images/thumb6.jpg' },

     ]
  }
  title = 'app';
}
