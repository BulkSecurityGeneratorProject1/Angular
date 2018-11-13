import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'agm-map';  
  lat: number = -33.785809; 
  lng: number = 151.121195;

}
