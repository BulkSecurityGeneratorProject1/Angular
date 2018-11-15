import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';

import {AgmCoreModule} from  '@agm/core';
import { MarkerService } from './markers.service';
import { GoogleMapsAPIWrapper} from '@agm/core';
import { AgmDirectionModule} from 'agm-direction';
import { GeocodeComponent } from './geocode/geocode.component'; // 
import { FormsModule } from '@angular/forms';

@NgModule({
  declarations: [
    AppComponent,
    GeocodeComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    AgmCoreModule.forRoot({
      apiKey:'AIzaSyAfJTVKnpLl0ULuuwDuix-9ANpyQhP6mfc'
    }),
    AgmDirectionModule //agm-direction
  ],
  providers: [
    MarkerService,
    GoogleMapsAPIWrapper
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
