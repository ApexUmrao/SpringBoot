var LOS_Common = document.createElement('script');
	LOS_Common.src = '/LOS_EG/LOS_EG/CustomJS/LOS_Common.js';
	document.head.appendChild(LOS_Common);

var incomeDetailsSection = "IncomeDetails";
var maximumEligibilitySection = "MaximumEligibility";
var employementDetailsSection = "EmploymentDetails";
var productDetailsTab = "sheet21_link";
var disbursementDetailsSection = "DisbursementDetails";
var conditionSection = "frame127";
var collateralChecklistSection = "frame132";
var allFieldsLos = "";
var allSections = "";


function afterformload(ActivityName)
{
	if(getValue('Loan_Reference_No')!="")
	{
		disableControl('button194');
	}
	
	if (getValue('CollResNo')!="") 
		{
		disableControl('button192');
		disableControl('button178');
		disableControl('Collateral_CIF');
		disableControl('table129');
		} 
	/*Header Start*/
	setValuesFromAnotherFeilds('Header_Loan_Amount', 'Req_Loan_Amt');
	setValuesFromAnotherFeilds('Header_Name', 'Applicant_Name');
	if('Product_Type'=='CC')
			{
				showControl('table140');
				 showControl("Card_Delivery_Method");
				mandatoryControl('Card_Delivery_Method');
			}
			else 
			{
				hideControl('table140');
				hideControl("Card_Delivery_Method");
				nonMandatoryControl('Card_Delivery_Method');
			}
	if(getValue('Applicant_National_ID').length==6)
		{
		setValuesFromAnotherFeilds('HeaderCIF', 'Applicant_National_ID');
		}
		else
		{
			setValuesFromAnotherFeilds('Header_NID', 'Applicant_National_ID');
		}
	/*Header End*/
	//Hide coApplicant Section on the basis of Application_Type on All workstep
        if (getValue('Application_Type') == "Single") {
            hideControl('CoApplicantDetails');
            nonMandatoryControl('Co_Applicant_National_ID,Co_Applicant_Name,Co_Applicant_DOB,Co_Applicant_Gender,Co_Applicant_Passport_Number,Co_Applicant_Sector,Co_Applicant_Employment_Type,Co_Applicant_Length_of_Service,Co_Applicant_Career_Level')

        } else {
            showControl('CoApplicantDetails');
            mandatoryControl('Co_Applicant_National_ID,Co_Applicant_Name,Co_Applicant_DOB,Co_Applicant_Gender,Co_Applicant_Passport_Number,Co_Applicant_Sector,Co_Applicant_Employment_Type,Co_Applicant_Length_of_Service,Co_Applicant_Career_Level');
        }
    
	if(ActivityName=='Introduction')
	{
	setControlValue('WI_Name',processInstanceId);
	setControlValue("Seller_Name",getWorkItemData("username"));
	setControlValue("Current_WS","Introduction");
	}
	var sActivityName = getWorkItemData("ActivityName");
	setControlValue("Decision", "");
	setControlValue("Remarks", "");
	validationsBasedOnRepaymentSchedule();
	validationsBasedOnProductType();
	validationsBasedOnSubProductType();
	wsWiseValidations();
	IdMandateOnEgyptForLoad();//anuj
	executeServerEvent("", "onLoad", "", true);
	
	if(getValue("FetchApplicant_Status")=='Y')
	{
		disableControl('button154');
	}
	if(getValue("FetchCoApplicant_Status")=='Y')
	{
		disableControl('button184');
	}
	if(getValue("FetchGuarantor_Status")=='Y')
	{
		disableControl('button185');
	}
	if(getValue("FetchMax_Eligibility")=='Y')
	{
		//disableControl('button218');
	}
	if(getValue("FetchCollateral_Status")=='Y')
	{
		//disableControl('button178');
	}
	if(getValue('Applicant_Nationality')=="EG")
	{
		nonMandatoryControl('Applicant_Passport_Number');
	}
	if(getValue('CO_Applicant_Nationality')=="EG")
	{
		nonMandatoryControl('CO_Applicant_Passport_Number');
	}
	if(getValue('Guarantor_Nationality')=="EG")
	{
		nonMandatoryControl('Guarantor_Passport_Number');
	}
	
		if(getValue("Applicant_Employer_Name")!="Others")
		{
			
			nonMandatoryControl('EMPLOYER_OTHERS');
			executeServerEvent("employerName", "onChange", getValue("Applicant_Employer_Name"), true);
		}
	if(getValue("Applicant_Employment_Type") == "Unemployed")
	{
		nonMandatoryControl("Applicant_Employment_Type,Applicant_Employer_Name,Applicant_Employer_Grade,Applicant_Joining_Date,Applicant_Length_of_Service,Company_Segment");
	}
	
	if(getValue("Co_Applicant_Employment_Type") == "Unemployed")
	{
		nonMandatoryControl("Co_Applicant_Employment_Type,Co_Applicant_EmployerName,Co_Applicant_Employer_Grade,Co_Applicant_Joining_Date,Co_Applicant_Length_of_Service");
	}
	
	if(getValue('Customer_Type')=='New To Bank')
	{
				setControlValue('Sub_Product_Type','Un-secured')
				disableControl('Sub_Product_Type');
	}
	
	/*if(ActivityName=="Introduction" || isRCRWS()) //Ajay 28Dec
	{
		showControl('button217');
	}
	else
	{
		hideControl('button217');
	}*/
	disableControl('NetSalary,Applicant_Length_of_Service,DisposableIncomeWithOverTime,DisposableIncomeWithoutOverTime');
}

