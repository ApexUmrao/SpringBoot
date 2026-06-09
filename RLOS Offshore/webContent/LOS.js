var LOS_onLoad = document.createElement('script');
	LOS_onLoad.src = '/LOS_EG/LOS_EG/CustomJS/LOS_onLoad.js';
	document.head.appendChild(LOS_onLoad);
	
var LOS_mandatory = document.createElement('script');
	LOS_mandatory.src = '/LOS_EG/LOS_EG/CustomJS/LOS_MandatoryFieldValidations.js';
	document.head.appendChild(LOS_mandatory);
	
var LOS_Common = document.createElement('script');
	LOS_Common.src = '/LOS_EG/LOS_EG/CustomJS/LOS_Common.js';
	document.head.appendChild(LOS_Common);

var LOS_EventHandler = document.createElement('script');
	LOS_EventHandler.src = '/LOS_EG/LOS_EG/CustomJS/LOS_EventHandler.js';
	document.head.appendChild(LOS_EventHandler);
	
var LOS_onSaveDone = document.createElement('script');
	LOS_onSaveDone.src = '/LOS_EG/LOS_EG/CustomJS/LOS_onSaveDone.js';
	document.head.appendChild(LOS_onSaveDone);
	
var LOS_Egypt_Template_Generation = document.createElement('script');
	LOS_Egypt_Template_Generation.src = '/LOS_EG/LOS_EG/CustomJS/LOS_Egypt_Template_Generation.js';
	document.head.appendChild(LOS_Egypt_Template_Generation);	

	
function setCommonVariables()
{
	Processname = getWorkItemData("ProcessName");
	ActivityName =getWorkItemData("ActivityName");
	WorkitemNo =getWorkItemData("processinstanceid");
	cabName =getWorkItemData("cabinetname");
	user= getWorkItemData("username");	
	viewMode=window.parent.wiViewMode;
	
}

function onFormLoad()
{
	setCommonVariables();
	afterformload(ActivityName);
	onInitiationFormLoad();
}

function refreshDecisionSection()
{
	setControlValue("Decision","");
	setControlValue("REMARKS","");
	
	
}

function CustomLoad(ControlObject)
{
	//refreshDecisionSection();
}



  function customValidationsBeforeSaveDone(op)
{
	
	switch (op) {
        case 'S':
			return true;
            break;
        case 'I':
		if(validationOnIntroduce())
		{
			//saveWorkItem(); //Added to Save
		var confirmDoneResponse = confirm("You are about to submit the workitem. Do you wish to continue?");
		if(confirmDoneResponse ==  true)
				
		{
			deleteUnReservedCollateralRows();
			
			executeServerEvent("Historyset","onDone",'',true);
			//addDecisionToGrid();
			saveWorkItem(); //Added to Save
			return true;
					
		}
				
		else
				
		{
					
			return false;
				
		}
		}
		  break;
        case 'D':
		if(validationOnIntroduce())
		{
			//saveWorkItem(); //Added to Save
		var confirmDoneResponse = confirm("You are about to submit the workitem. Do you wish to continue?");
		if(confirmDoneResponse ==  true)
				
		{
					
			executeServerEvent("Historyset","onDone",'',true);
			saveWorkItem(); //Added to Save
			//addDecisionToGrid();
			return true;
					
		}
				
		else
				
		{
					
			return false;
				
		}
		}
			break;
        default:
            break;
    }
	
}
 

function CustomEventDispatch(ControlObject, eventType) {
	
	switch (eventType.type) {
	case 'click':
		click_LOS(ControlObject);
		break;

	case 'change':
	change_LOS(ControlObject);
		break;

	case 'keydown':
		keydown_LOS(ControlObject);
		break;

	case 'blur':
		blur_LOS(ControlObject);
		break;

	case 'focus':
		//GotFocus_UBA(ControlObject);// got focus
		break;
		
	case 'load':
		CustomLoad(ControlObject);
		break;
		
	case 'tabclick': 
		onClickTabLOS(ControlObject);//got focus
	
		break;
	case 'onLoadSection':
		onLoadSectionLOS(frameId);
		break;
	}
}


function deleteUnReservedCollateralRows()
{
	var count=getGridRowCount("table129");
	var tempCount=0;
	var rowIndexToDelete =[];
	for (var i=0;i<count;i++)
	{
		if(!(getValueFromTableCell("table129", i, 0)=='true'))
		{
			rowIndexToDelete[tempCount]=i;
			tempCount++;
		}
	}
	if(tempCount>0)
	{
		deleteRowsFromGrid("table129",rowIndexToDelete);
	}
}