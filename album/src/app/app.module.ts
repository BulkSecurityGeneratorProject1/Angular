import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HolderjsDirective } from 'angular-2-holderjs/holderjs.directive';

import { AppComponent } from './app.component';

@NgModule({
  declarations: [
    AppComponent,
    HolderjsDirective
  ],
  imports: [
    BrowserModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
