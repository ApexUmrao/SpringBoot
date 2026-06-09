
function blur_LOS(ControlObject) {
   
}


function keydown_LOS(ControlObject) {
    var pControlName = ControlObject.id;

}


function click_LOS(ControlObject) {

    var pControlName = ControlObject.id;

    if (pControlName == 'Risk_Calculate') {} 
	else if (pControlName == 'Check_ID') {} 
	else if (pControlName == 'sigcap_veri') {} 
	else if (pControlName == 'relatedPartiesCheckID') {}
	/*else if (pControlName == 'table112_Use_DBR') {
		if(compareStringsIgnoreCase(getValue('table112_Use_DBR'),"true"))
		{
			calculateNewTotalLiabonUseDBRforCBS("true");
		}
		else{
			calculateNewTotalLiabonUseDBRforCBS("false");
		}
	}
	else if (pControlName == 'table120_Use_DBR') {
		if(compareStringsIgnoreCase(getValue('table120_Use_DBR'),"true"))
		{
			calculateNewTotalLiabonUseDBRforOther("true");
		}
		else{
			calculateNewTotalLiabonUseDBRforOther("false");
		}
	}*/
	else if (pControlName == 'Dedupe_Check') {
		
		saveWorkItem();
		if(compareStringsIgnoreCase(getValue("Product_Type"),''))
		{
			showAlertDialog('Product Type is missing');
			return false;
		}
		else if(compareStringsIgnoreCase(getValue("Sub_Product_Type"),''))
		{
			showAlertDialog('Sub Product Type is missing');
			return false;
		}
		else if(compareStringsIgnoreCase(getValue("Applicant_Nationality"),''))
		{
			showAlertDialog('Applicant Nationality is missing');
			return false;
		}
		else if(compareStringsIgnoreCase(getValue("Applicant_Nationality"),'EG') || compareStringsIgnoreCase(getValue("Applicant_Nationality"),'EGYPT'))
		{
			if(compareStringsIgnoreCase(getValue("Applicant_National_ID"),''))
			{
				showAlertDialog('Applicant national ID is missing');
				return false;
			}
		}
		else if(!(compareStringsIgnoreCase(getValue("Applicant_Nationality"),'EG') || compareStringsIgnoreCase(getValue("Applicant_Nationality"),'EGYPT')))
		{
			if(compareStringsIgnoreCase(getValue("Applicant_Passport_Number"),''))
			{
				showAlertDialog('Applicant Passport Number is missing');
				return false;
			}
		}
		
			var dedupe_resp = executeServerEvent("Dedupe_Check", "onClick", "", true);
			
			if(!compareStringsIgnoreCase(dedupe_resp,""))
			{
				//showAlertDialog(""+dedupe_resp);
				showMessage("Dedupe_Check",dedupe_resp,"error");
			}
	}
	else if(pControlName == 'button175')
	{
		if(compareStringsIgnoreCase(getValue('Document_Name'),''))
		{
			showAlertDialog("Kidly Select Document Name");
			return false;			
		}
		else if(compareStringsIgnoreCase(getValue('Document_Generated_For'),''))
		{
			showAlertDialog("Kidly Select Document Generated For");
			return false;
		}
		else
		{
			Generate_Template_Calling_Function();
		}
	}
	
    /*************************************************Integrations starts*************************************************************/
    else if (pControlName == 'button154') {
		if(getValue('Applicant_Nationality')=='')
		{
			showAlertDialog("Kidly Select Applicant Nationality");
			return false;
		}
		else if(getValue('Customer_Type')=='')
		{
			showAlertDialog("Kidly Select Customer Type");
			return false;
		}
        else if (getValue('Applicant_Nationality')=='EG' && getValue("Applicant_National_ID").length!= 14) 
		{
            showAlertDialog("National Id Must be 14 Digit");
            return false;
        }
		 else if (getValue('Applicant_Nationality')!='EG' && getValue("Applicant_National_ID").length != 6) 
		{
            showAlertDialog("CIF Id Must be 6 Digit");
            return false;
        }
		else if(getValue('Customer_Type')=="New To Bank")
		{
			showAlertDialog("Customer Type is New To bank , No Need To Fetch Details");
			return false;
		}
		else {
            var tempServerResponse = executeServerEvent("button154", "onClick", getValue("Applicant_National_ID"), true);
            var serverResponse = tempServerResponse.split("~");
            var status = serverResponse[0];
            var alertMessage = serverResponse[1];
            if (status == "true") {
				setControlValue('FetchApplicant_Status','Y')
                showAlertDialog("" + alertMessage);
            } else {
                showAlertDialog("" + alertMessage);
            }
        }

    } else if (pControlName == 'button184') {
        if(getValue('Co_Applicant_Nationality')=='')
		{
			showAlertDialog("Kidly Select Co-Applicant Nationality");
			return false;
		}
        else if (getValue('Co_Applicant_Nationality')=='EG' && getValue("Co_Applicant_National_ID").length != 14) 
		{
            showAlertDialog("National Id Must be 14 Digit");
            return false;
        }
		 else if (getValue('Co_Applicant_Nationality')!='EG' && getValue("Co_Applicant_National_ID").length != 6) 
		{
            showAlertDialog("CIF Id Must be 6 Digit");
            return false;
        } else if (getValue("FetchApplicant_Status") != "Y") {
            showAlertDialog("Kindly Fetch Applicant Details First");
            return false;
        } else if (getValue("Co_Applicant_National_ID") == getValue("Applicant_National_ID")) {
            showAlertDialog("Applicant and Co-Applicant ID can not be same");
            return false;
        } else if (getValue("Co_Applicant_National_ID") == getValue("Guarantor_National_ID")) {
            showAlertDialog("Co-Applicant and Guarantor ID can not be same");
            return false;
        } else {
            var serverResponse = executeServerEvent("button184", "onClick", getValue("Co_Applicant_National_ID"), true);
           var status = serverResponse[0];
            var alertMessage = serverResponse[1];
            if (status == "true") {
				setControlValue('FetchApplicant_Status','Y')
                showAlertDialog("" + alertMessage);
            } else {
                showAlertDialog("" + alertMessage);
            }
        }
    } 
	else if (pControlName == 'button185') {
        if(getValue('Guarantor_Applicant_Nationality')=='')
		{
			showAlertDialog("Kidly Select Guarantor Nationality");
			return false;
		}
        else if (getValue('Guarantor_Applicant_Nationality')=='EG' && getValue("Guarantor_Applicant_National_ID").length != 14) 
		{
            showAlertDialog("National Id Must be 14 Digit");
            return false;
        }
		 else if (getValue('Guarantor_Applicant_Nationality')!='EG' && getValue("Guarantor_Applicant_National_ID").length != 6) 
		{
            showAlertDialog("CIF Id Must be 6 Digit");
            return false;
        }  else if (getValue("FetchApplicant_Status") != "Y") {
            showAlertDialog("Kindly Fetch Applicant Details First");
            return false;
        } else if (getValue("Guarantor_National_ID") == getValue("Applicant_National_ID")) {
            showAlertDialog("Applicant and Guarantor ID can not be same");
            return false;
        } else if (getValue("Co_Applicant_National_ID") == getValue("Guarantor_National_ID")) {
            showAlertDialog("Co-Applicant and Guarantor ID can not be same");
            return false;
        } else {
            var serverResponse = executeServerEvent("button185", "onClick", getValue("Guarantor_National_ID"), true);
            var status = serverResponse[0];
            var alertMessage = serverResponse[1];
            if (status == "true") {
				setControlValue('FetchApplicant_Status','Y')
                showAlertDialog("" + alertMessage);
            } else {
                showAlertDialog("" + alertMessage);
            }
        }
    } else if (pControlName == 'button178') {
        if (getValue('Collateral_CIF') == "" || getValue('Collateral_CIF').length != 6) {
            showAlertDialog("Kindly Enter Correct Collateral CIF");
            return false;
        } 
		
		else {
			//Ajay 15Dec start
			var count=getGridRowCount("table129");
			var tempCount=0;
			for (var i=0;i<count;i++)
			{
				if(getValueFromTableCell("table129", i, 1)==getValue("Collateral_CIF"))
				{
					showAlertDialog('Data for this CIF is already fetched');
					return false;
				}
			}
			//Ajay 15Dec end
            var tempServerResponse = executeServerEvent("button178", "onClick", "", true);
            var serverResponse = tempServerResponse.split("~");
            var status = serverResponse[0];
            var alertMessage = serverResponse[1];
            if (status == "true") {
                showAlertDialog("" + alertMessage);
                //disableControl('button178');
                //enableControl('button192'); //Ajay 15Dec
            } else {
                showAlertDialog("" + alertMessage);
                //disableControl('button192');  //Ajay 15Dec
            }
        }
    } else if (pControlName == 'button192') {
		if(getValue('FetchApplicant_Status')!='Y')
		{
			showAlertDialog("Please Fetch Customer Details First");
			return false;
		}
        var ret = checkMandatory('Branch_Name');
        if (ret == false) {
            return false;
        }
		else if(getValue('Req_Loan_Amt')=='')
		{
			showAlertDialog("Request Finance/CC Amount is Blank");
			return false;
		}
		else {
			
			var reqLoanAmt = parseFloat(getValue('Req_Loan_Amt'));
			if(isNaN(reqLoanAmt))
			{
				showAlertDialog("Enter Valid  Finance/CC Amount");
				return false;
			}
			
			var reqLoanAmt = parseFloat(getValue('Req_Loan_Amt'));
			if(isNaN(reqLoanAmt))
			{
				showAlertDialog("Enter Valid  Finance/CC Amount");
				return false;
			}
			
			var totChosenCollValue = parseFloat(getValue('Total_Chosen_Coll_Value'));
			if(isNaN(totChosenCollValue))
			{
				showAlertDialog("Total Chosen Collateral Value is not Valid");
				return false;
			}
			
			if(reqLoanAmt>totChosenCollValue)
			{
				showAlertDialog("Requested Finance/CC Amount is more than the Total Chosen Collateral Value");
				return false;
			}
			
			
			if(getGridRowCount("table129")==0)
			{
				showAlertDialog("No Collateral Available to Reserve");
				return false;
			}
			else
			{
				var CollHeader="";
				var count=getGridRowCount("table129");
				var tempCount=0;
				for (var i=0;i<count;i++)
				{
					if(getValueFromTableCell("table129", i, 0)=='true')
					{
						tempCount++;
						CollHeader=CollHeader+getValueFromTableCell("table129", i, 12);
					}
				}
				if(tempCount==0 || CollHeader=='')
				{
					showAlertDialog("No Collateral Selected to Reserve");
					return false;
				}
				/* else if(tempCount>10)
				{
					showAlertDialog("Can Not Select More Than 10 Collateral to Reserve");
					return false;
				} */
				else
				{
				var tempServerResponse = executeServerEvent("button192", "onClick", CollHeader, true);
				saveWorkItem();
				var serverResponse = tempServerResponse.split("~");
				var status = serverResponse[0];
				var alertMessage = serverResponse[1];
				if (status == "true") 
				{
					showAlertDialog("" + alertMessage);
					disableControl('button192');
					disableControl('button178');
					disableControl('Collateral_CIF');
					disableControl('table129');
				} 
				else 
				{
					showAlertDialog("" + alertMessage);
				}	
				}
			}
            
        }
    } else if (pControlName == 'button218') {
		
		if(getValue('Sub_Product_Type')!='Secured')
		{
			if (getValue("Applicant_Employer_Grade") == "")
			{
				showMessage('Applicant_Employer_Grade',"Kindly Select Employer Tier  for Applicant","error");
				return false;
			}
		}	
        var ProgramType = "";         
		if (getValue("PROGRAM_TYPE") == "") 
		{
			showMessage('PROGRAM_TYPE',"Kindly Select Program Type for Applicant","error");
			return false;
		} else if (getValue("PROGRAM_TYPE") != "") {
			ProgramType = getValue("PROGRAM_TYPE");
		}
		
		if(compareStringsIgnoreCase(getValue("Applicant_Age"),''))
		{
			showMessage('Applicant_Age',"Applicant Age is not captured","error");
            return false;
        }
		
		CalculateTotalLiability();
		
        var tempServerResponse=executeServerEvent("button218", "onClick", ProgramType, true);
         var serverResponse = tempServerResponse.split("~");
            var status = serverResponse[0];
            var alertMessage = serverResponse[1];
            if (status == "true") {
                showAlertDialog("" + alertMessage);
                //disableControl('button218');
            } else {
                showAlertDialog("" + alertMessage);
            }
			saveWorkItem();
    }
	
	else if("button181"==pControlName)
	{
		if(getValue('table107_CustomerNationalID')=='' || (getValue('table107_CustomerNationalID').length) !=14)
		{
			showAlertDialog("National ID must be 14 digit");
			return false;
		}
		else
		{
			var tempServerResponse=executeServerEvent("button181", "onClick", "", true);
			var serverResponse = tempServerResponse.split("~");
            var status = serverResponse[0];
            var alertMessage = serverResponse[1];
			var responseXML = serverResponse[2];
            if (status == "true") {
                showAlertDialog("" + alertMessage);
				Generate_Template_NID_Function(responseXML);
                //disableControl('button181');-----------------09/01/2023
				setControlValue('NID_Check_Flag','Y');
				if(isRCRWS())
				{
				}
				else
				{
					
				disableControl("button181");
				}
            } else {
                showAlertDialog("" + alertMessage);
            }
		}

		 saveWorkItem();
		
	}
	else if('button220'==pControlName)
	{
		var tempServerResponse=executeServerEvent("button220", "onClick", "", true);
		var serverResponse = tempServerResponse.split("~");
            var status = serverResponse[0];
            var alertMessage = serverResponse[1];
			var responseXML = serverResponse[2];
			if (status == "true") {
                showAlertDialog("" + alertMessage);
				window.parent.customAddDoc(responseXML);
                disableControl('button193');
				setControlValue('Credit_Score_Flag','Y');
				if(isRCRWS())
				{
				}
				else
				{
					
				disableControl("button220");
				}
            } else {
                showAlertDialog("" + alertMessage);
            }
		
	}
	else if('button193'==pControlName)
	{
		var TotalNetSalary=getValue("NetSalary");
		var ReqLoanAmount=getValue("Req_Loan_Amt");
		var TIA=getColumnSum("table117", "Total Installment Amount");
		var AgeAtMaturity=getValueFromTableCell("table117", 0, 47);
		var AccountNumber=getValueFromTableCell("table107", 0, 35);
		if (typeof AccountNumber === 'undefined') 
		{
			AccountNumber = "";
		}
		
		setControlValue('DisposableIncomeWithOverTime',TotalNetSalary);
		
		if (getValue("Product_Type") == "CC")
		{
			setControlValue('Credit_Approved_Amount',getValueFromTableCell("table117", 0, 56));
			var creditInstallmentAmount=parseFloat((5*getValueFromTableCell("table117", 0, 56))/100);
			if(isNaN(creditInstallmentAmount))
			{
				creditInstallmentAmount = '0';
			}
			else
			{
				creditInstallmentAmount = creditInstallmentAmount.toFixed(2);
			}
			setControlValue('Credit_Approved_Installment',creditInstallmentAmount);
		}
		else		
		{
			setControlValue('Credit_Approved_Amount',getValueFromTableCell("table117", 0, 60));
			setControlValue('Credit_Approved_Installment',getValueFromTableCell("table117", 0, 58));
		}
		
		
		
		setControlValue('Age_at_Maturity',AgeAtMaturity);
		setControlValue('CustomerLoanAccountNumber',AccountNumber);
		if(getValue('Applicant_Name')=='')
		{
			showAlertDialog("Please Enter Applicant Name");
			return false;
		}
		else if(getValue('Applicant_DOB')=='')
		{
			showAlertDialog("Please Enter Applicant DOB");
			return false;
		}
		else if(getValue('Applicant_Gender')=='')
		{
			showAlertDialog("Please Enter Applicant Gender");
			return false;
		}
		var tempServerResponse=executeServerEvent("button193", "onClick", "", true);
		var serverResponse = tempServerResponse.split("~");
            var status = serverResponse[0];
            var alertMessage = serverResponse[1];
			var responseXML = serverResponse[2];
			if (status == "true") {
                showAlertDialog("" + alertMessage);
				window.parent.customAddDoc(responseXML);
				
                disableControl('button193');
            } else {
                showAlertDialog("" + alertMessage);
            }
		
		CalculateTotalLiability();
		
	}
	else if('button205'==pControlName)
	{
		if(getValue('Customer_Type')=='New To Bank')
		{
			showAlertDialog("Can Not Fetch Internal Liabilities for New To Bank");
			return false;
		}
		clearTable('table112');
		var tempServerResponse=executeServerEvent("button205", "onClick", "", true);
		var serverResponse = tempServerResponse.split("~");
            var status = serverResponse[0];
            var alertMessage = serverResponse[1];
			var responseXML = serverResponse[2];
			if (status == "true") {
                showAlertDialog("" + alertMessage);
				//window.parent.customAddDoc(responseXML);
                disableControl('button205');
            } else {
                showAlertDialog("" + alertMessage);
            }
			CalculateTotalLiability();
	}
	else if('button166'==pControlName)
	{
		if(getValue('Applicant_Name')=='')
		{
			showAlertDialog("Please Enter Applicant Name");
			return false;
		}
		else if(getValue('Applicant_DOB')=='')
		{
			showAlertDialog("Please Enter Applicant DOB");
			return false;
		}
		else if(getValue('Applicant_Gender')=='')
		{
			showAlertDialog("Please Enter Applicant Gender");
			return false;
		}
		var tempServerResponse=executeServerEvent("button166", "onClick", "", true);
		var serverResponse = tempServerResponse.split("~");
            var status = serverResponse[0];
            var alertMessage = serverResponse[1];
			var responseXML = serverResponse[2];
			if (status == "true") {
                showAlertDialog("" + alertMessage);
				
				setControlValue('Fetch_Iscore_Status','Y');
				if(isRCRWS())
				{
				}
				else
				{
					
				disableControl("button166");
				}
				window.parent.customAddDoc(responseXML);
                
            } else {
                showAlertDialog("" + alertMessage);
            }
	}
	else if('button194'==pControlName)
	{
		
		var smsFlag='N';
		if(getValue('table135_AccountNumber_at_AUB_Egypt_SAE')=='')
		{
			showAlertDialog("Account Number is blank");
			return false;
		}
		var refCounter=0;
		var rowCount=getGridRowCount('table135');
		for(var i=0;i<rowCount;i++)
		{
			if(getValueFromTableCell("table135", i, 13)!='')
			{
				refCounter++;
			}
		}
		if(refCounter==(rowCount-1))
		{
			smsFlag='Y';
		}
		
		
		
		var tempServerResponse=executeServerEvent("button194", "onClick", getValue('table135_AccountNumber_at_AUB_Egypt_SAE')+"~"+getValue('table135_TYPE')+"~"+getValue('table135_LINSTLMNTDATE')+"~"+getValue('table135_FINSTALMNTDATE')+"~"+getValue('table135_INSTLMNTAMNT')+"~"+getValue('table135_AMOUNT')+"~"+smsFlag, true); 
		var serverResponse = tempServerResponse.split("~");
            var status = serverResponse[0];
            var alertMessage = serverResponse[1];
			var responseXML = serverResponse[2];
            if (status == "True") {
                //disableControl('button194');
				document.getElementById('savechanges_table135').click();
				saveWorkItem();
				showAlertDialog("" + alertMessage);
            } else {
                showAlertDialog("" + alertMessage);
				//disableControl('button194');
            }
	}
	else if('Loan_Calculator'==pControlName)
	{
		var DateCompare=compareTwoDates(getValue('table117_ValueDate'),getValue('table117_FirstInstallmentDate'));
		if(getValue("Branch_Code")=='')
		{
			showAlertDialog("Please Enter Branch Details");
			return false;
		}
		else if(getValue("table117_Currency")=='')
		{
			showAlertDialog("Please Enter Currency");
			return false;
		}
		else if(getValue("table117_RequestedLoanAmount1")=='')
		{
			showAlertDialog("Please Enter Request Finance Amount");
			return false;
		}
		else if(getValue("table117_ValueDate")=='')
		{
			showAlertDialog("Please Enter Booking Date");
			return false;
		}
		else if(getValue("table117_InterestRate")=='')
		{
			showAlertDialog("Please Enter Profit Rate");
			return false;
		}
		else if(getValue("table117_LoanTenor1")=='')
		{
			showAlertDialog("Please Enter Tenor");
			return false;
		}
		else if(getValue("table117_FirstInstallmentDate")=="")
		{
			showAlertDialog("Please Enter First Instalment Date");
			return false;
		}
		else if(compareStringsIgnoreCase(DateCompare,"Large"))
		{
			showAlertDialog("Booking Date is Larger Than First Instalment Date");
			return false;
		}
		
		if(compareStringsIgnoreCase(getValue("Sub_Product_Type"), "Secured") && (getValue("Product_Type") != "CC"))
		{
			if(compareStringsIgnoreCase(getValue("table117_MarginRate"), ""))
			{
				showAlertDialog("Margin is blank");
				return false;
			}			
		}
		
		var tempServerResponse=executeServerEvent("Loan_Calculator", "onClick", getValue('table117_RequestedLoanAmount1')+"~"+getValue("table117_LoanTenor1")+"~"+getValue("table117_lastInstalmentDate")+"~Monthly", true);
			var serverResponse = tempServerResponse.split("~");
            var status = serverResponse[0];
            var alertMessage = serverResponse[1];
			if (status == "true") {
                showAlertDialog("" + alertMessage);
				//calculateTotalInstallmentAmount('table117_YearlyInstallment');
				calculateTotalInstlmtAmntBulk();
				calculateTotalrequestedAmount('table117_RequestedLoanAmount1');
                //disableControl('button193');
            } else {
                showAlertDialog("" + alertMessage);
            }
	}
	else if('Loan_Calculator_Quarterly'==pControlName)
	{
		var DateCompare=compareTwoDates(getValue('table117_ValueDate'),getValue('table117_FirstInstDateQuatrly'));
		if(getValue("Branch_Code")=='')
		{
			showAlertDialog("Please Enter Branch Details");
			return false;
		}
		else if(getValue("table117_Currency")=='')
		{
			showAlertDialog("Please Enter Currency");
			return false;
		}
		else if(getValue("table117_RqstQuaterLoanAmnt")=='')
		{
			showAlertDialog("Please Enter Request Finance Amount");
			return false;
		}
		else if(getValue("table117_ValueDate")=='')
		{
			showAlertDialog("Please Enter Booking Date");
			return false;
		}
		else if(getValue("table117_InterestRate")=='')
		{
			showAlertDialog("Please Enter Profit Rate");
			return false;
		}
		else if(getValue("table117_LoanTenor2")=='')
		{
			showAlertDialog("Please Enter Tenor");
			return false;
		}
		else if(getValue("table117_FirstInstDateQuatrly")=="")
		{
			showAlertDialog("Please Enter First Instalment Date");
			return false;
		}
		else if(compareStringsIgnoreCase(DateCompare,"Large"))
		{
			showAlertDialog("Booking Date is Larger Than First Instalment Date");
			return false;
		}
		
		
		var tempServerResponse=executeServerEvent("Loan_Calculator", "onClick", getValue('table117_RqstQuaterLoanAmnt')+"~"+getValue("table117_LoanTenor2")+"~"+getValue("table117_LastQuarterlyInstalmentDate")+"~Quarterly", true);
			var serverResponse = tempServerResponse.split("~");
            var status = serverResponse[0];
            var alertMessage = serverResponse[1];
			if (status == "true") {
                showAlertDialog("" + alertMessage);
				//calculateTotalInstallmentAmount('table117_InstalmentAmntQtrly');
				calculateTotalInstlmtAmntBulk();
				calculateTotalrequestedAmount('table117_RqstQuaterLoanAmnt');
                //disableControl('button193');
            } else {
                showAlertDialog("" + alertMessage);
            }
	}
	
	
	
	
	else if('Loan_Calculator_SemiAnnually'==pControlName)
	{
		var DateCompare=compareTwoDates(getValue('table117_ValueDate'),getValue('table117_FirstInstDateSemiAnnly'));
		if(getValue("Branch_Code")=='')
		{
			showAlertDialog("Please Enter Branch Details");
			return false;
		}
		else if(getValue("table117_Currency")=='')
		{
			showAlertDialog("Please Enter Currency");
			return false;
		}
		else if(getValue("table117_ReqstdSemiAnualLoanAmnt")=='')
		{
			showAlertDialog("Please Enter Request Finance Amount");
			return false;
		}
		else if(getValue("table117_ValueDate")=='')
		{
			showAlertDialog("Please Enter Booking Date");
			return false;
		}
		else if(getValue("table117_InterestRate")=='')
		{
			showAlertDialog("Please Enter Profit Rate");
			return false;
		}
		else if(getValue("table117_SemiAnnuallyTenor")=='')
		{
			showAlertDialog("Please Enter Tenor");
			return false;
		}
		else if(getValue("table117_FirstInstDateSemiAnnly")=="")
		{
			showAlertDialog("Please Enter First Instalment Date");
			return false;
		}
		else if(compareStringsIgnoreCase(DateCompare,"Large"))
		{
			showAlertDialog("Booking Date is Larger Than First Instalment Date");
			return false;
		}
		
		
		var tempServerResponse=executeServerEvent("Loan_Calculator", "onClick", getValue('table117_ReqstdSemiAnualLoanAmnt')+"~"+getValue("table117_SemiAnnuallyTenor")+"~"+getValue("table117_LastSemiAnnuallyInstalmentDate")+"~SemiAnnual", true);
			var serverResponse = tempServerResponse.split("~");
            var status = serverResponse[0];
            var alertMessage = serverResponse[1];
			if (status == "true") {
                showAlertDialog("" + alertMessage);
				//calculateTotalInstallmentAmount('table117_YearlyInstallment');
				calculateTotalInstlmtAmntBulk();
				calculateTotalrequestedAmount('table117_ReqstdSemiAnualLoanAmnt');
                //disableControl('button193');
            } else {
                showAlertDialog("" + alertMessage);
            }
	}
	else if('Loan_Calculator_Yearly'==pControlName)
	{
		var DateCompare=compareTwoDates(getValue('table117_ValueDate'),getValue('table117_FirstInstallmentDate2'));
		if(getValue("Branch_Code")=='')
		{
			showAlertDialog("Please Enter Branch Details");
			return false;
		}
		else if(getValue("table117_Currency")=='')
		{
			showAlertDialog("Please Enter Currency");
			return false;
		}
		else if(getValue("table117_RequestedLoanAmount2")=='')
		{
			showAlertDialog("Please Enter Request Finance Amount");
			return false;
		}
		else if(getValue("table117_ValueDate")=='')
		{
			showAlertDialog("Please Enter Booking Date");
			return false;
		}
		else if(getValue("table117_InterestRate")=='')
		{
			showAlertDialog("Please Enter Profit Rate");
			return false;
		}
		else if(getValue("table117_Yearly_Tenor")=='')
		{
			showAlertDialog("Please Enter Tenor");
			return false;
		}
		else if(getValue("table117_FirstInstallmentDate2")=="")
		{
			showAlertDialog("Please Enter First Instalment Date");
			return false;
		}
		else if(compareStringsIgnoreCase(DateCompare,"Large"))
		{
			showAlertDialog("Booking Date is Larger Than First Instalment Date");
			return false;
		}
		
		
		var tempServerResponse=executeServerEvent("Loan_Calculator", "onClick", getValue('table117_RequestedLoanAmount2')+"~"+getValue("table117_Yearly_Tenor")+"~"+getValue("table117_LastYearlyInstalmentDate")+"~Yearly", true);
			var serverResponse = tempServerResponse.split("~");
            var status = serverResponse[0];
            var alertMessage = serverResponse[1];
			if (status == "true") {
                showAlertDialog("" + alertMessage);
				//calculateTotalInstallmentAmount('table117_YearlyInstallment');
				calculateTotalInstlmtAmntBulk();
				calculateTotalrequestedAmount('table117_RequestedLoanAmount2');
                //disableControl('button193');
            } else {
                showAlertDialog("" + alertMessage);
            }
	}
	
	
	//Added by Shivanshu
	if (pControlName == 'AccountCreationBTN') {
		
		if(getValue('Customer_Type')=='New To Bank'){
		
	            var tempServerResponse = executeServerEvent("AccountCreationBTN", "onClick", getValue("Applicant_National_ID"), true);
	            var serverResponse = tempServerResponse.split("~");
	            var status = serverResponse[0];
	            var alertMessage = serverResponse[1];
	            if (status == "true") {
	                showAlertDialog("" + alertMessage);
	            } else {
	                showAlertDialog("" + alertMessage);
	            }
				
			} else {
					showAlertDialog("Account can be created for New To Bank Customer only");
			}
	  }
	
	/*************************************************Integrations ends*************************************************************/
	else if('button197'==pControlName)
	{
		CalculateDBR(true);
		
	}
	else if('Fetch_Exception'==pControlName) //Ajay 15Dec
	{
		if(compareStringsIgnoreCase(getValueFromTableCell("table108", 0, 11),''))
		{
			showAlertDialog('Area Type is missing');
			return false;
		}
		else if(compareStringsIgnoreCase(getValueFromTableCell("table137", 0, 12),'') && getValue("Sub_Product_Type")=='Un-secured')
		{
			showAlertDialog('Profession is missing');
			return false;
		}
		else if(compareStringsIgnoreCase(getValueFromTableCell("table137", 0, 13),'') && getValue("Sub_Product_Type")=='Un-secured')
		{
			showAlertDialog('Blue Collars is missing');
			return false;
		}
		else if(compareStringsIgnoreCase(getValue("PROGRAM_TYPE"),''))
		{
			showMessage('PROGRAM_TYPE',"Kindly Select Program Type","error");
			return false;
		}
		else if(compareStringsIgnoreCase(getValue("Product_Type"),''))
		{
			showAlertDialog('product Type is missing');
			return false;
		}
		else if(compareStringsIgnoreCase(getValue("Applicant_Age"),''))
		{
			showAlertDialog('Applicant Age is missing');
			return false;
		}
		else if(compareStringsIgnoreCase(getValue("Applicant_Employer_Grade"),'') && getValue("Sub_Product_Type")=='Un-secured')
		{
			showAlertDialog('Employer Tier / Segment is missing');
			return false;
		}
		else if(compareStringsIgnoreCase(getValue("Req_Loan_Amt"),''))
		{
			showAlertDialog('Requested Finance Amount is missing');
			return false;
		}
		else if(compareStringsIgnoreCase(getValue("Applicant_Length_of_Service"),'') && getValue("Sub_Product_Type")=='Un-secured')
		{
			showAlertDialog('Applicant Length of Service  is missing');
			return false;
		}
		else if(compareStringsIgnoreCase(getValue("Loan_Tenor"),'') && (compareStringsIgnoreCase(getValue("Product_Type"),'PL') || compareStringsIgnoreCase(getValue("Product_Type"),'AL')))
		{
			showAlertDialog('Finance tenor is missing');
			return false;
		}
		var data=getValueFromTableCell("table108", 0, 11)+"~"+getValueFromTableCell("table137", 0, 12)+"~"+getValueFromTableCell("table137", 0, 13)+"~"+getValue("PROGRAM_TYPE");
		var serverResponse = executeServerEvent("Fetch_Exception", "onClick", data, true);
		
		if(serverResponse != "")
		{
			serverResponse = serverResponse.split("~");
			var status = serverResponse[0];
            var alertMessage = serverResponse[1];
            if (status == "true") {
                showAlertDialog("" + alertMessage);
            } else {
                showAlertDialog("" + alertMessage);
            }
		}
	}
	else if('button208'==pControlName) //Ajay 15Dec
	{
		if(compareStringsIgnoreCase(getValue("cd"),''))
		{
			showAlertDialog('CD/TD CIF ID is blank');
			return false;
		}
		
		var count=getGridRowCount("table142");
		var tempCount=0;
		for (var i=0;i<count;i++)
		{
			if(getValueFromTableCell("table142", i, 0)==getValue("cd"))
			{
				showAlertDialog('Data for this CIF is already fetched');
				return false;
			}
		}
		
		var serverResponse = executeServerEvent("Fetch_CDTD", "onClick", "", true);
		
		if(serverResponse != "")
		{
			serverResponse = serverResponse.split("~");
			var status = serverResponse[0];
            var alertMessage = serverResponse[1];
            if (status == "true") {
                showAlertDialog("" + alertMessage);
            } else {
                showAlertDialog("" + alertMessage);
            }
		}
	}
	else if('button209'==pControlName) //Ajay 15Dec
	{
		if(compareStringsIgnoreCase(getValue("table142_CIF"),''))
		{
			showAlertDialog('CIF is blank');
			return false;
		}
		if(compareStringsIgnoreCase(getValue("table142_REFNO"),''))
		{
			showAlertDialog('Ref No. is blank');
			return false;
		}
		/*if(compareStringsIgnoreCase(getValue("table142_ExpireDate"),''))
		{
			showAlertDialog('Expiry Date is blank');
			return false;
		}*/
		if(compareStringsIgnoreCase(getValue("table142_CoverageRation"),''))
		{
			showAlertDialog('Coverage Ratio is blank');
			return false;
		}
		if(compareStringsIgnoreCase(getValue("table142_CoveringAmount"),''))
		{
			showAlertDialog('Coverage Amount is blank');
			return false;
		}
		
		var serverResponse = executeServerEvent("CreateCollateral", "onClick", "", true);
		
		if(serverResponse != "")
		{
			serverResponse = serverResponse.split("~");
			var status = serverResponse[0];
            var alertMessage = serverResponse[1];
            if (status == "true") 
			{
				document.getElementById("savechanges_table142").click();
				saveWorkItem();
                showAlertDialog("" + alertMessage);
            } else {
                showAlertDialog("" + alertMessage);
            }
		}
	}
	else if('button219'==pControlName) //Ajay 15Dec
	{
		if(!compareStringsIgnoreCase(getValue("CollResNo"),''))
		{
			showAlertDialog('Reservation has already been done. Cannot moify Collateral');
			return false;
		}
		
		if(compareStringsIgnoreCase(getValue("table129_CollateralCIF"),''))
		{
			showAlertDialog('CIF is blank');
			return false;
		}
		if(compareStringsIgnoreCase(getValue("table129_CollateralReference"),''))
		{
			showAlertDialog('Ref No. is blank');
			return false;
		}
		/*if(compareStringsIgnoreCase(getValue("table142_ExpireDate"),''))
		{
			showAlertDialog('Expiry Date is blank');
			return false;
		}*/
		if(compareStringsIgnoreCase(getValue("table129_Coverage_Ratio"),''))
		{
			showAlertDialog('Coverage Ratio is blank');
			return false;
		}
		if(compareStringsIgnoreCase(getValue("table129_ChosenCollateralValue"),''))
		{
			showAlertDialog('Outstanding Amount is blank');
			return false;
		}
		
		var serverResponse = executeServerEvent("UpdateCollateral", "onClick", "", true);
		
		if(serverResponse != "")
		{
			serverResponse = serverResponse.split("~");
			var status = serverResponse[0];
            var alertMessage = serverResponse[1];
            if (status == "true") 
			{
				document.getElementById("savechanges_table129").click();
				saveWorkItem();
                showAlertDialog("" + alertMessage);
            } else {
                showAlertDialog("" + alertMessage);
            }
		}
	}
	else if('button217'==pControlName) //Ajay 15Dec
	{		
		if(compareStringsIgnoreCase(getValue("CollResNo"),'')) //Ajay 28Dec
		{
			showAlertDialog('Reservation has not been done yet!');
			return false;
		}
		
		var serverResponse = executeServerEvent("ReservationEnquiry", "onClick", "", true);
		
		if(serverResponse != "")
		{
			serverResponse = serverResponse.split("~");
			var status = serverResponse[0];
            var alertMessage = serverResponse[1];
            if (status == "true") 
			{
				CalculateTotalCoverRatio();
                showAlertDialog("" + alertMessage);
            } else {
                showAlertDialog("" + alertMessage);
            }
		}
	}
}
function customListViewValidationLOS(controlId, action)
{
	switch (controlId) {
		case "table133": // Payment CC method  
        {
            switch (action) {
                case "A": {
                    if (getValue("table133_DebitType") == "Bank Account")
					{
						if(!CheckLengthOfField("table133_BankAccNumber",13))
						{
							return false;
						}
					}
                    break;
                }
                case "M": {
					if (getValue("table133_DebitType") == "Bank Account")
					{
						if(!CheckLengthOfField("table133_BankAccNumber",13))
						{
							return false;
						}
					}
                    break;
                }
                default:
                    break;
            }
            break;
        }
	}
	return true;
}

