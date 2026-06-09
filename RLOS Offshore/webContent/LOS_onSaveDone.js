
var LOS_onLoad = document.createElement('script');
	LOS_onLoad.src = '/LOS_EG/LOS_EG/CustomJS/LOS_onLoad.js';
	document.head.appendChild(LOS_onLoad);

	
function addDecisionToGrid()
{
	var decision = getValue('Decision');
	var decisionRemarks = getValue('Remarks');
	
	var stringData =  userName +"#"+ activityName +"#"+ decision +"#"+ decisionRemarks;
	var res = executeServerEvent("AddToDecisionHistoryGrid","AddToDecisionHistoryGrid",stringData,true);
	if(res.split('~')[0]=="SUCCESS")
		return true;
	else 
		return false;
}

function DecisionTOGrid()
{
	var confirmDoneResponse = confirm("You are about to submit the workitem. Do you wish to continue?");
		if(confirmDoneResponse ==  true)
				
		{
					
			executeServerEvent("Historyset","onDone",'',true);
			//addDecisionToGrid();
			return true;
					
		}
				
		else
				
		{
					
			return false;
				
		}
}

function validationOnIntroduce()
{
	
	if(compareStringsIgnoreCase(getValue('Decision'),"Discard"))
	{
		return true;
	}
	if(!CheckForExpDateAndDept())
	{
		return false;
	}
	if(!CheckForDocs())
	{
		return false;
	}
	if(activityName=='Introduction')
	{
		
		try{
			setControlValue('MOBILE_NO',getValueFromTableCell("table107", i, 8));
		}
		catch(e)
		{
			console.log(e);
		}
		if(compareStringsIgnoreCase(getValue('Acquisition_Channel'),""))
		{
			showMessage("Acquisition_Channel","Please Select Acquisition Channel","error");
			return false;
		}
		if(compareStringsIgnoreCase(getValue('Decision'),""))
		{
			showMessage("Decision","Please Select Decision","error");
			return false;
		}
		if(compareStringsIgnoreCase(getValue('IS_DUPLICATE'),""))
		{
			showMessage("Dedupe_Check","Please Click Dedupe Check before Proceeding","error");
			return false;
		}
		if(getGridRowCount('table133')==0 && getValue('Product_Type')=='CC' && (compareStringsIgnoreCase(getValue('Decision'),"Submit") ||compareStringsIgnoreCase(getValue('Decision'),"Approve")))
		{
			showMessage("table133","Please Enter Details in Payment Method CC","error");
			return false;
		}		
		if((compareStringsIgnoreCase(getValue('Sub_Product_Type'),"Secured")) && (compareStringsIgnoreCase(getValue('CollResNo'),"")))
		{
			showMessage("button192","Please Reserver Collateral","error");
			return false;
		}
	}
	if(activityName=='RCCU_RiskControl_Maker')
	{
		//doc Manadate
		var Decision = getValue('Decision');
		var Product = getValue('Product_Type');
		if(!(CheckDocNameAndWsWise("RCCU_RiskControl_Maker","RC Utilization Permit Doc",Decision,Product)))
		{
			return false;
		}
	}
	if(activityName=='RCR_CreditAnalyst')
	{
		var DBR=getValue('DBR_POLICY');
		if(compareStringsIgnoreCase(DBR,"") && getValue('Sub_Product_Type')!='Secured')
		{
			showMessage("DBR_POLICY","Please Calculate DBR","error");
			return false;
		}
		if(getGridRowCount('table144')==0 && getValue('Sub_Product_Type')!='Secured')
		{
			//showMessage("table144","Please Fetch Maximum Eligibility","error");
				//return false;
		}
		
		if(!compareRequestedAndEligibleAmount())
		{
			return false;
		}
	}
	
	if(activityName=='Introduction' ||  activityName=='Re_Initiator')//re_initiator
	{
		if(getValue('Sub_Product_Type')=='Secured')
		{
			if(getGridRowCount('table129')==0)
			{
				showMessage("table129","Please Enter Collateral Details","error");
				return false;
			}
		}
		
		if(getGridRowCount('table105')==0 && getValue('Sub_Product_Type')!='Secured')
		{
			showMessage("table105","Please Enter Income Details","error");
				return false;
		}
		if(getGridRowCount('table107')==0)
		{
			showMessage("table107","Please Enter Customer Details","error");
				return false;
		}
		// if(compareStringsIgnoreCase(getValueFromColumnName("table107",0,"Reference Name"),""))
		if(compareStringsIgnoreCase(getValueFromTableCell("table107",0,6),""))
		{
			showMessage("table107","Please Enter all Customer Details","error");
				return false;
		}
		
		if(getGridRowCount('table108')==0)
		{
			
			showMessage("table108","Please Enter Address Details","error");
				return false;
		}
		if(getGridRowCount('table137')==0 && getValue('Sub_Product_Type')!='Secured')
		{
			
			showMessage("table137","Please Enter Employment Details","error");
				return false;
		}
		if(getGridRowCount('table117')==0)
		{
			
			showMessage("table117","Please Enter Product Details","error");
				return false;
		}
		
		
		else if(getGridRowCount('table144')==0 && getValue('Sub_Product_Type')!='Secured')
		{
			//showMessage("table144","Please Fetch Maximum Eligibility","error");
				//return false;
		}
		
		if(!compareRequestedAndEligibleAmount())
		{
			return false;
		}
		
		
	}
	if(activityName=='LPU_Maker')
	{
		if(getValue('Product_Type')!='CC' && compareStringsIgnoreCase(getValue('Decision'),"Approve"))
		{
			if(getGridRowCount('table135')==0)
			{
				showMessage("table135","No Data Found in Operations Tab","error");
				return false;
			}
		}
	}
	if(activityName=='LPU_Checker')
	{
		var refCounter=0;
		var rowCount=getGridRowCount('table135');
		for(var i=0;i<rowCount;i++)
		{
			if(getValueFromTableCell("table135", i, 13)!='')
			{
				refCounter++;
			}
		}
		if(getValue('Product_Type')!='CC')
		{
			if(refCounter!=rowCount && compareStringsIgnoreCase(getValue('Decision'),"Approve"))
			{
				showMessage("table135","Please Book Loan For All Payment Type","error");
				return false;
			}
		}
	}
	
	try
	{
		if(is_RCR_WS() && getValue('Product_Type')=='CC')
		//if( getValue('Product_Type')=='CC')
		{
			// if(compareStringsIgnoreCase(getValueFromColumnName("table117",0,"Credit card expiry date"),""))
			if(compareStringsIgnoreCase(getValueFromTableCell("table117",0,38),"")) //Credit card expiry date
			{
				showMessage("table117","Please fill Credit Card Expiry Date","error");
				return false;
			}
		}
	}
	catch(ee)
	{
		showMessage("table117","Please Verify Product Details","error");
		return false;
	}
	
	if((is_RCR_WS() || activityName=='Introduction' ||  activityName=='Re_Initiator') && getValue('Product_Type')!='CC')
	{
		var monthPayment="";
		var quartPayment="";
		var semiAnnPayment="";
		var annualPayment="";
		
		try
		{
			// monthPayment= getValueFromColumnName("table117",0,"Monthly Payment");
			monthPayment= getValueFromTableCell("table117",0,63); //Monthly Payment Checkbox
			if(monthPayment == "")
			{
				monthPayment = "false";
			}
		}
		catch(ee)
		{
			monthPayment = "false";
		}
		
		try
		{
			// quartPayment= getValueFromColumnName("table117",0,"Quarterly Payment");
			quartPayment=  getValueFromTableCell("table117",0,64); //Quarterly Payment Checkbox
			if(quartPayment == "")
			{
				quartPayment = "false";
			}
		}
		catch(ee)
		{
			quartPayment = "false";
		}
		
		try
		{
			// semiAnnPayment= getValueFromColumnName("table117",0,"Semi Annually Payment");
			semiAnnPayment=getValueFromTableCell("table117",0,65); //Semi Annually Payment Checkbox
			if(semiAnnPayment == "")
			{
				semiAnnPayment = "false";
			}
		}
		catch(ee)
		{
			semiAnnPayment = "false";
		}
		
		try
		{
			// annualPayment= getValueFromColumnName("table117",0,"Yearly Payment");
			annualPayment= getValueFromTableCell("table117",0,66); //Yearly Payment Checkbox
			if(annualPayment == "")
			{
				annualPayment = "false";
			}
		}
		catch(ee)
		{
			annualPayment = "false";
		}
		
		if(monthPayment == "false" && quartPayment == "false" && semiAnnPayment == "false" && annualPayment == "false")
		{
			showMessage("table117","Please Select at least one payment type","error");
				return false;
		}
	}
	// if(compareStringsIgnoreCase(getValueFromColumnName("table107",0,"Reference Name"),""))
	if(compareStringsIgnoreCase(getValueFromTableCell("table107",0,6),""))
	{
		showMessage("table107","Please Enter all Customer Details","error");
			return false;
	}
	
	if(activityName=='Introduction' ||  activityName=='Re_Initiator')//re_initiator
	{
		if(compareStringsIgnoreCase(getValue('IS_DUPLICATE'),"Y"))
		{
			var duplicateWi=getValue('DUPLICATE_WI_NAME');
			var confirmDoneResponse = confirm("Duplicate Case Found - "+duplicateWi+" . Do you wish to continue?");
			if(confirmDoneResponse ==  true)
			{
				//return true;
			}
			else 
			{
				return false;
			}
		}
	}
		
	return true;
	
}