function onInitiationFormLoad(){
	console.log("onInitiationFormLoad...");
	executeServerEvent("", "onLoad", "", true);
}


function visibleNonVisibleAfterFormLoad(){
	
}

function enableDisableAfterFormLoad(){
	
}

function disableAllFields() {
	//tab1
	disableControl("BranchInfo,LeadInfo,BasicProdInfo,ApplicantDetails,CoApplicantDetails,IncomeDetails,GuarantorDetails,MaximumEligibility");
	
	//tab2
	disableControl("CustomerDetails,AddressDetails,EmploymentDetails,Consent,BusinessPerformance");
	
	//tab3
	disableControl("ProductDetails,Liabilities,DisbursementDetails,PaymentMethodCC");
	
	//tab4
	disableControl("frame104,frame105,frame132,frame139");
	
	//tab5
	disableControl("frame100");
	
}


function wsWiseValidations(){
	switch(ActivityName){
		case "Introduction":{
			//disableControl(maximumEligibilitySection);
			//disableControl("button193");//Fetch Liab Button
			//disableControl("button197");//DBR Button
			enableControl('Fetch_Exception');//Excption Button
			//hideControl('Liabilities');
			//hideControl('table117_ExternalVerficationResult');
			setStyle("Dedupe_Check","visible","true");
			enableControl('button218');
			break;
		}
		case "PB_Supervisor":{
			disableControl(maximumEligibilitySection);
			disableControl("button193");//Fetch Liab Button
			disableControl("button197");//DBR Button
			disableControl('Fetch_Exception');//Excption Button
			disableAllFields();
			enableControl("MaximumEligibility");
			hideControl('Liabilities');
			hideControl('table117_ExternalVerficationResult');
			disableControl("frame128");
			
			break;
		}
		case "Branch_Manager":{
			disableControl(maximumEligibilitySection);
			disableControl("button193");//Fetch Liab Button
			disableControl("button197");//DBR Button
			disableControl('Fetch_Exception');//Excption Button
			//disableAllFields();
			hideControl('Liabilities');
			hideControl('table117_ExternalVerficationResult');
			disableControl("frame128");
			
			break;
		}
		case "Authorizer_DSU":{
			disableControl(maximumEligibilitySection);
			disableControl("button193");//Fetch Liab Button
			disableControl("button197");//DBR Button
			disableControl('Fetch_Exception');//Excption Button
			//disableAllFields();
			hideControl('Liabilities');
			hideControl('table117_ExternalVerficationResult');
			disableControl("frame128");
			
			break;
		}
		case "Legal_Dept":{
			disableControl(maximumEligibilitySection);
			disableControl("button193");//Fetch Liab Button
			disableControl("button197");//DBR Button
			disableControl('Fetch_Exception');//Excption Button
			hideControl('Liabilities');
			hideControl('table117_ExternalVerficationResult');
			disableAllFields();
			disableControl("frame128");
			
			break;
		}
		case "RCR_PreScanner":{
			disableControl(maximumEligibilitySection);
			//disableAllFields();
			enableControl("IncomeDetails");
			enableControl("ProductDetails");
			disableControl("button193");//Fetch Liab Button
			// disableControl("button197");//DBR Button
			enableControl('Fetch_Exception');//Excption Button
			enableControl("frame104");
			hideControl('Liabilities');
			mandatoryControl("table117_FirstInstallmentDate","table117_TypeOfCard","table117_CredtCardExpiryDate","table117_AgeAtMaturity");//anuj
			disableControl("frame128");
			enableControl('button218');
			enableControl('frame125');
			break;			
		}
		case "RCR_CreditAnalyst":{
			disableControl(maximumEligibilitySection);
			//disableAllFields();
			showControl('IS_Fraud_Dept,IS_Admin,IS_Collect_Dept,IS_Compliance_Dept');
			enableControl('Fetch_Exception');//Excption Button
			enableControl("IncomeDetails");
			enableControl("ProductDetails");
			enableControl("frame104");
			enableControl("frame105");
			mandatoryControl("table117_FirstInstallmentDate","table117_TypeOfCard","table117_CredtCardExpiryDate","table117_AgeAtMaturity");//anuj
			enableControl('button218');
			enableControl('frame125');
			break;
		}
		case "Fraud_Dept":{
			disableControl(maximumEligibilitySection);
			disableAllFields();
			mandatoryControl("table117_FirstInstallmentDate");//anuj
			disableControl("frame128");
			disableControl("button193");//Fetch Liab Button
			disableControl("button197");//DBR Button
			disableControl('Fetch_Exception');//Excption Button
			break;
		}
		case "Admin":{
			disableControl(maximumEligibilitySection);
			disableAllFields();
			enableControl("frame128");
			enableControl("frame100");
			mandatoryControl("table117_FirstInstallmentDate");//anuj
			disableControl("frame128");
			disableControl("button193");//Fetch Liab Button
			disableControl("button197");//DBR Button
			disableControl('Fetch_Exception');//Excption Button
			break;
		}
		case "Collection_Dept":{
			disableControl(maximumEligibilitySection);
			disableAllFields();
			mandatoryControl("table117_FirstInstallmentDate");//anuj
			disableControl("frame128");
			disableControl("button193");//Fetch Liab Button
			disableControl("button197");//DBR Button
			disableControl('Fetch_Exception');//Excption Button
			break;
		}
		case "Compliance_Dept":{
			disableControl(maximumEligibilitySection);
			disableAllFields();
			mandatoryControl("table117_FirstInstallmentDate");//anuj
			disableControl("frame128");
			disableControl("button193");//Fetch Liab Button
			disableControl("button197");//DBR Button
			disableControl('Fetch_Exception');//Excption Button
			break;
		}
		case "RCR_Credit_TL":{
			//disableControl(maximumEligibilitySection);
			mandatoryControl("table117_FirstInstallmentDate");//anuj
			disableControl("frame128");
			disableControl("button193");//Fetch Liab Button
			// disableControl("button197");//DBR Button
			enableControl('Fetch_Exception');//Excption Button
			enableControl('button218');
			enableControl('frame125');
			break;
		}
		case "Head_Of_Initiation":{
			disableControl(maximumEligibilitySection);
			//disableAllFields();
			mandatoryControl("table117_FirstInstallmentDate");//anuj
			disableControl("frame128");
			disableControl("button193");//Fetch Liab Button
			// disableControl("button197");//DBR Button
			enableControl('Fetch_Exception');//Excption Button
			enableControl('button218');
			enableControl('frame125');
			break;
		}
		case "RCRH":{
			disableControl(maximumEligibilitySection);
			//disableAllFields();
			mandatoryControl("table117_FirstInstallmentDate");//anuj
			disableControl("frame128");
			disableControl("button193");//Fetch Liab Button
			// disableControl("button197");//DBR Button
			enableControl('Fetch_Exception');//Excption Button
			enableControl('button218');
			enableControl('frame125');
			break;
		}
		case "CRO_CRMH":{
			disableControl(maximumEligibilitySection);
			//disableAllFields();
			mandatoryControl("table117_FirstInstallmentDate");//anuj
			disableControl("frame128");
			disableControl("button193");//Fetch Liab Button
			// disableControl("button197");//DBR Button
			enableControl('Fetch_Exception');//Excption Button
			enableControl('button218');
			enableControl('frame125');
			break;
		}
		case "Fulfillment_Docs":{
			disableControl(maximumEligibilitySection);
			mandatoryControl("table117_FirstInstallmentDate");//anuj
			disableControl("frame128");
			disableControl("button193");//Fetch Liab Button
			// disableControl("button197");//DBR Button
			disableControl('Fetch_Exception');//Excption Button
			disableAllFields();
			enableControl('CustomerLoanAccountNumber');
			break;
		}
		case "RCCU_RiskControl_Maker":{
			disableControl(maximumEligibilitySection);
			disableAllFields();
			mandatoryControl("table117_FirstInstallmentDate");//anuj
			disableControl("button193");//Fetch Liab Button
			disableControl("button197");//DBR Button
			disableControl('Fetch_Exception');//Excption Button
			
			break;
		}
		case "RCCU_RiskControl_Checker":{
			disableControl(maximumEligibilitySection);
			disableAllFields();
			mandatoryControl("table117_FirstInstallmentDate");//anuj
			disableControl("frame128");
			disableControl("button193");//Fetch Liab Button
			disableControl("button197");//DBR Button
			disableControl('Fetch_Exception');//Excption Button
			break;
		}
		case "RCCU_RiskControl_Authorizer":{
			disableControl(maximumEligibilitySection);
			disableAllFields();
			//mandatoryControl("table117_FirstInstallmentDate");//anuj
			disableControl("frame128");
			disableControl("button193");//Fetch Liab Button
			disableControl("button197");//DBR Button
			disableControl('Fetch_Exception');//Excption Button
			break;
		}
		case "RCCU_RiskLimit_Maker":{
			disableControl(maximumEligibilitySection);
			disableAllFields();
			disableControl("frame128");
			disableControl("button193");//Fetch Liab Button
			disableControl("button197");//DBR Button
			disableControl('Fetch_Exception');//Excption Buttons
			break;
		}
		case "RCCU_RiskLimit_Authorizer":{
			disableControl(maximumEligibilitySection);
			disableAllFields();
			mandatoryControl("table117_FirstInstallmentDate");//anuj
			disableControl("frame128");
			disableControl("button193");//Fetch Liab Button
			disableControl("button197");//DBR Button
			disableControl('Fetch_Exception');//Excption Button
			break;
		}
		case "LPU_Maker":{
			disableControl(maximumEligibilitySection);
			mandatoryControl("table117_FirstInstallmentDate");//anuj
			disableControl("frame128");
			disableAllFields();
			enableControl('frame133');
			disableControl("button193");//Fetch Liab Button
			disableControl('Loan_Reference_No');
			disableControl('button194');
			
			disableControl("button197");//DBR Button
			disableControl('Fetch_Exception');//Excption Button
			break;
		}
		case "LPU_Checker":{
			disableControl(maximumEligibilitySection);
			disableAllFields();
			mandatoryControl("table117_FirstInstallmentDate");//anuj
			disableControl("frame128");
			disableControl("button193");//Fetch Liab Button
			disableControl("button197");//DBR Button
			disableControl('Fetch_Exception');//Excption Button
			if(getValue('Loan_Reference_No')!="" || getValue('Product_Type')=='CC')
	        {
		      disableControl('button194');  
	        }
			break;
		}
		case "CPU":{
			disableControl(maximumEligibilitySection);
			disableAllFields();
			mandatoryControl("table117_FirstInstallmentDate");//anuj
			disableControl("frame128");
			disableControl("button193");//Fetch Liab Button
			disableControl("button197");//DBR Button
			disableControl('Fetch_Exception');//Excption Button
			break;
		}
		case "Card_Center_To_Courier":{
			disableControl(maximumEligibilitySection);
			mandatoryControl("table117_FirstInstallmentDate");//anuj
			disableControl("frame128");
			disableControl("button193");//Fetch Liab Button
			disableControl("button197");//DBR Button
			disableControl('Fetch_Exception');//Excption Button
			break;
		}
		
		case "Checker_TL_DSU":{
			
			disableControl(maximumEligibilitySection);
			disableAllFields();
			enableControl("MaximumEligibility");
			disableControl("button193");//Fetch Liab Button
			disableControl("button197");//DBR Button
			disableControl('Fetch_Exception');//Excption Button
			hideControl('Liabilities');
			hideControl('table117_ExternalVerficationResult');
			disableControl("frame128");
			
			break;
		}
		default:break;
	}
}

function IdMandateOnEgyptForLoad() //anuj
{
	
	if(compareStringsIgnoreCase(getValue("Applicant_Nationality"),"Egypt") || compareStringsIgnoreCase(getValue("Applicant_Nationality"),"EG"))
	{
		mandatoryControl("Applicant_National_ID");
	}
	else
	{
		nonMandatoryControl("Applicant_National_ID");
	}
	

	if(compareStringsIgnoreCase(getValue("Co_Applicant_Nationality"),"Egypt") || compareStringsIgnoreCase(getValue("Co_Applicant_Nationality"),"EG"))
	{
		mandatoryControl("Co_Applicant_National_ID");
	}
	else
	{
		nonMandatoryControl("Co_Applicant_National_ID");
	}
	
	
	if(compareStringsIgnoreCase(getValue("Guarantor_Nationality"),"Egypt") || compareStringsIgnoreCase(getValue("Guarantor_Nationality"),"EG"))
	{
		mandatoryControl("Guarantor_National_ID");
	}
	else
	{
		nonMandatoryControl("Guarantor_National_ID");
	}
	
}