function customlistViewLoadLOS(controlId, action) {
    switch (controlId) {
        case "table105": // incomeDetailsTable
        {
            switch (action) {
                case "A": {
                    validationsBasedOnIncomeType();
                    validationsBasedOnAdditionalIncome();
                    populateProgramType();
                    removeCoApplicantFromCustType("table105_Applicant_Type");
                    break;
                }
                case "M": {
                    validationsBasedOnIncomeType();
                    validationsBasedOnAdditionalIncome();
                    populateProgramType();
                    removeCoApplicantFromCustType("table105_Applicant_Type");
                    break;
                }
                default:
                    break;
            }
            break;
        }
		
        case "table116": // DisbursementDetails
        {
            switch (action) {
                case "A": {
                    validationsBasedOnTransferType();
                    break;
                }
                case "M": {
                    validationsBasedOnTransferType();
                    break;
                }
                default:
                    break;
            }
            break;
        }
        case "table117": // productDetailsTable
        {
            switch (action) 
			{
                case "A": 
				{	//aarish
					if(activityName == "RCR_CreditAnalyst" || activityName == "RCR_Credit_TL"){
						 setStyle("table117_ExternalVerificationCompanyName","mandatory","true");
					 }	  
					if (getValue("Req_Loan_Amt") == "")
					{
						showAlertDialog("Kidly Fill Request Finance/CC Amount before Adding Product Details");
					}
					setControlValue('table117_Typeofproduct',getValue('Product_Type'));// 
					disableControl('table117_Typeofproduct');
					setControlValue('table117_SubproductTYpe',getValue('Sub_Product_Type'));
					setControlValue('table117_RepaymentShedule',getValue('Repayment_Schedule'));
					setControlValue('table117_LoanTenor1',getValue('Loan_Tenor'));
					setControlValue('table117_RequestedLoanAmount1',getValue('Req_Loan_Amt'));
					setControlValue('table117_RequestedLoanCCAmnt',getValue('Req_Loan_Amt'));
					
					var Count = getGridRowCount("table117");
                    for (var i = 0; i < Count; i++) 
					{
						var adFees=getValueFromColumnName("table117",i,"Admin Fees");
						var adFix=parseFloat(adFees).toFixed(5);
						setControlValue('table117_AdminFee',adFix);
						var mdFees=getValueFromColumnName("table117",i,"Margin / Differential rate");
						var mdFix=parseFloat(mdFees).toFixed(5);
						setControlValue('table117_MarginRate',mdFix);
					}
					
					saveWorkItem();
                    validationsForProductDetailsTable();
					calculateLastInstalmentDate();
					//CalcAgeAtMaturity();
					calMaturity();
					PopulateProductProgram();
					
					if (getValue("Product_Type") == "CC") 
					{
						setControlValue('table117_InterestRate','27');// 
					}
					else  if(compareStringsIgnoreCase(getValue("PROGRAM_TYPE"),'BANKER FINANCE'))
					{
						if(getValue('Loan_Tenor')<=84)
						{
							setControlValue('table117_InterestRate','13.5');
							setControlValue('table117_AdminFee','2.00000');
						}
						else if(getValue('Loan_Tenor')>84 && getValue('Loan_Tenor')<=120)
						{
							setControlValue('table117_InterestRate','14.5');
							setControlValue('table117_AdminFee','2.00000');
						}
						else if(getValue('Loan_Tenor')>120 && getValue('Loan_Tenor')<=144)
						{
								setControlValue('table117_InterestRate','15.5'); 
								setControlValue('table117_AdminFee','2.00000'); 
						}
					}
					else
					{
						executeServerEvent("INTEREST", "onTabLoad", getValue("PROGRAM_TYPE"), true);
					}
					
					//Ajay 29Dec Start
					if (compareStringsIgnoreCase(getValue("Sub_Product_Type"), "Secured")) 
					{
						var maxInterestRate = 0;
						var count=getGridRowCount("table129");
						var tempCount=0;
						for (var i=0;i<count;i++)
						{
							var tempInterestRate = parseFloat(getValueFromTableCell("table129", i, 14));
							
							if(!isNaN(tempInterestRate))
							{
								if(tempInterestRate>maxInterestRate)
								{
									maxInterestRate = tempInterestRate;
								}
							}
						}
						if(maxInterestRate!=0)
						{
							setControlValue('table117_InterestRate',maxInterestRate);// 
						}	
					}
					//Ajay 29Dec ENd
					
					 //setControlValue('table117_Monthly',true);
						//disableControl('table117_Monthly');
						
					 //mandatoryControl('table117_RequestedLoanAmount1,table117_FirstInstallmentDate,table117_LoanTenor1,table117_lastInstalmentDate,table117_YearlyInstallment');
                    break;
                }
                case "M": 
				{	//aarish
					if(activityName == "RCR_CreditAnalyst" || activityName == "RCR_Credit_TL"){
						 setStyle("table117_ExternalVerificationCompanyName","mandatory","true");
					 }	  
					setControlValue('table117_Typeofproduct',getValue('Product_Type'));// 
					disableControl('table117_Typeofproduct');
                    validationsForProductDetailsTable();
					var Count = getGridRowCount("table117");
                    
					for (var i = 0; i < Count; i++) 
					{
						var adFees=getValueFromColumnName("table117",i,"Admin Fees");
						var adFix=parseFloat(adFees).toFixed(5);
						setControlValue('table117_AdminFee',adFix);
						setControlValue('table117_AdminFee',adFix);
						var mdFees=getValueFromColumnName("table117",i,"Margin / Differential rate");
						var mdFix=parseFloat(mdFees).toFixed(5);
						setControlValue('table117_MarginRate',mdFix);
					}
		
					saveWorkItem();
					calculateLastInstalmentDate();
					//CalcAgeAtMaturity();
					calMaturity();
					PopulateProductProgram();
					
					/*if (getValue("Product_Type") == "CC") {
						 setControlValue('table117_InterestRate','27');// 
					 }*/
					 /*else
					 {
						executeServerEvent("INTEREST", "onTabLoad", getValue("PROGRAM_TYPE"), true);
					 }*/
					if(activityName=="Fulfillment_Docs")
					{
					 	enableControl('table117_AdminFee');
						enableControl('savechanges_table117');
					}
					 //disableControl('table117_Monthly');
					if(getValue('table117_Monthly')==true)
					{
						mandatoryControl('table117_RequestedLoanAmount1,table117_FirstInstallmentDate,table117_LoanTenor1,table117_lastInstalmentDate,table117_YearlyInstallment');
					}
					else 
					{
						nonMandatoryControl('table117_RequestedLoanAmount1,table117_FirstInstallmentDate,table117_LoanTenor1,table117_lastInstalmentDate,table117_YearlyInstallment');
						clearValues('table117_RequestedLoanAmount1,table117_FirstInstallmentDate,table117_LoanTenor1,table117_lastInstalmentDate,table117_YearlyInstallment');
					}
					
                    if(getValue('table117_Quarterly')==true)
					{
						mandatoryControl('table117_RqstQuaterLoanAmnt,table117_LoanTenor2,table117_FirstInstDateQuatrly,table117_LastQuarterlyInstalmentDate,table117_InstalmentAmntQtrly');
					}
					else
					{
						nonMandatoryControl('table117_RqstQuaterLoanAmnt,table117_LoanTenor2,table117_FirstInstDateQuatrly,table117_LastQuarterlyInstalmentDate,table117_InstalmentAmntQtrly');
						clearValues('table117_RqstQuaterLoanAmnt,table117_LoanTenor2,table117_FirstInstDateQuatrly,table117_LastQuarterlyInstalmentDate,table117_InstalmentAmntQtrly');
					}
					
					if(getValue('table117_Semi_Annually')==true)
					{
						mandatoryControl('table117_ReqstdSemiAnualLoanAmnt,table117_SemiAnnuallyTenor,table117_FirstInstDateSemiAnnly,table117_LastSemiAnnuallyInstalmentDate,table117_InstallmentAmtMonthly');
					}
					else
					{
						nonMandatoryControl('table117_ReqstdSemiAnualLoanAmnt,table117_SemiAnnuallyTenor,table117_FirstInstDateSemiAnnly,table117_LastSemiAnnuallyInstalmentDate,table117_InstallmentAmtMonthly');
						clearValues('table117_ReqstdSemiAnualLoanAmnt,table117_SemiAnnuallyTenor,table117_FirstInstDateSemiAnnly,table117_LastSemiAnnuallyInstalmentDate,table117_InstallmentAmtMonthly');
					}
					
					if(getValue('table117_Yearly')==true)
					{
						mandatoryControl('table117_RequestedLoanAmount2,table117_Yearly_Tenor,table117_FirstInstallmentDate2,table117_LastYearlyInstalmentDate,table117_InstallmentAmtBulk');
					}
					else
					{
						nonMandatoryControl('table117_RequestedLoanAmount2,table117_Yearly_Tenor,table117_FirstInstallmentDate2,table117_LastYearlyInstalmentDate,table117_InstallmentAmtBulk');
						clearValues('table117_RequestedLoanAmount2,table117_Yearly_Tenor,table117_FirstInstallmentDate2,table117_LastYearlyInstalmentDate,table117_InstallmentAmtBulk');
					}
					
					break;
                }
                default:
                break;
            }
            break;
        }

        case "table107": // customerDetails
        {
            switch (action) {
                case "A": {
                    //biasedMandatory("table107_NoOfYearsInEGYPT", "Sub_Product_Type", "Secured");
                    validationsOfCreditCardInCustomerDetails(false);
                   
                    removeCoApplicantFromCustType("table107_CustomerType");
					MandateForForeignCust();
					validationsBasedOnDealingWithEgypt(false);
                    break;
                }
                case "M": {
                    biasedNonMandatory("table107_ReferenceRelationWithApplicant", "table107_CustomerType", "Applicant");
          
                    validationsOfCreditCardInCustomerDetails(false);
                    removeCoApplicantFromCustType("table107_CustomerType");
					checkValidationAndPopulateData("CustomerGrid","M");
					validationsBasedOnDealingWithEgypt(false);
					MandateForForeignCust();
                    break;
                }
                default:
                    break;
            }
            break;
        }

        case "table108": // addressDetails
        {
            switch (action) {
                case "A": {
                    removeCoApplicantFromCustType("table108_CustomerType");
                    break;
                }
                case "M": {
                    removeCoApplicantFromCustType("table108_CustomerType");
                    break;
                }
                default:
                    break;
            }
            break;
        }

        case "table137": // employementDetails
        {
            switch (action) {
                case "A": {
                    removeCoApplicantFromCustType("table137_CustomerType");
					AutoPopEmpName();//anuj
					biasedNonMandatory("table137_TypeOfEmployment,table137_LengthOfService,table137_EmployerCategory,table137_JoiningDate", "Sub_Product_Type", "Secured");//abcd
					biasedMandatory("table137_Capital","Applicant_Employment_Type","Self- Employed");
                    break;
                }
                case "M": {
                    removeCoApplicantFromCustType("table137_CustomerType");
					AutoPopEmpName();//anuj
					biasedNonMandatory("table137_TypeOfEmployment,table137_LengthOfService,table137_EmployerCategory,table137_JoiningDate", "Sub_Product_Type", "Secured");//abcd
					biasedMandatory("table137_Capital","Applicant_Employment_Type","Self- Employed");
                    break;
                }
                default:
                    break;
            }
            break;
        }

        case "table106": // deductionsDetailsTable
        {
            switch (action) {
                case "A": {
                    validationsBasedOnSettlememtFromAppFacility();
                    removeCoApplicantFromCustType("table106_ApplicantType");
                    break;
                }
                case "M": {
                    validationsBasedOnSettlememtFromAppFacility();
                    removeCoApplicantFromCustType("table106_ApplicantType");
                    break;
                }
                default:
                    break;
            }
            break;
        }
		case "table116": // deductionsDetailsTable
        {
            switch (action) {
                case "A": {
                    MandateonExternalTransfer();
                    break;
                }
                case "M": {
					MandateonExternalTransfer();
                    break;
                }
                default:
                    break;
            }
            break;
        }
		
        case "table117": // deductionsDetailsTable
        {
            switch (action) {
                case "A": {
                    populateProductDetailsFromBasicInfo();
                    break;
                }
                case "M": {

                    break;
                }
                default:
                    break;
            }
            break;
        }
        case "table129": // collateralDetailsTable
        {
            switch (action) {
                case "A": {
                    removeCoApplicantFromCustType("table129_ApplicantType");
					ValidationsOnCollateralDetails();
					if(ActivityName=='LPU_Maker')
					{
						//enableControl('button192'); //Ajay 15Dec
					}
					else
					{
						//disableControl('button192'); //Ajay 15Dec
					}	
					enableUpdateCollateral();
                    break;
                }
                case "M": {
                    removeCoApplicantFromCustType("table129_ApplicantType");
					ValidationsOnCollateralDetails();
					enableUpdateCollateral();
                    break;					
                }
                default:
                    break;
            }
            break;
        }

        case "CUSTOMER_TYPE_BG_CHECK": // Background check 
        {
            switch (action) {
                case "A": {
                    removeCoApplicantFromCustType("CUSTOMER_TYPE_BG_CHECK");
                    break;
                }
                case "M": {
                    removeCoApplicantFromCustType("CUSTOMER_TYPE_BG_CHECK");
                    break;
                }
                default:
                    break;
            }
            break;
        }
		case "table130": // Background check 
        {
            switch (action) {
                case "A": {
                    setControlValue("table130_ApplicantType",getValue("PROGRAM_TYPE"));
                    break;
                }
                case "M": {
                   setControlValue("table130_ApplicantType",getValue("PROGRAM_TYPE"));
                    break;
                }
                default:
                    break;
            }
            break;
        }
		case "table135": // LPU /Operations grid 
        {
            switch (action) {
                case "A": {
                    setControlValue("table135_AccountNumber_at_AUB_Egypt_SAE",getValue("CustomerLoanAccountNumber"));//the
					setControlValue("table135_Account_Type",getValueFromTableCell("table107", 0, 36));
					var AccountNo=getValue("CustomerLoanAccountNumber");
					if(compareStringsIgnoreCase(getValue("Product_Type"),'PL') || compareStringsIgnoreCase(getValue("Product_Type"),'PL') )
					{
						try
						{
							var Sufficx=AccountNo.substring(AccountNo.length - 3);
							setControlValue("table135_Suffix",Sufficx);
						}
						catch(e)
						{
							console.log(e);
						}
					}
					disableControl('button194'); //Ajay 24Dec
                    break;
                }
                case "M": {
					
					
				if((getValue('table135_LOANREFNO')!='' && activityName!='LPU_Maker') || activityName=='LPU_Maker')
			   {
				   disableControl('button194');
				   
			   }
			   if((getValue('table135_LOANREFNO')=='' && activityName!='LPU_Maker'))
			   {
				    enableControl('button194');
			   }
				   
                    break;
                }
                default:
                    break;
            }
            break;
        }
		 case "table133": // Payment CC method  
        {
            switch (action) {
                case "A": {
                    validationsBasedonDebitType();
                    break;
                }
                case "M": {
					validationsBasedonDebitType();
                    break;
                }
                default:
                    break;
            }
            break;
        }
		
		case "table142": //Ajay 15Dec
        {
            switch (action) {
                case "A": {
                    enableCreateCollateral();
                    break;
                }
                case "M": {
                    enableCreateCollateral();
                    break;
                }
                default:
                    break;
            }
            break;
        }
		

    }
}

