import { Injectable } from '@angular/core';
import { AdminModule } from '../admin.module';


/**
 * Api service for port master
 * @export
 * @class PortMasterApiService
 */

@Injectable({
  providedIn: AdminModule
})


export class PortMasterApiService {

   selectedPortLocation : any;  
  constructor() { }

  /**
   * Method to get ports list 
   * @return {*} 
   * @memberof PortMasterApiService
   */

  getPortsList() {
    return [                               //TODO -has to be replaced with actual api call later
      {
        portId :"9",
        portName: "5 West",
        portCode: "FEW",
        country: "India",
        timeZone: "UTC",
        densityOfWater: "1.025",
        temperature: "10",
        maximumDraft: "3"
      },
      {
        portId :"10",
        portName: "RAS TANURA",
        portCode: "RAS",
        country: "India",
        timeZone: "UTC",
        densityOfWater: ".025",
        temperature: "20",
        maximumDraft: "2"
      }
    ]

  }

 /**
  * Method to get country list
  * @return {*} 
  * @memberof PortMasterApiService
  */
 async getCountryList()
  {
    return await [
      { name: "India" },                 //TODO-has to be replaced with actual api call later
      { name: "Australia" }
    ]
  }
}
