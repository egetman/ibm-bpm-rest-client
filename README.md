# IBM BPM Rest Client
---

* Note: ***If you want to use rest client on Websphere, you should follow the recommendations from [developerworks forum](https://www.ibm.com/developerworks/community/forums/html/topic?id=77777777-0000-0000-0000-000014853459)***

## API Usage Examples:


Assume you have this info:

    final String user = "user";
    final String password = "password";
    final String serverUrl = "http://127.0.0.1:9080";

Creating of bpm client as simple as:

    try (BpmClient bpmClient = BpmClientFactory.createClient(serverUrl, user, password)) {
        
    }

For now just a few endpoints are implemented.
### Exposed api:

To obtain exposed items you can use following methods:
    
    ExposedItems exposedItems = bpmClient.getExposedClient().listItems(ItemType.PROCESS); //Or Just .listItems();

    Item itemByTypeAndName = bpmClient.getExposedClient().getItemByName(ItemType.SERVICE, "MyService");
    Item itemByName = bpmClient.getExposedClient().getItemByName("MyUnknownTypeItem");
*Exposed api calls, that return single exposed item can throw a RestException, if the api call was unsuccessful.*

    logger.info(exposedItems.describe());

Output will be something like:
                    

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
                ]
        exception = null
    ]
You can check if the api call was successful or not:
    
    exposedItems.isExceptional();

And do a lot of other stuff:
(For example you can obtain process id's by process name and run unit tests for this process)

    List<Item> items = exposedItems.getPayload().getExposedItemsList(); //getPayload() will throw a RestException, if the api call was unsuccessful.
    for (Item item : items) {
    	if (item.getProcessAppName().equals("Advanced HR Open New Position")) {
    		//The id of the Business Process Definition to be used.
    		String itemID = item.getItemId();
    		//The id of the process application containing the Business Process Definition.
    		//If this parameter is specified, then the tip snapshot of the default branch within the specified process application will be used.
    		String processAppID = item.getProcessAppId();

    		String name = item.getName();
    	}
    }

### Process api:

If you need input parameters for your process, you can put them in map (parameter name (String) - parameter value (Object)).

    Map<String, Object> inputParams = Maps.newHashMap();
    inputParams.put("Loan", new MySuperPuperComplexObject()); // All will be serialized as json
    
    //You can put null instead of input, if your process doesn't need input.
    ProcessDetails processDetails = bpmClient.getProcessClient().startProcess(item.getItemId(), item.getProcessAppId(), null, null, inputParams);
    logger.info(processDetails.describe());                                   
                    
There will be some complex output like that:
    
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
    			]
    			...
    			lanes = [
    				name = Hiring Manager
    				height = 189
    				isSystemLane = false
    			][
    				name = General Manager
    				height = 140
    				isSystemLane = false
    			]
    			...
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
                   