function change_LOS(ControlObject) {
	
    var pControlName = ControlObject.id;
    var pControlValue = getValue(pControlName);
	populateIncomeOnChangeOfMonthIncome(pControlName);// need to put it down
	
	if("table105_Allowances"==pControlName)
	{
		
		setControlValue("table105_Total_IncomeAmt_Yearly",getValue("table105_Allowances"));
	}
	else if("table105_Bonus"==pControlName)
	{		
		setControlValue("table105_Total_IncomeAmt_Yearly",getValue("table105_Bonus"));
	}
	
	else if('table105_AUBE_CD_TDS_Profit'==pControlName)
	{
		setControlValue("table105_Total_IncomeAmt_Yearly",getValue("table105_AUBE_CD_TDS_Profit"));
	}
	else if('table105_Incentive'==pControlName)
	{
		setControlValue("table105_Total_IncomeAmt_Yearly",getValue("table105_Incentive"));
	}
	else if('table105_ProfitShare'==pControlName)
	{
		setControlValue("table105_Total_IncomeAmt_Yearly",getValue("table105_ProfitShare"));
	}
	else if('table105_Rent'==pControlName)
	{
		setControlValue("table105_Total_IncomeAmt_Yearly",getValue("table105_Rent"));
	}
	else if('table105_Pension'==pControlName)
	{
		setControlValue("table105_Total_IncomeAmt_Yearly",getValue("table105_Pension"));
	}
	else if('table105_ProfitShareInAdvance'==pControlName)
	{
		setControlValue("table105_Total_IncomeAmt_Yearly",getValue("table105_ProfitShareInAdvance"));
	}
	else if('table107_CustomerMobileNo'==pControlName)
	{
		setControlValue("MOBILE_NO",getValue("table107_CustomerMobileNo"));
		saveWorkItem();
	}
	
	else if('table117_InstallmentAmtBulk'==pControlName)
	{
		// Installment Amount Yearly - abcde
		//calculateTotalInstallmentAmount('table117_InstallmentAmtBulk');
		calculateTotalInstlmtAmntBulk();
	}
	else if('table117_YearlyInstallment'==pControlName)
	{
		// Monthly Installment Amount - abcde
		//calculateTotalInstallmentAmount('table117_YearlyInstallment');
		calculateTotalInstlmtAmntBulk();
	}
	else if('table117_InstallmentAmtMonthly'==pControlName)
	{
		// Installment Amount Semi Annualy - abcde
		//calculateTotalInstallmentAmount('table117_InstallmentAmtMonthly');
		calculateTotalInstlmtAmntBulk();
	}
	else if('table117_InstalmentAmntQtrly'==pControlName)
	{
		// Instalment Amount Quarterly- abcde
		//calculateTotalInstallmentAmount('table117_InstalmentAmntQtrly');
		calculateTotalInstlmtAmntBulk();
	}
	else if (pControlName == 'table112_Use_DBR') {
		/*if(compareStringsIgnoreCase(getValue('table112_Use_DBR').toString(),"true"))
		{
			calculateNewTotalLiabonUseDBRforCBS("true");
		}
		else{
			calculateNewTotalLiabonUseDBRforCBS("false");
		}*/
	}
	else if (pControlName == 'table120_Use_DBR') {
		/*if(compareStringsIgnoreCase(getValue('table120_Use_DBR').toString(),"true"))
		{
			calculateNewTotalLiabonUseDBRforOther("true");
		}
		else{
			calculateNewTotalLiabonUseDBRforOther("false");
		}*/
	}	
	else if("Applicant_Employer_Name"==pControlName)
	{
		if(getValue("Applicant_Employer_Name")!="Others")
		{
			
			nonMandatoryControl('EMPLOYER_OTHERS');
			executeServerEvent("employerName", "onChange", getValue("Applicant_Employer_Name")+"~Applicant_Employer_Grade", true);
			
			
		}
		else
		{
			clearValue('Applicant_Employer_Grade');
			mandatoryControl('EMPLOYER_OTHERS');
		}
	}
	else if("Co_Applicant_EmployerName"==pControlName)
	{
		if(getValue("Co_Applicant_EmployerName")!="Others")
		{
			
			//nonMandatoryControl('EMPLOYER_OTHERS');
			executeServerEvent("employerName", "onChange", getValue("Co_Applicant_EmployerName")+"~Co_Applicant_Employer_Grade", true);
			
			
		}
		else
		{
			//clearValue('Applicant_Employer_Grade');
			//mandatoryControl('EMPLOYER_OTHERS');
		}
	}
	else if("Guarantor_EmployerName"==pControlName)
	{
		if(getValue("Guarantor_EmployerName")!="Others")
		{
			
			//nonMandatoryControl('EMPLOYER_OTHERS');
			executeServerEvent("employerName", "onChange", getValue("Guarantor_EmployerName")+"~Guarantor_Employer_Grade", true);
			
			
		}
		else
		{
			//clearValue('Applicant_Employer_Grade');
			//mandatoryControl('EMPLOYER_OTHERS');
		}
	}
	else if("table137_EmployerName"==pControlName)
	{
		if(getValue("table137_EmployerName")!="Others")
		{
			
			nonMandatoryControl('table137_OthersSpecify');
			executeServerEvent("employerNameGrid", "onChange", getValue("table137_EmployerName"), true);
		}
		else
		{
			clearValue('table137_EmployerCategory');
			mandatoryControl('table137_OthersSpecify');
		}
	}
	else if("ManualDev_DevType"==pControlName)
	{
		if(getValue("ManualDev_DevType")=="Other")
		{
			
			mandatoryControl('table90_Others Exception');//other change
			
		}
		else
		{
			
			nonMandatoryControl('table90_Others Exception');
		}
	}
	else if("table117_PurposeOfFacility"==pControlName)
	{
		if(getValue("table117_PurposeOfFacility")=="Other Specify")
		{
			
			mandatoryControl('table117_OthersSpecify');//other change
			
		}
		else
		{
			
			nonMandatoryControl('table117_OthersSpecify');
		}
	}
	else if('Acquisition_Channel'==pControlName)
	{
		setValuesFromAnotherFeilds('Initiation_Type', 'Acquisition_Channel');
	}
	else if('Customer_Type'==pControlName)
	{
		if(getValue('Customer_Type')=='New To Bank')
		{
            		setControlValue('Sub_Product_Type','Un-secured')
					disableControl('Sub_Product_Type');
		}
		else{
			        clearValues('Sub_Product_Type')
					enableControl('Sub_Product_Type');
		}
	}
	else if('Req_Loan_Amt'==pControlName)
	{
		
		setValuesFromAnotherFeilds('Header_Loan_Amount', 'Req_Loan_Amt');
		if(parseFloat(getValue("table129_ChosenCollateralValue"))>parseFloat(getValue('table129_AvailableCollateralValue')))
		{
			showAlertDialog("Chosen Collateral Value is greater than Requested finance Amount value");
			clearValues('table129_ChosenCollateralValue');
			clearValues('Req_Loan_Amt');
		}
		else
		{
			var covratio=(parseFloat(100*(getValue("Req_Loan_Amt"))/getValue('table129_ChosenCollateralValue')));
			covratio=covratio.toFixed(2);
			// setControlValue("table129_Coverage_Ratio",removeNaNAndAddPercent(covratio)); //Commented Ajay 15Dec
			//setControlValue("table129_Coverage_Ratio",(parseFloat((100* getValue("table129_ChosenCollateralValue"))/getValue('Req_Loan_Amt'))));
		}
		CalculateTotalCoverRatio();
	}
	else if('Applicant_National_ID'==pControlName)
	{
		if(getValue(pControlName).length==6)
		{
		setValuesFromAnotherFeilds('HeaderCIF', 'Applicant_National_ID');
		}
		else
		{
			setValuesFromAnotherFeilds('Header_NID', 'Applicant_National_ID');
		}
	}
	else if('Applicant_Name'==pControlName)
	{
		setValuesFromAnotherFeilds('Header_Name', 'Applicant_Name');
	}
	else if('Applicant_Nationality'==pControlName)
	{
		if(getValue(pControlName)=='EG')
		{
			nonMandatoryControl('Applicant_Passport_Number');
		}
		else
		{
			mandatoryControl('Applicant_Passport_Number');
		}
	}
	else if('Co_Applicant_Nationality'==pControlName)
	{
		if(getValue(pControlName)=='EG')
		{
			nonMandatoryControl('Co_Applicant_Passport_Number');
		}
		else
		{
			mandatoryControl('Co_Applicant_Passport_Number');
		}
	}
	else if('Guarantor_Applicant_Nationality'==pControlName)
	{
		if(getValue(pControlName)=='EG')
		{
			nonMandatoryControl('Guarantor_Applicant_Passport_Number');
		}
		else
		{
			mandatoryControl('Guarantor_Applicant_Passport_Number');
		}
	}
    else if ("Repayment_Schedule" == pControlName) {
        validationsBasedOnRepaymentSchedule();
    }
    else if ("Product_Type" == pControlName) {
		//
        validationsBasedOnProductType();

    }
	else if("table129_ChosenCollateralValue"==pControlName)
	{
		if(parseFloat(getValue("table129_ChosenCollateralValue"))>parseFloat(getValue('table129_AvailableCollateralValue')))
		{
			showAlertDialog("Chosen Collateral Value is greater than Requested finance Amount value");
			clearValues('table129_ChosenCollateralValue');
			clearValues('Req_Loan_Amt');
		}
		else
		{
			var covratio=(parseFloat(100*(getValue("Req_Loan_Amt"))/getValue('table129_ChosenCollateralValue')));
			covratio=covratio.toFixed(2);
			// setControlValue("table129_Coverage_Ratio",removeNaNAndAddPercent(covratio)); //Commented Ajay 15Dec
			
			//setControlValue("table129_Coverage_Ratio",(parseFloat((100* getValue("table129_ChosenCollateralValue"))/getValue('Req_Loan_Amt'))));
		}
	
	}
	else if("table129_AvailableCollateralValue"==pControlName)
	{
		
	}
	
    else if ("table117_Typeofproduct" == pControlName) {
        validationsForProductDetailsTable();

    }

    else if ("table107_HAVE_CREDIT_CARD" == pControlName) {
        validationsOfCreditCardInCustomerDetails(true);
    }
	else if ("Credit_Card_Limit" == pControlName) {
       CCLimitByNetSalary();
    }
    else if ("Sub_Product_Type" == pControlName) {
        validationsBasedOnSubProductType();
		/*if(getValue("Sub_Product_Type")=='Secured')
		{
			disableControl('button218');
		}
		else
		{
			enableControl('button218');
		}*/

    }
    else if ("table105_AdditionalIncome" == pControlName) {
        calculationsOnAdditionalIncome();
        validationsBasedOnAdditionalIncome();

    }
    else if ("table105_IncomeType" == pControlName) {
        validationsBasedOnIncomeType();

    }
    else if ("table106_To_Be_Settle_From_Applied_Facility" == pControlName) {
        validationsBasedOnSettlememtFromAppFacility();
        validationsBasedOnSettlememtAmount();
    }
    if ("table106_Settlemant_Amount" == pControlName) {
        validationsBasedOnSettlememtAmount();

    }
    if ("table106_Deduction_Amount" == pControlName) {
        validationsBasedOnSettlememtAmount();

    }
    if ("table107_CustomerType" == pControlName) {
        populateDataFrombasicInfo(getValue("table107_CustomerType"));
        biasedNonMandatory("table107_ReferenceRelationWithApplicant", "table107_CustomerType", "Applicant");
        biasedHide("table107_ReferenceRelationWithApplicant", "table107_CustomerType", "Applicant");
		MandateForForeignCust();
		ValidationOnDoYouDealwithAUB();

    }
    if ("table107_DoyoudealwithAUBEgyptSAE" == pControlName) {
        validationsBasedOnDealingWithEgypt(true);
    }
    if ("table137_CustomerType" == pControlName) {
        populateDataFrombasicInfoToEmpTable(getValue("table137_CustomerType"));
		AutoPopEmpName();
    }
    if ("table116_TransferType" == pControlName) {
        validationsBasedOnTransferType();

    }
    if ("Product_Type" == pControlName) {
        clearEnteriesOnChangeOfProductType();

        if (getValue("Product_Type") == "CC") {
            showControl("Card_Delivery_Method");
			mandatoryControl('Card_Delivery_Method');
        } else {
            hideControl("Card_Delivery_Method");
			nonMandatoryControl('Card_Delivery_Method');
			
        }
    }
    if ("Application_Type" == pControlName) {
        if (getValue('Application_Type') == "Single") {
            hideControl('CoApplicantDetails');
            nonMandatoryControl('Co_Applicant_Nationality,Co_Applicant_National_ID,Co_Applicant_Name,Co_Applicant_DOB,Co_Applicant_Gender,Co_Applicant_Passport_Number,Co_Applicant_Sector,Co_Applicant_Employment_Type,Co_Applicant_Length_of_Service,Co_Applicant_Career_Level')

        } else {
            showControl('CoApplicantDetails');
            mandatoryControl('Co_Applicant_Nationality,Co_Applicant_National_ID,Co_Applicant_Name,Co_Applicant_DOB,Co_Applicant_Gender,Co_Applicant_Passport_Number,Co_Applicant_Sector,Co_Applicant_Employment_Type,Co_Applicant_Length_of_Service,Co_Applicant_Career_Level');
        }
    }
    if ("Request_Type" == pControlName) {
        if (getValue("Request_Type") == "New Card" || getValue("Request_Type")=="Buy Out Covered Card") {
            unlockControl("Card_Delivery_Method");
        } else {
            lockControl("Card_Delivery_Method");
        }

    }
	if ("Decision" == pControlName) {
        var decision=getValue("Decision");
		
		if(compareStringsIgnoreCase(decision,"Reject") || compareStringsIgnoreCase(decision,"Discard"))
		{
			mandatoryControl("Rejection_Reason");
		}
		else
		{
			nonMandatoryControl("Rejection_Reason");
		}
		
    }
    if ("table105_IncomeType" == pControlName) {
        clearEnteriesOnChangeOfIncomeType();
    }
    if ("table107_DoyoudealwithAUBEgyptSAE" == pControlName) {
		
        if (getValue("table107_DoyoudealwithAUBEgyptSAE") == "Yes") {
            setStyle("table107_AccountType", "mandatory", "true");
        } else {
            setStyle("table107_AccountType", "mandatory", "false");
        }
		ValidationOnDoYouDealwithAUB();
    }
    if (pControlName == "table133_DebitType" || pControlName == "table133_SetStandingPerc") {
        validationsBasedonDebitType();
    }

    if (pControlName == "Applicant_Employment_Type") {
        if (getValue("Applicant_Employment_Type") == "Salaried customer") {
            mandatoryControl("Applicant_Joining_Date");
			
        } 
		else {
            nonMandatoryControl("Applicant_Joining_Date");
        }
		if(getValue("Applicant_Employment_Type") == "Unemployed")
		{
			nonMandatoryControl("Applicant_Employment_Type,Applicant_Employer_Name,Applicant_Employer_Grade,Applicant_Joining_Date,Applicant_Length_of_Service,Company_Segment");
		}
		else 
		{
			mandatoryControl("Applicant_Employment_Type,Applicant_Employer_Name,Applicant_Employer_Grade,Applicant_Joining_Date,Applicant_Length_of_Service,Company_Segment");
		}
    }

    if (pControlName == "Co_Applicant_Employment_Type") {
        if (getValue("Co_Applicant_Employment_Type") == "Salaried customer") {
            mandatoryControl("Co_Applicant_Joining_Date");
        } else {
            nonMandatoryControl("Co_Applicant_Joining_Date");
        }
		if(getValue("Co_Applicant_Employment_Type") == "Unemployed")
		{
			nonMandatoryControl("Co_Applicant_Employment_Type,Co_Applicant_EmployerName,Co_Applicant_Employer_Grade,Co_Applicant_Joining_Date,Co_Applicant_Length_of_Service");
		}
		else 
		{
			mandatoryControl("Co_Applicant_Employment_Type,Co_Applicant_EmployerName,Co_Applicant_Employer_Grade,Co_Applicant_Joining_Date,Co_Applicant_Length_of_Service");
		}
    }

    if (pControlName == "Guarantor_Employment_Type") {
        if (getValue("Guarantor_Employment_Type") == "Salaried customer") {
            mandatoryControl("Guarantor_Joining_Date");
        } else {
            nonMandatoryControl("Guarantor_Joining_Date");
        }
    }

    if (pControlName == "table105_Applicant_Type") {
        populateProgramType();
    }
	 if (pControlName == "table107_DateOfIssueNID") {
        var issueDate=getValue('table107_DateOfIssueNID');
		setControlValue("table107_DateOfExpiryNID",parseInt(issueDate.split('/')[0])+7+"/"+issueDate.split('/')[1]+"/"+issueDate.split('/')[2])
    }
	
	//anuj val start
	if(compareStringsIgnoreCase(pControlName,"Applicant_Nationality"))
	{
		 IdMandateOnEgypt(pControlName);
	}
	if(compareStringsIgnoreCase(pControlName,"Co_Applicant_Nationality"))
	{
		 IdMandateOnEgypt(pControlName);
	}
	if(compareStringsIgnoreCase(pControlName,"Guarantor_Nationality"))
	{
		 IdMandateOnEgypt(pControlName);
	}
	if(compareStringsIgnoreCase(pControlName,"table137_CustomerType"))
	{
		AutoPopEmpName();
	}
	if(compareStringsIgnoreCase(pControlName,"table116_TransferType"))
	{
		MandateonExternalTransfer();
	}
	
	
	// anuj val end
	
	//-----------------Changes For BLUR Start //
	if (pControlName == 'Applicant_DOB') {
		if(getValue("Applicant_DOB")=="")
		{
			return false;
		}
		else{
			var Age=differenceBetweenDateAndNow(getValue("Applicant_DOB"));
			if(Number.isNaN(Age))
			{
				return false;
			}
			setControlValue("Applicant_Age",Age);
		}
    } else if (pControlName == 'Applicant_Joining_Date') {
		if(getValue("Applicant_Joining_Date")=="")
		{
			return false;
		}
		else{
        setControlValue("Applicant_Length_of_Service", monthDiff(getValue("Applicant_Joining_Date")));
		}
    } else if (pControlName == 'Co_Applicant_DOB') {
		if(getValue("Co_Applicant_DOB")=="")
		{
			return false;
		}
		else{
			var Age=differenceBetweenDateAndNow(getValue("Co_Applicant_DOB"));
			if(Number.isNaN(Age))
			{
				return false;
			}
        setControlValue("Co_Applicant_Age",Age);
		}
    } else if (pControlName == 'Co_Applicant_Joining_Date') {
		if(getValue("Co_Applicant_Joining_Date")=="")
		{
			return false;
		}
		else{
			
        setControlValue("Co_Applicant_Length_of_Service", monthDiff(getValue("Co_Applicant_Joining_Date")));
		}
    } else if (pControlName == 'Guarantor_DOB') {
		if(getValue("Guarantor_DOB")=="")
		{
			return false;
		}
		else{
			var Age=differenceBetweenDateAndNow(getValue("Guarantor_DOB"));
			if(Number.isNaN(Age))
			{
				return false;
			}
        setControlValue("Guarantor_Age", Age);
		}
    } else if (pControlName == 'Guarantor_Joining_Date') {
		if(getValue("Guarantor_Joining_Date")=="")
		{
			return false;
		}
		else{
        setControlValue("Guarantor_Length_of_Service", monthDiff(getValue("Guarantor_Joining_Date")));
		}
    } else if (pControlName == 'table137_JoiningDate') {
		if(getValue("table137_JoiningDate")=="")
		{
			return false;
		}
		else{
        setControlValue("table137_LengthOfService", monthDiff(getValue("table137_JoiningDate")));
		}
    }
	else if(pControlName == 'table117_FirstInstallmentDate')
	{
		if(getValue('table117_Monthly')==false)
		{
			showAlertDialog('Please Select Monthly payment');
			clearValue('table117_FirstInstallmentDate');
			return false;
		}
		calculateLastInstalmentDate();
		//CalcAgeAtMaturity();
		calMaturity();
		
	}
	else if(pControlName == 'table117_lastInstalmentDate')
	{
		//CalcAgeAtMaturity();
		calMaturity();
	}
	else if(pControlName == 'table117_LoanTenor1')
	{
		if(getValue('table117_Monthly')==false)
		{
			showAlertDialog('Please Select Monthly payment');
			clearValue('table117_LoanTenor1');
			return false;
		}
		calculateLastInstalmentDate();
		//CalcAgeAtMaturity();
		calMaturity();
		
	}
	else if(pControlName == 'Co_Applicant_National_ID')
	{
		var CoAppNID=getValue('Co_Applicant_National_ID');
		if(CoAppNID.length!=14)
		{
				return false;
		}
		var yearval=CoAppNID.substring(1,3);
		if(CoAppNID.startsWith('2'))
		{
			var year =19+yearval;
		}
		else
		{
			var year =20+yearval;
		}
		var monthval=CoAppNID.substring(3,5);
		var dateval=CoAppNID.substring(5,7);
		var CoAppDOB=year+"/"+monthval+"/"+dateval;
		/*if(Number.isNaN(CoAppDOB))
		{
			return false;
		}*/
		setControlValue('Co_Applicant_DOB',CoAppDOB);
		var age =differenceBetweenDateAndNow(getValue("Co_Applicant_DOB"));
		if(Number.isNaN(age))
		{
			return false;
		}
		setControlValue("Co_Applicant_Age", age);
	}
	else if(pControlName == 'Applicant_National_ID')
	{
		var AppNID=getValue('Applicant_National_ID');
		if(AppNID.length!=14)
		{
				return false;
		}
		var yearval=AppNID.substring(1,3);
		if(AppNID.startsWith('2'))
		{
			var year =19+yearval;
		}
		else
		{
			var year =20+yearval;
		}
		var monthval=AppNID.substring(3,5);
		var dateval=AppNID.substring(5,7);
		var AppDOB=year+"/"+monthval+"/"+dateval;
		/*if(Number.isNaN(AppDOB))
		{
			return false;
		}*/
		setControlValue('Applicant_DOB',AppDOB);
		var age =differenceBetweenDateAndNow(getValue("Applicant_DOB"));
		if(Number.isNaN(age))
		{
			return false;
		}
		setControlValue("Applicant_Age", age);
	}
	else if(pControlName == 'Guarantor_National_ID')
	{
		var GuarNID=getValue('Guarantor_National_ID');
		if(GuarNID.length!=14)
		{
			return false;
		}
		var yearval=GuarNID.substring(1,3);
		if(GuarNID.startsWith('2'))
		{
			var year =19+yearval;
		}
		else
		{
			var year =20+yearval;
		}
		
		var monthval=GuarNID.substring(3,5);
		var dateval=GuarNID.substring(5,7);
		var GuarDOB=year+"/"+monthval+"/"+dateval;
		/*if(Number.isNaN(GuarDOB))
		{
			return false;
		}*/
		setControlValue('Guarantor_DOB',GuarDOB);
		var age =differenceBetweenDateAndNow(getValue("Guarantor_DOB"));
		if(Number.isNaN(age))
		{
			return false;
		}
		setControlValue("Guarantor_Age", age);
	}
	///----------------------Changes For BLUR End///
	
	/********************************MultiLoan Start*********************************************/
	if(pControlName=='table117_Monthly')
	{
		if(getValue('table117_Monthly')==true)
					 {
					 mandatoryControl('table117_RequestedLoanAmount1,table117_FirstInstallmentDate,table117_LoanTenor1,table117_lastInstalmentDate,table117_YearlyInstallment');
					 }
					 else 
					 {
						 nonMandatoryControl('table117_RequestedLoanAmount1,table117_FirstInstallmentDate,table117_LoanTenor1,table117_lastInstalmentDate,table117_YearlyInstallment');
						 clearValues('table117_RequestedLoanAmount1,table117_FirstInstallmentDate,table117_LoanTenor1,table117_lastInstalmentDate,table117_YearlyInstallment');
						 calculateTotalrequestedAmount();
						 calculateTotalInstlmtAmntBulk();
						 
					 }
	}
	if(pControlName=='table117_Quarterly')
	{
		if(getValue('table117_Quarterly')==true)
		{
			mandatoryControl('table117_RqstQuaterLoanAmnt,table117_LoanTenor2,table117_FirstInstDateQuatrly,table117_LastQuarterlyInstalmentDate,table117_InstalmentAmntQtrly');
		}
		else
		{
			nonMandatoryControl('table117_RqstQuaterLoanAmnt,table117_LoanTenor2,table117_FirstInstDateQuatrly,table117_LastQuarterlyInstalmentDate,table117_InstalmentAmntQtrly');
			clearValues('table117_RqstQuaterLoanAmnt,table117_LoanTenor2,table117_FirstInstDateQuatrly,table117_LastQuarterlyInstalmentDate,table117_InstalmentAmntQtrly');
			calculateTotalrequestedAmount();
			calculateTotalInstlmtAmntBulk();
		}
	}
	if(pControlName=='table117_Semi_Annually')
	{
		if(getValue('table117_Semi_Annually')==true)
		{
			mandatoryControl('table117_ReqstdSemiAnualLoanAmnt,table117_SemiAnnuallyTenor,table117_FirstInstDateSemiAnnly,table117_LastSemiAnnuallyInstalmentDate,table117_InstallmentAmtMonthly');
		}
		else
		{
			nonMandatoryControl('table117_ReqstdSemiAnualLoanAmnt,table117_SemiAncnuallyTenor,table117_FirstInstDateSemiAnnly,table117_LastSemiAnnuallyInstalmentDate,table117_InstallmentAmtMonthly,table117_SemiAnnuallyTenor');
			clearValues('table117_ReqstdSemiAnualLoanAmnt,table117_SemiAnnuallyTenor,table117_FirstInstDateSemiAnnly,table117_LastSemiAnnuallyInstalmentDate,table117_InstallmentAmtMonthly');
			calculateTotalrequestedAmount();
			calculateTotalInstlmtAmntBulk();
		}
	}
	if(pControlName=='table117_Yearly')
	{
		if(getValue('table117_Yearly')==true)
		{
			mandatoryControl('table117_RequestedLoanAmount2,table117_Yearly_Tenor,table117_FirstInstallmentDate2,table117_LastYearlyInstalmentDate,table117_InstallmentAmtBulk');
		}
		else
		{
			nonMandatoryControl('table117_RequestedLoanAmount2,table117_Yearly_Tenor,table117_FirstInstallmentDate2,table117_LastYearlyInstalmentDate,table117_InstallmentAmtBulk');
			clearValues('table117_RequestedLoanAmount2,table117_Yearly_Tenor,table117_FirstInstallmentDate2,table117_LastYearlyInstalmentDate,table117_InstallmentAmtBulk');
			calculateTotalrequestedAmount();
			calculateTotalInstlmtAmntBulk();
		}
	}
	if(pControlName=='table117_LoanTenor2')
	{
		if(getValue('table117_Quarterly')==false)
		{
			showAlertDialog('Please Select Quarterly payment');
			clearValue('table117_LoanTenor2');
			return false;
		}
		calculateLastInstalmentDateBulk('table117_FirstInstDateQuatrly','table117_LastQuarterlyInstalmentDate','table117_LoanTenor2',3);
		//CalcAgeAtMaturityBulk('table117_LastQuarterlyInstalmentDate');
		calMaturity();
	}
	else if(pControlName=='table117_FirstInstDateQuatrly')
	{
		if(getValue('table117_Quarterly')==false)
		{
			showAlertDialog('Please Select Quarterly payment');
			clearValue('table117_FirstInstDateQuatrly');
			return false;
		}

		calculateLastInstalmentDateBulk('table117_FirstInstDateQuatrly','table117_LastQuarterlyInstalmentDate','table117_LoanTenor2',3);
		//CalcAgeAtMaturityBulk('table117_LastQuarterlyInstalmentDate');
		calMaturity();
	}
	
	if(pControlName=='table117_SemiAnnuallyTenor')
	{
		if(getValue('table117_Semi_Annually')==false)
		{
			showAlertDialog('Please Select Semi Annually Payment');
			clearValue('table117_SemiAnnuallyTenor');
			return false;
		}
		calculateLastInstalmentDateBulk('table117_FirstInstDateSemiAnnly','table117_LastSemiAnnuallyInstalmentDate','table117_SemiAnnuallyTenor',6);
		//CalcAgeAtMaturityBulk('table117_LastSemiAnnuallyInstalmentDate');
		calMaturity();
	}
	else if(pControlName=='table117_FirstInstDateSemiAnnly')
	{
		if(getValue('table117_Semi_Annually')==false)
		{
			showAlertDialog('Please Select Semi Annually Payment');
			clearValue('table117_FirstInstDateSemiAnnly');
			return false;
		}
		calculateLastInstalmentDateBulk('table117_FirstInstDateSemiAnnly','table117_LastSemiAnnuallyInstalmentDate','table117_SemiAnnuallyTenor',6);
		//CalcAgeAtMaturityBulk('table117_LastSemiAnnuallyInstalmentDate');
		calMaturity();
	}
	if(pControlName=='table117_Yearly_Tenor')
	{
		if(getValue('table117_Yearly')==false)
		{
			showAlertDialog('Please Select Yearly Payment');
			clearValue('table117_Yearly_Tenor');
			return false;
		}
		calculateLastInstalmentDateBulk('table117_FirstInstallmentDate2','table117_LastYearlyInstalmentDate','table117_Yearly_Tenor',12);
		//CalcAgeAtMaturityBulk('table117_LastYearlyInstalmentDate');
		calMaturity();
	}
	else if(pControlName=='table117_FirstInstallmentDate2')
	{
		if(getValue('table117_Yearly')==false)
		{
			showAlertDialog('Please Select Yearly Payment');
			clearValue('table117_FirstInstallmentDate2');
			return false;
		}
		calculateLastInstalmentDateBulk('table117_FirstInstallmentDate2','table117_LastYearlyInstalmentDate','table117_Yearly_Tenor',12);
		//CalcAgeAtMaturityBulk('table117_LastYearlyInstalmentDate');
		calMaturity();
	}
	else if(pControlName=='table117_RequestedLoanAmount1')
	{
		if(getValue('table117_Monthly')==false)
		{
			showAlertDialog('Please Select Monthly payment');
			clearValue('table117_RequestedLoanAmount1');
			return false;
		}
		calculateTotalrequestedAmount('table117_RequestedLoanAmount1');
	}
	else if(pControlName=='table117_RqstQuaterLoanAmnt')
	{
		if(getValue('table117_Quarterly')==false)
		{
			showAlertDialog('Please Select Quarterly payment');
			clearValue('table117_RqstQuaterLoanAmnt');
			return false;
		}
		calculateTotalrequestedAmount('table117_RqstQuaterLoanAmnt');
	}
	else if(pControlName=='table117_ReqstdSemiAnualLoanAmnt')
	{
		if(getValue('table117_Semi_Annually')==false)
		{
			showAlertDialog('Please Select Semi Annually payment');
			clearValue('table117_ReqstdSemiAnualLoanAmnt');
			return false;
		}
		calculateTotalrequestedAmount('table117_ReqstdSemiAnualLoanAmnt');
	}
	else if(pControlName=='table117_RequestedLoanAmount2')
	{
		if(getValue('table117_Yearly')==false)
		{
			showAlertDialog('Please Select Yearly payment');
			clearValue('table117_RequestedLoanAmount2');
			return false;
		}
		calculateTotalrequestedAmount('table117_RequestedLoanAmount2');
	}
	/********************************MultiLoan END*********************************************/
	
	if(pControlName=='table142_CoverageRation') //Ajay 15Dec
	{
		var nationality=getValue('table142_Nationality');
		var coverageRatio=getValue('table142_CoverageRation');
		var balance=getValue('table142_Balance');
		
		if(isNaN(coverageRatio))
		{
			setControlValue("table142_CoverageRation","");
			showAlertDialog('Not a Numeric Coverage Ratio');
			return;
		}
		else
		{
			if(nationality!='EG')
			{
				if(coverageRatio>90)
				{
					setControlValue("table142_CoverageRation","");
					showAlertDialog('Coverage Ratio can not be more than 90%');
					return;
				}
			}
			else
			{
				if(coverageRatio>95)
				{
					setControlValue("table142_CoverageRation","");
					showAlertDialog('Coverage Ratio can not be more than 95%');
					return;
				}
			}
		}
		
		
		var coveringAmount=(balance*coverageRatio)/100;
		
		if(isNaN(coveringAmount))
		{
			coveringAmount="";
		}
		else
		{
			coveringAmount=coveringAmount.toFixed(2);
			setControlValue("table142_CoveringAmount",coveringAmount);
		}
	}
	
	if(pControlName=='table117_RequestedLoanCCAmnt')
	{
		CCLimitByNetSalary();
	}
	
	if(pControlName=='table117_CredtCardExpiryDate')
	{
		calMaturity();
	}
	
	
}