function CheckDocNameAndWsWise(Ws,DocName,Decision,Product)
{
	if((Ws=='RCCU_RiskControl_Maker') && ((Decision=='Send to checker') ||  (Decision=='Application in Order')) && ((Product=='PL')))
	{
		if(docMandatory(DocName))
		{
			showMessage("",DocName +" Is Missing , Please Generate/Attached Before Proceeding","error");
			return false;
			
		}
	}
	return true;
}
function CheckForDocs()
{
	var sWorkstepName = getWorkItemData("activityname");
	var NoOfRowsIntable=getGridRowCount('documentVeificationTable');
	
    for(i=0;i<=NoOfRowsIntable;i++)
    {
    	var ActivityNameInGrid=getValueFromTableCell('documentVeificationTable',i,2);
    	if(compareStringsIgnoreCase(sWorkstepName,ActivityNameInGrid))
    	{
    		var DocName=getValueFromTableCell('documentVeificationTable',i,0).trim();
            if(docMandatory(DocName))
            {
            	//showMessage("",DocName +" Is Missing , Please Attached Before Proceeding","error");
				return true;
				
            }
    	}
    }
	return true;

	
}
function docMandatory(docTypeName)
{
var docJsonList=window.parent.getInterfaceData("D");
var returnFlag=true;
for(var i=0;i<docJsonList.length;i++)
{
if(compareStringsIgnoreCase(docJsonList[i].name,docTypeName))
{
returnFlag=false;
}
}
return returnFlag;
}


