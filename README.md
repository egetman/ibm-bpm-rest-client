# IBM BPM Rest Client
---

* Note: **If u want to use rest client on websphere as, u should create a shared library with
  apache httpcore and httpclient, and set "Use an isolated class loader for this shared library" to true.**

## API Usage:


Assume u have this info:

    final String user = "deadmin";
    final String password = "deadmin";
    final String serverUrl = "http://127.0.0.1:9080";
    final String serverUrlSecured = "https://127.0.0.1:9443";

Creating of bpm client as simple as:

    try (BpmClient bpmClient = BpmClientFactory.getFactory().createClient(URI.create(serverUrl), user, password)) {
        
    }

Or if u want to use SSL:
    
    try (BpmClient bpmClient = BpmClientFactory.getFactory().createClient(URI.create(serverUrlSecured), user, password, true)) {
                
    }
        
For now just a few endpoints are implemented.
### Exposed api:

To obtaint exposed items u can use following methods:
    
    ExposedItems exposedItems = bpmClient.getExposedClient().listItems(ItemType.PROCESS); //Or Just .listItems();
    logger.info(exposedItems.describe());

Output will be something like:

    12:53:21.574 [main] INFO  ru.Test - 
                    ExposedItems [
                    	status = 200
                    	payload = [
                    		exposedItemsList = [
                    			    id = 2015.35
                    			    itemType = PROCESS
                    			    sybType = null
                    			    runUrl = null
                    			    itemId = 25.12df730b-3c1f-4658-98c9-cb99eefbc9ad
                    			    itemReference = /25.12df730b-3c1f-4658-98c9-cb99eefbc9ad
                    			    processAppId = 2066.7b7f619b-1f3c-434f-a4be-63dd5508f388
                    			    processAppName = Hiring Sample Advanced
                    			    processAppAcronym = HSAV1
                    			    snapshotId = 2064.17b5290a-04fa-4a65-a352-226adaec20be
                    			    snapshotName = null
                    			    snapshotCreatedOn = Thu Jun 18 09:16:35 MSK 2015
                    			    name = Advanced HR Open New Position
                    			    branchId = 2063.a93a5b31-e2f6-4d4a-8299-9d8e8c7e476f
                    			    branchName = Main
                    			    startUrl = /rest/bpm/wle/v1/process?action=start&bpdId=25.12df730b-3c1f-4658-98c9-cb99eefbc9ad&processAppId=2066.7b7f619b-1f3c-434f-a4be-63dd5508f388
                    			    isDefault = false
                    			    tip = true
                    		][
                            	id = 2015.17251
                             	itemType = SERVICE
                            	sybType = STARTABLE_SERVICE
                            	runUrl = https://127.0.0.1:9443/teamworks/executeServiceByName?processApp=AppName&serviceName=Error+Elaboration+Service&snapshot=0.2.11
                            	itemId = 1.c588bd09-63c1-487b-8bb4-ceea61f83c08
                                itemReference = /1.c588bd09-63c1-487b-8bb4-ceea61f83c08
                            	processAppId = 2066.d7eba346-d42e-4e11-ad8c-0d2e2e8281e6
                            	processAppName = null
                            	processAppAcronym = null
                            	snapshotId = 2064.038357bf-6a78-4be7-8abf-a6e9f6adecd1
                            	snapshotName = 0.2.11
                            	snapshotCreatedOn = Wed Aug 26 08:29:16 MSK 2015
                            	name = Error Elaboration Service
                            	branchId = 2063.929e6493-4693-49de-ae49-e8af99ac7d2b
                            	branchName = Main
                            	startUrl = null
                            	isDefault = false
                            	tip = false
                            ]
                        ]
                        exception = null
                    ]
                    
U can check if the api call was successfull or not:
    
    exposedItems.isExceptional();

And do a lot of other stuff:

    List<Item> items = exposedItems.getPayload().getExposedItemsList(); //getPayload() will throw an Exception, if the api call was unsuccessfull.
    for (Item item : items) {
    	if (item.getName().equals("MySuperProcess")) {
    		//Get the process App ID
    		String processAppID = item.getProcessAppId();
    		String processAppName = item.getProcessAppName();
    	}
    }
    
    Item itemByTypeAndName = bpmClient.getExposedClient().getItemByName(ItemType.SERVICE, "MyService");
    
    Item itemByName = bpmClient.getExposedClient().getItemByName("MyUnknownTypeItem");
                    
