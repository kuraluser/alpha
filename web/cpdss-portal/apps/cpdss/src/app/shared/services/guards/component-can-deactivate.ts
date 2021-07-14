import {HostListener, Injectable} from "@angular/core";



@Injectable()
export abstract class ComponentCanDeactivate {
 
  abstract  hasUnsavedData(): boolean;



    @HostListener('window:beforeunload', ['$event'])
    unloadNotification($event: any) {
        if (!this.hasUnsavedData()) {
            $event.returnValue =true;
        }
    }
}