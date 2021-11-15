rh._.exports({"0":[[" ","What is CP-DSS"]],"1":[[" ","What is CP-DSS"]],"2":[[" ","Features and Benefits"],[" ","CP-DSS Process Flow"]],"3":[[" ","Cargo Planning Decision Support System (CP-DSS) is a web application developed to simplify and automate the tedious processes involved in the cargo allocation of VLCC (Very Large Crude Carrier) vessels. It reduces the demand for human effort in the complex logical operations associated with generating loading and discharging plans while maintaining maximum safety and security. "," ","With the help of various associated tools and technologies, the CP-DSS application allows the user to perform cargo plan generation and management without any concern of location, equipment, and infrastructure restrictions."],[" ","Optimized generation of cargo plans for loading and discharging.     "," ","Provides essential information regarding the cargo, port, vessel, loading sequence, and discharging sequence to facilitate better planning of cargo operations."," ","Maximizes the loadable cargo quantity while reducing the waiting time at ports and thereby the operational costs. "," ","Improves the existing operation to achieve speedy cargo plan generation without compromising safety and security. "," ","Improved accessibility regardless of location constraints."," ","Enhanced vessel safety ensured through Loadicator validation."," ","Integrated loadable and discharge study patterns that minimize human errors."],[" ","The CP-DSS application is integrated with Loadicator and algorithm. The Loadicator checks the stability parameters of the vessel and the data is passed to the algorithm for validation. The algorithm validates the input data and provides optimal output. "," ","The CP-DSS application workflow consists of four different stages:"," ","Cargo Enquiry"," ","Cargo Planning"," ","Before, during, and after the cargo loading operation"," ","Before and after the cargo discharging operation"],[" ","The Loadable study comprises the cargo enquiry and planning stages. In this stage, the user identifies the maximum amount of cargoes that can be loaded to the vessel based on the input conditions and generates multiple stowage patterns with individual loadable plan output. "],[" ","Before cargo loading, the user generates a loading plan that includes the following details:"," ","The arrival and departure condition of the vessel"," ","Cargoes to be loaded"," ","Loading instructions"," ","Topping off sequence and details"," ","Loading time sequence"," ","De-ballasting time sequence"],[" ","In the cargo loading stage, the user updates the API and temperature values of the cargoes that are loaded onto the vessel and recalculates the vessel's stability based on these values. "],[" ","After cargo loading, the user updates the B/L figure and final API values in the stowage plan generated after the cargo planning operation. The user can enter multiple B/L figures against each cargo that has a different B/L reference number. "],[" ","In the Discharge Study stage, the user generates discharge plans by calculating the estimated time for cargo discharging based on the estimated discharging rate of the vessel. The user must consider the port restrictions and other constraints if any when creating a discharge plan."," ","If the discharge port restriction details are unknown, the algorithm creates a discharge plan with the maximum equipment design capacity. A practical and more relevant discharge plan is generated once the user updates the port restriction values."],[" ","Before cargo discharging, the user generates a discharge plan that includes the following details:"," ","The arrival and departure condition of the vessel"," ","Cargoes to be discharged"," ","Discharging instructions "," ","COW (Crude Oil Wash) instructions"," ","Discharging time sequence"," ","Ballasting time sequence"," ","COW time sequence"],[" ","After the discharging operation, the user updates the ROB (Remains On Board) figures and departure condition of the vessel. The actual values of the vessel and shore parameters are updated in the ","Synoptical Table"," under the corresponding voyage after the discharging operation. For more information on Synoptical Table, see ","Synoptical","."]],"4":[[" ","Loadable Study"],[" ","Before Cargo Loading"],[" ","During Cargo Loading"],[" ","After Cargo Loading"],[" ","Discharge Study"],[" ","Before Cargo Discharging"],[" ","After Cargo Discharging"]],"id":"71"})