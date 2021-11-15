rh._.exports({"0":[[" ","Loadable Plan"]],"1":[[" ","Loadable Plan"]],"2":[[" ","Loadable Quantity"],[" ","Stowage Pattern"],[" ","Arrival/Departure Condition at Each Port"],[" ","User Comments"]],"3":[[" ","A loadable plan is the output generated from the loadable study. Once the user generates multiple loadable patterns for a loadable study and select one option based on the ranking, loading order, and the quantity achievable, the user can proceed to loadable plan. "," ","The user can select and confirm a Loadable plan only for the loadable study for which the status is ","Plan Generated",". "," ","To view the loadable plan for a loadable study, go to the required Loadable Study page and click ","View Pattern",". "," ","The Loadable Pattern page corresponding to the selected loadable study appears:"," ","The loadable pattern page displays all the loadable patterns generated against the selected loadable study. Click ","View Plan"," to review each plan and select the optimal loadable plan that meets all the cargo allocation requirements in the best way possible. "," ","The ","LOADABLE PLAN"," page appears:"," ","The loadable plan is the stowage plan generated before the loading operation and displays the loadable quantity, cargo allocation, and arrival and departure condition details. All these details are explained in three sections:"," ","Loadable quantity"," ","Stowage pattern"," ","Arrival and Departure condition at each port"],[" ","The loadable quantity section populates the maximum quantity of cargo that can be loaded at each loading port during the voyage."," ","The application auto-calculates the loadable quantity value considering the following factors:"," ","The difference between the deadweight and on board quantities at each port."," ","The feasibility of cargo allocation in the tanks."," ","The tolerance limit."," ","Volume maximization to reduce the loading and discharging time."," ","Usage of minimal number of tanks for loading operations to load new cargoes in the vacant tanks when required. "," ","The Loadable Quantity section on the Loadable Plan page displays the following details:"," ","Field Name"," ","Description"," ","Grade "," ","This field displays the name of the cargo selected in the loadable study."," ","Estimated API"," ","This field displays the estimated value of the API used for the calculation of loadable study. "," ","Estimated Temp"," ","This field displays the estimated value of temperature used for the calculation of loadable study."," ","Order Quantity"," ","This field displays the actual volume of each cargo based on the enquiry received against the voyage.","\n            The Order Quantity of each cargo is displayed in both BBLS and MT units. "," ","Tolerance"," ","This field displays the minimum and maximum tolerance value of each cargo. "," ","Loadable Quantity"," ","This field displays the volume or weight of each cargo that is feasible with the selected loadable pattern. "," ","The Loadable Quantity is displayed in multiple units including BBLS, MT, KL, and LT."," ","% Diff"," ","This field displays the percentage difference between the order quantity and the loadable quantity. "," ","The % Diff value can either be positive or negative. "," ","The details of the commingled cargoes if any are displayed under the ","Loadable Quantity ","section. "," ","The ","COMMINGLE CARGO DETAILS"," section displays the following details:"," ","Field Name"," ","Description"," ","Grade "," ","This field displays the name of the commingled cargo selected in the loadable study."," ","Tank"," ","This field displays the tank allocation of commingled cargoes."," ","Quantity"," ","This field displays the volume or weight of the commingled cargoes loaded in the respective tank. "," ","API"," ","This field displays the API of commingled cargoes."," ","Temp"," ","This field displays the temperature of commingled cargoes."," ","Composition Breakdown"," ","This field displays the commingled cargo breakdown into different grades, percentage of commingle, and units. "," ","The commingled cargo quantity is displayed in the following units:"," ","BBLS at observed temperature"," ","BBLS at 60F"," ","LT"," ","MT"," ","KL"],[" ","The stowage pattern section displays the cargo and ballast allocation details. "," ","The CP-DSS application displays the cargo allocation details of the selected loadable study by default. To view the ballast allocation details, click ","Ballast",". "," ","The stowage layout of cargo and ballast tanks displays the following details of each cargo when the user mouse hovers the respective tank name."," ","Field Name"," ","Description"," ","Tank Name"," ","The name of the selected tank."," ","Cargo ABV"," ","The cargo allocated in the selected tank as per the loadable plan."," ","Different cargoes in the tanks are represented with unique colors. "," ","Volume"," ","The calculated volume of cargo that can be loaded in each tank as per the loadable plan. "," ","Weight"," ","The expected weight of the cargo in each tank as per the selected loadable plan."," ","Ullage"," ","The ullage level in each tank. This value is calculated based on the volume of cargo in each tank."," ","Filling percentage"," ","The percentage of filling in each tank. "," ","Click ","More ","to view more details on the stowage plan. "," ","The stowage details mapped against the selected loadable study appears in a table format. The stowage data is fetched from the Loadicator."," ","The stowage data table displays the quantity and volume of each cargo is in different units."," ","The stowage data table displays the following tank allocation details based on the selected loadable plan:"," ","Field Name"," ","Description"," ","Tank Name"," ","This field populates the name of the tanks in the vessel."," ","Cargo Abbreviation"," ","This field populates the abbreviation of the cargoes loaded in the tanks."," ","Ullage"," ","This field populates the observed ullage value updated by the user. "," ","The ullage value is displayed in centimeters. "," ","Correction Factor"," ","This field populates the correction factor value. The data is updated from the Loadicator. "," ","Corrected Ullage"," ","This field populates the corrected ullage value based on the correction factor."," ","Observed Volume"," ","This field populates the quantity of cargo at observed temperature. The value is updated from the Loadicator. "," ","Weight"," ","This field populates the weight of cargo in each tank. "," ","The default unit is MT."," ","Percentage Filling"," ","This field populates the percentage of filling in each tank."," ","API"," ","This field populates the API of the cargo loaded in each tank."," ","Temperature"," ","This field populates the temperature of cargo loaded in each tank. "," ","To hide the stowage data, click ","Hide",". "," ","To edit the stowage data, click"," Edit Stowage",". "," ","The ","RDG Ullage"," field becomes editable."," ","Edit the RDG Ullage value against the required tanks. The other stowage values are auto-updated based on the RDG Ullage value. "," ","The RDG Ullage value for each tank must be positive. The updated total cargo quantity must stay within the tolerance limit and must not exceed the loadable quantity calculated in the respective loadable study."," ","Click"," Validate and Save ","to update the changes. "," ","A success message appears on the screen indicating that the ullage value is updated successfully. "," ","The Loadicator validates the new loadable plan for stability parameters. The CP-DSS application saves the updated loadable plan only if the plan satisfies all the stability parameters. "],[" ","The ","ETA/ETD CONDITION AT EACH PORT ","section displays the arrival and departure condition of the vessel at each port in a table format. "," ","The arrival and departure condition data is updated from the ","Synoptical Table",". The table displays the following ETA/ETD values of the vessel. "," ","Field Name"," ","Description"," ","Port Name"," ","This field populates the names of the ports selected in the respective loadable study. "," ","ETA/ETD Date"," ","This field populates the estimated date of arrival and departure of the vessel at each port. "," ","ETA/ETD Time"," "," This field populates the estimated time of arrival and departure of the vessel at each port."," ","Draft"," ","This field populates the Fore Draft, After Draft, and Mid Ship Draft values of the vessel at each port. "," ","The draft values are updated from the Loadicator. "," ","Trim"," ","This field populates the trim condition of the vessel at each port. "," ","The trim values are updated from the Loadicator. "," ","Bending Moment"," ","This field populates the bending moment of the vessel at each port. "," ","The data is updated from the Loadicator. "," ","Shearing Force"," ","This field populates the shearing force on the vessel at each port. "," ","The data is updated from the Loadicator. "," ","List"," ","This field populates the list condition of the vessel at each port. "," ","The data is updated from the Loadicator. "," ","Cargo MT"," ","This field populates the amount of cargo on board at each port during the arrival and departure condition. "," ","Fuel Oil"," ","This field populates the the amount of fuel oil on board at each port during the arrival and departure condition. "," ","Diesel Oil"," ","This field populates the amount of diesel oil on board at each port during the arrival and departure condition. "," ","Ballast"," ","This field populates the quantity of water ballast on board at each port during the arrival and departure condition. "," ","Fresh Water"," ","This field populates the amount of fresh water on board at each port during the arrival and departure condition. "," ","Others"," ","This field populates the amount of other remains on board at each port during the arrival and departure condition. "," ","Total DWT"," ","This field populates the total weight the vessel can carry."," ","Displacement"," ","This field populates the total weight of the vessel."," ","SP Gravity"," ","This field populates the specific gravity of sea water at each port. The default value is 1.025."],[" ","The user can make changes to the loadable plan by updating the ullage value in the stowage section. The CP-DSS application regenerates an updated loadable plan based on the changes in the stowage quantities."," ","The user must enter the reason for updating the current loadable plan in the ","COMMENTS ","field once the Loadicator validates the updated plan. "]],"id":"82"})