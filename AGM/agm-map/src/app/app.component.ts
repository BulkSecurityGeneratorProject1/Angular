import { Component, ViewChild, OnInit, AfterViewInit } from '@angular/core';
import {MyMarker} from './marker';
import {MarkerService} from './markers.service';
import { GoogleMapsAPIWrapper,AgmMap, LatLngBounds,LatLngBoundsLiteral } from '@agm/core';


declare var google: any;

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit,AfterViewInit{
  title = 'agm-map';  

  //initial center location
  lat: number = 24.799448; 
  lng: number = 120.979021;
  markers:MyMarker[];
  zoom:Number =14;

  @ViewChild('AgmMap') agmMap: AgmMap;

  constructor(private markersService:MarkerService) {}

  ngOnInit() {
    this.getMarkers();
  }

    ngAfterViewInit(){
    console.log(this.agmMap);
    this.agmMap.mapReady.subscribe(map=> {
      const bounds: LatLngBounds = new google.maps.LatLngBounds();
      for (const mm of this.markers) {
        bounds.extend(new google.maps.LatLng(mm.lat,mm.lng));
      }
      map.fitBounds(bounds);
  });
}
  getMarkers():void{
    this.markers = this.markersService.getMarkers();
  }

  mapIdle(){
    console.log('idle')
  }
}