function ValidateDecision() {
    /*
  setControlValue("Decision", getValue("Decision_drop"));

    if (!securedCardsValidations()) {
      setControlValue("Decision_drop", "");
    }*/

}


function selectRowHookLOS(tableId, selectedRowsArray, isAllRowsSelected) {
    switch (tableId) {

        case "table1": {
            if (selectedRowsArray.length >= 1) {
                break;
            }
        }

        case "table4": {

            break;
        }


    }
}

function executeAddRowPostHookLOS(tableId) {
    switch (tableId) {
        case "table105": {
            //setControlValue("GrossSalary", getColumnSum("table105", "Total Income Amount (Yearly)"));
			//setControlValue("NetSalary", (parseFloat(toFixed(getValue("GrossSalary"))) - getColumnSum("table106", "Deduction Amount"))/12);
			setControlValue("NetSalary", (parseFloat(toFixed(getColumnSum("table105", "Total Income Amount (Yearly)"))) - getColumnSum("table106", "Deduction Amount"))/12);
			CCLimitByNetSalary();
            saveWorkItem();
            break;
        }

        case "table106": {
            setControlValue("NetSalary", (parseFloat(toFixed(getColumnSum("table105", "Total Income Amount (Yearly)"))) - getColumnSum("table106", "Deduction Amount"))/12);
			CCLimitByNetSalary();
            saveWorkItem();
            break;
        }
		case "table129": {
            CalculateTotalCoverRatio();
            saveWorkItem();
            break;
        }
		case "table112": {
            CalculateTotalLiability();
            saveWorkItem();
            break;
        }
		case "table120": {
            CalculateTotalLiability();
            saveWorkItem();
            break;
        }
		case "table117": {
			if((getValue('table117_YearlyInstallment')=='' || getValue('table117_RequestedLoanAmount1')=='' || getValue('table117_LoanTenor1v')=='' || getValue('table117_FirstInstallmentDate')=='') && getValue('Product_Type')!='CC')
			{
				showAlertDialog("Please Enter All Monthly Payment Details");
			}
            CalculateTotalLiability();
            saveWorkItem();
            break;
        }
		case "table133": {
			
            break;
        }
        default:
            break;
    }
}

function modifyRowPostHookLOS(tableId) {
    switch (tableId) {
        case "table105": {
            //setControlValue("GrossSalary", getColumnSum("table105", "Total Income Amount (Yearly)"));
           setControlValue("NetSalary", (parseFloat(toFixed(getColumnSum("table105", "Total Income Amount (Yearly)"))) - getColumnSum("table106", "Deduction Amount"))/12);
		   CCLimitByNetSalary();
            saveWorkItem();
            break;
        }

        case "table106": {
           setControlValue("NetSalary", (parseFloat(toFixed(getColumnSum("table105", "Total Income Amount (Yearly)"))) - getColumnSum("table106", "Deduction Amount"))/12);
		   CCLimitByNetSalary();
            saveWorkItem();
            break;
        }
		case "table129": {
            CalculateTotalCoverRatio();
            saveWorkItem();
            break;
        }
		case "table112": {
            CalculateTotalLiability();
            saveWorkItem();
            break;
        }
		case "table120": {
            CalculateTotalLiability();
            saveWorkItem();
            break;
        }
		case "table117": {
			
			if((getValue('table117_YearlyInstallment')=='' || getValue('table117_RequestedLoanAmount1')=='' || getValue('table117_LoanTenor1v')=='' || getValue('table117_FirstInstallmentDate')=='') && getValue('Product_Type')!='CC')
			{
				showAlertDialog("Please Enter All Monthly Payment Details");
			}
            CalculateTotalLiability();
            saveWorkItem();
            break;
        }
        default:
            break;
    }

}

function deleteRowPostHookLOS(tableId) {
    switch (tableId) {
        case "table105": {
            //setControlValue("GrossSalary", getColumnSum("table105", "Total Income Amount (Yearly)"));
           setControlValue("NetSalary", (parseFloat(toFixed(getColumnSum("table105", "Total Income Amount (Yearly)"))) - getColumnSum("table106", "Deduction Amount"))/12);
		   CCLimitByNetSalary();
            saveWorkItem();
            break;
        }

        case "table106": {
           setControlValue("NetSalary", (parseFloat(toFixed(getColumnSum("table105", "Total Income Amount (Yearly)"))) - getColumnSum("table106", "Deduction Amount"))/12);
		   CCLimitByNetSalary();
            saveWorkItem();
            break;
        }
		case "table129": {
            CalculateTotalCoverRatio();
            saveWorkItem();
            break;
        }
		case "table112": {
            CalculateTotalLiability();
            saveWorkItem();
            break;
        }
		case "table120": {
            CalculateTotalLiability();
            saveWorkItem();
            break;
        }
		case "table117": {
            CalculateTotalLiability();
            saveWorkItem();
            break;
        }
        default:
            break;
    }

}



function tableOperationLOS(tableId, operationType) {
    console.log('[tableOperationLOS]');
    if (operationType == "AddRow") {
        if (tableId == "table4") {

        }
    } else if (operationType == "DeleteRow") {
        if (tableId == "table105") {
			
        
        } else if (tableId == "table106") {
			
           
        } else if (tableId == "table4") {

        }
    } else if (operationType == "CopyRow") {

    }
}


/******************************************** CUSTOM FUNCTIONS *******************************/

function validationsBasedOnRepaymentSchedule() {
    biasedUnlock("Bulk_Payment", "Repayment_Schedule", "Normal (With Bulk Payment)");
    biasedMandatory("Bulk_Payment", "Repayment_Schedule", "Normal (With Bulk Payment)");
}

function validationsBasedOnProductType() {
    biasedHide("Card_Delivery_Method", "Product_Type", "CC");
	biasedShow('PaymentMethodCC',"Product_Type", "CC");
	
    var currValue = getValue("Request_Type");
    var combo_count = getItemCountInCombo('Request_Type');
    for (var i = combo_count - 1; i > 0; i--) {
        removeItemFromCombo("Request_Type", i);
    }
    if (getValue("Product_Type") == "CC") {
        addItemInCombo('Request_Type', 'Limit Increase for Covered Card', 'Limit Increase for Covered Card', '', '');
        addItemInCombo('Request_Type', 'New Card', 'New Card', '', '');
		addItemInCombo('Request_Type', 'Buy Out Covered Card', 'Buy Out Covered Card', '', '');
		showControl('table140');
		try{
			removeThisFromCombo("Repayment_Schedule", "Normal (With Bulk Payment)");
		}
		catch(e)
		{
			console.log(e);
		}
		nonMandatoryControl('Repayment_Schedule,Bulk_Payment');
    } else if(getValue("Product_Type") == "AL"){
        addItemInCombo('Request_Type', 'New Grant', 'New Grant', '', '');
        addItemInCombo('Request_Type', 'Top Up', 'Top Up', '', '');
		addItemInCombo('Repayment_Schedule','Normal (With Bulk Payment)', 'Normal (With Bulk Payment)', '', '');
		mandatoryControl('Repayment_Schedule');
		hideControl('table140');
       
    }
	else
	{
        addItemInCombo('Request_Type', 'New Grant', 'New Grant', '', '');
        addItemInCombo('Request_Type', 'Buy Out', 'Buy Out', '', '');
        addItemInCombo('Request_Type', 'Top Up', 'Top Up', '', '');
        addItemInCombo('Request_Type', 'Top up + Buy out', 'Top up + Buy out', '', '');
		addItemInCombo('Request_Type', 'Buy Out - Cash Back', 'Buy Out - Cash Back', '', '');
		addItemInCombo('Request_Type', 'New Finance - Cash Back', 'New Finance - Cash Back', '', '');
		addItemInCombo('Repayment_Schedule', 'Normal (With Bulk Payment)', 'Normal (With Bulk Payment)', '', '');
		mandatoryControl('Repayment_Schedule');
		hideControl('table140');
    }
	
    setControlValue("Request_Type", currValue);
    /*biasedMandatory("Req_Loan_Amt", "Product_Type", "CC");
	biasedMandatory("Req_Loan_Amt", "Product_Type", "AL");
	biasedMandatory("Req_Loan_Amt", "Product_Type", "PL");*/
	biasedMandatory("Loan_Tenor", "Product_Type", "AL");
	biasedMandatory("Loan_Tenor", "Product_Type", "PL");
	
    biasedHide("Loan_Tenor", "Product_Type", "CC");
}

