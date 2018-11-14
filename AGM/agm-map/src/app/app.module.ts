import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';

import {AgmCoreModule} from  '@agm/core';
import { MarkerService } from './markers.service';
import { GoogleMapsAPIWrapper} from '@agm/core';

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    AgmCoreModule.forRoot({
      apiKey:'AIzaSyAfJTVKnpLl0ULuuwDuix-9ANpyQhP6mfc'
    })
  ],
  providers: [
    MarkerService,
    GoogleMapsAPIWrapper
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