function CheckForExpDateAndDept()
{
	var sWorkstepName = getWorkItemData("activityname");
	var NoOfRowsIntable=getGridRowCount('documentVeificationTable');
	
	for(i=0;i<=NoOfRowsIntable;i++)
	{
		var ExpDate=getValueFromTableCell('documentVeificationTable',i,1);
		var DocName=getValueFromTableCell('documentVeificationTable',i,0);
		var Dept=getValueFromTableCell('documentVeificationTable',i,2);
		if(compareStringsIgnoreCase(ExpDate,""))
		{
			//showMessage("documentVeificationTable","Expected Date For "+DocName+" Is Mandatory","error");
			return true;
			
		}
		if(compareStringsIgnoreCase(Dept,""))
		{
			//showMessage("documentVeificationTable","Department  For "+DocName+" Is Mandatory","error");
			return true;
			
        }
	}
	return true;
} 

function Duplicate_check()
{
	
}

function OnSaveValidation()
{
	
}


function paperRequested()
{
		
}
function Check_avail_budget()
{
	
}

//Ajay 4Jan
function compareRequestedAndEligibleAmount()
{
	if(getValue('Sub_Product_Type')=='Secured')
	{
		
		var exceptionCount1=getGridRowCount("AutoDev");
		
		if(exceptionCount>0)
		{
			var confirmAmountResponse = confirm("There are Exceptions with this case. Do you wish to continue?");
			if(confirmAmountResponse ==  true)
			{
				return true;
			}
			else 
			{
				return false;
			}
		}
		else
		{
			return true;
		}	
	}
	
	var count=getGridRowCount("table144");
	
	var eligibleAmount = 0;
	if(count == 0)
	{
		//showMessage('button218',"Please Fetch Maximum Eligibility","error");
		//return false;
	}
	else
	{
		eligibleAmount = parseFloat(getValueFromTableCell("table144", 0, 0));
	}
	
	if(isNaN(eligibleAmount))
	{
		eligibleAmount = 0;
	}
	
	var totalAmount=parseFloat(getValue('Credit_Approved_Amount'));
	
	if(totalAmount == "" || totalAmount == "0" || isNaN(totalAmount))
	{
		totalAmount=parseFloat(getValue('Req_Loan_Amt'));
	}
	
	
	var exceptionCount=getGridRowCount("AutoDev");
	
	// if(totalAmount>eligibleAmount ||  exceptionCount>0)
	if(exceptionCount>0)
	{
		var confirmAmountResponse = confirm("There are Exceptions with this case. Do you wish to continue?");
		if(confirmAmountResponse ==  true)
		{
			return true;
		}
		else 
		{
			return false;
		}
	}
	
	return true;
}




function is_RCR_WS()
{
	if(activityName=="RCR_PreScanner" || activityName=="RCR_CreditAnalyst" || activityName=="RCR_Credit_TL" || activityName=="RCRH" || activityName=="Head_Of_Initiation" || activityName=="CRO_CRMH")
	{
		return true;
	}
	else
	{
		return false;
	}
	//buttom217 - Reservation Enquiry
}