function validationsBasedOnSubProductType() {
	
	//Ajay 15Dec Start
	/*
    biasedShow("table129", "Sub_Product_Type", "Secured");
	biasedShow("Total_CoverageRation,Total_Chosen_Coll_Value", "Sub_Product_Type", "Secured");
	biasedShow("button178", "Sub_Product_Type", "Secured");
	biasedShow("button192", "Sub_Product_Type", "Secured");
	biasedShow("Collateral_CIF", "Sub_Product_Type", "Secured");
	*/
	biasedShow("Collateral_CIF,table129,Total_CoverageRation,Total_Chosen_Coll_Value,cd,table142,button217,CollResNo", "Sub_Product_Type", "Secured"); //Ajay 28Dec
	if(ActivityName == "Introduction")
	{
		biasedShow("button208,button178,button192,button217", "Sub_Product_Type", "Secured");
	}
	else
	{
		hideControl('button208,button178,button192,button217'); 
	}
	
	if(isRCRWS())
	{
		showControl("button217");
	}
	//Ajay 15Dec end
	
	biasedNonMandatory("Applicant_Sector,Applicant_Employment_Type,Applicant_Employer_Name,Applicant_Length_of_Service,Applicant_Career_Level,Applicant_Employer_Grade,Company_Segment", "Sub_Product_Type", "Secured");
	
	
	biasedNonMandatory("Applicant_Joining_Date,Applicant_Length_of_Service", "Sub_Product_Type", "Secured");
	biasedNonMandatory("Co_Applicant_Joining_Date,Co_Applicant_Length_of_Service", "Sub_Product_Type", "Secured");
	biasedNonMandatory("NetSalary", "Sub_Product_Type", "Secured");
	biasedNonMandatory("Applicant_Employment_Type", "Sub_Product_Type", "Secured");
	biasedNonMandatory("Applicant_Career_Level", "Sub_Product_Type", "Secured");
	
	if (getValue("Sub_Product_Type") == "Secured") {
		 addItemInCombo('Applicant_Employment_Type', 'Unemployed', 'Unemployed', '', '');
		 addItemInCombo('Co_Applicant_Employment_Type', 'Unemployed', 'Unemployed', '', '');
	 }
    else if (getValue("Sub_Product_Type") == "Un-secured") {
		
		mandatoryControl('Applicant_Joining_Date,Applicant_Length_of_Service');
		mandatoryControl('Co_Applicant_Joining_Date,Co_Applicant_Length_of_Service');
		removeThisFromCombo("Applicant_Employment_Type", "Unemployed");
		removeThisFromCombo("Co_Applicant_Employment_Type", "Unemployed");
        clearTable("table129", true);
    }
	
	//Ajay 29Dec	
	if (getValue("Sub_Product_Type") == "Secured") 
	{
		setControlValue("Repayment_Schedule","Normal (Without Bulk Payment)");
	}		
	//Ajay 29Dec
	
	
}

function differenceBetweenDateAndNow(d) {
    var arr = d.split("/");
    var year = arr[0];
    var month = arr[1];
    var day = arr[2];
    var inputDate = new Date(Date.parse(month + "/" + day + "/" + year));
    var ageDifMs = Date.now() - inputDate.getTime();
    var ageDate = new Date(ageDifMs);
    return Math.abs(ageDate.getUTCFullYear() - 1970);
}

function monthDiff(d1) {
	var arr = d1.split("/");
    var year = arr[0];
    var month = arr[1];
    var day = arr[2];
    var inputDate = new Date(Date.parse(month + "/" + day + "/" + year));
	var d2_month= new Date().getMonth();
	d2_month= parseInt(d2_month) + parseInt("1");
	var d2_year=new Date().getFullYear();
	var d2_date=new Date().getDate();
    var months;
    months = (d2_year - year) * 12;
    months -= month;
    months += d2_month;
    return months <= 0 ? 0 : months;
}

function setOutstandingAmount() {
    setControlValue("", getValue("") - getValue(""));
}

function validationsBasedOnIncomeType() {
    biasedUnlock("table105_AdditionalIncome,table105_Month1,table105_Month2,table105_Month3,table105_Month4,table105_Month5,table105_Month6,table105_Month7,table105_Month8,table105_Month9,table105_Month10,table105_Month11,table105_Month12", "table105_IncomeType", "Yearly Net Salary");
    biasedMandatory("table105_Month1,table105_Month2,table105_Month3,table105_Month4,table105_Month5,table105_Month6,table105_Month7,table105_Month8,table105_Month9,table105_Month10,table105_Month11,table105_Month12", "table105_IncomeType", "Yearly Net Salary");
    biasedMandatoryAndUnlock("table105_Bonus", "table105_IncomeType", "Yearly Bonus");
    biasedMandatoryAndUnlock("table105_AUBE_CD_TDS_Profit", "table105_IncomeType", "Yearly AUBE CDs/TDS profit");
    biasedMandatoryAndUnlock("table105_Allowances", "table105_IncomeType", "Yearly Allowances");
    biasedMandatoryAndUnlock("table105_Incentive", "table105_IncomeType", "Yearly Incentive");
    biasedMandatoryAndUnlock("table105_ProfitShare", "table105_IncomeType", "Yearly Profit share");
    biasedMandatoryAndUnlock("table105_ProfitShareInAdvance", "table105_IncomeType", "Yearly Profit share in advance");
    biasedMandatoryAndUnlock("table105_Pension", "table105_IncomeType", "Yearly Pension");
    biasedMandatoryAndUnlock("table105_Rent", "table105_IncomeType", "Yearly Rent");
    /*
    if (getValue("table105_IncomeType") != "Net Salary") {
    	clearValues("table105_AdditionalIncome,table105_Month1,table105_Month2,table105_Month3,table105_Month4,table105_Month5,table105_Month6,table105_Month7,table105_Month8,table105_Month9,table105_Month10,table105_Month11,table105_Month12,table105_Total_IncomeAmt_Yearly,table105_Total_IncomeAmt_Monthly");
    }
    */

}

function validationsBasedOnAdditionalIncome() {
    biasedLock("table105_SourceOfAdditionalIncome", "table105_AdditionalIncome", "");
    biasedNonMandatory("table105_SourceOfAdditionalIncome", "table105_AdditionalIncome", "");
}

function calculationsOnAdditionalIncome() {
    var netSalary = 0;
    for (var i = 1; i < 13; i++) {
        netSalary += parseFloat(toFixed(getValue("table105_Month" + i) != "" ? getValue("table105_Month" + i) : 0, 2));
    }
    setControlValue("table105_Total_IncomeAmt_Yearly", parseFloat(getValue("table105_AdditionalIncome") != "" ? getValue("table105_AdditionalIncome") : 0) + parseFloat(toFixed(netSalary, 2)));
}

function populateIncomeOnChangeOfMonthIncome(pControlName) {
    if ("table105_Month1,table105_Month2,table105_Month3,table105_Month4,table105_Month5,table105_Month6,table105_Month7,table105_Month8,table105_Month9,table105_Month10,table105_Month11,table105_Month12".split(",").indexOf(pControlName) >= 0) {
        var netSalary = 0;
        for (var i = 1; i < 13; i++) {
            netSalary += parseFloat(toFixed(getValue("table105_Month" + i) != "" ? getValue("table105_Month" + i) : 0, 2));
        }
        var monthlyIncome = netSalary / 12;
        var yearlyIncome = monthlyIncome * 12 + parseFloat(getValue("table105_AdditionalIncome") != "" ? getValue("table105_AdditionalIncome") : 0);
        setControlValue("table105_Total_IncomeAmt_Monthly", toFixed(monthlyIncome, 2));
        setControlValue("table105_Total_IncomeAmt_Yearly", toFixed(yearlyIncome, 2));
    }
}

function validationsBasedOnSettlememtFromAppFacility() {
    biasedUnlock("table106_Settlemant_Amount,table106_Amount_Outstanding", "table106_To_Be_Settle_From_Applied_Facility", "Yes");
	
    biasedMandatory("table106_Settlemant_Amount,table106_Amount_Outstanding", "table106_To_Be_Settle_From_Applied_Facility", "Yes");
}

function validationsBasedOnSettlememtAmount() {
    setControlValue("table106_Amount_Outstanding", parseFloat(getValue("table106_Deduction_Amount") != "" ? getValue("table106_Deduction_Amount") : 0) - parseFloat(getValue("table106_Settlemant_Amount") != "" ? getValue("table106_Settlemant_Amount") : 0));
}

function populateProgramType() 
{
    if (getValue("Product_Type") == "CC" || getValue("Product_Type") == "Covered Card") 
	{
        addItemsToComboFromMaster("table105_ProgramType", "NG_ELOS_MAST_PROGRAM_TYPE", "PROGRAM_TYPE", "Product_Type", "CC");
		//addItemsToComboFromMaster("table117_Program_Type", "NG_ELOS_MAST_PROGRAM_TYPE", "PROGRAM_TYPE", "Product_Type", "CC");
    } 
	else if (getValue("Product_Type") == "AL" || getValue("Product_Type") == "Auto Finance") 
	{
        addItemsToComboFromMaster("table105_ProgramType", "NG_ELOS_MAST_PROGRAM_TYPE", "PROGRAM_TYPE", "Product_Type", "AL");
		//addItemsToComboFromMaster("table117_Program_Type", "NG_ELOS_MAST_PROGRAM_TYPE", "PROGRAM_TYPE", "Product_Type", "AL");
    }
    else if (getValue("Product_Type") == "PL" || getValue("Product_Type") == "Personal Finance") 
	{
        addItemsToComboFromMaster("table105_ProgramType", "NG_ELOS_MAST_PROGRAM_TYPE", "PROGRAM_TYPE", "Product_Type,PL");
		//addItemsToComboFromMaster("table117_Program_Type", "NG_ELOS_MAST_PROGRAM_TYPE", "PROGRAM_TYPE", "Product_Type,PL");
    }
	else if (getValue("Product_Type") == "CL" || getValue("Product_Type") == "Cash Line Facility") 
	{
        addItemsToComboFromMaster("table105_ProgramType", "NG_ELOS_MAST_PROGRAM_TYPE", "PROGRAM_TYPE", "Product_Type,PL");
		//addItemsToComboFromMaster("table117_Program_Type", "NG_ELOS_MAST_PROGRAM_TYPE", "PROGRAM_TYPE", "Product_Type,PL");
    }
	else if (getValue("Product_Type") == "MR" || getValue("Product_Type") == "Murabaha Finance") 
	{
        addItemsToComboFromMaster("table105_ProgramType", "NG_ELOS_MAST_PROGRAM_TYPE", "PROGRAM_TYPE", "Product_Type,PL");
		//addItemsToComboFromMaster("table117_Program_Type", "NG_ELOS_MAST_PROGRAM_TYPE", "PROGRAM_TYPE", "Product_Type,PL");
    }
	
	var count=getGridRowCount("table105");
	if(count>=1)
	{
		if (getValueFromTableCell("table105",0,0) == "Applicant" && getValue("PROGRAM_TYPE") != "") 
		{
			setControlValue('table105_ProgramType',getValue("PROGRAM_TYPE"));
		
		}
		//not blank
		//setValue
	}
}

function PopulateProductProgram()
{
	if (getValue("Product_Type") == "CC" || getValue("Product_Type") == "Covered Card") 
	{
        //addItemsToComboFromMaster("table105_ProgramType", "NG_ELOS_MAST_PROGRAM_TYPE", "PROGRAM_TYPE", "Product_Type", "CC");
		addItemsToComboFromMaster("table117_Program_Type", "NG_ELOS_MAST_PROGRAM_TYPE", "PROGRAM_TYPE", "Product_Type", "CC");
		setControlValue('table117_Program_Type',getValue("PROGRAM_TYPE"));
    } 
	else if (getValue("Product_Type") == "AL" || getValue("Product_Type") == "Auto Finance") 
	{
        //addItemsToComboFromMaster("table105_ProgramType", "NG_ELOS_MAST_PROGRAM_TYPE", "PROGRAM_TYPE", "Product_Type", "AL");
		addItemsToComboFromMaster("table117_Program_Type", "NG_ELOS_MAST_PROGRAM_TYPE", "PROGRAM_TYPE", "Product_Type", "AL");
		setControlValue('table117_Program_Type',getValue("PROGRAM_TYPE"));
    }
    else if (getValue("Product_Type") == "PL" || getValue("Product_Type") == "Personal Finance") 
	{
       // addItemsToComboFromMaster("table105_ProgramType", "NG_ELOS_MAST_PROGRAM_TYPE", "PROGRAM_TYPE", "Product_Type,PL");
		addItemsToComboFromMaster("table117_Program_Type", "NG_ELOS_MAST_PROGRAM_TYPE", "PROGRAM_TYPE", "Product_Type,PL");
		setControlValue('table117_Program_Type',getValue("PROGRAM_TYPE"));
    }
	else if (getValue("Product_Type") == "CL" || getValue("Product_Type") == "Cash Line Facility") 
	{
       // addItemsToComboFromMaster("table105_ProgramType", "NG_ELOS_MAST_PROGRAM_TYPE", "PROGRAM_TYPE", "Product_Type,PL");
		addItemsToComboFromMaster("table117_Program_Type", "NG_ELOS_MAST_PROGRAM_TYPE", "PROGRAM_TYPE", "Product_Type,CL");
		setControlValue('table117_Program_Type',getValue("PROGRAM_TYPE"));
    }
	else if (getValue("Product_Type") == "MR" || getValue("Product_Type") == "Murabaha Finance") 
	{
       // addItemsToComboFromMaster("table105_ProgramType", "NG_ELOS_MAST_PROGRAM_TYPE", "PROGRAM_TYPE", "Product_Type,PL");
		addItemsToComboFromMaster("table117_Program_Type", "NG_ELOS_MAST_PROGRAM_TYPE", "PROGRAM_TYPE", "Product_Type,PL");
		setControlValue('table117_Program_Type',getValue("PROGRAM_TYPE"));
    }
}
function populateDataFrombasicInfo(customerType) {
    if (customerType == "") {
        clearValue("table107_Age,table107_DateOfBirth,table107_Gender,table107_PassportNo,table107_EmployerType,table107_CustomerNationalID,table107_CustomerName");
    } else {
        if (customerType == "Co-Applicant") {
            customerType = "Co_Applicant";
        }
		else if(customerType == "Guarantor")
		{
			customerType="Guarantor";
		}
        var toThese = "table107_Age,table107_DateOfBirth,table107_Gender,table107_PassportNo,table107_EmployerType,table107_CustomerNationalID,table107_CustomerName,table107_Nationality";
        var fromThese = "customerType_Age,customerType_DOB,customerType_Gender,customerType_Passport_Number,customerType_Employment_Type,customerType_National_ID,customerType_Name,Applicant_Nationality"; // Applicant_Name
        fromThese = fromThese.split("customerType").join(customerType);
        setValuesFromAnotherFeilds(toThese, fromThese);
		var nationality=customerType+"_Nationality"
		if(getValue(nationality)=="EG")
			{
				nonMandatoryControl('table107_PassportNo,table107_DateOfIssuePassport,table107_DateOfExpiryPassport,table107_PlaceOfIssuePassport');
			}
			else
			{
				mandatoryControl('table107_PassportNo,table107_DateOfIssuePassport,table107_DateOfExpiryPassport,table107_PlaceOfIssuePassport');
			}
    }
}


function populateDataFrombasicInfoToEmpTable(customerType) {
    if (customerType == "") {
        clearValues("table137_JoiningDate,table137_EmployerCategory,table137_LengthOfService,table137_TypeOfEmployment"); // table137_EmployerName
    } else {
        if (customerType == "Co-Applicant") {
            customerType = "Co_Applicant";
        }
		else if(customerType == "Guarantor")
		{
			customerType="Guarantor";
		}
        var toThese = "table137_JoiningDate,table137_EmployerCategory,table137_LengthOfService,table137_TypeOfEmployment,table137_EmploymentSector";
        var fromThese = "Applicant_Joining_Date,customerType_Employer_Grade,Applicant_Length_of_Service,customerType_Employment_Type,Applicant_Sector"; // Applicant_Name
        fromThese = fromThese.split("customerType").join(customerType);
        setValuesFromAnotherFeilds(toThese, fromThese);
    }
	/*if(getValue('Customer_Type')=="Existing")
				{
					setControlValue('table107_DoyoudealwithAUBEgyptSAE','Yes');
					mandatoryControl('table107_AccountNumberatAUBEgyptSAE,table107_AccountType');
				}
				else if(getValue('Customer_Type')=="New To Bank")
				{
					setControlValue('table107_DoyoudealwithAUBEgyptSAE','No')
					nonMandatoryControl('table107_AccountNumberatAUBEgyptSAE,table107_AccountType');
				}*/
}

function populateProductDetailsFromBasicInfo() {
    setValuesFromAnotherFeilds("table117_Typeofproduct,table117_SubproductTYpe", "Product_Type,Sub_Product_Type");
}

function onClickTabLOS(tabId, sheetindex) {
    switch (sheetindex) {
        case 1: {
            try {
                executeServerEvent("tab1", "tabclick", "", true);
                if (getValue("Application_Type") != "Joint") {
                    removeThisFromCombo("CUSTOMER_TYPE_BG_CHECK", "Co-Applicant");
                } else {
                    if (!isThisExistInCombo("CUSTOMER_TYPE_BG_CHECK", "Co-Applicant")) {
                        addItemInCombo('CUSTOMER_TYPE_BG_CHECK', 'Co-Applicant', 'Co-Applicant', '', '');
                    }
                }
            } catch (e) {
                console.log(e);
            }
            break;
        }
        case 2: {
		
           // biasedHide(disbursementDetailsSection, "Product_Type", "CC");
            break;
        }

        case 3: {
            if (ActivityName == "Branch_Manager" || ActivityName == "PB_Supervisor" || ActivityName == "Checker_TL_DSU" || ActivityName == "Authorizer_DSU" || ActivityName == "Legal_Dept" || ActivityName == "Re_Initiator" || ActivityName == "Introduction") 
			{
                hideControl("Marginal_Interest,Legal_Action,Negative_List,Protest_and_Bankruptcy,CPV_Feedback");
                nonMandatoryControl("Marginal_Interest,Legal_Action,Negative_List,Protest_and_Bankruptcy,CPV_Feedback");
                hideControl(conditionSection);
               // disableControl(collateralChecklistSection);
					// clearTable('table130')
            }
			
			if (ActivityName == "Branch_Manager" || ActivityName == "PB_Supervisor" || ActivityName == "Checker_TL_DSU" || ActivityName == "Authorizer_DSU" || ActivityName == "Legal_Dept" || ActivityName == "Re_Initiator" || ActivityName == "Introduction" || isRCRWS())
			{
				var productType=getValue('Product_Type');
				var programType=getValue("PROGRAM_TYPE");
				var alreadyLoaded = false;
				var count=getGridRowCount("table130");
				for (var i=0;i<count;i++)
				{
					var gProgramType = getValueFromTableCell("table130", i, 1);
					
					if(compareStringsIgnoreCase(gProgramType,programType))
					{
						alreadyLoaded = true;
						break;
					}
				}
				if(!alreadyLoaded)
				{
					executeServerEvent("collateralChecklist", "onClick", productType+"~"+programType, true);
					// showAlertDialog("Loading "+alreadyLoaded);
				}
				else				
				{
					// showAlertDialog("Not Loading");
				}
			}	
			break;
        }
		case 4:
		{
		if(ActivityName == "Introduction")
						{
						clearTable('documentVeificationTable');
						
						}
			executeServerEvent("tab1", "tabclick", "", true);
											
		}
			case 5:
		{
		
			if(activityName=="LPU_Maker")
			{
				if(getGridRowCount("table135")==0)
				{
				clearTable('table135');
				var accNO=getValue("CustomerLoanAccountNumber");
				var accountType=getValueFromTableCell("table107", 0, 36);
				var suffix='';
				if(getValueFromTableCell("table117", 0, 63)=="true")
				{
				var lpuGridJsonArrayM = [];
				var lpuGridJsonM = {};
				lpuGridJsonM["Account Number at AUB Egypt SAE"]=accNO;
				lpuGridJsonM["Account Type"]=accountType;
				//lpuGridJsonM["Suffix (NA for CC)"]=";
				lpuGridJsonM["Payment Type"]="Monthly";
				lpuGridJsonM["Loan Amount"]=getValueFromTableCell("table117", 0, 22);
				lpuGridJsonM["Installment Amount"]=getValueFromTableCell("table117", 0, 32);
				lpuGridJsonM["First Installment Date"]=getValueFromTableCell("table117", 0, 3);
				lpuGridJsonM["Last installment Date"]=getValueFromTableCell("table117", 0, 46);
				lpuGridJsonArrayM.push(lpuGridJsonM);
				addDataToGrid('table135', lpuGridJsonArrayM);
				}
				if(getValueFromTableCell("table117", 0, 64)=="true")
				{
				var lpuGridJsonArrayQ = [];
				var lpuGridJsonQ = {};
				lpuGridJsonQ["Account Number at AUB Egypt SAE"]=accNO;
				lpuGridJsonQ["Account Type"]=accountType;
				//lpuGridJsonQ["Suffix (NA for CC)"]=";
				lpuGridJsonQ["Payment Type"]="Quarterly";
				lpuGridJsonQ["Loan Amount"]=getValueFromTableCell("table117", 0, 52);
				lpuGridJsonQ["Installment Amount"]=getValueFromTableCell("table117", 0, 53);
				lpuGridJsonQ["First Installment Date"]=getValueFromTableCell("table117", 0, 55);
				lpuGridJsonQ["Last installment Date"]=getValueFromTableCell("table117", 0, 67);
				lpuGridJsonArrayQ.push(lpuGridJsonQ);
				addDataToGrid('table135', lpuGridJsonArrayQ);
				}
				if(getValueFromTableCell("table117", 0, 65)=="true")
				{
				var lpuGridJsonArrayS = [];
				var lpuGridJsonS = {};
				lpuGridJsonS["Account Number at AUB Egypt SAE"]=accNO;
				lpuGridJsonS["Account Type"]=accountType;
				//lpuGridJsonS["Suffix (NA for CC)"]=";
				lpuGridJsonS["Payment Type"]="Semi Annually";
				lpuGridJsonS["Loan Amount"]=getValueFromTableCell("table117", 0, 51);
				lpuGridJsonS["Installment Amount"]=getValueFromTableCell("table117", 0, 25);
				lpuGridJsonS["First Installment Date"]=getValueFromTableCell("table117", 0, 54);
				lpuGridJsonS["Last installment Date"]=getValueFromTableCell("table117", 0, 68);
				lpuGridJsonArrayS.push(lpuGridJsonS);
				addDataToGrid('table135', lpuGridJsonArrayS);
				}
				if(getValueFromTableCell("table117", 0, 66)=="true")
				{
				var lpuGridJsonArrayA = [];
				var lpuGridJsonA = {};
				lpuGridJsonA["Account Number at AUB Egypt SAE"]=accNO;
				lpuGridJsonA["Account Type"]=accountType;
				//lpuGridJsonA["Suffix (NA for CC)"]=";
				lpuGridJsonA["Payment Type"]="Yearly";
				lpuGridJsonA["Loan Amount"]=getValueFromTableCell("table117", 0, 23);
				lpuGridJsonA["Installment Amount"]=getValueFromTableCell("table117", 0, 24);
				lpuGridJsonA["First Installment Date"]=getValueFromTableCell("table117", 0, 29);
				lpuGridJsonA["Last installment Date"]=getValueFromTableCell("table117", 0, 69);
				lpuGridJsonArrayA.push(lpuGridJsonA);
				addDataToGrid('table135', lpuGridJsonArrayA);
				}
				}
			}
		}
        case 7: {  //new sheet added hence decision sheet index changed
            executeServerEvent("tab1", "tabclick", "", true);
			if(ActivityName=="Branch_Manager" && (getValue('Sub_Product_Type')=='Secured') && (getValue('Product_Type')=='PL' || getValue('Product_Type')=='AL' || getValue('Product_Type')=='MR'))
            {
				addItemInCombo('Decision', 'Send to Legal department', 'Send to Legal department', '', '');
            	removeThisFromCombo('Decision','Approve');
            }
			if(ActivityName=="Branch_Manager" && (getValue('Sub_Product_Type')=='Un-secured') && (getValue('Product_Type')=='PL' || getValue('Product_Type')=='AL' || getValue('Product_Type')=='MR'))
            {
            	addItemInCombo('Decision', 'Approve', 'Approve', '', '');
				removeThisFromCombo('Decision','Send to Legal department');
            }
			if(ActivityName=="Branch_Manager" && (getValue('Sub_Product_Type')=='Secured') && getValue('Product_Type')=='CC' )
            {
				addItemInCombo('Decision', 'Send to Legal department', 'Send to Legal department', '', '');
            	//removeThisFromCombo('Decision','Approve');
            }
			if(ActivityName=="Branch_Manager" && (getValue('Sub_Product_Type')=='Un-secured') && getValue('Product_Type')=='CC' )
            {
				removeThisFromCombo('Decision', 'Send to Legal department', 'Send to Legal department', '', '');
            	//removeThisFromCombo('Decision','Approve');
            }
            if (ActivityName == "Card_Center_To_Courier") {
                showControl("cardCentreSection");
            } else {
                hideControl("cardCentreSection");
            }
            break;
        }
        default:
            break;
    }
}

