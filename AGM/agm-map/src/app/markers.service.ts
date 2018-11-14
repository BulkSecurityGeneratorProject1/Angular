import {Injectable} from '@angular/core'

import {MyMarker} from './marker';

import {MARKERS} from './mock-markers'; 



@Injectable()
export class MarkerService{

    constructor() {}

    public getMarkers():MyMarker[] {
        return MARKERS;
    }
}