### Process api:
                                    
    Map<String, Object> inputParams = Maps.newHashMap();
    inputParams.put("Loan", new MySuperPuperComplexObject()); // All will be serialized as json
    
    //U can put null instead of input, if ur process doesn't need input.
    ProcessDetails processDetails = bpmClient.getProcessClient().startProcess(item.getItemId(), item.getProcessAppId(), null, null, inputParams);
    logger.info(processDetails.describe());                                   
                    
There will be some complex output like that:
    
    13:31:45.100 [main] INFO  ru.Test - 
    ProcessDetails [
    	status = 200
    	payload = [
    		actionDetails = null
    		businessData = [{name=requisition.department, type=String, alias=Department, label=Department, value=Software Engineering}, {name=requisition.status, type=String, alias=EmploymentStatus, label=Employment Status}, {name=requisition.requestor, type=String, alias=HiringManager, label=Hiring Manager, value=Roland Peisl}, {name=requisition.location, type=String, alias=Location, label=Location}, {name=requisition.reqNum, type=String, alias=RequistionNumber, label=Requistion Number, value=1140}]
    		creationTime = Tue Mar 15 10:33:42 MSK 2016
    		comments = []
    		data = "{\"currentPosition\":{\"positionType\":\"\",\"jobTitle\":\"Management\",\"iId\":\"\",\"replacement\":{\"lastName\":\"\",\"firstName\":\"\",\"supervisor\":\"\",\"payLevel\":\"\",\"payType\":\"\",\"notes\":\"\",\"@metadata\":{\"dirty\":false,\"shared\":false,\"rootVersionContextID\":\"2064.17b5290a-04fa-4a65-a352-226adaec20beT\",\"className\":\"PersonData\"}},\"@metadata\":{\"dirty\":false,\"shared\":false,\"rootVersionContextID\":\"2064.17b5290a-04fa-4a65-a352-226adaec20beT\",\"className\":\"Position\"}},\"ListOfCandidates\":{\"NrOfCandidatesFound\":0,\"Candidate\":{ \"selected\": [], \"items\": [{\"lastName\":\"\",\"firstName\":\"\",\"supervisor\":\"\",\"payLevel\":\"\",\"payType\":\"\",\"notes\":\"\",\"@metadata\":{\"dirty\":false,\"shared\":false,\"rootVersionContextID\":\"2064.17b5290a-04fa-4a65-a352-226adaec20beT\",\"className\":\"PersonData\"}}], \"@metadata\":{\"dirty\":false,\"shared\":false}},\"@metadata\":{\"dirty\":false,\"shared\":false,\"rootVersionContextID\":\"2064.17b5290a-04fa-4a65-a352-226adaec20beT\",\"className\":\"Candidates\"}},\"person\":{\"lastName\":\"\",\"firstName\":\"\",\"supervisor\":\"\",\"payLevel\":\"\",\"payType\":\"\",\"notes\":\"\",\"@metadata\":{\"dirty\":false,\"shared\":false,\"rootVersionContextID\":\"2064.17b5290a-04fa-4a65-a352-226adaec20beT\",\"className\":\"PersonData\"}},\"requisition\":{\"reqNum\":\"1140\",\"requestor\":\"Roland Peisl\",\"status\":\"\",\"approvalNeeded\":false,\"date\":\"2016-03-15T10:33:42Z\",\"department\":\"Software Engineering\",\"location\":\"\",\"empNum\":1,\"gmApproval\":\"\",\"gmComments\":\"\",\"instanceId\":\"\",\"hrcandidateAvailable\":false,\"@metadata\":{\"dirty\":false,\"shared\":false,\"rootVersionContextID\":\"2064.17b5290a-04fa-4a65-a352-226adaec20beT\",\"className\":\"Requisition\"}},\"candidatesRequest\":{\"status\":\"XXX\",\"positionType\":\"New\",\"location\":\"Boston\",\"payType\":\"Excempt\",\"payLevel\":\"4\",\"@metadata\":{\"dirty\":false,\"shared\":false,\"rootVersionContextID\":\"2064.17b5290a-04fa-4a65-a352-226adaec20beT\",\"className\":\"RequestForCandidates\"}}}"
    		description = null
    		diagram = [
    			processAppId = 2066.7b7f619b-1f3c-434f-a4be-63dd5508f388
    			milestone = null
    			steps = [
    				name = Start
    				type = EVENT
    				activityType = null
    				externalId = null
    				lane = Hiring Manager
    				x = 24
    				y = 56
    				color = DEFAULT
    				attachedTimers = null
    				attachedEventHandlers = null
    				lines = [
    					to = bpdid:2b2df4d4837e7578:-3584d239:139f9038c49:-7f80
    					points = 
    					tokenId = null
    				]
    				id = bpdid:2b2df4d4837e7578:-3584d239:139f9038c49:-7ff6
    			][
    				name = Need GM approval?
    				type = GATEWAY
    				activityType = null
    				externalId = null
    				lane = Hiring Manager
    				x = 342
    				y = 51
    				color = DEFAULT
    				attachedTimers = null
    				attachedEventHandlers = null
    				lines = [
    					to = bpdid:2b2df4d4837e7578:-3584d239:139f9038c49:-7f16
    					points = 
    					tokenId = null
    				][
    					to = bpdid:2b2df4d4837e7578:-3584d239:139f9038c49:-7ff1
    					points = 
    					tokenId = null
    				]
    				id = bpdid:2b2df4d4837e7578:-3584d239:139f9038c49:-7f1d
    			][
    				name = Submit job requisition
    				type = ACTIVITY
    				activityType = task
    				externalId = 1.e9dcdbce-873e-41b6-9350-a4a5de140cad
    				lane = Hiring Manager
    				x = 167
    				y = 36
    				color = BLUE
    				attachedTimers = null
    				attachedEventHandlers = null
    				lines = [
    					to = bpdid:2b2df4d4837e7578:-3584d239:139f9038c49:-7f1d
    					points = 
    					tokenId = null
    				]
    				id = bpdid:2b2df4d4837e7578:-3584d239:139f9038c49:-7f80
    			][
    				name = Select candidate for interview
    				type = ACTIVITY
    				activityType = task
    				externalId = 1.c7a3bd61-4b31-4a57-b777-9c651dbfa022
    				lane = Hiring Manager
    				x = 801
    				y = 44
    				color = DEFAULT
    				attachedTimers = null
    				attachedEventHandlers = null
    				lines = [
    					to = bpdid:2b2df4d4837e7578:-3584d239:139f9038c49:-7ef4
    					points = 
    					tokenId = null
    				]
    				id = bpdid:2b2df4d4837e7578:-3584d239:139f9038c49:-7fee
    			][
    				name = Requesting new list?
    				type = GATEWAY
    				activityType = null
    				externalId = null
    				lane = Hiring Manager
    				x = 962
    				y = 58
    				color = DEFAULT
    				attachedTimers = null
    				attachedEventHandlers = null
    				lines = [
    					to = bpdid:2b2df4d4837e7578:-3584d239:139f9038c49:-7ff5
    					points = 
    					tokenId = null
    				][
    					to = bpdid:2b2df4d4837e7578:-3584d239:139f9038c49:-7ff1
    					points = 
    					tokenId = null
    				]
    				id = bpdid:2b2df4d4837e7578:-3584d239:139f9038c49:-7ef4
    			][
    				name = Approve / reject requisition
    				type = ACTIVITY
    				activityType = task
    				externalId = 1.262847c1-2a6c-4267-876f-5a998358023b
    				lane = General Manager
    				x = 334
    				y = 41
    				color = RED
    				attachedTimers = null
    				attachedEventHandlers = null
    				lines = [
    					to = bpdid:2b2df4d4837e7578:-3584d239:139f9038c49:-7f0f
    					points = 
    					tokenId = null
    				]
    				id = bpdid:2b2df4d4837e7578:-3584d239:139f9038c49:-7f16
    			][
    				name = GM approved?
    				type = GATEWAY
    				activityType = null
    				externalId = null
    				lane = General Manager
    				x = 470
    				y = 56
    				color = DEFAULT
    				attachedTimers = null
    				attachedEventHandlers = null
    				lines = [
    					to = bpdid:2b2df4d4837e7578:-3584d239:139f9038c49:-7f06
    					points = 
    					tokenId = null
    				][
    					to = bpdid:2b2df4d4837e7578:-3584d239:139f9038c49:-7ff1
    					points = 
    					tokenId = null
    				]
    				id = bpdid:2b2df4d4837e7578:-3584d239:139f9038c49:-7f0f
    			][
    				name = End
    				type = EVENT
    				activityType = null
    				externalId = null
    				lane = General Manager
    				x = 1169
    				y = 79
    				color = DEFAULT
    				attachedTimers = null
    				attachedEventHandlers = null
    				lines = null
    				id = bpdid:2b2df4d4837e7578:-3584d239:139f9038c49:-7ff5
    			][
    				name = FindCandidatesList
    				type = ACTIVITY
    				activityType = task
    				externalId = 1.a132efb9-1a3b-4317-9e05-25f23ac626bd
    				lane = Automatic
    				x = 591
    				y = 28
    				color = DEFAULT
    				attachedTimers = null
    				attachedEventHandlers = null
    				lines = [
    					to = bpdid:2b2df4d4837e7578:-3584d239:139f9038c49:-7eff
    					points = 
    					tokenId = null
    				]
    				id = bpdid:2b2df4d4837e7578:-3584d239:139f9038c49:-7ff1
    			][
    				name = Candidate found?
    				type = GATEWAY
    				activityType = null
    				externalId = null
    				lane = Automatic
    				x = 812
    				y = 44
    				color = DEFAULT
    				attachedTimers = null
    				attachedEventHandlers = null
    				lines = [
    					to = bpdid:2b2df4d4837e7578:-3584d239:139f9038c49:-7fee
    					points = 
    					tokenId = null
    				][
    					to = bpdid:2b2df4d4837e7578:-3584d239:139f9038c49:-7f06
    					points = 
    					tokenId = null
    				]
    				id = bpdid:2b2df4d4837e7578:-3584d239:139f9038c49:-7eff
    			][
    				name = Notify hiring manager
    				type = ACTIVITY
    				activityType = null
    				externalId = null
    				lane = System
    				x = 803
    				y = 17
    				color = ORANGE
    				attachedTimers = null
    				attachedEventHandlers = null
    				lines = [
    					to = bpdid:2b2df4d4837e7578:-3584d239:139f9038c49:-7ff5
    					points = 
    					tokenId = null
    				]
    				id = bpdid:2b2df4d4837e7578:-3584d239:139f9038c49:-7f06
    			]
    			lanes = [
    				name = Hiring Manager
    				height = 189
    				isSystemLane = false
    			][
    				name = General Manager
    				height = 140
    				isSystemLane = false
    			][
    				name = Automatic
    				height = 140
    				isSystemLane = false
    			][
    				name = System
    				height = 150
    				isSystemLane = true
    			]
    		]
    		documents = []
    		executionState = ACTIVE
    		executionTree = {executionStatus=1, root={name=Advanced HR Open New Position, nodeId=1, children=[{name=Submit job requisition, nodeId=3, children=null, flowObjectId=bpdid:2b2df4d4837e7578:-3584d239:139f9038c49:-7f80, tokenId=2, createdTaskIDs=[52129]}], createdTaskIDs=null}}
    		instanceError = null
    		lastModificationTime = Tue Mar 15 10:33:43 MSK 2016
    		name = Advanced Employee Requisition NG (List) for Roland Peisl (47275)
    		piid = 47275
    		processTemplateID = 25.12df730b-3c1f-4658-98c9-cb99eefbc9ad
    		processTemplateName = Advanced HR Open New Position
    		processAppName = Hiring Sample Advanced
    		processAppAcronym = HSAV1
    		snapshotName = null
    		snapshotId = 2064.17b5290a-04fa-4a65-a352-226adaec20be
    		dueDate = Wed Mar 30 01:33:42 MSK 2016
    		predictedDueDate = null
    		tasks = [
    			activationTime = Tue Mar 15 10:33:43 MSK 2016
    			assignedTo = egetman
    			assignedToType = USER
    			clientTypes = [IBM_WLE_Coach]
    			completionTime = null
    			containmentContextId = 47275
    			data = {variables={currentPosition={positionType=, jobTitle=Management, iId=, replacement={lastName=, firstName=, supervisor=, payLevel=, payType=, notes=, @metadata={dirty=false, shared=false, rootVersionContextID=2064.17b5290a-04fa-4a65-a352-226adaec20beT, className=PersonData}}, @metadata={dirty=false, shared=false, rootVersionContextID=2064.17b5290a-04fa-4a65-a352-226adaec20beT, className=Position}}, ListOfCandidates={NrOfCandidatesFound=0.0, Candidate={selected=[], items=[{lastName=, firstName=, supervisor=, payLevel=, payType=, notes=, @metadata={dirty=false, shared=false, rootVersionContextID=2064.17b5290a-04fa-4a65-a352-226adaec20beT, className=PersonData}}], @metadata={dirty=false, shared=false}}, @metadata={dirty=false, shared=false, rootVersionContextID=2064.17b5290a-04fa-4a65-a352-226adaec20beT, className=Candidates}}, person={lastName=, firstName=, supervisor=, payLevel=, payType=, notes=, @metadata={dirty=false, shared=false, rootVersionContextID=2064.17b5290a-04fa-4a65-a352-226adaec20beT, className=PersonData}}, requisition={reqNum=1140, requestor=Roland Peisl, status=, approvalNeeded=false, date=2016-03-15T10:33:42Z, department=Software Engineering, location=, empNum=1.0, gmApproval=, gmComments=, instanceId=, hrcandidateAvailable=false, @metadata={dirty=false, shared=false, rootVersionContextID=2064.17b5290a-04fa-4a65-a352-226adaec20beT, className=Requisition}}, candidatesRequest={status=XXX, positionType=New, location=Boston, payType=Excempt, payLevel=4, @metadata={dirty=false, shared=false, rootVersionContextID=2064.17b5290a-04fa-4a65-a352-226adaec20beT, className=RequestForCandidates}}}}
    			description = 
    			displayName = Step: Submit job requisition
    			dueTime = Tue Mar 15 11:33:43 MSK 2016
    			externalActivityId = null
    			kind = KIND_PARTICIPATING
    			lastModificationTime = Tue Mar 15 10:33:43 MSK 2016
    			milestone = null
    			name = Submit job requisition
    			namespace = null
    			originator = tw_admin
    			owner = egetman
    			priority = 30
    			priorityName = NORMAL
    			processData = null
    			runUrl = null
    			serviceId = 1.e9dcdbce-873e-41b6-9350-a4a5de140cad
    			startTime = Tue Mar 15 10:33:43 MSK 2016
    			state = STATE_CLAIMED
    			status = Received
    			tktid = null
    			tkiid = 52129
    		]
    		variables = {currentPosition={positionType=, jobTitle=Management, iId=, replacement={lastName=, firstName=, supervisor=, payLevel=, payType=, notes=, @metadata={dirty=false, shared=false, rootVersionContextID=2064.17b5290a-04fa-4a65-a352-226adaec20beT, className=PersonData}}, @metadata={dirty=false, shared=false, rootVersionContextID=2064.17b5290a-04fa-4a65-a352-226adaec20beT, className=Position}}, ListOfCandidates={NrOfCandidatesFound=0.0, Candidate={selected=[], items=[{lastName=, firstName=, supervisor=, payLevel=, payType=, notes=, @metadata={dirty=false, shared=false, rootVersionContextID=2064.17b5290a-04fa-4a65-a352-226adaec20beT, className=PersonData}}], @metadata={dirty=false, shared=false}}, @metadata={dirty=false, shared=false, rootVersionContextID=2064.17b5290a-04fa-4a65-a352-226adaec20beT, className=Candidates}}, person={lastName=, firstName=, supervisor=, payLevel=, payType=, notes=, @metadata={dirty=false, shared=false, rootVersionContextID=2064.17b5290a-04fa-4a65-a352-226adaec20beT, className=PersonData}}, requisition={reqNum=1140, requestor=Roland Peisl, status=, approvalNeeded=false, date=2016-03-15T10:33:42Z, department=Software Engineering, location=, empNum=1.0, gmApproval=, gmComments=, instanceId=, hrcandidateAvailable=false, @metadata={dirty=false, shared=false, rootVersionContextID=2064.17b5290a-04fa-4a65-a352-226adaec20beT, className=Requisition}}, candidatesRequest={status=XXX, positionType=New, location=Boston, payType=Excempt, payLevel=4, @metadata={dirty=false, shared=false, rootVersionContextID=2064.17b5290a-04fa-4a65-a352-226adaec20beT, className=RequestForCandidates}}}
    		state = STATE_RUNNING
    	]
    	exception = null
    ]
                   
U can refresh process state by api call:
   
    processDetails = bpmClient.getProcessClient().currentState(processDetails.getPayload().getPiid()); //Refreshing by process instance ID
   
Or u can do some more things, like:

    bpmClient.getProcessClient().resumeProcess("piid");
    bpmClient.getProcessClient().suspendProcess("piid");
    bpmClient.getProcessClient().terminateProcess("piid");

All the calls return ProcessDetails type. It can be usefull for different checks if u r using junit for process tests.

### Task api:
TODO

### ProcessApps api:
TODO

### Query api (Task, TaskTemplate, Process):
TODO