function CustomLoad(ControlObject) {
    console.log("CustomLoad...");
    var sActivityName = getWorkItemData("ActivityName");
    //setControlValue("Decision", "");

    if (compareStringsIgnoreCase(sActivityName, "Introduction")) {
        executeServerEvent("", "onLoad", "", true);


    }
}

function validationsBasedOnDealingWithEgypt(clearValuesToo) {
    if (clearValuesToo) {
        clearValues("table107_AccountNumberatAUBEgyptSAE");
    }
	 ValidationOnDoYouDealwithAUB();
    //biasedEnable("table107_AccountNumberatAUBEgyptSAE", "table107_DoyoudealwithAUBEgyptSAE", "Yes");
    biasedMandatory("table107_AccountNumberatAUBEgyptSAE", "table107_DoyoudealwithAUBEgyptSAE", "Yes");
}

function ValidationOnDoYouDealwithAUB()
{
	var DoYouDeal=getValue("table107_DoyoudealwithAUBEgyptSAE");
	if(compareStringsIgnoreCase(DoYouDeal,"Yes"))
	{
		setStyle("table107_AccountNumberatAUBEgyptSAE","disable","false");
	}
	else
	{
		setStyle("table107_AccountNumberatAUBEgyptSAE","disable","true");
	}
	
}
function validationsOfCreditCardInCustomerDetails(clearValuesToo) {
    if (clearValuesToo) {
        clearValues("table107_numberOfCards,table107_ifcreditcardprovidednumberandtype");
    }
    biasedEnable("table107_numberOfCards,table107_ifcreditcardprovidednumberandtype", "table107_HAVE_CREDIT_CARD", "Yes");
    biasedMandatory("table107_numberOfCards,table107_ifcreditcardprovidednumberandtype", "table107_HAVE_CREDIT_CARD", "Yes");
}

function validationsForProductDetailsTable() {
    biasedShow("table117_Auto Loan,table117_InsuranceAmount,table117_InsuranceCertificateNumber,table117_CarPlateNumber,table117_CategoryOfVehicle,table117_TypeOfVehicle,table117_DealerName,table117_MinimumDownPaymentRequired,table117_VehiclePrice,table117_EnginerNo,table117_NetPayable,table117_ChassisNo,table117_YearOfManufacture,table117_CountryOfManufacture,table117_Car_Brand,table117_Car_Model","table117_Typeofproduct", "AL");
    biasedShow("table117_Credit card,table117_CredtCardExpiryDate,table117_TypeOfCard, table117_SubCardType,table117_Embosing_Name,table117_CCLimitByNetSalary,,table117_RequestedLoanCCAmnt", "table117_Typeofproduct", "CC");
	biasedHide("table117_SubCardType","CL");
	
	biasedHide("table117_RqstQuaterLoanAmnt,table117_ReqstdSemiAnualLoanAmnt,table117_RequestedLoanAmount2,table117_InstallmentAmtBulk,table117_YearlyInstallment,table117_InstallmentAmtMonthly,table117_InstalmentAmntQtrly,table117_TotalInstallmentAmount,Loan_Calculator,table117_LoanTenor1,table117_LoanTenor2,table117_InterestFrequency1,table117_InterestFrequency2,table117_FirstInstallmentDate,table117_FirstInstDateQuatrly,table117_FirstInstDateSemiAnnly,table117_FirstInstallmentDate2,table117_ValueDate,table117_SpecialBaseRate,table117_EarlyPaymentFee,table117_AdminFee,table117_lastInstalmentDate","table117_Typeofproduct", "CC");
	//table117_ClubName
	/*biasedMandatory("table117_Car_Model,table117_YearOfManufacture,table117_CountryOfManufacture,table117_ChassisNo,table117_EnginerNo,table117_VehiclePrice,table117_DealerName,table117_TypeOfVehicle,table117_CarPlateNumber,table117_InsuranceCertificateNumber,table117_InsuranceAmount", "Product_Type", "AL");*/
	
	biasedMandatory("table117_Car_Model,table117_VehiclePrice,table117_DealerName,table117_TypeOfVehicle", "Product_Type", "AL");
	biasedMandatory("table117_Car_Brand", "Product_Type", "AL");
	biasedMandatory("table117_CategoryOfVehicle", "Product_Type", "AL");
	if(getValue('Product_Type')=='CC')
	{
		nonMandatoryControl('table117_AdminFee');
		nonMandatoryControl('table117_PurposeOfFacility');
		mandatoryControl('table117_SubCardType');
		
		document.getElementById('table117_RequestedLoanAmount1_label').innerHTML='Requested Covered Card Limit'
		CCLimitByNetSalary();
	}
	else if(getValue('Product_Type')=='AL')
	{
		biasedHide("table117_SubCardType", "table117_Typeofproduct", "AL");
	
		mandatoryControl('table117_AdminFee');
		nonMandatoryControl('table117_PurposeOfFacility');
		document.getElementById('table117_RequestedLoanAmount1_label').innerHTML='Requested Finance Amount Monthly'
		
	}
	else if(getValue('Product_Type')=='PL')
	{
		biasedHide("table117_SubCardType", "table117_Typeofproduct", "PL");
	
		mandatoryControl('table117_AdminFee');
		mandatoryControl('table117_PurposeOfFacility');
		document.getElementById('table117_RequestedLoanAmount1_label').innerHTML='Requested Finance Amount Monthly'
	}
	
	else if(getValue('Product_Type')=='CL')
	{
		biasedHide("table117_SubCardType", "table117_Typeofproduct", "CL");
	}
	
	if (ActivityName.trim() == "RCR_PreScanner" || ActivityName.trim() == "RCR_CreditAnalyst" || ActivityName.trim() =="RCR_Credit_TL" || ActivityName.trim()=="RCRH"|| ActivityName.trim()=="Head_Of_Initiation"|| ActivityName.trim()=="CRO_CRMH")
	{		
		if(getValue('Product_Type')=='CC')
		{
			mandatoryControl("table117_SubCardType");
		}
		mandatoryControl("table117_FirstInstallmentDate,table117_TypeOfCard,table117_CredtCardExpiryDate,table117_AgeAtMaturity,table117_ExternalVerificationType,Marginal_Interest,Legal_Action,Negative_List,Protest_and_Bankruptcy,CPV_Feedback");//anuj
		if (getValue("Product_Type") == "CC") {
			nonMandatoryControl("table117_FirstInstallmentDate,table117_AgeAtMaturity");
		}
		enableControl('table117_Program_Type');
		enableControl('table117_CCLimitByNetSalary');
		unlockControl("table117_RequestedLoanAmount1");
		unlockControl("table117_RqstQuaterLoanAmnt");
		unlockControl("table117_ReqstdSemiAnualLoanAmnt");
		unlockControl("table117_RequestedLoanAmount2");
		unlockControl("table117_InstallmentAmtBulk");
		unlockControl("table117_YearlyInstallment");
		unlockControl("table117_InstallmentAmtMonthly");
		unlockControl("table117_InstalmentAmntQtrly");
		unlockControl("table117_FirstInstallmentDate");
		unlockControl("table117_FirstInstDateQuatrly");
		unlockControl("table117_FirstInstDateSemiAnnly");
		unlockControl("table117_FirstInstallmentDate2");
		
	}
	else
	{
		nonMandatoryControl("table117_FirstInstallmentDate,table117_CredtCardExpiryDate,table117_AgeAtMaturity,Marginal_Interest,Legal_Action,Negative_List,Protest_and_Bankruptcy,CPV_Feedback");//anuj
		disableControl('table117_Program_Type');
		disableControl('table117_CCLimitByNetSalary');
		lockControl("table117_RequestedLoanAmount1");
		lockControl("table117_RqstQuaterLoanAmnt");
		lockControl("table117_ReqstdSemiAnualLoanAmnt");
		lockControl("table117_RequestedLoanAmount2");
		lockControl("table117_InstallmentAmtBulk");
		lockControl("table117_YearlyInstallment");
		lockControl("table117_InstallmentAmtMonthly");
		lockControl("table117_InstalmentAmntQtrly");
		lockControl("table117_FirstInstallmentDate");
		lockControl("table117_FirstInstDateQuatrly");
		lockControl("table117_FirstInstDateSemiAnnly");
		lockControl("table117_FirstInstallmentDate2");
	}
	if (ActivityName.trim() == "Introduction" || ActivityName.trim() == "Re_Initiator")
	{
		unlockControl("table117_RequestedLoanAmount1");
		unlockControl("table117_RqstQuaterLoanAmnt");
		unlockControl("table117_ReqstdSemiAnualLoanAmnt");
		unlockControl("table117_RequestedLoanAmount2");
		unlockControl("table117_InstallmentAmtBulk");
		unlockControl("table117_YearlyInstallment");
		unlockControl("table117_InstallmentAmtMonthly");
		unlockControl("table117_InstalmentAmntQtrly");
		unlockControl("table117_FirstInstallmentDate");
		unlockControl("table117_FirstInstDateQuatrly");
		unlockControl("table117_FirstInstDateSemiAnnly");
		unlockControl("table117_FirstInstallmentDate2");
		
	}
	if(ActivityName.trim() == "RCR_CreditAnalyst")
	{
		enableControl('Loan_Calculator');
	}
	
	//Ajay 29Dec Start
	biasedHide("table117_Quarterly,table117_RqstQuaterLoanAmnt,table117_LoanTenor2,table117_FirstInstDateQuatrly,table117_LastQuarterlyInstalmentDate,Loan_Calculator_Quarterly,table117_InstalmentAmntQtrly,table117_Semi_Annually,table117_ReqstdSemiAnualLoanAmnt,table117_SemiAnnuallyTenor,table117_FirstInstDateSemiAnnly,table117_LastSemiAnnuallyInstalmentDate,Loan_Calculator_SemiAnnually,table117_InstallmentAmtMonthly,table117_Yearly,table117_RequestedLoanAmount2,table117_Yearly_Tenor,table117_FirstInstallmentDate2,table117_LastYearlyInstalmentDate,Loan_Calculator_Yearly,table117_InstallmentAmtBulk","Sub_Product_Type","Secured");
	
	if(getValue('Product_Type')=='CC')
	{
		hideControl("table117_Monthly,table117_Quarterly,table117_RqstQuaterLoanAmnt,table117_LoanTenor2,table117_FirstInstDateQuatrly,table117_LastQuarterlyInstalmentDate,Loan_Calculator_Quarterly,table117_InstalmentAmntQtrly,table117_Semi_Annually,table117_ReqstdSemiAnualLoanAmnt,table117_SemiAnnuallyTenor,table117_FirstInstDateSemiAnnly,table117_LastSemiAnnuallyInstalmentDate,Loan_Calculator_SemiAnnually,table117_InstallmentAmtMonthly,table117_Yearly,table117_RequestedLoanAmount2,table117_Yearly_Tenor,table117_FirstInstallmentDate2,table117_LastYearlyInstalmentDate,Loan_Calculator_Yearly,table117_InstallmentAmtBulk");	
	}
	
	if(compareStringsIgnoreCase(getValue("Sub_Product_Type"), "Secured") && (getValue("Product_Type") != "CC"))
	{
		mandatoryControl('table117_MarginRate');
	}
	else
	{
		nonMandatoryControl('table117_MarginRate');
	}
	
	var progType= getValue("PROGRAM_TYPE");
	
	if(progType=="CLUB MEMBERSHIP" || progType=="INCOME SURROGATE CLUB MEMBERSHIP PROGRAM" || progType=="INCOME SURROGATE CLUB MEMBERSHIP")
	{
		mandatoryControl('table117_ClubName');
	}
	else
	{
		nonMandatoryControl("table117_ClubName");
	}
	
	disableControl('table117_YearlyInstallment,table117_InstalmentAmntQtrly,table117_InstallmentAmtMonthly,table117_InstallmentAmtBulk,table117_TotalInstallmentAmount,table117_AgeAtMaturity,table117_lastInstalmentDate,table117_LastQuarterlyInstalmentDate,table117_LastSemiAnnuallyInstalmentDate,table117_LastYearlyInstalmentDate,table117_CCLimitByNetSalary');
	
	//Ajay 29Dec End
}


function validationsBasedOnTransferType() {
    if (ActivityName.trim() == "Fulfillment_Docs" && (getValue("table116_TransferType") != "Bank Draft Check" && getValue("table116_TransferType") != "")) {
        mandatoryControl("table116_AccountNumber,table116_BeneficiaryBankName,table116_ChequeNumber");
        unlockControl("table116_AccountNumber,table116_BeneficiaryBankName,table116_ChequeNumber");
    } else {
        nonMandatoryControl("table116_AccountNumber,table116_BeneficiaryBankName,table116_ChequeNumber");
        lockControl("table116_AccountNumber,table116_ChequeNumber");
    }

    if (ActivityName.trim() == "Fulfillment_Docs" && (getValue("table116_TransferType") != "Bank Draft Check" && getValue("table116_TransferType") != "") && getValue("Request_Type") == "Buy Out") {
        mandatoryControl("table116_BeneficiaryBankAddress");
        unlockControl("table116_BeneficiaryBankAddress");
    } else {
        nonMandatoryControl("table116_BeneficiaryBankAddress");
        lockControl("table116_BeneficiaryBankAddress");
    }

    if (ActivityName.trim() == "Fulfillment_Docs" && getValue("Request_Type") == "Buy Out") {
        mandatoryControl("table116_IBANNumber,table116_AccountNumber,table116_BeneficiaryBankName");
        unlockControl("table116_IBANNumber,table116_AccountNumber,table116_BeneficiaryBankName");
    } else {
        nonMandatoryControl("table116_IBANNumber");
		
    }

    biasedMandatory("table116_BeneficiaryAddress,table116_AccountNumber,table116_BeneficiaryBankName,table116_IBANNumber", "Request_Type", "Buy Out");
    if (ActivityName == "Fulfillment_Docs") {
        mandatoryControl("table116_AmountHold");
    } else {
        nonMandatoryControl("table116_AmountHold");
    }
}

function removeCoApplicantFromCustType(e) {
    try {
        if (getValue("Application_Type") != "Joint") {
            removeThisFromCombo(e, "Co-Applicant");
        }
    } catch (e) {
        console.log(e);
    }

}

function clearEnteriesOnChangeOfIncomeType() {
    clearValues("table105_ProfitShare,table105_AUBE_CD_TDS_Profit,table105_Bonus,table105_Incentive,table105_Rent,table105_Pension,table105_Allowances,table105_Month1,table105_Month2,table105_Month3,table105_Month4,table105_Month5,table105_Month6,table105_Month7,table105_Month8,table105_Month9,table105_Month10,table105_Month11,table105_Month12,table105_Total_IncomeAmt_Monthly");
}

function clearEnteriesOnChangeOfProductType() {
    clearValues("Sub_Product_Type,Bulk_Payment,Application_Type,Request_Type,Repayment_Schedule,Customer_Type,Req_Loan_Amt,Loan_Tenor,Card_Delivery_Method");
}


function onLoadSectionLOS(frameId, sEvent) {

    if (frameId == "CustomerDetails") {
        if (getValue('FetchApplicant_Status') != 'Y') {
            var customerGridJsonArray = [];
            var customerGridJson = {};
            customerGridJson["Customer Type "] = "Applicant";
            customerGridJson["Cutomer Name"] = getValue('Applicant_Name');
            customerGridJson["Passport No"] = getValue('Applicant_Passport_Number');
            customerGridJson["Gender"] = getValue('Applicant_Gender');
            customerGridJson["Date of Birth"] = getValue('Applicant_DOB'); //
            customerGridJson["Age"] == getValue('Applicant_Age');
            customerGridJsonArray.push(customerGridJson);
            addDataToGrid('table107', customerGridJsonArray);
        }
        if (getValue('FetchCoApplicant_Status') != 'Y' && getValue('Application_Type') == 'Joint') {
            var customerGridJsonArray = [];
            var customerGridJson = {};
            customerGridJson["Customer Type "] = "Co-Applicant";
            customerGridJson["Cutomer Name"] = getValue('Co_Applicant_Name');
            customerGridJson["Passport No"] = getValue('Co_Applicant_Passport_Number');
            customerGridJson["Gender"] = getValue('Co_Applicant_Gender');
            customerGridJson["Date of Birth"] = getValue('Co_Applicant_DOB'); //
            customerGridJson["Age"] == getValue('Co_Applicant_Age');
            customerGridJsonArray.push(customerGridJson);
            addDataToGrid('table107', customerGridJsonArray);
        }
        if (getValue('FetchGuarantor_Status') != 'Y' && getValue('Guarantor_Name') != '') {
            var customerGridJsonArray = [];
            var customerGridJson = {};
            customerGridJson["Customer Type "] = "Guarantor";
            customerGridJson["Cutomer Name"] = getValue('Guarantor_Name');
            customerGridJson["Passport No"] = getValue('Guarantor_Passport_Number');
            customerGridJson["Gender"] = getValue('Guarantor_Gender');
            customerGridJson["Date of Birth"] = getValue('Guarantor_DOB'); //
            customerGridJson["Age"] == getValue('Guarantor_Age');
            customerGridJsonArray.push(customerGridJson);
            addDataToGrid('table107', customerGridJsonArray);
        }
    }

    if (frameId == 'EmploymentDetails') {
        if (getValue('Applicant_Employer_Name') != '') {
            var employerGridJsonArray = [];
            var employerGridJson = {};
            employerGridJson["Customer Type "] = "Guarantor";
            employerGridJson["Employer Name"] = getValue('Applicant_Employer_Name');
            employerGridJsonArray.push(employerGridJson);
            addDataToGrid('table137', employerGridJsonArray);
        }
        // need to check for co-applicant and guarantor
    }


    if (frameId == 'ProductDetails') {
        var productGridJsonArray = [];
        var productGridJson = {};
        productGridJson["Type of product"] = "Guarantor";
        productGridJson["Employer Name"] = getValue('Applicant_Employer_Name');
        productGridJsonArray.push(productGridJson);
        addDataToGrid('table137', productGridJsonArray);
    }


}

function validationsBasedonDebitType() {

    if (getValue("table133_DebitType") == "Bank Account") {
        enableControl("table133_BankName,table133_BankIBANNumber,table133_BankAccNumber,table133_SetStandingPerc");
        //mandatoryControl("table133_BankAccNumber,table133_SetStandingPerc,table133_BankName,table133_BankIBANNumber");
		mandatoryControl("table133_BankAccNumber");
        if (getValue("table133_SetStandingPerc") == "Yes") {
            enableControl("table133_StandingInstructionPercent");
            mandatoryControl("table133_StandingInstructionPercent");
        } else {
            clearValues("table133_StandingInstructionPercent");
            disableControl("table133_StandingInstructionPercent");
            nonMandatoryControl("table133_StandingInstructionPercent");
        }
    } else {
        clearValues("table133_BankName,table133_BankIBANNumber,table133_BankAccNumber,table133_SetStandingPerc,table133_StandingInstructionPercent");
        disableControl("table133_BankName,table133_BankIBANNumber,table133_BankAccNumber,table133_SetStandingPerc,table133_StandingInstructionPercent");
        nonMandatoryControl("table133_BankName,table133_BankIBANNumber,table133_BankAccNumber,table133_SetStandingPerc,table133_StandingInstructionPercent");
    }
}