You can refresh process state (and receive it's new state) by api call:
   
    processDetails = bpmClient.getProcessClient().currentState(processDetails.getPayload().getPiid()); //Refreshing by process instance ID
   
Or you can do some more things, like 'resume', 'suspend' or 'terminate':

	//Get process instance ID
	String piid = processDetails.getPayload().getPiid();

    bpmClient.getProcessClient().resumeProcess(piid);
    bpmClient.getProcessClient().suspendProcess(piid);
    bpmClient.getProcessClient().terminateProcess(piid);

All the calls return ProcessDetails type. It can be useful for different checks if you are using junit for process tests.

### Task api:

You can obtain task instance id from ProcessDetails type, after you start process.
After you have it, you can obtain detailed information about started tasks:

	TaskDetails taskDetails = bpmClient.getTaskClient().getTask(tkiid);
	logger.info(taskDetails.describe());

After that you will see similar output:

	TaskDetails [
    	status = 200
    	payload = [
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
    		processData = {businessData=[{name=requisition.department, type=String, alias=Department, label=Department, value=Software Engineering}, {name=requisition.status, type=String, alias=EmploymentStatus, label=Employment Status}, {name=requisition.requestor, type=String, alias=HiringManager, label=Hiring Manager, value=Roland Peisl}, {name=requisition.location, type=String, alias=Location, label=Location}, {name=requisition.reqNum, type=String, alias=RequistionNumber, label=Requistion Number, value=1140}]}
    		runUrl = null
    		serviceId = 1.e9dcdbce-873e-41b6-9350-a4a5de140cad
    		startTime = Tue Mar 15 10:33:43 MSK 2016
    		state = STATE_CLAIMED
    		status = Received
    		tktid = null
    		tkiid = 52129
    	]
    	exception = null
    ]

You can use following api possibilities:

	//Start task
	TaskStartData startData = bpmClient.getTaskClient().startTask(tkiid);

	//Assign task to somebody
	TaskDetails taskDetails = bpmClient.getTaskClient().assignTaskToMe(tkiid);
	TaskDetails taskDetails = bpmClient.getTaskClient().assignTaskBack(tkiid);
	TaskDetails taskDetails = bpmClient.getTaskClient().assignTaskToUser(tkiid, userName);
	TaskDetails taskDetails = bpmClient.getTaskClient().assignTaskToGroup(tkiid, groupName);

	//Complete task
	TaskDetails taskDetails = bpmClient.getTaskClient().completeTask(tkiid, input); // Where input is @Nullable Map<String, Object>

	//Or retrieve data from specified task
	TaskData taskData = bpmClient.getTaskClient().getData(tkiid, fields); // Where fields is comma-separated list of fields.

	//Update task metadata
	TaskDetails taskDetails = bpmClient.getTaskClient().updateTaskDueTime(tkiid, new Date());
	TaskDetails taskDetails = bpmClient.getTaskClient().updateTaskPriority(tkiid, TaskPriority.HIGH);

### ProcessApps api:

With process apps client you can obtain information about all process applications installed on process server and it's snapshots.

    ProcessApps processApps  = bpmClient.getProcessAppsClient().listProcessApps();
    logger.info(processApps.describe());
    
After that you can obtain needed id's. 
    
    ProcessApps [
    	status = 200
    	payload = [
    		processAppsList = [
    		    id = 2066.bbdd633b-9c86-4534-a5c2-0e75deac46ea
    		    shortName = TEST
    		    name = Test
    		    description = null
    		    richDescription = null
    		    lastModifiedBy = Administrator
    		    defaultVersion = Main
    		    installedSnapshots = [
                    name = 0.0.2
                	acronym = 0.0.2
                	isActive = true
                	createdOn = Thu Aug 13 12:30:25 MSK 2015
                	isTip = false
                	activeSince = Fri Aug 14 09:15:00 MSK 2015
                	branchId = 2063.f6dd26b0-67a7-4762-9c06-12d2819b3192
                	branchName = Main
                	id = 2064.bf2e87f0-6383-4b7f-af1e-67af2a1259b7
                	][
                	name = 0.0.1
                	acronym = 0.0.1
                	isActive = false
                	createdOn = Thu Aug 13 12:30:17 MSK 2015
                	isTip = false
                	activeSince = null
                	branchId = 2063.f6dd26b0-67a7-4762-9c06-12d2819b3192
                	branchName = Main
                	id = 2064.be5c907a-c165-4ff3-8a1e-baeab478b1aa
                	][
                	name = Version1
                	acronym = V1
                	isActive = false
                	createdOn = Fri May 29 13:57:59 MSK 2015
                	isTip = false
                	activeSince = null
                	branchId = 2063.f6dd26b0-67a7-4762-9c06-12d2819b3192
                	branchName = Main
                	id = 2064.3d880bdf-9980-4ce9-b2fe-38e0a07973d0
                	]
                lastModified = Mon May 25 09:35:33 MSK 2015
                ]
            ]
        exception = null
    ]

### Query api (Task, TaskTemplate, Process):
TODO (see javadoc)