function checkValidationAndPopulateData(gridName,Action)
{
		if(gridName=="CustomerGrid")
		{
			
			if (getValue("Sub_Product_Type") == "Secured") 
			{
				addItemInCombo('table107_EmployerType', 'Unemployed', 'Unemployed', '', '');
			}
	 
			if(getValue('Applicant_Nationality')=="EG")
			{
				nonMandatoryControl('table107_PassportNo,table107_DateOfIssuePassport,table107_DateOfExpiryPassport,table107_PlaceOfIssuePassport');
			}
			else
			{
				mandatoryControl('table107_PassportNo,table107_DateOfIssuePassport,table107_DateOfExpiryPassport,table107_PlaceOfIssuePassport');
			}
			if(Action=="M")
			{
			if(getValue('table107_CustomerType')=='Applicant')
			{
				setControlValue('table107_Nationality',getValue('Applicant_Nationality'));
				if(getValue('Applicant_Nationality').length==14)
				{
					setControlValue('table107_Nationality',getValue('Applicant_National_ID'));
				}
				setControlValue('table107_CustomerName',getValue('Applicant_Name'));
				setControlValue('table107_DateOfBirth',getValue('Applicant_DOB'));
				setControlValue('table107_Age',getValue('Applicant_Age'));
				setControlValue('table107_Gender',getValue('Applicant_Gender'));
				setControlValue('table107_PassportNo',getValue('Applicant_Passport_Number'));
				setControlValue('table107_EmployerType',getValue('Applicant_Employment_Type'));
				//setControlValue('table107_CustomerName',getValue('Applicant_Employer_Name'));
				//setControlValue('table107_CustomerName',getValue('Applicant_Joining_Date'));
				//setControlValue('table107_CustomerName',getValue('Applicant_Length_of_Service'));
				//setControlValue('table107_CustomerName',getValue('Applicant_Career_Level'));
				if(getValue('Customer_Type')=="Existing")
				{
					setControlValue('table107_DoyoudealwithAUBEgyptSAE','Yes');
					mandatoryControl('table107_AccountNumberatAUBEgyptSAE,table107_AccountType');
					setControlValue('table107_CustomerNationalID',getValue('Applicant_National_ID')); //For Case 437 Bug
				}
				else if(getValue('Customer_Type')=="New To Bank")
				{
					setControlValue('table107_DoyoudealwithAUBEgyptSAE','No')
					nonMandatoryControl('table107_AccountNumberatAUBEgyptSAE,table107_AccountType');
				}
			}
			}
		}
}


function Generate_Template_Calling_Function()
{
	var wi_name = getWorkItemData("processinstanceid");
	var docname=getValue('Document_Name');
	//var docName = "Personal Loan Application Form";
	var attrbList = "";
	
	//if(docname == "Loan Application")		//IC changes
	
	
	//Added by Akash 11-05-26-------Start---------------------------------
	if(docname == "Intl Commodity Murabaha Fin Contract-Individuals")
	{		
		if(getValue("Product_Type") == "MR")
		{	
			attrbList += NG_Intl_Commodity_Fin_Contract_Indv();
			docname ="Intl Commodity Murabaha Fin Contract-Individuals";
		}
	}	
	
	if(docname == "Intl Comdty Murabaha Fin Contract-Ints Prd Indv")
	{		
		if(getValue("Product_Type") == "MR")
		{	
			attrbList += NG_Intl_Commodity_Murabaha_FinContract_VarInst_Indv();
			docname ="Intl Comdty Murabaha Fin Contract-Ints Prd Indv";
		}
	}	
	
	if(docname == "Intl Comdty Murabaha Contract-Agy-Indv Contract")
	{		
		if(getValue("Product_Type") == "MR")
		{	
			attrbList += NG_Intl_Commodity_Murabaha_Agency_Indv();
			docname ="Intl Comdty Murabaha Contract-Agy-Indv Contract";
		}
	}	
	
	if(docname == "Car Murabaha Contracts- Framework Agreement-LOR")
	{		
		if(getValue("Product_Type") == "MR")
		{	
			attrbList += NG_Car_Murabaha_Framework_LetterRecom();
			docname ="Car Murabaha Contracts- Framework Agreement-LOR";
		}
	}	
	
	if(docname == "Car Murabaha Contracts- Framework Agreement- CPR")
	{		
		if(getValue("Product_Type") == "MR")
		{	
			attrbList += NG_Car_Murabaha_Framework_CarPurchaseReq();
			docname ="Car Murabaha Contracts- Framework Agreement- CPR";
		}
	}	
		//-----------END---------------------------------
	
	if(docname == "Finance Application")
	{		
		if(getValue("Product_Type") == "PL")
		{	
			attrbList += Loan_Application_TemplateData();
			docname ="PL_LoanApplication";
		}
		if(getValue("Product_Type") == "AL")
		{
			attrbList += Auto_Loan_Application();
			docname ="AL_LoanApplication";
		}
		if(getValue("Product_Type") == "CC")
		{
			attrbList +=  Credit_Card_App_Form();
			docname ="CC_LoanApplication";
		}
	}
	//if(docname == "Secured Loan")			//IC changes
	if(docname == "Secured Finance")
	{
		attrbList += PL_SecuredLoan_Contract_TemplateData();  
	}
	if(docname == "Unsecured")
	{
		attrbList += PL_Unsecured_Contract_TemplateData(); 
	}
	//if(docname == "Loans with Bulk installments payments")		//IC changes
	if(docname == "Finances with Bulk installments payments")
	{
		attrbList += Clean_Loan_Contract_Personal_loan_Bulk_Installments_TemplateData() 
	}
	//if(docname == "Clean loan contract premium loan")												//IC changes
	if(docname == "Clean finance contract premium finance")
	{
		attrbList +=  Clean_Loan_Contract_Premium_Loan_TemplateData(); 
	}
	//if(docname == "Club Membership Loan")					//IC changes
	if(docname == "Club Membership Finance")
	{
		attrbList += Club_Membership_Contract_TemplateData();   
	}
	//if(docname == "Second Salary Loan")					//IC changes
	if(docname == "Second Salary Finance")
	{
		attrbList += Second_Salary_Contract__TemplateData();
	}
	if(docname == "Student Declaration Form")
	{
		attrbList += Student_loan_Declaration();
	}
	if(docname ==  "HR Undertaking Form for Monthly Salary")
	{
		attrbList += Undertaking_Letter_TemplateData();
	}	
	if(docname ==  "HR Undertaking Form+Include Profit Share")
	{
		attrbList += Undertaking_Letter_Annual_TemplateData();
	}	  
	if(docname == "Letter of lien")
	{
		attrbList +=  Letter_of_lien_TemplateData(); 
	}
	if(docname == "Authorization to fill in blank doc")       
	{
		attrbList += authorization_to_fill_data ();
	}
	if(docname == "Ban on Sale")
	{
		attrbList += Ban_on_Sale(); 
	}
	if(docname == "Letter of Payment LOP")
	{
		attrbList += Letter_of_Payment_LOP(); 
	}
	if(docname == "Car Receving Confirmation letter")
	{
		attrbList += Car_Receving_Confirmation_letter();   
	}			
	if(docname == "Insurance Form")
	{
		attrbList += Short_Form_Questionnaire_Loans_Insurance();
	}
	if(docname == "Unsecured CC Approval Memo")
	{
		if(getValue('Product_Type')== 'CC')
		{
			//attrbList += UnsecuredCC_ApprovalMemo();  
			//by sakshi
			attrbList += UnsecuredCC_ApprovalMemo_1();
		}
	}
	if(docname == "RC Utilization Permit Doc")
	{
		//Ajay RC Utilization 1 start				 
		var Product_Type = getValue('Product_Type');
		var Sub_Product_Type= getValue('Sub_Product_Type');
		var Request_Type =  getValue('Request_Type');
		 
		if(Product_Type != "PL")
		{
			alert("Not Applicable for Selected Product Type");
			return;
		}
		if(Sub_Product_Type == "Un-secured" )
		{
			if(getValue("Repayment_Schedule")=="Normal (Without Bulk Payment)")
			{
				if(Request_Type == "New Grant")
				{
					docname = "RC Utilization Permit Doc New Grant";
					attrbList +=  RC_Utilization_Permits_NewGrant();
				}
				else if(Request_Type == "Buy Out")
				{
					docname = "RC Utilization Permit Doc Buy Out";
					attrbList +=  RC_Utilization_Permits_BuyOut();
				}
				else if(Request_Type == "Top Up")
				{
					docname = "RC Utilization Permit Doc Top Up";
					attrbList +=  RC_Utilization_Permits_TopUp();
				}
				else if(Request_Type == "Top up + Buy out")
				{
					docname = "RC Utilization Permit Doc Top up and Buy out";
					attrbList +=  RC_Utilization_Permits_TopUpAndBuyOut();
				}
			}
			else if(getValue("Repayment_Schedule")=="Normal (With Bulk Payment)")
			{
				docname = "RC Utilization Permit Doc Bulk";
				attrbList +=  RC_Utilization_Permits_Bulk();
			}
			else
			{
				alert("Not Applicable for Selected Repayment Schedule");
				return;
			}		
		}
		else if (Sub_Product_Type == "Secured" )
		{
			docname = "RC Utilization Permit Doc Secured";
			attrbList +=  RC_Utilization_Permits_Secured();
		}
		else
		{
			alert("Not Applicable for Selected Sub Product Type");
			return;
		}				
		//Ajay RC Utilization 1 End
	}		
	if(docname == "Unsecured PL Approval Memo")
	{
		if(getValue('Product_Type')== 'PL')
		{
			attrbList += UnsecuredPL_ApprovalMemo();  
		}
	}	
	/*
	if(docname == "Unsecured CC Approval Memo")
	{
		if(getValue('Product_Type')== 'CC')
		{
			attrbList += UnsecuredCC_ApprovalMemo();  
		}
	}	 */			  
	/* 
	if(docname == "Secured CC Approval Memo")
	{
		if(getValue('Product_Type')== 'CC'  && getValue('Sub_Product_Type') == 'Secured')	
		{
			attrbList += Secured_CC_Approval_Memo(); //Unsecured_AL_Approval_Memo()
		}
	} */
	if(docname == "Unsecured AL Approval Memo")
	{
		var Product_Type  = getValue('Product_Type');
		var Sub_Product_Type = getValue('Sub_Product_Type');
				 
		if(getValue('Product_Type')== 'AL'  && getValue('Sub_Product_Type') == 'Un-secured')	
		{
		attrbList += Unsecured_AL_Approval_Memo();
		}
	}
	if(docname == "Secured PL Approval Memo")
	{		
		if(getValue("Product_Type") == "PL")
		{	
			attrbList += Secured_Facility_Approval_Memo();
			docname ="Secured PL Approval Memo";
		}
	}
	if(docname == "Secured AL Approval Memo")
	{
		if(getValue("Product_Type") == "AL")
		{
			attrbList += Secured_Facility_Approval_Memo();
			docname ="Secured AL Approval Memo";
		}
	}
	if(docname == "Secured CC Approval Memo")
	{
		if(getValue("Product_Type") == "CC")
		{
			attrbList +=  Secured_Facility_Approval_Memo();
			docname ="Secured CC Approval Memo";
		}
	}
	if(docname == "Unsecured PL Approval Memo Bulk")
	{
		if(getValue('Product_Type')== 'PL' && getValue('Sub_Product_Type') == 'Un-secured')
		{
			attrbList += Unsecured_PL_Bulk_Memo();
		}
	}
			 
	attrbList=attrbList.replaceAll("%"," Percentage");
	attrbList=attrbList.replaceAll("&<","STARTINGTAG");
	attrbList=attrbList.replaceAll(">&","ENDINGTAG");
	attrbList=attrbList.replaceAll("&","and");
	attrbList=attrbList.replaceAll("STARTINGTAG","&<");
	attrbList=attrbList.replaceAll("ENDINGTAG",">&");
	attrbList=encodeURIComponent(attrbList);
			
	var ajaxReq;
	var dataFromAjax;				
	
	if (window.XMLHttpRequest) 
	{
		ajaxReq= new XMLHttpRequest();
	}
	else if (window.ActiveXObject)
	{
		ajaxReq= new ActiveXObject("Microsoft.XMLHTTP");
	}		
								
	var url = "/webdesktop/custom/CustomJSP/EgyptGenerate_Template.jsp?attrbList="+attrbList+"&wi_name="+wi_name+"&docName="+docname+"&sessionId="+window.parent.sessionId;                		   
				
	// alert('Generate_Template_Calling_Function url '+ url);  	
	ajaxReq.open("POST", url, false);
	ajaxReq.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
	ajaxReq.send(null);
	if (ajaxReq.status == 200 && ajaxReq.readyState == 4)
	{
		dataFromAjax=ajaxReq.responseText;
		//alert("result copy data "+ dataFromAjax);
		var columnValue_arr=dataFromAjax.split('~');
		var statusDoc = columnValue_arr[0];
		var inputXML = columnValue_arr[1];
		var docIndex = columnValue_arr[2];
					
		if (typeof inputXML === 'undefined') 
		{
			alert("Template Could not be generated!");
			return;
		}
		
		statusDoc= statusDoc.trim();
		inputXML= inputXML.trim();
		docIndex= docIndex.trim();
		//alert("inputXML "+ inputXML);
								
		window.parent.customAddDoc(inputXML);
		if(statusDoc == 'Success')
		{
			alert("Template generated successfully");
		}
	}
	else
	{
		alert("INVALID_REQUEST_ERROR : "+ajaxReq.status);
	}
}

function IdMandateOnEgypt(pControlName) //anuj
{
	
	
	if(compareStringsIgnoreCase(pControlName,"Applicant_Nationality"))
	{
		if(compareStringsIgnoreCase(getValue("Applicant_Nationality"),"Egypt") || compareStringsIgnoreCase(getValue("Applicant_Nationality"),"EG"))
		{
			mandatoryControl("Applicant_National_ID");
		}
		else
		{
			nonMandatoryControl("Applicant_National_ID");
		}
	}
	else if(compareStringsIgnoreCase(pControlName,"Co_Applicant_Nationality"))
	{
		if(compareStringsIgnoreCase(getValue("Co_Applicant_Nationality"),"Egypt") || compareStringsIgnoreCase(getValue("Co_Applicant_Nationality"),"EG"))
		{
			mandatoryControl("Co_Applicant_National_ID");
		}
		else
		{
			nonMandatoryControl("Co_Applicant_National_ID");
		}
	}
	else if(compareStringsIgnoreCase(pControlName,"Guarantor_Nationality"))
	{
		if(compareStringsIgnoreCase(getValue("Guarantor_Nationality"),"Egypt") || compareStringsIgnoreCase(getValue("Guarantor_Nationality"),"EG"))
		{
			mandatoryControl("Guarantor_National_ID");
		}
		else
		{
			nonMandatoryControl("Guarantor_National_ID");
		}
	}
}

function AutoPopEmpName()// anuj
{
	if(compareStringsIgnoreCase(getValue('table137_CustomerType'),"Applicant"))
	{
		var ApplicantEmpName=getValue('Applicant_Employer_Name');
		setControlValue('table137_EmployerName',ApplicantEmpName);
		setControlValue('table137_OthersSpecify',getValue('EMPLOYER_OTHERS'));
	}
}

function MandateonExternalTransfer()
{
	if(compareStringsIgnoreCase(getValue("table116_TransferType"),"External Transfer"))
	{
		mandatoryControl("table116_BeneficiaryBankName");
		mandatoryControl("table116_IBANNumber");
		mandatoryControl("table116_AccountNumber");
		unlockControl("table116_BeneficiaryBankName,table116_IBANNumber,table116_AccountNumber");
	}
	else
	{
		nonMandatoryControl("table116_BeneficiaryBankName");
		nonMandatoryControl("table116_IBANNumber");
		nonMandatoryControl("table116_AccountNumber");
	}
}
function calculateLastInstalmentDate()
{
	setControlValue('table117_lastInstalmentDate',"");
	var tenor=getValue('table117_LoanTenor1');
	
	if(compareStringsIgnoreCase(tenor.toString(),""))
	{
		return false;
	}
	var FirstInstalmentDate=getValue('table117_FirstInstallmentDate');
	if(compareStringsIgnoreCase(FirstInstalmentDate,""))
	{
		return false;
	}
	
	
	var updatedDate=addMonthsToDate(FirstInstalmentDate,tenor);
	
	setControlValue('table117_lastInstalmentDate',updatedDate);
	disableControl('table117_lastInstalmentDate');
	//CalcAgeAtMaturity();
	calMaturity();
}
function CalcAgeAtMaturity()
{
	setControlValue('table117_AgeAtMaturity',"");
	var DOB=getValue('Applicant_DOB');
	if(compareStringsIgnoreCase(DOB,""))
	{
		showMessage('Applicant_DOB',"Please Fill Details of Applicant","error");
		return false;
	}
	var LastInstalmentDate=getValue('table117_lastInstalmentDate');
	var AgeAtMaturity=dateInDifference(DOB,LastInstalmentDate);
	if(isNaN(AgeAtMaturity))
	{
		return false;
	}
	 setControlValue("table117_AgeAtMaturity",AgeAtMaturity );

	
}

function calculateLastInstalmentDateBulk(fDate,lDate,tempTenor,multi)
{
	setControlValue(lDate,"");
	var tenor=getValue(tempTenor)*multi;
	
	if(compareStringsIgnoreCase(tenor.toString(),"") && compareStringsIgnoreCase(tenor.toString(),"0"))
	{
		return false;
	}
	var FirstInstalmentDate=getValue(fDate);
	if(compareStringsIgnoreCase(FirstInstalmentDate,""))
	{
		return false;
	}
	var updatedDate=addMonthsToDateBulk(FirstInstalmentDate,tenor,multi);
	
	setControlValue(lDate,updatedDate);
	disableControl(lDate);
	//CalcAgeAtMaturity();
	calMaturity();
	}
	

function addMonthsToDate(dt,monthToBeAdded)
{
	var dateFields = dt.split("/");
	
	var dDate = dateFields[2];
	var dMonth = dateFields[1];
	var dYear = dateFields[0];
                
    const d = new Date();
	d.setDate(dDate);
	//d.setMonth(dMonth-1);
	d.setFullYear(dYear);
                
    d.setMonth((dMonth-1) + (monthToBeAdded-1));
	var mnth = d.getMonth()+1+"";
    
	if(mnth.length==1)
    {
        mnth = '0'+mnth;
    }
    
    var day = d.getDate() + "";
    
    if(day.length==1)
    {
        day = '0'+day;
    }
    
    return  d.getFullYear()+"/"+mnth+"/"+day;
}
function addMonthsToDateBulk(dt,monthToBeAdded,multi)
{
	var dateFields = dt.split("/");
	
	var dDate = dateFields[2];
	var dMonth = dateFields[1];
	var dYear = dateFields[0];
                
    const d = new Date();
	d.setDate(dDate);
	//d.setMonth(dMonth-1);
	d.setFullYear(dYear);
                
    d.setMonth((dMonth-1) + (monthToBeAdded-multi));
	var mnth = d.getMonth()+1+"";
    
	if(mnth.length==1)
    {
        mnth = '0'+mnth;
    }
    
    var day = d.getDate() + "";
    
    if(day.length==1)
    {
        day = '0'+day;
    }
    
    return  d.getFullYear()+"/"+mnth+"/"+day;
}
function dateInDifference(dt1,dt2)
{
	const d1 = new Date(dt1);
    const d2 = new Date(dt2);
    
    var diff = d2.getFullYear() - d1.getFullYear();
    return  diff;
}


// added by disha - 11-Oct-2021 for NID template
function Generate_Template_NID_Function(responseXML)
{

			var wi_name = getWorkItemData("processinstanceid");
			var docname="NID";
			var attrbList = "";	

			attrbList += NIDReport(responseXML);  
			  				 
			attrbList=encodeURIComponent(attrbList);
			
			var ajaxReq;
				var dataFromAjax;				
				if (window.XMLHttpRequest) 
				{
					ajaxReq= new XMLHttpRequest();
				}
				else if (window.ActiveXObject)
				{
					ajaxReq= new ActiveXObject("Microsoft.XMLHTTP");
				}		
								
				var url = "/webdesktop/custom/CustomJSP/EgyptGenerate_Template.jsp?attrbList="+attrbList+"&wi_name="+wi_name+"&docName="+docname+"&sessionId="+window.parent.sessionId;                		   
				 //alert('Generate_Template_Calling_Function url '+ url);  	
				ajaxReq.open("POST", url, false);
				ajaxReq.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
				ajaxReq.send(null);
				if (ajaxReq.status == 200 && ajaxReq.readyState == 4)
				{
					dataFromAjax=ajaxReq.responseText;
					//alert("result copy data "+ dataFromAjax);
					var columnValue_arr=dataFromAjax.split('~');
					var statusDoc = columnValue_arr[0];
					var inputXML = columnValue_arr[1];
					var docIndex = columnValue_arr[2];
					statusDoc= statusDoc.trim();
					inputXML= inputXML.trim();
					docIndex= docIndex.trim();
					//alert("inputXML "+ inputXML);
					
					//window.parent.customAddDoc(inputXML, docIndex, 'newversion');
					window.parent.customAddDoc(inputXML);
					if(statusDoc == 'Success')
					{
						showAlertDialog("Template generated successfully");
					}
				}
				else
				{
					alert("INVALID_REQUEST_ERROR : "+ajaxReq.status);
				}
}

function CCLimitByNetSalary()
{
	try
	{
		var val=parseFloat(getValue("table117_RequestedLoanCCAmnt"));
		
		if(isNaN(val))
		{
			val=parseFloat(getValue("Req_Loan_Amt"))
		}
		
		if(isNaN(val))
		{
			return;
		}
		
		var netSal=parseFloat(getValue("NetSalary"));
		
		if(isNaN(netSal))
		{
			return;
		}
		
		var limitByNetSal=parseFloat(val/netSal);
		limitByNetSal=limitByNetSal.toFixed(3);
		setControlValue("table117_CCLimitByNetSalary",limitByNetSal);
	}
	catch(x)
	{
		
		
	}
}

//Ajay 15Dec
function CalculateTotalCoverRatio()
{
	var totalAmount=getValue('Credit_Approved_Amount');
	
	if(totalAmount == "" || totalAmount == "0" || isNaN(totalAmount))
	{
		totalAmount=getValue('Req_Loan_Amt');
	}
	// var ChooseCollValue=getColumnSum("table129", "Outstanding");
	var ChooseCollValue=0;
	
	
	
	var count=getGridRowCount("table129");
	var tempCount=0;
	for (var i=0;i<count;i++)
	{
		if(getValueFromTableCell("table129", i, 0)=='true')
		{
			var outstanding = getValueFromTableCell("table129", i, 7);
			
			if(outstanding=="")
			{
				outstanding = 0;
			}
			
			if(!isNaN(outstanding))
			{
				ChooseCollValue = ChooseCollValue + parseFloat(outstanding);
			}
		}
	}
				
	setControlValue("Total_Chosen_Coll_Value",ChooseCollValue);
	var TotatCoverageRatio=(totalAmount/ChooseCollValue)*100;
	TotatCoverageRatio=TotatCoverageRatio.toFixed(2);
	setControlValue("Total_CoverageRation", removeNaNAndAddPercent(TotatCoverageRatio));
	disableControl('Total_CoverageRation');
}

function removeNaNAndAddPercent(ipVar)
{
	if(isNaN(ipVar))
    {    	
        ipVar = "0"+ '%';
    }
    else if(ipVar == "Infinity")
    {
    	ipVar = "0"+ '%';
    }
    else
    {
        ipVar = ipVar+ '%';
    }
    return ipVar;
}

function MandateForForeignCust()
{
	var ApplicantNationalId=getValue("Applicant_National_ID");
	var CoAppNationalId=getValue("Co_Applicant_National_ID");
	var guarantorNationalId=getValue("Guarantor_National_ID");
	
	var CustomerType=getValue("table107_CustomerType");
	
	if(compareStringsIgnoreCase(CustomerType,"Applicant"))
	{
		if(ApplicantNationalId.length!=14)
		{
			nonMandatoryControl("table107_DateOfIssueNID,table107_DateOfExpiryNID,table107_NID_Check_Status");
			setStyle("button181","disable","true");
		}
		else{
			mandatoryControl("table107_DateOfIssueNID,table107_DateOfExpiryNID,table107_NID_Check_Status");
			setStyle("button181","disable","false");
		}
	}
	else if(compareStringsIgnoreCase(CustomerType,"Co-Applicant"))
	{
		if(CoAppNationalId.length!=14)
		{
			nonMandatoryControl("table107_DateOfIssueNID,table107_DateOfExpiryNID,table107_NID_Check_Status");
			setStyle("button181","disable","true");
		}
		else{
			mandatoryControl("table107_DateOfIssueNID,table107_DateOfExpiryNID,table107_NID_Check_Status");
			setStyle("button181","disable","false");
		}
	}
	else if(compareStringsIgnoreCase(CustomerType,"Guarantor"))
	{
		if(guarantorNationalId.length!=14)
		{
			nonMandatoryControl("table107_DateOfIssueNID,table107_DateOfExpiryNID,table107_NID_Check_Status");
			setStyle("button181","disable","true");
		}
		else{
			mandatoryControl("table107_DateOfIssueNID,table107_DateOfExpiryNID,table107_NID_Check_Status");
			setStyle("button181","disable","false");
		}
	}
	
	
}
function compareTwoDates(D1,D2)
{
	var dt1 =new Date(D1);
	var dt2=new Date(D2);
	var result;
	 if (dt1.getTime() < dt2.getTime())
	 {
     	  result="Small";
		  return result;
	 } 
    else if (dt1.getTime() > dt2.getTime())
	  {
     	result="Large";
		return result;
	  }
    else
	  {
		result="Equal";
		return result;
  	}
}

function calculateTotalInstallmentAmount(ControlName)
{
	//table117_InstallmentAmtBulk- Installment yearly
	//table117_InstallmentAmtMonthly-InstallmentSemiAnuualy
	//table117_InstalmentAmntQtrly-InstallmentQuaterly
	//table117_YearlyInstallment-monthly Intallmentamount
	if(compareStringsIgnoreCase(ControlName,"table117_InstallmentAmtBulk"))//yearly
	{
		//clearValue('table117_InstallmentAmtMonthly');
		//clearValue('table117_InstalmentAmntQtrly');
		var MonthlyInstalmentAmount=getValue('table117_YearlyInstallment');//Monthly id
		var InstalmentYearly=parseFloat(getValue('table117_InstallmentAmtBulk')/12);
		InstalmentYearly=InstalmentYearly.toFixed(3);
		var TotalInstalmentAmount=parseFloat(MonthlyInstalmentAmount)+parseFloat(InstalmentYearly);
		if(isNaN(TotalInstalmentAmount))
		{
			return false;
		}
		setControlValue("table117_TotalInstallmentAmount",TotalInstalmentAmount);
		
	}
	else if(compareStringsIgnoreCase(ControlName,"table117_InstallmentAmtMonthly"))//Semi Annually
	{
		//clearValue('table117_InstallmentAmtBulk');
		//clearValue('table117_InstalmentAmntQtrly');
		var MonthlyInstalmentAmount=getValue('table117_YearlyInstallment');//Monthly id
		var InstalmentYearly=parseFloat(getValue('table117_InstallmentAmtMonthly')/6);
		InstalmentYearly=InstalmentYearly.toFixed(3);
		var TotalInstalmentAmount=parseFloat(MonthlyInstalmentAmount)+parseFloat(InstalmentYearly);
		if(isNaN(TotalInstalmentAmount))
		{
			return false;
		}
		setControlValue("table117_TotalInstallmentAmount",TotalInstalmentAmount);
		
	}
	else if(compareStringsIgnoreCase(ControlName,"table117_InstalmentAmntQtrly"))//Semi Annually
	{
		//clearValue('table117_InstallmentAmtBulk');
		//clearValue('table117_InstallmentAmtMonthly');
		var MonthlyInstalmentAmount=getValue('table117_YearlyInstallment');//Monthly id
		var InstalmentYearly=parseFloat(getValue('table117_InstalmentAmntQtrly')/3);
		InstalmentYearly=InstalmentYearly.toFixed(3);
		var TotalInstalmentAmount=parseFloat(MonthlyInstalmentAmount)+parseFloat(InstalmentYearly);
		if(isNaN(TotalInstalmentAmount))
		{
			return false;
		}
		setControlValue("table117_TotalInstallmentAmount",TotalInstalmentAmount);
		
	}
	else if(compareStringsIgnoreCase(ControlName,"table117_YearlyInstallment"))//Monthly
	{
		if(!compareStringsIgnoreCase(getValue('table117_InstallmentAmtBulk'),""))
		{
			calculateTotalInstallmentAmount('table117_InstallmentAmtBulk');
		}
		else if(!compareStringsIgnoreCase(getValue('table117_InstallmentAmtMonthly'),""))
		{
			calculateTotalInstallmentAmount('table117_InstallmentAmtMonthly');
		}
		else if(!compareStringsIgnoreCase(getValue('table117_InstalmentAmntQtrly'),""))
		{
			calculateTotalInstallmentAmount('table117_InstalmentAmntQtrly');
		}
		else if(compareStringsIgnoreCase(getValue('table117_InstallmentAmtBulk'),"") && compareStringsIgnoreCase(getValue('table117_InstallmentAmtBulk'),"") && compareStringsIgnoreCase(getValue('table117_InstallmentAmtBulk'),"") )
		{
			var MonthlyInstalmentAmount=getValue('table117_YearlyInstallment');
			if(compareStringsIgnoreCase(MonthlyInstalmentAmount,""))
			{
				setControlValue("table117_TotalInstallmentAmount","0");
				return false;
			}
			var TotalInstalmentAmount=parseFloat(MonthlyInstalmentAmount);
			if(isNaN(TotalInstalmentAmount))
			{
				return false;
			}
			setControlValue("table117_TotalInstallmentAmount",TotalInstalmentAmount);
		}
		
		
	}
	
}
function calculateTotalInstlmtAmntBulk()
{
var totalImstallment=getZeroFromNAN(parseFloat(getValue('table117_YearlyInstallment')))+(getZeroFromNAN(parseFloat(getValue('table117_InstallmentAmtMonthly')))/6)+(getZeroFromNAN(parseFloat(getValue('table117_InstalmentAmntQtrly')))/3)+(getZeroFromNAN(parseFloat(getValue('table117_InstallmentAmtBulk')))/12);
if(totalImstallment<1)
{
totalImstallment='';	
}
setControlValue('table117_TotalInstallmentAmount',totalImstallment);
}

function getZeroFromNAN(val)
{
if(isNaN(val))
{
	return 0;
}
return val
}
function calculateTotalrequestedAmount(AdditionValue)
{
	var totalAmnt=getZeroFromNAN(parseFloat(getValue('table117_RequestedLoanAmount1')))+getZeroFromNAN(parseFloat(getValue('table117_RqstQuaterLoanAmnt')))+getZeroFromNAN(parseFloat(getValue('table117_ReqstdSemiAnualLoanAmnt')))+getZeroFromNAN(parseFloat(getValue('table117_RequestedLoanAmount2')));
	if(totalAmnt<1)
	{
		totalAmnt='';
	}
	setControlValue('table117_TotalApprovedAmount',totalAmnt);
	
}
function calMaturity()
{
	setControlValue('table117_AgeAtMaturity',"");
	var DOB=getValue('Applicant_DOB');
	if(compareStringsIgnoreCase(DOB,""))
	{
		showMessage('Applicant_DOB',"Please Fill Details of Applicant","error");
		return false;
	}
	if (getValue("Product_Type") == "CC") 
	{
		var CreditCardMaturity=getZeroFromNAN(dateInDifference(DOB,getValue('table117_CredtCardExpiryDate')));
		if(CreditCardMaturity<1)
		{
			CreditCardMaturity='';
		}
		setControlValue('table117_AgeAtMaturity',CreditCardMaturity);
	}
	else
	{
		var AgeAtMaturityM=getZeroFromNAN(dateInDifference(DOB,getValue('table117_lastInstalmentDate')));
		var AgeAtMaturityQ=getZeroFromNAN(dateInDifference(DOB,getValue('table117_LastQuarterlyInstalmentDate')));
		var AgeAtMaturityS=getZeroFromNAN(dateInDifference(DOB,getValue('table117_LastSemiAnnuallyInstalmentDate')));
		var AgeAtMaturityY=getZeroFromNAN(dateInDifference(DOB,getValue('table117_LastYearlyInstalmentDate')));
		var maturity=Math.max(AgeAtMaturityM,AgeAtMaturityQ,AgeAtMaturityS,AgeAtMaturityY);
		if(maturity<1)
		{
			maturity='';
		}
		setControlValue('table117_AgeAtMaturity',maturity);
	}
}

/*
function calculateNewTotalLiabonUseDBRforCBS(Checkvalue)
{
	var type=getValue("table112_Type");
	if(compareStringsIgnoreCase(type,"")||compareStringsIgnoreCase(type,"Null"))
	{
		return false;
	}
	var totalLiab=getValue('DisposableIncomeWithoutOverTime');
	
	if(compareStringsIgnoreCase(Checkvalue,"true"))
	{
		
		if(type.includes('credit')||type.includes('Credit'))
		{
			var limit=getValue('table112_Limit');
			var newTotalLiab=parseFloat(totalLiab)+parseFloat((5*limit)/100);
			newTotalLiab=newTotalLiab.toFixed(2);
			if(isNaN(newTotalLiab))
			{
				return false;
			}
			setControlValue('DisposableIncomeWithoutOverTime',newTotalLiab);
		}
		else{
			var EMI=getValue('table112_EMI');
			var newTotalLiab=parseFloat(totalLiab)+parseFloat(EMI);
			newTotalLiab=newTotalLiab.toFixed(2);
			if(isNaN(newTotalLiab))
			{
				return false;
			}
			setControlValue('DisposableIncomeWithoutOverTime',newTotalLiab);
		}
	}
	else if(compareStringsIgnoreCase(Checkvalue,"false"))
	{
		
		if(type.includes('credit')||type.includes('Credit'))
		{
			var limit=getValue('table112_Limit');
			var newTotalLiab=parseFloat(totalLiab)-parseFloat((5*limit)/100);
			newTotalLiab=newTotalLiab.toFixed(2);
			if(isNaN(newTotalLiab))
			{
				return false;
			}
			setControlValue('DisposableIncomeWithoutOverTime',newTotalLiab);
		}
		else{
			var EMI=getValue('table112_EMI');
			var newTotalLiab=parseFloat(totalLiab)-parseFloat(EMI);
			newTotalLiab=newTotalLiab.toFixed(2);
			if(isNaN(newTotalLiab))
			{
				return false;
			}
			setControlValue('DisposableIncomeWithoutOverTime',newTotalLiab);
		}
	}
}
*/

/*
function calculateNewTotalLiabonUseDBRforOther(Checkvalue)
{
	var type=getValue("table120_Facility_Type");
	if(compareStringsIgnoreCase(type,"")||compareStringsIgnoreCase(type,"Null"))
	{
		return false;
	}
	var totalLiab=getValue('DisposableIncomeWithoutOverTime');
	
	if(compareStringsIgnoreCase(Checkvalue,"true"))
	{
		
		if(type.includes('credit')||type.includes('Credit'))
		{
			var limit=getValue('table120_Facility_Amount');
			var newTotalLiab=parseFloat(totalLiab)+parseFloat((5*limit)/100);
			newTotalLiab=newTotalLiab.toFixed(2);
			if(isNaN(newTotalLiab))
			{
				return false;
			}
			setControlValue('DisposableIncomeWithoutOverTime',newTotalLiab);
		}
		else{
			var EMI=getValue('table120_Installment_Amount');
			var newTotalLiab=parseFloat(totalLiab)+parseFloat(EMI);
			newTotalLiab=newTotalLiab.toFixed(2);
			if(isNaN(newTotalLiab))
			{
				return false;
			}
			setControlValue('DisposableIncomeWithoutOverTime',newTotalLiab);
		}
	}
	else if(compareStringsIgnoreCase(Checkvalue,"false"))
	{
		
		if(type.includes('credit')||type.includes('Credit'))
		{
			var limit=getValue('table120_Facility_Amount');
			var newTotalLiab=parseFloat(totalLiab)-parseFloat((5*limit)/100);
			newTotalLiab=newTotalLiab.toFixed(2);
			if(isNaN(newTotalLiab))
			{
				return false;
			}
			setControlValue('DisposableIncomeWithoutOverTime',newTotalLiab);
		}
		else{
			var EMI=getValue('table120_Installment_Amount');
			var newTotalLiab=parseFloat(totalLiab)-parseFloat(EMI);
			newTotalLiab=newTotalLiab.toFixed(2);
			if(isNaN(newTotalLiab))
			{
				return false;
			}
			setControlValue('DisposableIncomeWithoutOverTime',newTotalLiab);
		}
	}
}
*/
function ValidationsOnCollateralDetails()
{
	if(ActivityName == "LPU_Maker")
	{
		if(compareStringsIgnoreCase(getValue('table129_Collateral_reservation_No'),""))
		{
			//enableControl('button192');
		}
		else{
			 //disableControl('button192');
		}
	}
}

function CalculateTotalLiability()
{//condition added for new product
	var Liability=0;
	var existingLiabilitiesOnly=0;
	if(compareStringsIgnoreCase(getValue("Product_Type"),'PL') || compareStringsIgnoreCase(getValue("Product_Type"),'AL') 
		|| compareStringsIgnoreCase(getValue("Product_Type"),'CL') || compareStringsIgnoreCase(getValue("Product_Type"),'MR'))
	{
		var TotalInstallmentAmount=parseFloat(getColumnSum("table117", "Total Installment Amount"));//total instament Amount from Product grid
		
		if(!isNaN(TotalInstallmentAmount))
		{
			Liability=Liability+parseFloat(TotalInstallmentAmount);
		}	
	}
	else if(compareStringsIgnoreCase(getValue("Product_Type"),'CC'))
	{
		var RequestedCCLimit=getColumnSum("table117", "Requested Card Amount");// Monthly CC limit from product grid
		var AmountTobeAdded=parseFloat((5*RequestedCCLimit)/100);
		if(!isNaN(AmountTobeAdded))
		{	
			Liability=Liability+parseFloat(AmountTobeAdded);
		}
	}
	
	var GridCountLiabAUB=getGridRowCount("table112");
	
	for(var i=0;i<GridCountLiabAUB;i++)
	{
		var DBRTickAUB=getValueFromTableCell("table112",i,0);//use DBR tick in AUB liab
		DBRTickAUB=DBRTickAUB.toString();
		if(compareStringsIgnoreCase(DBRTickAUB,"true"))
		{
			var type=getValueFromTableCell("table112",i,3); // type in AUB liab grid
			if(type.includes('credit')||type.includes('Credit'))
			{
				var Limit=parseFloat(getValueFromTableCell("table112",i,4));//Limit in AUB liab grid 
				if(!isNaN(Limit))
				{
					Limit=parseFloat((5*limit)/100);
					Liability=Liability+parseFloat(Limit);
					existingLiabilitiesOnly=existingLiabilitiesOnly+parseFloat(Limit);
				}
			}
			else
			{
				var EMI=parseFloat(getValueFromTableCell("table112",i,7));//Installment in AUB Liab grid
				if(!isNaN(EMI))
				{
					Liability=Liability + EMI;
					existingLiabilitiesOnly=existingLiabilitiesOnly + EMI;
				}	
			}			
		}
	}
	var GridCountLiabAUB=getGridRowCount("table120");
	for(var i=0;i<GridCountLiabAUB;i++)
	{
		var DBRTickAUBother=getValueFromTableCell("table120",i,0);//use DBR tick in AUB Other liab  grid
		DBRTickAUBother=DBRTickAUBother.toString();
		if(compareStringsIgnoreCase(DBRTickAUBother,"true"))
		{
			var Typeother=getValueFromTableCell("table120",i,1);//facility type in AUB Other liab  grid
			if(Typeother.includes('credit')||Typeother.includes('Credit'))
			{
				var LimitOther=parseFloat(getValueFromTableCell("table120",i,2)); //facility Amount in AUB Other liab  grid
				if(!isNaN(LimitOther))
				{
					LimitOther=parseFloat((5*LimitOther)/100);
					Liability=parseFloat(Liability)+LimitOther;
					existingLiabilitiesOnly=parseFloat(existingLiabilitiesOnly)+LimitOther;
				}
			}
			else
			{
				var EMIOther=parseFloat(getValueFromTableCell("table120",i,4));//Installment Amount in AUB Other liab  grid
				if(!isNaN(EMIOther))
				{
					Liability=Liability+EMIOther;
					existingLiabilitiesOnly=existingLiabilitiesOnly+EMIOther;
				}
			}
		}
	}
	
	Liability=Liability.toFixed(2);
	existingLiabilitiesOnly=existingLiabilitiesOnly.toFixed(2);
	setControlValue('DisposableIncomeWithoutOverTime',Liability);
	setControlValue('TotalExposureWithAUB',existingLiabilitiesOnly);
	CalculateDBR(false);
	
}

function CalculateDBR(calledFromFlag)
{
	/*var TotalLiab=getValue('DisposableIncomeWithoutOverTime');
	var TotalNetSalary=getValue("NetSalary");
	var ReqLoanAmount=getValue("Req_Loan_Amt");
	var TIA=getColumnSum("table117", "Total Installment Amount");
	var AgeAtMaturity=getValueFromTableCell("table117", 0, 47);
	var AccountNumber=getValueFromTableCell("table107", 0, 35);
	setControlValue('DisposableIncomeWithoutOverTime',TotalLiab);
	setControlValue('DisposableIncomeWithOverTime',TotalNetSalary);
	
	
	setControlValue('Age_at_Maturity',AgeAtMaturity);
	setControlValue('CustomerLoanAccountNumber',AccountNumber);*/
	if(calledFromFlag==true)
	{
		CalculateTotalLiability();
	}	
	
	var TotalNetSalary=getValue("NetSalary");
	var ReqLoanAmount=getValue("Req_Loan_Amt");
	var TIA=getColumnSum("table117", "Total Installment Amount");
	var AgeAtMaturity=getValueFromTableCell("table117", 0, 47);
	var AccountNumber=getValueFromTableCell("table107", 0, 35);
	
	if (typeof AccountNumber === 'undefined') 
	{
		AccountNumber = "";
	}
	setControlValue('DisposableIncomeWithOverTime',TotalNetSalary);
	if (getValue("Product_Type") == "CC")
	{
		setControlValue('Credit_Approved_Amount',getValueFromTableCell("table117", 0, 56));
		var creditInstallmentAmount=parseFloat((5*getValueFromTableCell("table117", 0, 56))/100);
		if(isNaN(creditInstallmentAmount))
		{
			creditInstallmentAmount = '0';
		}
		else
		{
			creditInstallmentAmount = creditInstallmentAmount.toFixed(2);
		}
		setControlValue('Credit_Approved_Installment',creditInstallmentAmount);
	}
	else		
	{
		setControlValue('Credit_Approved_Amount',getValueFromTableCell("table117", 0, 60));
		setControlValue('Credit_Approved_Installment',getValueFromTableCell("table117", 0, 58));
	}
	
	setControlValue('Age_at_Maturity',AgeAtMaturity);
	setControlValue('CustomerLoanAccountNumber',AccountNumber);
	var totalLiab=getValue('DisposableIncomeWithoutOverTime')
	if(compareStringsIgnoreCase(totalLiab,"") || compareStringsIgnoreCase(totalLiab,"0"))
	{
		if(calledFromFlag==true)
		{
			showAlertDialog("Please Check Total Installment Amount/ Requested CC limit");
		}	
		return false;
	}
	if(compareStringsIgnoreCase(TotalNetSalary,"") || compareStringsIgnoreCase(TotalNetSalary,"0"))
	{
		if(calledFromFlag==true)
		{
			showAlertDialog(" Net Salary is Zero");
		}	
		return false;
	}
	var DBR=(totalLiab/TotalNetSalary)*100;
	if(isNaN(DBR))
	{
		return false;
	}
	DBR=DBR.toFixed(2);
		
	if(!compareStringsIgnoreCase(DBR,""))
	{	
		setControlValue('DBR_POLICY',DBR);
		//disableControl('button197');
		disableControl('DBR_POLICY');
		if(calledFromFlag==true)
		{
			showAlertDialog("DBR Calculated!");
		}	
	}
	else
	{
		if(calledFromFlag==true)
		{
			showAlertDialog("DBR Not Calculated!");
		}	
	}
	CalculateTotalCoverRatio();//Ajay 15Dec
	
}


function getTotalInstallmentAmount()
{
	var TotalInstallmentAmount = 0;
	if(compareStringsIgnoreCase(getValue("Product_Type"),'PL') || compareStringsIgnoreCase(getValue("Product_Type"),'AL') )
	{
		TotalInstallmentAmount=getColumnSum("table117", "Total Installment Amount");
	}
	else if(compareStringsIgnoreCase(getValue("Product_Type"),'CC'))
	{
		TotalInstallmentAmount=getColumnSum("table117", "Requested Card Amount");// Monthly CC limit from product grid
		TotalInstallmentAmount=parseFloat((5*TotalInstallmentAmount)/100);
	}
	return TotalInstallmentAmount;
}

function CheckLengthOfField(FieldName,Len)
{
	var FieldValue=getValue(FieldName);
	if(compareStringsIgnoreCase(FieldName,"table133_BankAccNumber") && FieldValue.length!= Len)
	{
		showAlertDialog('Please Enter ' +Len+' Digit Account No');
		return false;
	}
	return true;
}


//Ajay 15Dec
function enableCreateCollateral()
{
	if(activityName=="Introduction" || isRCRWS())
	{
		enableControl('button209');
	}
}

function enableUpdateCollateral()
{
	if(activityName=="Introduction" || isRCRWS())
	{
		enableControl('button219');
	}
}

//Ajay 15Dec
function isRCRWS()
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
