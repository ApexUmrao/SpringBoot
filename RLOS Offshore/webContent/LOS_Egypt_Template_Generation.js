function Loan_Application_TemplateData()
{

	       var attrbList = "";
	
	                    attrbList += "&<Branch_Name>&" +getValue('Branch_Name');//
						 attrbList += "&<Applicant_Employer_Name>&" + getValue('Applicant_Employer_Name');
						var loan_tenor = getValue('Loan_Tenor');
						// var loan_tenor = getValueFromColumnName("table117",0,"Tenor Monthly");
						if(loan_tenor == "12"  || loan_tenor == "24" || loan_tenor == "36" ||  loan_tenor == "60" ||loan_tenor == "72" ||loan_tenor == "84" )
						{
							attrbList += "&<Loan_Tenor>&" + getValue('Loan_Tenor');
						}
						else
						{
                        attrbList += "&<Loan_Tenor>&" + "Others" ;
						}		
						
						// attrbList += "&<Nationality>&" + getvalue('Applicant_Nationality');//
						
						
	                 
	                    attrbList += "&<Req_Loan_Amt>&" + getValue('Req_Loan_Amt');
	                    attrbList += "&<Applicant_Name>&" + getValue('Applicant_Name');
		                attrbList += "&<Applicant_DOB>&" + getValue('Applicant_DOB');
						 var Applicant_Nationality = getValue('Applicant_Nationality');
				 if(Applicant_Nationality == "EG")
						{
							attrbList += "&<Passport_No>&" + getValue('Applicant_National_ID');
							
						}
                        else{
							attrbList += "&<Passport_No>&" +   getValue('Applicant_Passport_Number');
						}
						
						
						var application_type = getValue('Application_Type');
				  var CO_Applicant_name = getValue('Co_Applicant_Name');
				  if(application_type == "Single")
				  {
				  attrbList +=  "&<Applicant_Name1>&" + getValue('Applicant_Name');
				  }
				  if(application_type == "Joint")
				  {
				  attrbList +=  "&<Applicant_Name1>&" + getValue('Applicant_Name') +"/ "+ CO_Applicant_name;
				  }
						
					 var Count = getGridRowCount("table107");
                      for (var i = 0; i < Count; i++) {
                     if (getValueFromTableCell("table107", i, 0) == 'Applicant')
			         {
                
                       // attrbList += "&<Nationality>&" + getValueFromColumnName("table107",i,"Nationality");
					    attrbList += "&<Nationality>&" + getSelectedItemLabel('Applicant_Nationality');

				        attrbList += "&<Marital_status>&" + getValueFromColumnName("table107",i,"Marital status ");
						
							
						
						
					    attrbList += "&<Applicant_Gender>&" + getValueFromColumnName("table107",i,"Gender");
						attrbList += "&<Number_of_Dependant>&" + getValueFromColumnName("table107",i,"Number of Dependant(s)");
						//attrbList += "&<Passport_No>&" + getValueFromColumnName("table107",i,"Passport No");
						attrbList += "&<Place_of_issue>&" + getValueFromColumnName("table107",i,"Place of issue (Passport)");
						attrbList += "&<Education Qualification>&" + getValueFromColumnName("table107",i,"Education Qualification"); 
						attrbList += "&<Customer_Mobile_number>&" + getValueFromColumnName("table107",i,"Customer Mobile number");
						attrbList += "&<Email Address>&" + getValueFromColumnName("table107",i,"Email Address");
						attrbList += "&<Employment_Type>&" + getValueFromColumnName("table107",i,"Employment Type");
						attrbList += "&<What_is_the_Name_of_your_bank_you_deal_with>&" + getValueFromColumnName("table107",i,"What is the Name of your bank you deal with"); 
						attrbList += "&<Account_type>&" + getValueFromColumnName("table107",i,"Account type");
						attrbList += "&<Number_of_Cards>&" + getValueFromColumnName("table107",i,"Number of Cards");
						attrbList += "&<Do_you_deal_with_AUB_Egypt_SAE>&" +  getValueFromColumnName("table107",i,"Do you deal with AUB Egypt SAE");
						attrbList += "&<Account_Number_at_AUB_Egypt_SAE>&" +   getValueFromColumnName("table107",i,"Account Number at AUB Egypt SAE");
						attrbList += "&<Reference_Name>&" +  getValueFromColumnName("table107",i,"Reference Name");
						attrbList += "&<Reference_Contact_Number>&" +  getValueFromColumnName("table107",i,"Reference Contact Number");
						 attrbList += "&<Please_provide_the_Type>&" + getValueFromColumnName("table107",i,"Please provide the Type");
						
					  }}
						 var Count = getGridRowCount("table108");
                      for (var i = 0; i < Count; i++) {
                     if (getValueFromTableCell("table108", i, 0) == 'Applicant')
			         {
						attrbList += "&<Address_Line1>&" + getValueFromColumnName("table108",i,"Address Line 1: House number/Flat no.");
						attrbList += "&<Address_Line2 : Building_Block>&" + getValueFromColumnName("table108",i,"Address Line 2 : Building & Block");
						attrbList += "&<Address_Line3>&" +  getValueFromColumnName("table108",i,"Address Line 3");
						attrbList += "&<PO_BOX_ZIP_Code>&" + getValueFromColumnName("table108",i,"PO BOX / ZIP Code");
						attrbList += "&<Residence_Status>&" + getValueFromColumnName("table108",i,"Residence Status");
						 var Residence = getValueFromColumnName("table108",i,"Residence Status");
						 attrbList += "&<Residence>&"  +  Residence;
						  attrbList += "&<City>&" + getValueFromColumnName("table108",i,"City");//No. of Years at  Address
						 attrbList += "&<No_of_Years_at_Address>&" + getValueFromColumnName("table108",i,"No. of Years at  Address");
						 
						
					 }
					  }
					  
					  
						 var Count = getGridRowCount("table117");
                      for (var i = 0; i < Count; i++) {
                     if (getValueFromTableCell("table117", i, 0) == 'PL')
			         {
                
				        attrbList += "&<Purpose_of_the_facility>&" + getValueFromColumnName("table117",i,"Purpose of the facility");
						 attrbList += "&<Car_Model>&" + getValueFromColumnName("table117",i,"Car Model");//Car Brand
					     attrbList += "&<Car_Brand>&" + getValueFromColumnName("table117",i,"Car Brand");
					  
					 }
					  }
					      var Count = getGridRowCount("table105");
                      for (var i = 0; i < Count; i++) {
                     if (getValueFromTableCell("table105", i, 0) == 'Applicant')
			         {
					     attrbList += "&<Additional_Income>&" + getValueFromColumnName("table105",i,"Additional Income"); 
						attrbList += "&<Source_of_additional_income>&" +  getValueFromColumnName("table105",i,"Source of additional income");
							attrbList += "&<Company_Segment>&" +  getValueFromColumnName("table105",i,"Company Segment");
						
					  }	}
						
						attrbList += "&<NetSalary>&" + getValue('NetSalary');
						attrbList += "&<Applicant_Name>&" + getValue('Applicant_Name');
						  var year = new Date().getFullYear();
						 var month =  new Date().getMonth();
						 var date = new Date().getDate();
						 month = parseInt(month) + parseInt("1");
						 var current_date_time = year + "/"+ month + "/" +date;
						 attrbList += "&<Current_Date>&" + current_date_time ;
						
						 
						 return attrbList;
						}
						
						
						
						
function PL_SecuredLoan_Contract_TemplateData()
{

        var attrbList = "";
	
						  attrbList += "&<Req_Loan_Amt>&" + getValue('Req_Loan_Amt');
					   var loan_tenor = getValue('Loan_Tenor');
	
						 var Count = getGridRowCount("table117");
                      for (var i = 0; i < Count; i++) {
                     if (getValueFromTableCell("table117", i, 0) == 'PL')
			         {
                
                        //attrbList += "&<Requested Loan Amount 1>&" + getValueFromColumnName("table117",i,"Requested Loan Amount 1");

				        attrbList += "&<Purpose_of_the_facility>&" + getValueFromColumnName("table117",i,"Purpose of the facility");//Admin Fees
						 attrbList += "&<Interest_Rate>&" + getValueFromColumnName("table117",i,"Interest Rate");
						  attrbList += "&<First_Installment_date_Monthly>&" + getValueFromColumnName("table117",i,"First Installment date Monthly");
                            attrbList += "&<Admin_Fees>&" + getValueFromColumnName("table117",i,"Admin Fees");
					  }}

                           attrbList += "&<Loan_Tenor>&" + loan_tenor;
						   
                           attrbList += "&<CustomerLoanAccountNumber>&" + getValue('CustomerLoanAccountNumber');
						   
						   return attrbList;
}




function PL_Unsecured_Contract_TemplateData()
{
	var attrbList ="";
	 var Count = getGridRowCount("table117");
                      for (var i = 0; i < Count; i++) {
                     if (getValueFromTableCell("table117", i, 0) == 'PL')
			         {
                
                        attrbList += "&<Requested Loan Amount 1>&" + getValueFromColumnName("table117",i,"Requested Loan Amount 1");
						 attrbList += "&<Purpose of the facility>&" + getValueFromColumnName("table117",i,"Purpose of the facility");
						   attrbList += "&<First Installment date 1>&" + getValueFromColumnName("table117",i,"First Installment date 1"); 
					  }}


                          attrbList += "&<Loan Tenor (in months)>&" + getValue('Loan Tenor (in months)');
                         attrbList += "&<CustomerLoanAccountNumber>&" + getValue('CustomerLoanAccountNumber');
						     
						    return attrbList; 
} 




function Clean_Loan_Contract_Personal_loan_Bulk_Installments_TemplateData() 
{
	
	          var  attrbList= "";
			  var year = new Date().getFullYear();
			 var month =  new Date().getMonth();
			 var date = new Date().getDate();
			 month = parseInt(month) + parseInt("1");
			 var current_date_time = year + "/"+ month + "/" +date;
			 attrbList += "&<Current_Date>&" + current_date_time ;
	          
	          var Count = getGridRowCount("table117");
                      for (var i = 0; i < Count; i++) {
                     if (getValueFromTableCell("table117", i, 0) == 'PL')
			         {
						 attrbList += "&<Requested Loan Amount 1>&" + getValueFromColumnName("table117",i,"Requested Loan Amount 1");
						 attrbList += "&<Purpose of the facility>&" + getValueFromColumnName("table117",i,"Purpose of the facility"); 
						  attrbList += "&<Installment Amount (Monthly)>&" + getValueFromColumnName("table117",i,"Installment Amount (Monthly)");
						   attrbList += "&<First Installment date 1>&" + getValueFromColumnName("table117",i,"First Installment date 1");
						   attrbList += "&<Interest Rate>&" + getValueFromColumnName("table117",i,"Interest Rate");
                
					  }}
					  
					   attrbList += "&<CustomerLoanAccountNumber>&" + getValue('CustomerLoanAccountNumber');
                        attrbList += "&<Loan Tenor (in months)>&" + getValue('Loan Tenor (in months)');

                         return attrbList; 
}



function Clean_Loan_Contract_Premium_Loan_TemplateData()
{
	                  var  attrbList= "";
					   var Count = getGridRowCount("table117");
                      for (var i = 0; i < Count; i++) {
                     if (getValueFromTableCell("table117", i, 0) == 'PL')
			         {
						 attrbList += "&<Requested Loan Amount 1>&" + getValueFromColumnName("table117",i,"Requested Loan Amount 1");
					      attrbList += "&<Purpose of the facility>&" + getValueFromColumnName("table117",i,"Purpose of the facility");
					  }}
					  return attrbList;
}



function Club_Membership_Contract_TemplateData()
{
	                 var  attrbList= "";
					  var Count = getGridRowCount("table117");
                      for (var i = 0; i < Count; i++) {
                     if (getValueFromTableCell("table117", i, 0) == 'PL')
			         {
						 attrbList += "&<Requested Loan Amount 1>&" + getValueFromColumnName("table117",i,"Requested Loan Amount 1");
						 attrbList += "&<First Installment date 1>&" + getValueFromColumnName("table117",i,"First Installment date 1");
						  attrbList += "&<Interest Rate>&" + getValueFromColumnName("table117",i,"Interest Rate");
						 
						 
					  }}
					  
					     attrbList += "&<CustomerLoanAccountNumber>&" + getValue('CustomerLoanAccountNumber');
						 attrbList += "&<Loan Tenor (in months)>&" + getValue('Loan Tenor (in months)');
	                   return attrbList;
}



 function Second_Salary_Contract__TemplateData()
 {
	           var  attrbList= "";
			    var year = new Date().getFullYear();
			 var month =  new Date().getMonth();
			 var date = new Date().getDate();
			 month = parseInt(month) + parseInt("1");
			 var current_date_time = year + "/"+ month + "/" +date;
			 attrbList += "&<Current_Date>&" + current_date_time ;
			   
				 var Count = getGridRowCount("table107");//customer details
                      for (var i = 0; i < Count; i++) {
                     if (getValueFromTableCell("table107", i, 0) == 'Applicant')
			         {
                         attrbList += "&<Cutomer Name>&" + getValueFromColumnName("table107",i,"Cutomer Name");  
	                     attrbList += "&<Customer National ID >&" + getValueFromColumnName("table107",i,"Customer National ID "); 
					  }}
					  
					   var Count = getGridRowCount("table108");
                      for (var i = 0; i < Count; i++) {
                     if (getValueFromTableCell("table108", i, 0) == 'Applicant')
			         {
					attrbList += "&<Address Line 1: House number/Flat no.>&" + getValueFromColumnName("table108",i,"Address Line 1: House number/Flat no.");
					  }}
					  
					   var Count = getGridRowCount("table117");//product_Details
                      for (var i = 0; i < Count; i++) {
                     if (getValueFromTableCell("table117", i, 0) == 'PL')
			         {
						  attrbList += "&<Purpose of the facility>&" + getValueFromColumnName("table117",i,"Purpose of the facility");  
						 
						 }}
                             return attrbList;
 }

function Student_loan_Declaration()
{
            var attrbList = "";
			var year = new Date().getFullYear();
			 var month =  new Date().getMonth();
			 var date = new Date().getDate();
			 month = parseInt(month) + parseInt("1");
			 var current_date_time = year + "/"+ month + "/" +date;
			 attrbList += "&<Current_Date>&" + current_date_time ;
				
	             var Count = getGridRowCount("table107");//customer details
                      for (var i = 0; i < Count; i++) {
                     if (getValueFromTableCell("table107", i, 0) == 'Applicant')
			         {
                         attrbList += "&<Cutomer Name>&" + getValueFromColumnName("table107",i,"Cutomer Name"); 
						
						  attrbList += "&<Name of University>&" + getValueFromColumnName("table107",i,"Name of University");
						   attrbList += "&<Customer National ID>&" + getValueFromColumnName("table107",i,"Customer National ID");//Customer Mobile number
						    attrbList += "&<Customer Mobile number>&" + getValueFromColumnName("table107",i,"Customer Mobile number");//Email Address
							 attrbList += "&<Email Address>&" + getValueFromColumnName("table107",i,"Email Address")
							}}
					  
				 var Count = getGridRowCount("table108");
                      for (var i = 0; i < Count; i++) {
                     if (getValueFromTableCell("table108", i, 0) == 'Applicant')
			         {
					attrbList += "&<Address Line 1: House number/Flat no.>&" + getValueFromColumnName("table108",i,"Address Line 1: House number/Flat no.");
					  }}
					  
					  return attrbList;
	
}	



function Undertaking_Letter_TemplateData()
{
	                 var attrbList= "";
					 var year = new Date().getFullYear();
					 var month =  new Date().getMonth();
					 var date = new Date().getDate();
					 month = parseInt(month) + parseInt("1");
					 var current_date_time = year + "/"+ month + "/" +date;
					 attrbList += "&<Current_Date>&" + current_date_time ;
	                   
						 attrbList += "&<Applicant_Joining_Date>&" + getValue('Applicant_Joining_Date');//Applicant_Joining_Date
						 attrbList += "&<Applicant_Employment_Type>&" + getValue('Applicant_Employment_Type');
						 attrbList += "&<NetSalary>&" + getValue('NetSalary');
						 
						
						var Count = getGridRowCount("table107");//customer details
                      for (var i = 0; i < Count; i++) {
                     if (getValueFromTableCell("table107", i, 0) == 'Applicant')
			         {
                         attrbList += "&<Cutomer Name>&" + getValueFromColumnName("table107",i,"Cutomer Name"); 
						 attrbList += "&<Customer National ID>&" + getValueFromColumnName("table107",i,"Customer National ID");
					  }}
					  
					  
					 var Count = getGridRowCount("table108");
                      for (var i = 0; i < Count; i++) {
                     if (getValueFromTableCell("table108", i, 0) == 'Applicant')
			         {
					attrbList += "&<Address Line 1: House number/Flat no.>&" + getValueFromColumnName("table108",i,"Address Line 1: House number/Flat no.");
					  }}
					  
					  
					   var Count = getGridRowCount("table117");
                      for (var i = 0; i < Count; i++) {
                     if (getValueFromTableCell("table117", i, 0) == 'PL')
			         {
						  attrbList += "&<Installment Amount (Monthly)>&" + getValueFromColumnName("table117",i,"Installment Amount (Monthly)");  
						 
					  }}
					  
					  return attrbList;
						 
}





function Undertaking_Letter_Annual_TemplateData()
{
	                 var attrbList= "";
					 var year = new Date().getFullYear();
					 var month =  new Date().getMonth();
					 var date = new Date().getDate();
					 month = parseInt(month) + parseInt("1");
					 var current_date_time = year + "/"+ month + "/" +date;
					 attrbList += "&<Current_Date>&" + current_date_time ;
	                   
	                    
						 attrbList += "&<Applicant_Joining_Date>&" + getValue('Applicant_Joining_Date');//Applicant_Joining_Date
						 attrbList += "&<Applicant_Employment_Type>&" + getValue('Applicant_Employment_Type');
						 attrbList += "&<NetSalary>&" + getValue('NetSalary');
						 
						
						var Count = getGridRowCount("table107");//customer details
                      for (var i = 0; i < Count; i++) {
                     if (getValueFromTableCell("table107", i, 0) == 'Applicant')
			         {
                         attrbList += "&<Cutomer Name>&" + getValueFromColumnName("table107",i,"Cutomer Name"); 
						 attrbList += "&<Customer National ID>&" + getValueFromColumnName("table107",i,"Customer National ID");
					  }}
					  
					  
					 var Count = getGridRowCount("table108");
                      for (var i = 0; i < Count; i++) {
                     if (getValueFromTableCell("table108", i, 0) == 'Applicant')
			         {
					attrbList += "&<Address Line 1: House number/Flat no.>&" + getValueFromColumnName("table108",i,"Address Line 1: House number/Flat no.");
					  }}
					  
					  
					   var Count = getGridRowCount("table117");
                      for (var i = 0; i < Count; i++) {
                     if (getValueFromTableCell("table117", i, 0) == 'PL')
			         {
						  attrbList += "&<Installment Amount (Monthly)>&" + getValueFromColumnName("table117",i,"Installment Amount (Monthly)"); //Installment Amount (Bulk) 
						   attrbList += "&<Installment Amount (Bulk)>&" + getValueFromColumnName("table117",i,"Installment Amount (Bulk)"); 
						 
					  }}
					  
					  return attrbList;
}	  
					  
					  
		function Letter_of_lien_TemplateData()

		{
			  var attrbList ="";
			  
			   attrbList += "&<CustomerLoanAccountNumber>&" + getValue('CustomerLoanAccountNumber');
			    attrbList += "&<Branch_Name>&" + getValue('Branch_Name');
				var year = new Date().getFullYear();
					 var month =  new Date().getMonth();
					 var date = new Date().getDate();
					 month = parseInt(month) + parseInt("1");
					 var current_date_time = year + "/"+ month + "/" +date;
					 attrbList += "&<Current_Date>&" + current_date_time ;
				 
				
				 var Count = getGridRowCount("table117");
                      for (var i = 0; i < Count; i++) {
                     if (getValueFromTableCell("table117", i, 0) == 'PL')
			         {
						 attrbList += "&<Requested Loan Amount 1>&" + getValueFromColumnName("table117",i,"Requested Loan Amount 1");
						   attrbList += "&<Interest Rate>&" + getValueFromColumnName("table117",i,"Interest Rate");
						 }}
                          
                        
						var Count = getGridRowCount("table107");//customer details
                      for (var i = 0; i < Count; i++) {
                     if (getValueFromTableCell("table107", i, 0) == 'Applicant')
			         {
                         attrbList += "&<Cutomer Name>&" + getValueFromColumnName("table107",i,"Cutomer Name");
                         attrbList += "&<Customer National ID>&" + getValueFromColumnName("table107",i,"Customer National ID");						 
					  
					  }}

                      var Count = getGridRowCount("table108");
                      for (var i = 0; i < Count; i++) {
                     if (getValueFromTableCell("table108", i, 0) == 'Applicant')
			         {
					attrbList += "&<Address Line 1: House number/Flat no.>&" + getValueFromColumnName("table108",i,"Address Line 1: House number/Flat no.");
					  }}
              return 	attrbList;
			  }	


function authorization_to_fill_data()
{
	                     var attrbList= "";
						 var year = new Date().getFullYear();
					 var month =  new Date().getMonth();
					 var date = new Date().getDate();
					 month = parseInt(month) + parseInt("1");
					 var current_date_time = year + "/"+ month + "/" +date;
					 attrbList += "&<Current_Date>&" + current_date_time ;
	                  
						
						var Count = getGridRowCount("table107");//customer details
                      for (var i = 0; i < Count; i++) {
                     if (getValueFromTableCell("table107", i, 0) == "Applicant")
			         {
                         attrbList += "&<Cutomer Name>&" + getValueFromColumnName("table107",i,"Cutomer Name"); 
					 }
					  }
					   return attrbList;
}



function Ban_on_Sale()
{
            var attrbList= "";
	                     var year = new Date().getFullYear();
					 var month =  new Date().getMonth();
					 var date = new Date().getDate();
					 month = parseInt(month) + parseInt("1");
					 var current_date_time = year + "/"+ month + "/" +date;
					 attrbList += "&<Current_Date>&" + current_date_time ;	
						
						 return attrbList;
}

function Letter_of_Payment_LOP()
{
	 var attrbList= "";
	                     var year = new Date().getFullYear();
					 var month =  new Date().getMonth();
					 var date = new Date().getDate();
					 month = parseInt(month) + parseInt("1");
					 var current_date_time = year + "/"+ month + "/" +date;
					 attrbList += "&<Current_Date>&" + current_date_time ;

                      return attrbList;						
	
}





function Car_Receving_Confirmation_letter()
{
	 var attrbList= "";
	                    var year = new Date().getFullYear();
					 var month =  new Date().getMonth();
					 var date = new Date().getDate();
					 month = parseInt(month) + parseInt("1");
					 var current_date_time = year + "/"+ month + "/" +date;
					 attrbList += "&<Current_Date>&" + current_date_time ;
						 return attrbList;
}



function Auto_Loan_Application()
{
    var attrbList = "";
	//var Others = "";
	var product=getValue('Product_Type');
	if(product == "AL")
	{
	                     attrbList += "&<Branch_Name>&" +getValue('Branch_Name');
                        attrbList += "&<Req_Loan_Amt>&" + getValue('Req_Loan_Amt');	
						 attrbList += "&<Applicant_Employer_Name>&" + getValue('Applicant_Employer_Name');
						 // attrbList += "&<Nationality>&" + getvalue('Applicant_Nationality');
						  attrbList += "&<Applicant_Length_of_Service>&" + getValue('Length of service in Months');
						   var Applicant_Nationality = getValue('Applicant_Nationality');
				 if(Applicant_Nationality == "EG")
						{
							attrbList += "&<Passport No>&" + getValue('Applicant_National_ID');
							
						}
                        else{
							attrbList += "&<Passport No>&" +   getValue('Applicant_Passport_Number');
						}
						  
						var loan_tenor = getValue('Loan_Tenor');
						if(loan_tenor == "12"  || loan_tenor == "24" || loan_tenor == "36" || loan_tenor == "48" || loan_tenor == "60" ||loan_tenor == "72" ||loan_tenor == "84" )
						{
							attrbList += "&<Loan_Tenor>&" + getValue('Loan_Tenor');
						}
						else
						{
                        attrbList += "&<Loan_Tenor>&" + "Others" ;
						}						
                       attrbList += "&<Applicant_Name>&" + getValue('Applicant_Name');
					    attrbList += "&<Applicant_DOB>&" + getValue('Applicant_DOB');
						
						var application_type = getValue('Application_Type');
				  var CO_Applicant_name = getValue('Co_Applicant_Name');
				  if(application_type == "Single")
				  {
				  attrbList +=  "&<Applicant_Name1>&" + getValue('Applicant_Name');
				  }
				  if(application_type == "Joint")
				  {
				  attrbList +=  "&<Applicant_Name1>&" + getValue('Applicant_Name') +"/ "+ CO_Applicant_name;
				  }
				 

                 var Count = getGridRowCount("table107");
                      for (var i = 0; i < Count; i++) {
                     if (getValueFromTableCell("table107", i, 0) == 'Applicant')
			         {
						 attrbList += "&<Customer National ID>&" + getValueFromColumnName("table107",i,"Customer National ID");//Customer National ID
						 attrbList += "&<Customer Mobile number>&" + getValueFromColumnName("table107",i,"Customer Mobile number");	
                        //  attrbList += "&<Nationality>&" + getValueFromColumnName("table107",i,"Nationality");//Customer National ID
						 attrbList += "&<Nationality>&" + getSelectedItemLabel('Applicant_Nationality');

				        attrbList += "&<Marital status>&" + getValueFromColumnName("table107",i,"Marital status ");
					    attrbList += "&<Applicant_Gender>&" + getValueFromColumnName("table107",i,"Gender");
						attrbList += "&<Number of Dependant(s)>&" + getValueFromColumnName("table107",i,"Number of Dependant(s)");
						//attrbList += "&<Passport No>&" + getValueFromColumnName("table107",i,"Passport No");
						attrbList += "&<Place of issue (Passport)>&" + getValueFromColumnName("table107",i,"Place of issue (Passport)");
						attrbList += "&<Education Qualification>&" + getValueFromColumnName("table107",i,"Education Qualification"); 



                        attrbList += "&<Employment Type>&" + getValueFromColumnName("table107",i,"Employment Type");
						attrbList += "&<What is the Name of your bank you deal with>&" + getValueFromColumnName("table107",i,"What is the Name of your bank you deal with"); 
						attrbList += "&<Account type>&" + getValueFromColumnName("table107",i,"Account type");
						attrbList += "&<Number of Cards>&" + getValueFromColumnName("table107",i,"Number of Cards");
						attrbList += "&<Do you deal with AUB Egypt SAE>&" +  getValueFromColumnName("table107",i,"Do you deal with AUB Egypt SAE");
						attrbList += "&<Account Number at AUB Egypt SAE>&" +   getValueFromColumnName("table107",i,"Account Number at AUB Egypt SAE");
						attrbList += "&<Reference Name>&" +  getValueFromColumnName("table107",i,"Reference Name");
						attrbList += "&<Reference Contact Number>&" +  getValueFromColumnName("table107",i,"Reference Contact Number");	
                attrbList += "&<Please provide the Type>&" + getValueFromColumnName("table107",i,"Please provide the Type");//No. of years in Egypt	

                  attrbList += "&<No. of years in Egypt>&" +  getValueFromColumnName("table107",i,"No. of years in Egypt");				
					 }
					  }
					 // var attrbList = "";
	
						 var Count = getGridRowCount("table117");
                      for (var i = 0; i < Count; i++) {
                     if (getValueFromTableCell("table117", i, 0) == 'AL')
			         {
                
                        attrbList += "&<Minimum Down payment required>&" + getValueFromColumnName("table117",i,"Minimum Down payment required");
						 attrbList += "&<Car Model>&" + getValueFromColumnName("table117",i,"Car Model");//Car Brand
					     attrbList += "&<Car Brand>&" + getValueFromColumnName("table117",i,"Car Brand");
					  
					 }
					  }
					  
					  
					   var Count = getGridRowCount("table108");//Minimum Down payment required
                      for (var i = 0; i < Count; i++) {
                     if (getValueFromTableCell("table108", i, 0) == 'Applicant')
			         {
					attrbList += "&<Address Line 1: House number/Flat no.>&" + getValueFromColumnName("table108",i,"Address Line 1: House number/Flat no.");
						attrbList += "&<Address Line 2 : Building_Block>&" + getValueFromColumnName("table108",i,"Address Line 2 : Building & Block");
						attrbList += "&<Address Line 3>&" +  getValueFromColumnName("table108",i,"Address Line 3");
						attrbList += "&<PO BOX / ZIP Code>&" + getValueFromColumnName("table108",i,"PO BOX / ZIP Code");
						attrbList += "&<Residence Status>&" + getValueFromColumnName("table108",i,"Residence Status");
						 var Residence = getValueFromColumnName("table108",i,"Residence Status");
						 attrbList += "&<Residence>&"  +  Residence;
						  attrbList += "&<City>&" + getValueFromColumnName("table108",i,"City");
						 
					 }
					  }
					  
					   var Count = getGridRowCount("table105");
                      for (var i = 0; i < Count; i++) {
                     if (getValueFromTableCell("table105", i, 0) == 'Applicant')
			         {
					     attrbList += "&<Additional Income>&" + getValueFromColumnName("table105",i,"Additional Income"); 
						attrbList += "&<Source of additional income>&" +  getValueFromColumnName("table105",i,"Source of additional income");
						attrbList += "&<Company Segment>&" +  getValueFromColumnName("table105",i,"Company Segment");
						
					  }	}
					  
					  attrbList += "&<NetSalary>&" + getValue('NetSalary');
					 // attrbList += "&<Current_Date>&" + new Date().getDate()+ "/"+ new Date().getMonth() + "/" + new Date().getFullYear();
					  
					   var year = new Date().getFullYear();
					 var month =  new Date().getMonth();
					 var date = new Date().getDate();
					 month = parseInt(month) + parseInt("1");
					 var current_date_time = year + "/"+ month + "/" +date;
					 attrbList += "&<Current_Date>&" + current_date_time ;	
	
	                   
	}
	
	                    return attrbList;
}


function Short_Form_Questionnaire_Loans_Insurance()
{
	var attrbList="";
	
	 attrbList += "&<Applicant_Name>&" + getValue('Applicant_Name');
	  attrbList += "&<Applicant_DOB>&" + getValue('Applicant_DOB');
	   attrbList += "&<Req_Loan_Amt>&" + getValue('Req_Loan_Amt');
	  
	  
	   var Count = getGridRowCount("table107");
                      for (var i = 0; i < Count; i++) {
                     if (getValueFromTableCell("table107", i, 0) == 'Applicant')
			         {
						 attrbList += "&<Customer National ID>&" + getValueFromColumnName("table107",i,"Customer National ID");//Customer National ID
						 attrbList += "&<Customer Mobile number>&" + getValueFromColumnName("table107",i,"Customer Mobile number");	
	
					  }}
					  
					  
					   return attrbList;
}







//Ajay RC Utilization 2 start
/* function RC_Utilization_Permits_NewGrant()
{
	var attrbList="";
	var Product_Type = getValue('Product_Type');
	var Sub_Product_Type= getValue('Sub_Product_Type');
	var Request_Type =  getValue('Request_Type');
	
	attrbList += "&<Applicant_Name>&" + getValue('Applicant_Name');
	var year = new Date().getFullYear();
	var month =  new Date().getMonth();
	var date = new Date().getDate();
	month = parseInt(month) + parseInt("1");
	var current_date_time = year + "/"+ month + "/" +date;
	attrbList += "&<Current_Date>&" + current_date_time ;
	

	  attrbList += "&<Customer_Name>&" + getValue('Applicant_Name');
	   attrbList += "&<Account_Number>&" + getValue('CustomerLoanAccountNumber');
	     // attrbList += "&<Product_Type>&" + getSelectedItemLabel('Product_Type');
		   attrbList += "&<Approved_loan_amount>&" + getValue('Credit_Approved_Amount');
		    attrbList += "&<Approved_By>&" + getValue('APPROVED_BY');
		  
	var Count = getGridRowCount("table117");
	for (var i = 0; i < Count; i++) 
	{
		if (getValueFromTableCell("table117", i, 0) == 'PL')
		{
			attrbList += "&<First_Installment_date_Monthly>&" + getValueFromColumnName("table117",i,"First Installment date Monthly");
			attrbList += "&<Product_Type>&" + getValue("PROGRAM_TYPE");//Last_installment_date
			attrbList += "&<Last_installment_date>&" + getValueFromColumnName("table117",i,"Last Instament Date");
			var last_inst = getValueFromColumnName("table117",i,"Last Instament Date");
			  attrbList += "&<Loan_Tenor>&" + getValue('Loan_Tenor') + "Months" + "  " + last_inst;
		}
	}
	
	return  attrbList;
}

function RC_Utilization_Permits_BuyOut()
{
	var attrbList="";
	var Product_Type = getValue('Product_Type');
	var Sub_Product_Type= getValue('Sub_Product_Type');
	var Request_Type =  getValue('Request_Type');
	
	attrbList += "&<Applicant_Name_Buy Out>&" + getValue('Applicant_Name');
	var year1 = new Date().getFullYear();
	var month1 =  new Date().getMonth();
	var date1 = new Date().getDate();
	month1 = parseInt(month1) + parseInt("1");
	var current_date_time1 = year1 + "/"+ month1 + "/" +date1;
	attrbList += "&<Current_Date>&" + current_date_time1 ;
	attrbList += "&<Product_Type_Buy Out>&" + getValue('Product_Type');
	attrbList += "&<Req_Loan_Amt_Buy Out>&" + getValue('Req_Loan_Amt');

	var Count = getGridRowCount("table117");
	for (var i = 0; i < Count; i++) 
	{
		if (getValueFromTableCell("table117", i, 0) == 'Personal Loan')
		{
			attrbList += "&<First Installment date 1_Buy Out>&" + getValueFromColumnName("table117",i,"First Installment date 1");
		}
	}
}

function RC_Utilization_Permits_TopUp()
{
	var attrbList="";
	var Product_Type = getValue('Product_Type');
	var Sub_Product_Type= getValue('Sub_Product_Type');
	var Request_Type =  getValue('Request_Type');
	
	attrbList += "&<Applicant_Name_Top Up>&" + getValue('Applicant_Name');
	var year2 = new Date().getFullYear();
	var month2 =  new Date().getMonth();
	var date2 = new Date().getDate();
	month2 = parseInt(month1) + parseInt("1");
	var current_date_time2 = year2 + "/"+ month2 + "/" +date2;
	attrbList += "&<Current_Date>&" + current_date_time2 ;
	attrbList += "&<Product_Type_Top Up>&" + getValue('Product_Type');
	attrbList += "&<Req_Loan_Amt_Top Up>&" + getValue('Req_Loan_Amt');

	var Count = getGridRowCount("table117");
	for (var i = 0; i < Count; i++) 
	{
		if (getValueFromTableCell("table117", i, 0) == 'Personal Loan')
		{
			attrbList += "&<First Installment date 1_Top Up>&" + getValueFromColumnName("table117",i,"First Installment date 1");
		}
	}
}

function RC_Utilization_Permits_TopUpAndBuyOut()
{
	var attrbList="";
	var Product_Type = getValue('Product_Type');
	var Sub_Product_Type= getValue('Sub_Product_Type');
	var Request_Type =  getValue('Request_Type');
	
	
	attrbList += "&<Applicant_Name_Top up+Buy out>&" + getValue('Applicant_Name');
	var year2 = new Date().getFullYear();
	var month2 =  new Date().getMonth();
	var date2 = new Date().getDate();
	month2 = parseInt(month1) + parseInt("1");
	var current_date_time2 = year2 + "/"+ month2 + "/" +date2;
	attrbList += "&<Current_Date>&" + current_date_time2 ;
	attrbList += "&<Product_Type_Top up+Buy out>&" + getValue('Product_Type');
	attrbList += "&<Req_Loan_Amt_Top up+Buy out>&" + getValue('Req_Loan_Amt');

	var Count = getGridRowCount("table117");
	for (var i = 0; i < Count; i++) 
	{
		if (getValueFromTableCell("table117", i, 0) == 'Personal Loan')
		{
			attrbList += "&<First Installment date 1_Top up+Buy out>&" + getValueFromColumnName("table117",i,"First Installment date 1");
		}
	}
}

function RC_Utilization_Permits_Secured()
{
	var attrbList="";
	var Product_Type = getValue('Product_Type');
	var Sub_Product_Type= getValue('Sub_Product_Type');
	var Request_Type =  getValue('Request_Type');
	
	attrbList += "&<Applicant_Name_Secured>&" + getValue('Applicant_Name');
	var year2 = new Date().getFullYear();
	var month2 =  new Date().getMonth();
	var date2 = new Date().getDate();
	month2 = parseInt(month1) + parseInt("1");
	var current_date_time2 = year2 + "/"+ month2 + "/" +date2;
	attrbList += "&<Current_Date>&" + current_date_time2 ;
	attrbList += "&<Product_Type_Secured>&" + getValue('Product_Type');
	attrbList += "&<Req_Loan_Amt_Secured>&" + getValue('Req_Loan_Amt');

	var Count = getGridRowCount("table117");
	for (var i = 0; i < Count; i++) 
	{
		if (getValueFromTableCell("table117", i, 0) == 'Personal Loan')
		{
			attrbList += "&<First Installment date 1_Secured>&" + getValueFromColumnName("table117",i,"First Installment date 1");
		}
	}
}

function RC_Utilization_Permits_Bulk()
{
	var attrbList="";
	var Product_Type = getValue('Product_Type');
	var Sub_Product_Type= getValue('Sub_Product_Type');
	var Request_Type =  getValue('Request_Type');
} */
	
//Ajay RC Utilization 2 end

      
	  function Credit_Card_App_Form()
      {
	      var product=getValue('Product_Type');
	      if(product == "CC")
	     {
	  var attrbList="";
	    attrbList += "&<Applicant_DOB>&" + getValue('Applicant_DOB');
		   attrbList += "&<Applicant_Name>&" + getValue('Applicant_Name');
		   
		     attrbList += "&<Applicant_National_ID>&" + getValue('Applicant_National_ID');
			  attrbList += "&<Credit_Card_Limit>&" + getValue('Credit_Card_Limit');//
		       attrbList += "&<Applicant_Employer_Name>&" + getValue('Applicant_Employer_Name');
			    attrbList += "&<Branch_Name>&" + getValue('Branch_Name');//Applicant_Length_of_Service
				 //attrbList += "&<Applicant_Length_of_Service>&" + getValue('Applicant_Length_of_Service');//Application_Type
				  var Applicant_Nationality = getValue('Applicant_Nationality');
				 if(Applicant_Nationality == "EG")
						{
							attrbList += "&<Passport No>&" + getValue('Applicant_National_ID');
							
						}
                        else{
							attrbList += "&<Passport No>&" +   getValue('Applicant_Passport_Number');
						}
				 
				  var application_type = getValue('Application_Type');
				  var CO_Applicant_name = getValue('Co_Applicant_Name');
				  if(application_type == "Single")
				  {
				  attrbList +=  "&<Applicant_Name1>&" + getValue('Applicant_Name');
				  }
				  if(application_type == "Joint")
				  {
				  attrbList +=  "&<Applicant_Name1>&" + getValue('Applicant_Name') +"/ "+ CO_Applicant_name;
				  }
				 
				    attrbList += "&<Applicant_Employment_Type>&" + getValue('Applicant_Employment_Type');
				  
				  var year = new Date().getFullYear();
			 var month =  new Date().getMonth();
			 var date = new Date().getDate();
			 month = parseInt(month) + parseInt("1");
			 var current_date_time = year + "/"+ month + "/" +date;
			 
			 attrbList += "&<year>&" + year ;
			  attrbList += "&<month>&" +  month;
			 attrbList += "&<Current_Date>&" + current_date_time ;
			 
			 
			  var Count = getGridRowCount("table137");
		
                      for (var i = 0; i < Count; i++) {
                     if (getValueFromTableCell("table137", i, 0) == 'Applicant') 
			         {
					 
                        attrbList += "&<Length_of_service>&" + getValueFromColumnName("table137",i,"Length of service in Months");
					 }
					  }
		  
		   
		    var Count = getGridRowCount("table107");
		
                      for (var i = 0; i < Count; i++) {
                     if (getValueFromTableCell("table107", i, 0) == 'Applicant')//Length of service 
			         {
					 
                       // attrbList += "&<Nationality>&" + getValueFromColumnName("table107",i,"Nationality");
						 attrbList += "&<Nationality>&" + getSelectedItemLabel('Applicant_Nationality');
					    attrbList += "&<Applicant_Gender>&" + getValueFromColumnName("table107",i,"Gender");
						attrbList += "&<Number of Dependant(s)>&" + getValueFromColumnName("table107",i,"Number of Dependant(s)");
						//attrbList += "&<Passport No>&" + getValueFromColumnName("table107",i,"Passport No");
						
						attrbList += "&<Customer Mobile number>&" + getValueFromColumnName("table107",i,"Customer Mobile number");
						attrbList += "&<Email Address>&" + getValueFromColumnName("table107",i,"Email Address");
					
						attrbList += "&<Marital>&" + getValueFromColumnName("table107",i,"Marital status ");
						attrbList += "&<Do you deal with AUB Egypt SAE>&" +  getValueFromColumnName("table107",i,"Do you deal with AUB Egypt SAE");	
						attrbList += "&<Account Number at AUB Egypt SAE>&" +   getValueFromColumnName("table107",i,"Account Number at AUB Egypt SAE");
						
						attrbList += "&<Reference Name>&" +  getValueFromColumnName("table107",i,"Reference Name");
						attrbList += "&<Reference Contact Number>&" +  getValueFromColumnName("table107",i,"Reference Contact Number");//new
						attrbList += "&<What is the Name of your bank you deal with>&" + getValueFromColumnName("table107",i,"What is the Name of your bank you deal with");
						
						attrbList += "&<Account type>&" + getValueFromColumnName("table107",i,"Account type");
						attrbList += "&<Number of Cards>&" + getValueFromColumnName("table107",i,"Number of Cards");//Account type
						attrbList += "&<Account type>&" + getValueFromColumnName("table107",i,"Account type");//Please provide the Type
						attrbList += "&<Please provide the Type>&" + getValueFromColumnName("table107",i,"Please provide the Type");//Education Qualification
						attrbList += "&<Date_of_Issue_NID>&" + getValueFromColumnName("table107",i,"Date of Issue  (NID)");//Date of Issue (Passport)
				         attrbList += "&<Date_of_Issue_Passport>&" + getValueFromColumnName("table107",i,"Date of Issue (Passport)"); 
						
						
						
					 }
					  }
					  
					   var Count = getGridRowCount("table117");
                      for (var i = 0; i < Count; i++) {
                     if (getValueFromTableCell("table117", i, 0) == 'CC')
			         {
                
				        attrbList += "&<Type_of_card>&" + getValueFromColumnName("table117",i,"Type of card");
					 }
					  }
					  
					    var Count = getGridRowCount("table129");//Collateral Type
                      for (var i = 0; i < Count; i++) {
                    
				        attrbList += "&<Collateral_Type>&" + getValueFromColumnName("table129",i,"Collateral Type");
						 attrbList += "&<Available_Collateral_Value>&" + getValueFromColumnName("table129",i,"Available Collateral Value");
					
					 }
					  
					  
					  
					  
					  
					   var Count = getGridRowCount("table108");
                      for (var i = 0; i < Count; i++) {
                     if (getValueFromTableCell("table108", i, 0) == 'Applicant')
			         {
				attrbList += "&<Address Line 1: House number/Flat no.>&" + getValueFromColumnName("table108",i,"Address Line 1: House number/Flat no.");
				attrbList += "&<PO BOX / ZIP Code>&" + getValueFromColumnName("table108",i,"PO BOX / ZIP Code");//Residence Status
				attrbList += "&<Residence Status>&" + getValueFromColumnName("table108",i,"Residence Status");//No. of Years at  Address
				attrbList += "&<No_of_Years_at_Address>&" + getValueFromColumnName("table108",i,"No. of Years at  Address");
				 attrbList += "&<City>&" + getValueFromColumnName("table108",i,"City");//No. of Years at  Address
						 
					 }
					  }
		   
		    var Count = getGridRowCount("table105");
                      for (var i = 0; i < Count; i++) {
                     if (getValueFromTableCell("table105", i, 0) == 'Applicant')
			         {
					     attrbList += "&<Additional Income>&" + getValueFromColumnName("table105",i,"Additional Income"); 
						attrbList += "&<Source of additional income>&" +  getValueFromColumnName("table105",i,"Source of additional income");
						attrbList += "&<Company Segment>&" +  getValueFromColumnName("table105",i,"Company Segment");
						
					  }	}
					  
					 var Count = getGridRowCount("table133");
					var card_type= getValueFromColumnName("table133",0,"Card Type");
                      for (var i = 0; i < Count; i++) {
                    
					     attrbList += "&<Debit Type>&" + getValueFromColumnName("table133",i,"Debit Type"); 
						
                       // attrbList += "&<Card Type>&" + getValueFromColumnName("table133",i,"Card Type");//
					   
					  }
					 
					  
					  
					   var Count = getGridRowCount("table140");//new
                      for (var i = 0; i < Count; i++) {
					  if(Applicant_Nationality == "Egypt")
					  {
                       attrbList += "&<ID number>&" + getValueFromColumnName("table140",i,"ID number / Passport number"); 
						}
						else{
						attrbList += "&<passport number>&" + getValueFromColumnName("table140",i,"ID number / Passport number");
						}
						attrbList += "&<Name>&" + getValueFromColumnName("table140",i,"Name");//Relationship
						var Relationship= getValueFromColumnName("table140",i,"Relationship");
						
						if(Relationship != "Son" ||  Relationship != "Daughter" || Relationship != "Brother" ||  Relationship != "Sister" ||  Relationship != "Parent"  || Relationship != "Spouse"  )
						{
							attrbList += "&<Relationship>&" + "Others";//Others
						}
						
						if(Relationship == "Son" ||  Relationship == "Daughter")
						{
							attrbList += "&<Relationship>&" + "Son/Daughter";//Son/Daughter
						}
						
						if(Relationship == "Brother" ||  Relationship == "Sister")
						{
							attrbList += "&<Relationship>&" + "Brother/Sister";//Brother/Sister
						}
						
						if(Relationship == "Parent" )
						{
							attrbList += "&<Relationship>&" + "Parent";//Parent
						}
						if(Relationship == "Spouse" )
						{
							attrbList += "&<Relationship>&" + "Spouse";//Spouse
						}
						
						//attrbList += "&<Relationship>&" + getValueFromColumnName("table140",i,"Relationship");
						
						var dob =  getValueFromColumnName("table140",i,"Date of Birth");
						
                         var array = new Array();

                        array = dob.split('/');
						 var date = array[2];
						var month = array[1];
					    var year = array[0];
						attrbList += "&<year_dob>&" + year;
			            attrbList += "&<month_dob>&" + month;
			             attrbList += "&<date_dob>&" + date;
						
						
						
					attrbList += "&<Supplementary Limit>&" + getValueFromColumnName("table140",i,"Supplementary Limit");//Percentage Limit in Amount
					attrbList += "&<Percentage Limit in Amount>&" + getValueFromColumnName("table140",i,"Percentage Limit in Amount");//Date of Issuance
						
                      attrbList += "&<Date of Issuance>&" + getValueFromColumnName("table140",i,"Date of Issuance");  						 
					  }
					  
					  var Count = getGridRowCount("table117");
	                    for (var i = 0; i < Count; i++) 
	                     {
		               if (getValueFromTableCell("table117", i, 0) == 'CC')
		               {
			  
			           attrbList += "&<Car Model>&" + getValueFromColumnName("table117",i,"Car Model");//Car Brand
					     attrbList += "&<Car Brand>&" + getValueFromColumnName("table117",i,"Car Brand");
						 }
	                    }
			
						attrbList += "&<NetSalary>&" + getValue('NetSalary');
						
						 return attrbList; 
	
	}
}

// added by disha - 11-Oct-2021 for NID template
function NIDReport(responseXML)
{
	var nidResponse =  responseXML.split(",");
	
				var Address = nidResponse[0];
				var BirthDate = nidResponse[1];
				var CardExpirationDate = nidResponse[2];
				var FamilyName= nidResponse[3];
				var Gender= nidResponse[4];
				var Governorate= nidResponse[5];
				var MotherFamilyName= nidResponse[6];
				var MotherFirstName= nidResponse[7];
				var NationalIdNumber=nidResponse[8];
				var PersonName= nidResponse[9];
				var PoliceStation = nidResponse[10];
				var ReferenceNumber=nidResponse[11];
				var fcn = nidResponse[12];
				var RequestNumber=nidResponse[13];
				var RequestTimestamp=nidResponse[14];
				var RequestTime=nidResponse[15];

			var attrbList="";

	      attrbList += "&<DOB>&" + BirthDate;
		   attrbList += "&<First_Name>&" + PersonName;
		   attrbList += "&<Family_Name>&" + FamilyName;
		   attrbList += "&<Request_Date>&" + RequestTimestamp + RequestTime;
		   
		     attrbList += "&<National_ID_No>&" + NationalIdNumber;
			 attrbList += "&<Request_Number>&" + RequestNumber;
			 attrbList += "&<User_Name>&" + getWorkItemData("username");
			 attrbList += "&<police_station>&" + PoliceStation;
			 attrbList += "&<governorate>&" + Governorate;
			 var year = new Date().getFullYear();
			 var month =  new Date().getMonth();
			 var date = new Date().getDate();
			 month = parseInt(month) + parseInt("1");
			 var current_date_time = year + "/"+ month + "/" +date;
			 attrbList += "&<Current_date_time>&" + current_date_time ;
			 
		
			attrbList += "&<address>&" + Address;
			attrbList += "&<gender>&" + Gender;
			attrbList += "&<Reference_No>&" +  ReferenceNumber;
			attrbList += "&<Mother_Name>&" +  MotherFirstName; 
			attrbList += "&<NID_ExpiryDate>&" +  CardExpirationDate;
			attrbList += "&<FCN>&" +  fcn;
					
					  
			 return attrbList;
}

function UnsecuredPL_ApprovalMemo()
{
	var attrbList="";
	     attrbList += "&<DOB>&" + getValue('Applicant_DOB');
		   attrbList += "&<First_Name>&" + getValue('Applicant_Name');
		    var year = new Date().getFullYear();
			 var month =  new Date().getMonth();
			 var date = new Date().getDate();
			 month = parseInt(month) + parseInt("1");
			 var current_date_time = year + "/"+ month + "/" +date;
			 attrbList += "&<Current_date_time>&" + current_date_time ;
		   
		   attrbList += "&<WI_Name>&" + getWorkItemData("processinstanceid");//refrence
		   attrbList += "&<Workstep_Name>&" + "New";//
		   attrbList += "&<Branch_Name>&" +getValue('Branch_Name');

		   attrbList += "&<Request_Type>&" + getValue('Request_Type');//type of loan
		   attrbList += "&<Account_Number>&" + getValue('CustomerLoanAccountNumber');
		   attrbList += "&<DOB>&" + getValue('Applicant_DOB');
		   attrbList += "&<Customer_Name>&" + getValue('Applicant_Name');
		    attrbList += "&<Customer_Type>&" + getValue("Applicant_Employment_Type");
			attrbList += "&<Company_Segment>&" + getValue("Company_Segment");
			 attrbList += "&<Tenor_Monthly>&" + getValue("Loan_Tenor");//Tenor_Yr
			 var loan_tenor = getValue("Loan_Tenor");
			 var Tenor_Yr =(parseFloat(loan_tenor/12));
			 Tenor_Yr = Tenor_Yr.toFixed(2);
			  attrbList += "&<Tenor_Yr>&" +  Tenor_Yr;
			  
			 attrbList += "&<NetSalary>&" + getValue('NetSalary');
			attrbList += "&<Age>&" +  getValue('Applicant_Age');
			attrbList += "&<Career_Level>&" + getValue('Applicant_Career_Level');
			attrbList += "&<Company_Tier>&" + getValue('Applicant_Employer_Grade');
			attrbList += "&<Source>&" + getValue('Acquisition_Channel');
			 
			    attrbList += "&<Total_Liability>&" + getValue('DisposableIncomeWithoutOverTime');
				  attrbList += "&<Protest_and_Bankruptcy>&" + getValue('Protest_and_Bankruptcy');
				   attrbList += "&<Negative_List>&" + getValue('Negative_List');
				    attrbList += "&<IScore_Rate>&" + getValue('I_Score_Rating');
					 attrbList += "&<Nationality>&" + getSelectedItemLabel('Applicant_Nationality');
				     attrbList += "&<Installment>&" + getValue('Credit_Approved_Installment');
			
				  attrbList += "&<IScore_Evaluation>&" + getValue('IScore_Grade');
				   attrbList += "&<Approval_Limit>&" + getValue('Credit_Approved_Amount');
				   attrbList += "&<Age_at_maturity>&" + getValue('Age_at_Maturity');//Net_Monthly_Salary
				      attrbList += "&<Net_Monthly_Salary>&" + getValue('NetSalary');
				 
			var Applicant_Nationality = getValue('Applicant_Nationality');
						if(Applicant_Nationality == "EG")
						{
							attrbList += "&<Passport>&" + getValue('Applicant_National_ID');
							
						}
                        else{
							attrbList += "&<Passport>&" +   getValue('Applicant_Passport_Number');
						}
			
		
		   attrbList += "&<DBR>&" + getValue('DBR_POLICY');
		 
		   attrbList += "&<IScore_Rate>&" + getValue('I_Score_Rating');
		   
		   var Count = getGridRowCount("table107");
           for (var i = 0; i < Count; i++) 
		   {
			 if (getValueFromTableCell("table107", i, 0) == 'Applicant')
			 {               
				
				attrbList += "&<Marital_Status>&" + getValueFromColumnName("table107",i,"Marital status ");					
				attrbList += "&<Gender>&" + getValueFromColumnName("table107",i,"Gender");
				
	
			  }
		 }
		  var Count = getGridRowCount("table108");
          for (var i = 0; i < Count; i++) 
		  {
			 if (getValueFromTableCell("table108", i, 0) == 'Applicant')
			 {
				attrbList += "&<Area>&" + getValueFromColumnName("table108",i,"Area Type");//Country
				attrbList += "&<Country>&" + getValueFromColumnName("table108",i,"Country");
				
			 }
			}
		var Count = getGridRowCount("table137");
        for (var i = 0; i < Count; i++) 
		  {
			 if (getValueFromTableCell("table137", i, 0) == 'Applicant')
			 {
				attrbList += "&<Employer_Name>&" + getValueFromColumnName("table137",i,"Employer Name");
				attrbList += "&<Hiring_Date>&" + getValueFromColumnName("table137",i,"Joining Date");//Customer_Position
				attrbList += "&<Customer_Position>&" + getValueFromColumnName("table137",i,"Customer Position");
			 }
			}
		var Count = getGridRowCount("table117");
         for (var i = 0; i < Count; i++) 
		 {
			 if (getValueFromTableCell("table117", i, 0) == 'PL')
			 {
				
				  var Monthly_installment = getValueFromColumnName("table117",i,"Monthly Installment Amount");//Monthly Installment Amount
				  var quar_inst = (parseFloat(Monthly_installment * 3));
				  quar_inst = quar_inst.toFixed(2);
				   attrbList += "&<Installment_Quarterly>&" + quar_inst;
				 
				  
				  
				   attrbList += "&<First_Installment_Date>&" + getValueFromColumnName("table117",i,"First Installment date Monthly");
				   attrbList += "&<Interest_Rate>&" + getValueFromColumnName("table117",i,"Interest Rate");
				   attrbList += "&<External_Verification>&" + getValueFromColumnName("table117",i,"External Verification Type");
				   
					attrbList += "&<Admin_Fees>&" + getValueFromColumnName("table117",i,"Admin Fees");
					
					
				   
		
			  }
		}
		 var Count = getGridRowCount("table105");
                      for (var i = 0; i < Count; i++) {
                     if (getValueFromTableCell("table105", i, 0) == 'Applicant')
			         {
					     attrbList += "&<Additional_Income>&" + getValueFromColumnName("table105",i,"Additional Income"); //Program type
						   attrbList += "&<Product_Type>&" + getValue("PROGRAM_TYPE"); //Income Type
						    /* var Income_type = getValueFromColumnName("table105",i,"Income Type");
							if(Income_type == 'Yearly Net Salary')
							{
								 attrbList += "&<Net_Monthly_Salary>&" + getValueFromColumnName("table105",i,"Total Income Amount (Monthly)");
							} */
						 attrbList += "&<Additional_Income>&" + getValueFromColumnName("table105",i,"Additional Income");
						  attrbList += "&<Product_Type>&" + getValue("PROGRAM_TYPE");//Product name
						
					  }	}
						
return attrbList;				
}





/* function UnsecuredCC_ApprovalMemo()
{
	var attrbList="";
          attrbList += "&<DOB>&" + getValue('Applicant_DOB');
		   attrbList += "&<First_Name>&" + getValue('Applicant_Name');
		   var year = new Date().getFullYear();
			 var month =  new Date().getMonth();
			 var date = new Date().getDate();
			 month = parseInt(month) + parseInt("1");
			 var current_date_time = year + "/"+ month + "/" +date;
			 attrbList += "&<Current_date_time>&" + current_date_time ;
			 
		   
		   attrbList += "&<WI_Name>&" + getWorkItemData("processinstanceid");
		   attrbList += "&<Workstep_Name>&" + getWorkItemData("ActivityName");
		   attrbList += "&<Branch_Name>&" +getValue('Branch_Name');
		   attrbList += "&<Product_Type>&" + "Un Secured";
		   attrbList += "&<Request_Type>&" + getValue('Request_Type');
		   attrbList += "&<Account_Number>&" + getValue('CustomerLoanAccountNumber');
		   attrbList += "&<DOB>&" + getValue('Applicant_DOB');
		   attrbList += "&<Customer_Name>&" + getValue('Applicant_Name');
		    attrbList += "&<Customer_Type>&" + getValue("Application_Type");
			attrbList += "&<Company_Segment>&" + getValue("Applicant_Sector");
			 attrbList += "&<Tenor_Monthly>&" + getValue('Loan Tenor (in months)');
			 attrbList += "&<Tenor_Yr>&" + getValue('Loan Tenor (in months)') / 12;
			 attrbList += "&<NetSalary>&" + getValue('NetSalary');
			attrbList += "&<Age>&" +  getValue('Applicant_Age');
			attrbList += "&<Career_Level>&" + getValue('Applicant_Career_Level');
			attrbList += "&<Company_Tier>&" + getValue('Applicant_Employer_Grade');
			
			var Applicant_Nationality = getValue('Applicant_Nationality');
						if(Applicant_Nationality == "EG")
						{
							attrbList += "&<Passport>&" + getValue('Applicant_National_ID');
							
						}
                        else{
							attrbList += "&<Passport>&" +   getValue('Applicant_Passport_Number');
						}
						
			
			//attrbList += "&<Passport>&" + getValue('Applicant_Passport_Number');
			//attrbList += "&<Country>&" + getValue('Applicant_Nationality');
			 attrbList += "&<Country>&" + getSelectedItemLabel('Applicant_Nationality');
		   attrbList += "&<Gross_Salary>&" + getValue("GrossSalary");
		   attrbList += "&<Gross_Monthly_Salary>&" + getValue("GrossSalary") / 12;
		   attrbList += "&<DBR>&" + getValue('DBR_POLICY');
		   attrbList += "&<Age_at_maturity>&" + getValue('Age_at_Maturity');
		   attrbList += "&<IScore_Rate>&" + getValue('I_Score_Rating');
		    attrbList += "&<Negative_List>&" + getValue('Negative_List');
			 attrbList += "&<Protest>&" + getValue('Protest_and_Bankruptcy');
		     attrbList += "&<Credit_Card_Limit>&" + getValue('Credit_Card_Limit');
		   
		   var Count = getGridRowCount("table107");
           for (var i = 0; i < Count; i++) 
		   {
			 if (getValueFromTableCell("table107", i, 0) == 'Applicant')
			 {               
				//attrbList += "&<Nationality>&" + getValueFromColumnName("table107",i,"Nationality");
				attrbList += "&<Marital_Status>&" + getValueFromColumnName("table107",i,"Marital status ");					
				attrbList += "&<Gender>&" + getValueFromColumnName("table107",i,"Gender");
				//attrbList += "&<Passport>&" + getValueFromColumnName("table107",i,"Passport No");
	
			  }
		 }
		  var Count = getGridRowCount("table108");
          for (var i = 0; i < Count; i++) 
		  {
			 if (getValueFromTableCell("table108", i, 0) == 'Applicant')
			 {
				attrbList += "&<Area>&" + getValueFromColumnName("table108",i,"Area Type");
				
			 }
			}
			var Count = getGridRowCount("table117");
	        for (var i = 0; i < Count; i++) 
	         {
		      if (getValueFromTableCell("table117", i, 0) == 'CC')
		      {
			  
			  attrbList += "&<Credit_card _expiry_date>&" + getValueFromColumnName("table117",i,"Credit card expiry date");//Credit card expiry date
	  
		      }
	        }
			
		
		var Count = getGridRowCount("table137");
        for (var i = 0; i < Count; i++) 
		  {
			 if (getValueFromTableCell("table137", i, 0) == 'Applicant')
			 {
				attrbList += "&<Employer_Name>&" + getValueFromColumnName("table137",i,"Employer Name");
				attrbList += "&<Hiring_Date>&" + getValueFromColumnName("table137",i,"Joining Date");
				
			 }
			}
		
return attrbList;				
	     
}
 */

/* function Secured_CC_Approval_Memo()
{
 var attrbList="";
	  attrbList += "&<Applicant_Name>&" + getValue('Applicant_Name');
	  attrbList += "&<Branch_Name>&" +getValue('Branch_Name');
	  attrbList += "&<Applicant_DOB>&" + getValue('Applicant_DOB');
	  attrbList += "&<Applicant_Employer_Name>&" + getValue('Applicant_Employer_Name');
	  attrbList += "&<I_Score_Rating>&" + getValue('I_Score_Rating');
	  attrbList += "&<Negative_List>&" + getValue('Negative_List');
      attrbList += "&<Protest_and_Bankruptcy>&" + getValue('Protest_and_Bankruptcy');
	  attrbList += "&<Product_Type>&" + getValue('Product_Type');
	  attrbList += "&<Request_Type>&" + getValue('Request_Type');
      attrbList += "&<Credit_Card_Limit>&" + getValue('Credit_Card_Limit');	
      attrbList += "&<Applicant_Age>&" + getValue('Applicant_Age');
	  attrbList += "&<Applicant_Career_Level>&" + getValue('Applicant_Career_Level');
	  attrbList += "&<Source>&" + getValue('Acquisition_Channel');
	   attrbList += "&<Nationality>&" + getSelectedItemLabel('Applicant_Nationality');
	  
	  attrbList += "&<WorkItemName>&" + "New";
	  
	  var Applicant_Nationality = getValue('Applicant_Nationality');

						if(Applicant_Nationality == "EG")
						{
							attrbList += "&<Customer_National_ID>&" + getValue('Applicant_National_ID');
							
						}
                        else{
							attrbList += "&<Customer_National_ID>&" +   getValue('Applicant_Passport_Number');
						}
						
	 
	  
	  var year = new Date().getFullYear();
			 var month =  new Date().getMonth();
			 var date = new Date().getDate();
			 month = parseInt(month) + parseInt("1");
			 var current_date_time = year + "/"+ month + "/" +date;
			 attrbList += "&<Current_Date>&" + current_date_time ;
	
		 

	var Count = getGridRowCount("table107");
    for (var i = 0; i < Count; i++) 
	{
       if (getValueFromTableCell("table107", i, 0) == 'Applicant')
		 {

			attrbList += "&<Account_Number_at_AUB_Egypt_SAE>&" +   getValueFromColumnName("table107",i,"Account Number at AUB Egypt SAE");		 
		
			attrbList += "&<Applicant_Gender>&" + getValueFromColumnName("table107",i,"Gender");
			attrbList += "&<Marital_status>&" + getValueFromColumnName("table107",i,"Marital status ");
			
		 }
	}
		   
	var Count = getGridRowCount("table108");
	  for (var i = 0; i < Count; i++) 
	  {
		 if (getValueFromTableCell("table108", i, 0) == 'Applicant')
		 {
			attrbList += "&<Area_Type>&" + getValueFromColumnName("table108",i,"Area Type");
			attrbList += "&<Country>&" + getValueFromColumnName("table108",i,"Country");						
		 }
	  }
					  
	var Count = getGridRowCount("table117");
	  for (var i = 0; i < Count; i++) 
	  {
		 if (getValueFromTableCell("table117", i, 0) == 'CC')
		 {
			  attrbList += "&<Purpose_of_the_facility>&" + getValueFromColumnName("table117",i,"Purpose of the facility");	
			  attrbList += "&<Type_of_card>&" + getValueFromColumnName("table117",i,"Type of card"); 				 
			  attrbList += "&<Credit_card_expiry_date>&" + getValueFromColumnName("table117",i,"Credit card expiry date");//Credit card expiry date
			  attrbList += "&<Interest_Rate>&" + getValueFromColumnName("table117",i,"Interest Rate");
			  	attrbList += "&<Age_at_maturity>&" + getValueFromColumnName("table117",i,"Age at Loan maturity");
	  
		 }
	 }
	var Count = getGridRowCount("table129");
    for (var i = 0; i < Count; i++) 
	{
		 if (getValueFromTableCell("table129", i, 3) == 'Applicant')
		 {
		   attrbList += "&<Collateral_Type>&" + getValueFromColumnName("table129",i,"Collateral Type");
		   attrbList += "&<Chosen_Collateral_Value>&" + getValueFromColumnName("table129",i,"Chosen Collateral Value");
		   attrbList += "&<Collateral_Currency>&" +  "EGP";
		 }
	}				  
		return attrbList;
	
} */

function Unsecured_AL_Approval_Memo()
{
var attrbList="";
	
	  var year = new Date().getFullYear();
			 var month =  new Date().getMonth();
			 var date = new Date().getDate();
			 month = parseInt(month) + parseInt("1");
			 var current_date_time = year + "/"+ month + "/" +date;
			 attrbList += "&<Current_date_time>&" + current_date_time ;
	
 attrbList += "&<Account_Number>&" + getValue('CustomerLoanAccountNumber');	
  attrbList += "&<WI_Name>&" + getWorkItemData("processinstanceid");  	
 attrbList += "&<Branch_Name>&" +getValue('Branch_Name');
 attrbList += "&<Applicant_Name>&" + getValue('Applicant_Name'); 
  attrbList += "&<Customer_Type>&" + getValue('Applicant_Employment_Type');
   attrbList += "&<Company_Segment>&" + getValue('Company_Segment');
   attrbList += "&<Company_Tier>&" + getValue('Applicant_Employer_Grade');//Loan_Tenor
   attrbList += "&<Career_Level>&" + getValue('Applicant_Career_Level');
   // attrbList += "&<>&" + getWorkItemData("ActivityName");//Product_Type
	 attrbList += "&<Type_of_loan>&" + getValue('Request_Type');
	 // attrbList += "&<Product_Type>&" + "Un Secured";
	   attrbList += "&<Loan_Tenor>&" + getValue('Loan_Tenor');
	   var loan_tenor = getValue("Loan_Tenor");
			 var Tenor_Yr =(parseFloat(loan_tenor/12));
			 Tenor_Yr = Tenor_Yr.toFixed(2);
			  attrbList += "&<Loan_tenor_year>&" +  Tenor_Yr;
	   
	    attrbList += "&<DBR>&" + getValue('DBR_POLICY');
		attrbList += "&<Age>&" +  getValue('Applicant_Age');
	//	attrbList += "&<Age_at_maturity>&" + getValue('Age_at_Maturity');
		 attrbList += "&<IScore_Rate>&" + getValue('I_Score_Rating');
		 attrbList += "&<Negative_List>&" + getValue('Negative_List');
		  attrbList += "&<Protest>&" + getValue('Protest_and_Bankruptcy');
		    attrbList += "&<Acquisition_Channel>&" + getValue('Acquisition_Channel');
			 attrbList += "&<New>&" +  "New";
			  attrbList += "&<Total_Liability>&" + getValue('DisposableIncomeWithoutOverTime');//Seller_Name
			   attrbList += "&<Seller_Name>&" + getValue('Seller_Name');
			    attrbList += "&<Seller_Code>&" + getValue('Seller_Code');//Installment_Amount
				 attrbList += "&<Installment_Amount>&" + getValue('Credit_Approved_Amount');
				 // attrbList += "&<Installment_Quarterly>&" + getValue('Credit_Approved_Installment');//Approval_Limit
				    attrbList +=  "&<Approval_Limit>&" + getValue('Credit_Approved_Amount');//I-Score Grade
					  attrbList +=  "&<IScore_Grade>&" + getValue('IScore_Grade');
					   attrbList += "&<Net_Monthly_Salary>&" + getValue('NetSalary');
		
  
 var Applicant_Nationality = getValue('Applicant_Nationality');

						if(Applicant_Nationality == "EG")
						{
							attrbList += "&<Passport_No>&" + getValue('Applicant_National_ID');
							
						}
                        else{
							attrbList += "&<Passport_No>&" +   getValue('Applicant_Passport_Number');
						}
						
		attrbList += "&<Applicant_DOB>&" + getValue('Applicant_DOB');				
						
var Count = getGridRowCount("table107");
    for (var i = 0; i < Count; i++) 
	{
       if (getValueFromTableCell("table107", i, 0) == 'Applicant')
		 {

			
			attrbList += "&<Applicant_Gender>&" + getValueFromColumnName("table107",i,"Gender");
			attrbList += "&<Marital_status>&" + getValueFromColumnName("table107",i,"Marital status ");
			
		 }
	}

 var Count = getGridRowCount("table108");
	  for (var i = 0; i < Count; i++) 
	  {
		 if (getValueFromTableCell("table108", i, 0) == 'Applicant')
		 {
			attrbList += "&<Area_Type>&" + getValueFromColumnName("table108",i,"Area Type");
									
		 }
	  }	
	  
	  var Count = getGridRowCount("table137");
        for (var i = 0; i < Count; i++) 
		  {
			 if (getValueFromTableCell("table137", i, 0) == 'Applicant')
			 {
				attrbList += "&<Employer_Name>&" + getValueFromColumnName("table137",i,"Employer Name");
				attrbList += "&<Hiring_Date>&" + getValueFromColumnName("table137",i,"Joining Date");
				attrbList += "&<Customer_Position>&" + getValueFromColumnName("table137",i,"Customer Position");
				
			 }
			}
				


   var Count = getGridRowCount("table117");
         for (var i = 0; i < Count; i++) 
		 {
			 if (getValueFromTableCell("table117", i, 0) == 'AL')
			 {
				
				     var Monthly_installment = getValueFromColumnName("table117",i,"Monthly Installment Amount");//Monthly Installment Amount
				  var quar_inst = (parseFloat(Monthly_installment * 3));
				  quar_inst = quar_inst.toFixed(2);
				   attrbList += "&<Installment_Quarterly>&" + quar_inst;
				   
				   attrbList += "&<First_Installment_Date>&" + getValueFromColumnName("table117",i,"First Installment date Monthly");
				   attrbList += "&<Interest_Rate>&" + getValueFromColumnName("table117",i,"Interest Rate");
				   attrbList += "&<External_Verification>&" + getValueFromColumnName("table117",i,"External Verification Type");
				   attrbList += "&<Dealer_Name>&" + getValueFromColumnName("table117",i,"Dealer Name");
				    attrbList += "&<Car_Brand>&" + getValueFromColumnName("table117",i,"Car Brand"); 
					 attrbList += "&<Vehicle_Price>&" + getValueFromColumnName("table117",i,"Vehicle Price ");
					  attrbList += "&<Admin_Fees>&" + getValueFromColumnName("table117",i,"Admin Fees");
					   attrbList += "&<Car_Model>&" + getValueFromColumnName("table117",i,"Car Model");
					    attrbList += "&<Year_of_manufacture>&" + getValueFromColumnName("table117",i,"Year of manufacture");
				
					 attrbList += "&<Category_of_vehicle>&" + getValueFromColumnName("table117",i,"Category of vehicle");
					  // attrbList += "&<Installment_Amount>&" + getValueFromColumnName("table117",i,"Monthly Installment Amount");
					  // 	attrbList += "&<Installment_Quarterly>&" + getValueFromColumnName("table117",i,"Instalment Amount Quarterly");
						attrbList += "&<Age_at_maturity>&" + getValueFromColumnName("table117",i,"Age at Loan maturity");//Minimum Down payment required
						 attrbList += "&<Minimum_Down_payment_required>&" + getValueFromColumnName("table117",i,"Minimum Down payment required");	
				   
				   
		
			  }
		}	

      var Count = getGridRowCount("table105");
                      for (var i = 0; i < Count; i++) {
                     if (getValueFromTableCell("table105", i, 0) == 'Applicant')
			         {
						 attrbList += "&<Product_Type>&" + getValue("PROGRAM_TYPE"); 
					     attrbList += "&<Additional_Income>&" + getValueFromColumnName("table105",i,"Additional Income"); //Total Income Amount (Monthly)
						attrbList += "&<Income_Amount_Monthly>&" +  getValueFromColumnName("table105",i,"Total Income Amount (Monthly)");
						 
						
					  }	}


		return attrbList;	
						
						
	
}



function Secured_Facility_Approval_Memo()
{
	var attrbList="";
	
	 var year = new Date().getFullYear();
			 var month =  new Date().getMonth();
			 var date = new Date().getDate();
			 month = parseInt(month) + parseInt("1");
			 var current_date_time = year + "/"+ month + "/" +date;
			 attrbList += "&<Current_date_time>&" + current_date_time ;
	
 attrbList += "&<Account_Number>&" + getValue('CustomerLoanAccountNumber');	
  //attrbList += "&<WI_Name>&" + getWorkItemData("processinstanceid");  	
 attrbList += "&<Branch_Name>&" +getValue('Branch_Name');
  attrbList += "&<Source>&" + getValue('Acquisition_Channel');
   attrbList += "&<Applicant_Name>&" + getValue('Applicant_Name');
     attrbList += "&<Applicant_DOB>&" + getValue('Applicant_DOB');
	  attrbList += "&<Applicant_Employer_Name>&" + getValue('Applicant_Employer_Name');
	   attrbList += "&<IScore_Rate>&" + getValue('I_Score_Rating');
	   attrbList += "&<Negative_List>&" + getValue('Negative_List');
	    attrbList += "&<Protest_and_Bankruptcy>&" + getValue('Protest_and_Bankruptcy');
		 attrbList += "&<Application_status>&" + "New";
		 // attrbList += "&<Product_Name>&" + getValue('Product_Type');
		   attrbList += "&<Request_Type>&" + getValue('Request_Type');
		     attrbList += "&<Loan_Tenor>&" + getValue('Loan_Tenor');//Evaluation
			 attrbList +=  "&<Evaluation>&" + getValue('I-Score Grade');//Facility_amount
			  attrbList +=  "&<Facility_amount>&" + getValue('Credit_Approved_Amount');
			   attrbList +=  "&<Purpose_of_the_facility>&" + getSelectedItemLabel('Product_Type');
				  attrbList +=  "&<Collateral_acc_number>&" + getValue('Collateral_CIF');
				    //attrbList += "&<Product_Name>&" + getValue('Sub_Product_Type');
					 attrbList +=  "&<Evaluation>&" + getValue('IScore_Grade');
			  
			  
		 
   var Applicant_Nationality = getValue('Applicant_Nationality');

						if(Applicant_Nationality == "EG")
						{
							attrbList += "&<Passport_No>&" + getValue('Applicant_National_ID');
							
						}
                        else{
							attrbList += "&<Passport_No>&" +   getValue('Applicant_Passport_Number');
						}
						
						
						
						var Count = getGridRowCount("table107");
    for (var i = 0; i < Count; i++) 
	{
       if (getValueFromTableCell("table107", i, 0) == 'Applicant')
		 {

			
			attrbList += "&<Applicant_Gender>&" + getValueFromColumnName("table107",i,"Gender");
			attrbList += "&<Marital_status>&" + getValueFromColumnName("table107",i,"Marital status ");
			 attrbList += "&<Nationality>&" + getSelectedItemLabel('Applicant_Nationality');
			
		 }
	}
	
	
	var Count = getGridRowCount("table105");
                      for (var i = 0; i < Count; i++) {
                     if (getValueFromTableCell("table105", i, 0) == 'Applicant')
			         {
						 
	  attrbList += "&<Product_Name>&" + getValue("PROGRAM_TYPE"); //Income Type
	
					 }
					  }


	
	
	
	var Count = getGridRowCount("table108");
	  for (var i = 0; i < Count; i++) 
	  {
		 if (getValueFromTableCell("table108", i, 0) == 'Applicant')
		 {
			attrbList += "&<Area_Type>&" + getValueFromColumnName("table108",i,"Area Type");
			attrbList += "&<Country>&" + getValueFromColumnName("table108",i,"Country");
									
		 }
	  }
	  
	  
	  
	   var Count = getGridRowCount("table137");
        for (var i = 0; i < Count; i++) 
		  {
			 if (getValueFromTableCell("table137", i, 0) == 'Applicant')
			 {
				
				attrbList += "&<Customer_Position>&" + getValueFromColumnName("table137",i,"Customer Position");
				
			 }
			}
	  
	  
	  
						 var Count = getGridRowCount("table117");
                      for (var i = 0; i < Count; i++) {
                    // if (getValueFromTableCell("table117", i, 0) 
			         //{
                
				        attrbList += "&<Purpose_of_the_facility>&" + getValueFromColumnName("table117",i,"Purpose of the facility");
					    attrbList += "&<Car_Model>&" + getValueFromColumnName("table117",i,"Car Model");
					    attrbList += "&<Car_Brand>&" + getValueFromColumnName("table117",i,"Car Brand");
						 attrbList += "&<Admin_Fees>&" + getValueFromColumnName("table117",i,"Admin Fees");
						  attrbList += "&<Interest_Rate>&" + getValueFromColumnName("table117",i,"Interest Rate");
						    attrbList += "&<Type_of_card>&" + getValueFromColumnName("table117",i,"Type of card");
							 attrbList += "&<Category_of_vehicle>&" + getValueFromColumnName("table117",i,"Category of vehicle");
							  attrbList += "&<Type_of_vehicle>&" + getValueFromColumnName("table117",i,"Type of vehicle");
							    attrbList += "&<Dealer_Name>&" + getValueFromColumnName("table117",i,"Dealer Name");
								 attrbList += "&<Car_Model>&" + getValueFromColumnName("table117",i,"Car Model");
								  attrbList += "&<Year_of_manufacture>&" + getValueFromColumnName("table117",i,"Year of manufacture"); 
								  attrbList += "&<Vehicle_Price>&" + getValueFromColumnName("table117",i,"Vehicle Price ");
                         //  attrbList += "&<Minimum_Down_payment_required>&" + getValueFromColumnName("table117",i,"Minimum Down payment required");								  
					  
					 //}
					  }
					  
					  
					  var Count = getGridRowCount("table129");
    for (var i = 0; i < Count; i++) 
	{
		 if (getValueFromTableCell("table129", i, 3) == 'Applicant')
		 {
		   attrbList += "&<Collateral_Type>&" + getValueFromColumnName("table129",i,"Collateral Type");
		   attrbList += "&<Chosen_Collateral_Value>&" + getValueFromColumnName("table129",i,"Chosen Collateral Value");
		   attrbList += "&<Collateral_Currency>&" +  "EGP";
		 }
	}
	
	return attrbList;
	
}



function Unsecured_PL_Bulk_Memo()
{
	var attrbList="";
	var attrbList="";
	 var year = new Date().getFullYear();
			 var month =  new Date().getMonth();
			 var date = new Date().getDate();
			 month = parseInt(month) + parseInt("1");
			 var current_date_time = year + "/"+ month + "/" +date;
			attrbList += "&<Current_Date>&" + current_date_time ;

 attrbList += "&<Refrence>&" + getWorkItemData("processinstanceid");	
attrbList += "&<Account Number>&" + getValue('CustomerLoanAccountNumber');//Refrence	
attrbList += "&<Branch_Name>&" + getValue('Branch_Name');
attrbList += "&<Customer_Name>&" + getValue('Applicant_Name');
attrbList += "&<Customer_Type>&" + getValue('Applicant_Employment_Type');
attrbList += "&<Company_Segment>&" + getValue("Company_Segment");//
attrbList += "&<Career_Level>&" + getValue('Applicant_Career_Level');
attrbList += "&<Company_Tier>&" + getValue('Applicant_Employer_Grade');
attrbList += "&<Date_Of_Birth>&" + getValue('Applicant_DOB');				
//attrbList += "&<DBR>&" + getValue('DBR_Percentage');
 attrbList += "&<DBR>&" + getValue('DBR_POLICY');
attrbList += "&<Age>&" + getValue('Applicant_Age');
attrbList += "&<I_Score_Evaluation>&" + getValue('IScore_Grade');
attrbList += "&<I_Score_Rate>&" + getValue('I_Score_Rating');
attrbList += "&<Negative_Lists>&" + getValue('Negative_List');
attrbList += "&<Negative_List>&" + getValue('Negative_List');
attrbList += "&<Protest_Bankruptcy>&" + getValue('Protest_and_Bankruptcy');//Request_Type
attrbList += "&<Type_Of_Loan>&" + getValue('Request_Type');	//Net_Income
attrbList += "&<Net_Income>&" + getValue('NetSalary');


var Applicant_Nationality = getValue('Applicant_Nationality');
						if(Applicant_Nationality == "EG")
						{
							attrbList += "&<Passport>&" + getValue('Applicant_National_ID');
							
						}
                        else{
							attrbList += "&<Passport>&" +   getValue('Applicant_Passport_Number');
						}

var Count = getGridRowCount("table107");
    for (var i = 0; i < Count; i++) 
	{
       if (getValueFromTableCell("table107", i, 0) == 'Applicant')
		 {

			
			attrbList += "&<Gender>&" + getValueFromColumnName("table107",i,"Gender");
			attrbList += "&<Marital_Status>&" + getValueFromColumnName("table107",i,"Marital status ");
			
		 }
	}
 var Count = getGridRowCount("table108");
	  for (var i = 0; i < Count; i++) 
	  {
		 if (getValueFromTableCell("table108", i, 0) == 'Applicant')
		 {
			attrbList += "&<Area_Type>&" + getValueFromColumnName("table108",i,"Area Type");
									
		 }
	  }	
	  
	  var Count = getGridRowCount("table137");
        for (var i = 0; i < Count; i++) 
		  {
			 if (getValueFromTableCell("table137", i, 0) == 'Applicant')
			 {
				attrbList += "&<Employer_Name>&" + getValueFromColumnName("table137",i,"Employer Name");
				attrbList += "&<Hiring_Date>&" + getValueFromColumnName("table137",i,"Joining Date");
					attrbList += "&<Customer_Position>&" + getValueFromColumnName("table137",i,"Customer Position");
				
			 }
			}
			
			
 
  var Count = getGridRowCount("table105");
                      for (var i = 0; i < Count; i++) {
                     if (getValueFromTableCell("table105", i, 0) == 'Applicant')
			         {
						   attrbList += "&<Product_Name>&" + getValue("PROGRAM_TYPE"); 
						  attrbList += "&<Additional_Income>&" + getValueFromColumnName("table105",i,"Additional Income");
						
						
					  }	}




							  
					  
 var Count = getGridRowCount("table117");
                      for (var i = 0; i < Count; i++) 
					  {
						 if (getValueFromTableCell("table117", i, 0) == 'PL')
			           {
				   attrbList += "&<External_Verfication>&" + getValueFromColumnName("table117",i,"External Verification Type");
						  
                        attrbList += "&<Monthly_Installment>&" + getValueFromColumnName("table117",i,"Installment Amount Monthly");	
				         attrbList += "&<Loan_Interest>&" + getValueFromColumnName("table117",i,"Interest Rate");	
						 attrbList += "&<Admin_Fees>&" + getValueFromColumnName("table117",i,"Admin Fees");
						 attrbList += "&<Yearly_Installment>&" + getValueFromColumnName("table117",i,"Installment Amount Yearly");  
					 attrbList += "&<Half_Yearly_Installment>&" + getValueFromColumnName("table117",i,"Installment Amount Semi Annualy");
					 
						 attrbList += "&<First_Installment_Date>&" + getValueFromColumnName("table117",i,"First Installment date Yearly");
						 attrbList += "&<Monthly_First_Installment_Date>&" + getValueFromColumnName("table117",i,"First Installment date Monthly");						 
						 attrbList += "&<Quaterly_Installment>&" + getValueFromColumnName("table117",i,"Installment Amount Quarterly");
						  var Monthly_installment = getValueFromColumnName("table117",i,"Monthly Installment Amount");//Monthly Installment Amount
				  /* var quar_inst = (parseFloat(Monthly_installment * 3));
				  quar_inst = quar_inst.toFixed(2);
				   attrbList += "&<Quaterly_Installment>&" + quar_inst; */
						 
						  attrbList += "&<Age_Loan_Maturity>&" + getValueFromColumnName("table117",i,"Age at Loan maturity");
					   }
						 	 
					  }
attrbList += "&<Application_Status>&" +  "New";
//attrbList += "&<Product_Name>&" +  getSelectedItemLabel('Product_Type');
attrbList += "&<Approval_Limit>&" + getValue('Credit_Approved_Amount');  
attrbList += "&<Source>&" + getValue('Acquisition_Channel');
attrbList += "&<Sales_Name>&" + getValue('Seller_Name'); 
attrbList += "&<Sales_Id>&" + getValue('Seller_Code');  
attrbList += "&<Total_Liabilities>&" + getValue('DisposableIncomeWithoutOverTime'); 
attrbList += "&<Type_Of_Bulk_Payment>&" + getValue('Bulk_Payment'); 
attrbList += "&<Monthly_Approval_Limit>&" + getValueFromColumnName("table117",0,"Requested Loan Amount Monthly"); 
attrbList += "&<Bulk_Inst_Approval_Limit>&" + getValueFromColumnName("table117",0,"Requested Loan Amount Yearly"); 
   attrbList += "&<Loan_Tenor>&" + getValue('Loan_Tenor');
   attrbList += "&<Bulk_Payment>&" + getValueFromColumnName("table117",0,"Tenor Yearly");


return attrbList;
}


function UnsecuredCC_ApprovalMemo_1()
{         
    var attrbList="";
         attrbList += "&<Reference_Id>&" + getWorkItemData("processinstanceid");
          attrbList += "&<Account_Number>&" + getValue('CustomerLoanAccountNumber');
	      attrbList += "&<DOB>&" + getValue('Applicant_DOB');
          attrbList += "&<Customer_Type>&" + getValue("Applicant_Employment_Type");
			attrbList += "&<Company_Segment>&" + getValue("Company_Segment");
			attrbList += "&<Career_Level>&" + getValue('Applicant_Career_Level');
			attrbList += "&<Company_Tier>&" + getValue('Applicant_Employer_Grade');
			attrbList += "&<Branch_Name>&" + getValue('Branch_Name');
			attrbList += "&<Source>&" + getValue('Acquisition_Channel');
			attrbList += "&<DBR>&" + getValue('DBR_POLICY');
attrbList += "&<Age>&" + getValue('Applicant_Age');
attrbList += "&<Customer_Name>&" + getValue('Applicant_Name');
attrbList += "&<I_Score_Evaluation>&" + getValue('IScore_Grade');
attrbList += "&<I_Score_Rate>&" + getValue('I_Score_Rating');
attrbList += "&<Negative_Lists>&" + getValue('Negative_List');
 attrbList += "&<NetSalary>&" + getValue('NetSalary');
 attrbList += "&<Customer_Request>&" + getValue('Request_Type');
 attrbList += "&<Credit_Card_Payment>&" +  "Monthly";
 attrbList += "&<Protest_Bankruptcy>&" + getValue('Protest_and_Bankruptcy');
attrbList += "&<Age_Card_Expiration>&" + getValue('Age_at_Maturity');//change on sit
attrbList += "&<Sales_Name>&" + getValue('Seller_Name'); 
attrbList += "&<Sales_Id>&" + getValue('Seller_Code'); 
attrbList += "&<AUB_CC_Monthly_Payment>&" + getValue('Credit_Approved_Installment');  
 
attrbList += "&<Approval_Limit>&" + getValue('Credit_Approved_Amount'); 
//attrbList += "&<Other_Liabilities>&" + getValue('DisposableIncomeWithoutOverTime'); 
attrbList += "&<Total_Liabilities>&" + getValue('DisposableIncomeWithoutOverTime'); 
   
 
			
			
			           var Applicant_Nationality = getValue('Applicant_Nationality');
						if(Applicant_Nationality == "EG")
						{
							attrbList += "&<Passport>&" + getValue('Applicant_National_ID');
							
						}
                        else{
							attrbList += "&<Passport>&" +   getValue('Applicant_Passport_Number');
						}
						
						
						
						
						var Count = getGridRowCount("table107");
    for (var i = 0; i < Count; i++) 
	{
       if (getValueFromTableCell("table107", i, 0) == 'Applicant')
		 {

			
			attrbList += "&<Gender>&" + getValueFromColumnName("table107",i,"Gender");
			attrbList += "&<Marital_Status>&" + getValueFromColumnName("table107",i,"Marital status ");
			
		 }
	}
	
	
	
	    var Count = getGridRowCount("table108");
          for (var i = 0; i < Count; i++) 
		  {
			 if (getValueFromTableCell("table108", i, 0) == 'Applicant')
			 {
				attrbList += "&<Area_type>&" + getValueFromColumnName("table108",i,"Area Type");//Country
			
				
			 }
			}
			
	
	
	   var Count = getGridRowCount("table137");
          for (var i = 0; i < Count; i++) 
		  {
			 if (getValueFromTableCell("table137", i, 0) == 'Applicant')
			 {
				attrbList += "&<Employer_Name>&" + getValueFromColumnName("table137",i,"Employer Name");
				attrbList += "&<Hiring_Date>&" + getValueFromColumnName("table137",i,"Joining Date");
				attrbList += "&<Customer_Position>&" + getValueFromColumnName("table137",i,"Customer Position");
				
			 }
			}
			
			
			
			var Count = getGridRowCount("table117");
	  for (var i = 0; i < Count; i++) 
	  {
		 if (getValueFromTableCell("table117", i, 0) == 'CC')
		 {
			  
			 attrbList += "&<Type_of_card>&" + getValueFromColumnName("table117",i,"Type of card"); 	
             attrbList += "&<Interest_rate>&" + getValueFromColumnName("table117",i,"Interest Rate");	
             attrbList += "&<External_Verification>&" + getValueFromColumnName("table117",i,"External Verification Type");
             attrbList += "&<Credit_Card_Net_Salary>&" + getValueFromColumnName("table117",i,"Credit Card Limit/Total Net salary ");			 
		 }
	 }
	 
	  var Count = getGridRowCount("table105");
                      for (var i = 0; i < Count; i++) {
                     if (getValueFromTableCell("table105", i, 0) == 'Applicant')
			         {
					    
						  attrbList += "&<Product_Name>&" + getValue("PROGRAM_TYPE");
						  /*  var Income_type = getValueFromColumnName("table105",i,"Income Type");
							if(Income_type == 'Yearly Net Salary')
							{
								 attrbList += "&<NetSalary>&" + getValueFromColumnName("table105",i,"Total Income Amount (Monthly)");
							} */
						
					  }	}
	
return attrbList;					
}



function RC_Utilization_Permits_NewGrant()
{
	var attrbList="";
	var Product_Type = getValue('Product_Type');
	var Sub_Product_Type= getValue('Sub_Product_Type');
	var Request_Type =  getValue('Request_Type');
	
	attrbList += "&<Applicant_Name>&" + getValue('Applicant_Name');
	var year = new Date().getFullYear();
	var month =  new Date().getMonth();
	var date = new Date().getDate();
	month = parseInt(month) + parseInt("1");
	var current_date_time = year + "/"+ month + "/" +date;
	attrbList += "&<Current_Date>&" + current_date_time ;
	

	  attrbList += "&<Customer_Name>&" + getValue('Applicant_Name');
	   attrbList += "&<Account_Number>&" + getValue('CustomerLoanAccountNumber');
	     // attrbList += "&<Product_Type>&" + getSelectedItemLabel('Product_Type');
		   attrbList += "&<Approved_loan_amount>&" + getValue('Credit_Approved_Amount');
		    attrbList += "&<Approved_By>&" + getValue('APPROVED_BY');
		  
	var Count = getGridRowCount("table117");
	for (var i = 0; i < Count; i++) 
	{
		if (getValueFromTableCell("table117", i, 0) == 'PL')
		{
			attrbList += "&<First_Installment_date_Monthly>&" + getValueFromColumnName("table117",i,"First Installment date Monthly");
			attrbList += "&<Product_Type>&" + getValue("PROGRAM_TYPE");//Last_installment_date
			attrbList += "&<Last_installment_date>&" + getValueFromTableCell("table117",i,46);
			var last_inst = getValueFromTableCell("table117",i,46);
			  attrbList += "&<Loan_Tenor>&" + getValue('Loan_Tenor') + "Months" + "  " + last_inst;
		}
	}
	
	return  attrbList;
}

function RC_Utilization_Permits_BuyOut()
{
	var attrbList="";
	var Product_Type = getValue('Product_Type');
	var Sub_Product_Type= getValue('Sub_Product_Type');
	var Request_Type =  getValue('Request_Type');
	

	  attrbList += "&<Customer_Name>&" + getValue('Applicant_Name');
	  attrbList += "&<Account_Number>&" + getValue('CustomerLoanAccountNumber');
	var year1 = new Date().getFullYear();
	var month1 =  new Date().getMonth();
	var date1 = new Date().getDate();
	month1 = parseInt(month1) + parseInt("1");
	var current_date_time1 = year1 + "/"+ month1 + "/" +date1;
	
	attrbList += "&<Current_Date>&" + current_date_time1 ;
	
	attrbList +=  "&<Purpose_of_the_facility>&" + getSelectedItemLabel('Product_Type');//Product_Type_Buy_Out
	 // attrbList += "&<Approved_loan_amount>&" + getValue('Credit_Approved_Amount');//amount
	  attrbList += "&<amount>&" + getValue('Credit_Approved_Amount');
	   attrbList += "&<Approved_By>&" + getValue('APPROVED_BY');
	  // attrbList += "&<Loan_Tenor>&" + getValue('Loan_Tenor');
	

	var Count = getGridRowCount("table117");
	for (var i = 0; i < Count; i++) 
	{
		if (getValueFromTableCell("table117", i, 0) == 'PL')
		{
			attrbList += "&<First_Installment_date_Monthly>&" + getValueFromColumnName("table117",i,"First Installment date Monthly");
			attrbList += "&<Product_Type_Buy_Out>&" + getValue("PROGRAM_TYPE");
			var last_inst = getValueFromTableCell("table117",i,46);
			  attrbList += "&<Loan_Tenor>&" + getValue('Loan_Tenor') + "Months" + "  " + last_inst;
			  	attrbList += "&<Expiry>&" + getValueFromTableCell("table117",i,46);
		}
	}
	
	var Count = getGridRowCount("table116");
	for (var i = 0; i < Count; i++) 
	{
		  attrbList += "&<Approved_loan_amount>&" + getValueFromColumnName("table116",i,"Amount");	
	     attrbList += "&<Bank>&" + getValueFromColumnName("table116",i,"Beneficiary Bank Name");	
	
	}
	
	return attrbList;
	
}

function RC_Utilization_Permits_TopUp()
{
	var attrbList="";
	var Product_Type = getValue('Product_Type');
	var Sub_Product_Type= getValue('Sub_Product_Type');
	var Request_Type =  getValue('Request_Type');
	
	attrbList += "&<Applicant_Name>&" + getValue('Applicant_Name');
	  attrbList += "&<Account_Number>&" + getValue('CustomerLoanAccountNumber');
	var year2 = new Date().getFullYear();
	var month2 =  new Date().getMonth();
	var date2 = new Date().getDate();
	month2 = parseInt(month2) + parseInt("1");
	var current_date_time2 = year2 + "/"+ month2 + "/" +date2;
	attrbList += "&<Current_Date>&" + current_date_time2 ;
	
	 attrbList += "&<Approved_loan_amount>&" + getValue('Credit_Approved_Amount');
	   attrbList += "&<Approved_By>&" + getValue('APPROVED_BY');
	 
	  

	var Count = getGridRowCount("table117");
	for (var i = 0; i < Count; i++) 
	{
		if (getValueFromTableCell("table117", i, 0) == 'PL')
		{
			attrbList += "&<First_Installment_date_Monthly>&" + getValueFromColumnName("table117",i,"First Installment date Monthly");
			attrbList += "&<Product_Type>&" + getValue("PROGRAM_TYPE");
			var last_inst = getValueFromTableCell("table117",i,46);
			  attrbList += "&<Loan_Tenor>&" + getValue('Loan_Tenor') + "Months" + "  " + last_inst;
			  attrbList += "&<Expiry>&" + getValueFromTableCell("table117",i,46);
		}
	}
	
	return attrbList;
	
}

function RC_Utilization_Permits_TopUpAndBuyOut()
{
	var attrbList="";
	var Product_Type = getValue('Product_Type');
	var Sub_Product_Type= getValue('Sub_Product_Type');
	var Request_Type =  getValue('Request_Type');
	
	
	attrbList += "&<Applicant_Name>&" + getValue('Applicant_Name');
	var year2 = new Date().getFullYear();
	var month2 =  new Date().getMonth();
	var date2 = new Date().getDate();
	month2 = parseInt(month2) + parseInt("1");
	var current_date_time2 = year2 + "/"+ month2 + "/" +date2;
	attrbList += "&<Current_Date>&" + current_date_time2 ;

	  attrbList += "&<Account_Number>&" + getValue('CustomerLoanAccountNumber');
	
	attrbList +=  "&<Purpose_of_the_facility>&" + getSelectedItemLabel('Product_Type');
	
	 attrbList += "&<Approved_loan_amount>&" + getValue('Credit_Approved_Amount');//Approved_By
	  attrbList += "&<Approved_By>&" + getValue('APPROVED_BY');
	 

	var Count = getGridRowCount("table117");
	for (var i = 0; i < Count; i++) 
	{
		if (getValueFromTableCell("table117", i, 0) == 'PL')
		{
			attrbList += "&<First_Installment_date_Monthly>&" + getValueFromColumnName("table117",i,"First Installment date Monthly");
			attrbList += "&<Product_Type>&" + getValue("PROGRAM_TYPE");
			var last_inst = getValueFromTableCell("table117",i,46);
			  attrbList += "&<Loan_Tenor>&" + getValue('Loan_Tenor') + "Months" + "  " + last_inst;
			   attrbList += "&<Expiry>&" + getValueFromTableCell("table117",i,46);
		}
	}
	
	return attrbList;
	
	
}

function RC_Utilization_Permits_Secured()
{
	var attrbList="";
	var Product_Type = getValue('Product_Type');
	var Sub_Product_Type= getValue('Sub_Product_Type');
	var Request_Type =  getValue('Request_Type');
	
	attrbList += "&<Applicant_Name_Secured>&" + getValue('Applicant_Name');
	var year2 = new Date().getFullYear();
	var month2 =  new Date().getMonth();
	var date2 = new Date().getDate();
	month2 = parseInt(month2) + parseInt("1");
	var current_date_time2 = date2 + "/"+ month2 + "/" +year2;
	attrbList += "&<Current_Date>&" + current_date_time2 ;
	
	attrbList += "&<Account_Number>&" + getValue('CustomerLoanAccountNumber');
	attrbList += "&<Approved_loan_amount>&" + getValueFromColumnName("table117",0,"Currency") + " "+ getValue('Credit_Approved_Amount');
	  

	var Count = getGridRowCount("table117");
	for (var i = 0; i < Count; i++) 
	{
		if (getValueFromTableCell("table117", i, 0) == 'PL')
		{
			var firstInstDate = getValueFromColumnName("table117",i,"First Installment date Monthly");
			
			try
			{
				var firsDateArr = firstInstDate.split('/');
				firstInstDate = firsDateArr[2]+"/"+firsDateArr[1]+"/"+firsDateArr[0];
			}
			catch(ee)
			{
				
			}
			attrbList += "&<First_Installment_date_Monthly>&" + firstInstDate;
			attrbList += "&<Product_Type_Secured>&" + getValue("PROGRAM_TYPE");
			var last_inst = getValueFromColumnName("table117",i,"Last Installment date Monthly");
			
			try
			{
				var last_instArr = last_inst.split('/');
				last_inst = last_instArr[2]+"/"+last_instArr[1]+"/"+last_instArr[0];
			}
			catch(ee)
			{
				
			}
			
			attrbList += "&<Loan_Tenor>&" + last_inst +" ("+getValueFromColumnName("table117",i,"Tenor Monthly") + " Months)" ;//amount
		}
	}
	
	attrbList += "&<amount>&" + getValue('Total_Chosen_Coll_Value');
	attrbList += "&<Refrence>&" + getValue('CollResNo');
	
	
	var caApprovedDate = getValue("FULFILMENT_ENTRY_DATE");
	try
	{
		var caApprovedDateArr = caApprovedDate.split('/');
		caApprovedDate = caApprovedDateArr[2]+"/"+caApprovedDateArr[1]+"/"+caApprovedDateArr[0];
		attrbList += "&<CA_Date>&" + caApprovedDate ;
	}
	catch(ee)
	{
		
	}
	
	return attrbList;
	
}

function RC_Utilization_Permits_Bulk()
{
	var attrbList="";
	var Product_Type = getValue('Product_Type');
	var Sub_Product_Type= getValue('Sub_Product_Type');
	var Request_Type =  getValue('Request_Type');
	
	var year2 = new Date().getFullYear();
	var month2 =  new Date().getMonth();
	var date2 = new Date().getDate();
	month2 = parseInt(month2) + parseInt("1");
	var current_date_time2 = date2 + "/"+ month2 + "/" +year2;
	attrbList += "&<Current_Date>&" + current_date_time2 ;
	
	
	attrbList += "&<Applicant_Name>&" + getValue('Applicant_Name');
	attrbList += "&<Account_Number>&" + getValue('CustomerLoanAccountNumber');
 // attrbList += "&<Product_Type&" + getSelectedItemLabel('Product_Type');
	attrbList += "&<CreditApproved_loan_amount>&" + getValueFromColumnName("table117",0,"Currency") + " "+ getValue('Credit_Approved_Amount');
	attrbList += "&<Credit_loan_amount>&" + getValueFromColumnName("table117",0,"Currency") + " "+ getValue('Credit_Approved_Amount');
	
	
	attrbList += "&<Approved_monthly>&" + getValueFromColumnName("table117",0,"Currency") + " "+ getValueFromColumnName("table117",0,"Requested Loan Amount Monthly") ;
	
	
	var lastInstDateMonthly = getValueFromColumnName("table117",0,"Last Installment date Monthly");
	try
	{
		var lastInstDateMonthlyArr = lastInstDateMonthly.split('/');
		lastInstDateMonthly = lastInstDateMonthlyArr[2]+"/"+lastInstDateMonthlyArr[1]+"/"+lastInstDateMonthlyArr[0];
	}
	catch(ee)
	{
		
	}
	attrbList += "&<Date_Month>&" + lastInstDateMonthly;		
	attrbList += "&<Tenor_months>&" + getValueFromColumnName("table117",0,"Tenor Monthly") + " Months";//Tenor_months
	
	 
	 
	attrbList += "&<Approved_loan_amount>&" + getValueFromColumnName("table117",0,"Currency") + " "+ getValueFromColumnName("table117",0,"Requested Loan Amount Yearly") ;
	
	attrbList += "&<Expiry>&" + lastInstDateMonthly;
	
	var lastInstDateYearly = getValueFromColumnName("table117",0,"Last Installment Date Yearly");
	try
	{
		var lastInstDateYearlyArr = lastInstDateYearly.split('/');
		lastInstDateYearly = lastInstDateYearlyArr[2]+"/"+lastInstDateYearlyArr[1]+"/"+lastInstDateYearlyArr[0];
	}
	catch(ee)
	{
		
	}
	attrbList += "&<Date_Year>&" + lastInstDateYearly;		
	attrbList += "&<Loan_Tenor>&" + getValueFromColumnName("table117",0,"Tenor Yearly") + " Year";//Tenor_months
	
	
	var firstInstDateMonthly = getValueFromColumnName("table117",0,"First Installment date Monthly");
	try
	{
		var firstInstDateMonthlyArr = firstInstDateMonthly.split('/');
		firstInstDateMonthly = firstInstDateMonthlyArr[2]+"/"+firstInstDateMonthlyArr[1]+"/"+firstInstDateMonthlyArr[0];
	}
	catch(ee)
	{
		
	}
	attrbList += "&<First_Installment_date_Monthly>&" + firstInstDateMonthly;
	
	var firstInstDateYearly = getValueFromColumnName("table117",0,"First Installment date Yearly");
	try
	{
		var firstInstDateYearlyArr = firstInstDateYearly.split('/');
		firstInstDateYearly = firstInstDateYearlyArr[2]+"/"+firstInstDateYearlyArr[1]+"/"+firstInstDateYearlyArr[0];
	}
	catch(ee)
	{
		
	}
	attrbList += "&<First_Installment_date_Yearly>&" + firstInstDateYearly;


	attrbList += "&<Product_Type>&" + getValue("PROGRAM_TYPE");
	
	 
	attrbList += "&<Approved_By>&" + getValue('APPROVED_BY');
	
	return attrbList;		 
}
function RC_Unsecured_AL_Utilization_Permit()
{
	var attrbList="";
	var Product_Type = getValue('Product_Type');
	var Sub_Product_Type= getValue('Sub_Product_Type');
	var Request_Type =  getValue('Request_Type');
	

	attrbList += "&<Customer_Name>&" + getValue('Applicant_Name');
	attrbList += "&<Account_Number>&" + getValue('CustomerLoanAccountNumber');
	
	
	attrbList += "&<Approved_loan_amount>&" + getValueFromColumnName("table117",0,"Currency") + " "+ getValue('Credit_Approved_Amount');
	attrbList += "&<Amount>&" + getValue('Credit_Approved_Amount');
	var Count = getGridRowCount("table117");
	for (var i = 0; i < Count; i++) 
	{
		if (getValueFromTableCell("table117", i, 0) == 'AL')
		{
			attrbList += "&<First_Installment_date_Monthly>&" + getValueFromColumnName("table117",i,"First Installment date Monthly");
			attrbList += "&<Dealer_Name>&" + getValueFromColumnName("table117",0,"Dealer Name");
			attrbList += "&<Loan_Tenor>&" + getValue('Loan_Tenor') + "Months" ;
			attrbList += "&<Expiry>&" + getValueFromTableCell("table117",i,46);
				
		}
	}
	
	var Count = getGridRowCount("table74");
	for (var i = 0; i < Count; i++) 
	{
		if (getValueFromColumnName("table74", i, "Workstep Name") == "Fulfillment_Docs")
		{
				var fullfillmentDate = getValueFromColumnName("table74", i, "Date & Time");
				fullfillmentDate = formatToYYYYMMDD(fullfillmentDate);
			  	attrbList += "&<Fullfillment_Date>&" + fullfillmentDate ;
				
		}
	}
	
	
	return attrbList;
	
}

function formatToYYYYMMDD(dateString) {
  if (!dateString || typeof dateString !== 'string') return "Invalid Date";

  // Normalize extra spaces
  const cleanString = dateString.replace(/\s+/g, ' ').trim(); // e.g. "Sep 3 2025 3:46PM"

  // Split into parts like ["Sep", "3", "2025", "3:46PM"]
  const parts = cleanString.split(' ');
  if (parts.length < 3) return "Invalid Date";

  const monthMap = {
    Jan: "01", Feb: "02", Mar: "03", Apr: "04",
    May: "05", Jun: "06", Jul: "07", Aug: "08",
    Sep: "09", Oct: "10", Nov: "11", Dec: "12"
  };

  const month = monthMap[parts[0]];
  const day = String(parts[1]).padStart(2, '0');
  const year = parts[2];

  if (!month || isNaN(day) || isNaN(year)) return "Invalid Date";

  return `${year}/${month}/${day}`;
}




//////Added by Akash-----------------Start -------------------------------------
function NG_Intl_Commodity_Fin_Contract_Indv()
{
	var attrbList="";
	var Product_Type = getValue('Product_Type');
	var Sub_Product_Type= getValue('Sub_Product_Type'); //Secured or unsecured
	var Request_Type =  getValue('Request_Type');
	//var Application_Date =  getValue('CreatedDateTime');//Need to check
	attrbList += "&<Application_Date:>&" + getValue('CreatedDateTime');
	attrbList += "&<Customer_Name>&" + getValue('Applicant_Name');
	attrbList += "&<Customer_National_ID/CIF>&" + getValue('Applicant_National_ID');
	//attrbList += "&<Finance_Account_Number>&" + getValue('CustomerLoanAccountNumber');
	attrbList += "&<Finance_Account_Number>&" + getValue('CustomerLoanAccountNumber');
	// attrbList += "&<Admin_Fees>&" + getValueFromColumnName("table117",i,"Admin Fees");
	
	var year3 = new Date().getFullYear();
	var month3 =  new Date().getMonth();
	var date3 = new Date().getDate();
	month3 = parseInt(month3) + parseInt("1");
	var current_date_time3 = date3 + "/"+ month3 + "/" +year3;
	attrbList += "&<Printed_Date>&" + current_date_time3 ;//needto check
	
	
	 var Count = getGridRowCount("table107");//customer details
                for (var i = 0; i < Count; i++) {
                    if (getValueFromTableCell("table107", i, 0) == 'Applicant')
						    {
						        attrbList += "&<Place_of_issue>&" + getValueFromColumnName("table107",i,"Place of issue (Passport)");
						        //attrbList += "&<Customer_National_ID/CIF>&" + getValueFromColumnName("table107",i,"Applicant_National_ID ");  
							}
				}
					
					
	var Count = getGridRowCount("table108");
                for (var i = 0; i < Count; i++) {
					
								
					if (getValueFromTableCell("table108", i, 0) == 'Applicant')
							{
						       attrbList += "&<Address_Line:House_Number/Flat_no.>&" + getValueFromColumnName("table108",i,"Address Line 1: House number/Flat no.");
						       attrbList += "&<City>&" + getValueFromColumnName("table108",i,"City");//No. of Years at  Address
						 
						
							}
				}
	
	var Count = getGridRowCount("table117");
                for (var i = 0; i < Count; i++) {
					
								//attrbList += "&<Admin_Fees>&" + getValueFromColumnName("table117",i,"Admin Fees");
					if (getValueFromTableCell("table117", i, 0) == 'PL')
			         {
							{
						       attrbList += "&<Total_Requested_Amount>&" + getValueFromColumnName("table117",i,"Total Requested Amount");
						       attrbList += "&<Admin_Fees>&" + getValueFromColumnName("table117",i,"Admin Fees");
						       attrbList += "&<Tenor_Monthly>&" + getValueFromColumnName("table117",i,"Tenor Monthly");
						       attrbList += "&<Installment_Amount_Monthly>&" + getValueFromColumnName("table117",i,"Installment Amount Monthly");
							   var year4 = new Date().getFullYear();
							   var month4 =  new Date().getMonth();
							   var date4 = new Date().getDate();
							   month4 = parseInt(month3) + parseInt("1");
							   var LastInstDate = date4 + "/"+ month4 + "/" +year4;
							   attrbList += "&<Last_Installment_date_Monthly>&" + LastInstDate ;//needto check
						       //attrbList += "&<Last_Installment_date_Monthly>&" + getValueFromColumnName("table117",i,"Last Installment date Monthly");
						       //attrbList += "&<First_Installment_date_Monthly>&" + getValueFromColumnName("table117",i,"First Installment date Monthly");
							}
                     }

				}

					return attrbList;
}

//--------------------------------------------------------------------
function NG_Intl_Commodity_Murabaha_FinContract_VarInst_Indv()
{
	var attrbList="";
	var Product_Type = getValue('Product_Type');
	var Sub_Product_Type= getValue('Sub_Product_Type'); //Secured or unsecured
	var Request_Type =  getValue('Request_Type');
	//var Application_Date =  getValue('CreatedDateTime');//Need to check
	attrbList += "&<Application_Date:>&" + getValue('CreatedDateTime');
	attrbList += "&<Customer_Name>&" + getValue('Applicant_Name');
	attrbList += "&<Customer_National_ID/CIF>&" + getValue('Applicant_National_ID');
	//attrbList += "&<Finance_Account_Number>&" + getValue('CustomerLoanAccountNumber');
	attrbList += "&<Finance_Account_Number>&" + getValue('CustomerLoanAccountNumber');
	// attrbList += "&<Admin_Fees>&" + getValueFromColumnName("table117",i,"Admin Fees");
	
	var year3 = new Date().getFullYear();
	var month3 =  new Date().getMonth();
	var date3 = new Date().getDate();
	month3 = parseInt(month3) + parseInt("1");
	var current_date_time3 = date3 + "/"+ month3 + "/" +year3;
	attrbList += "&<Printed_Date>&" + current_date_time3 ;//needto check
	
	
	 var Count = getGridRowCount("table107");//customer details
                for (var i = 0; i < Count; i++) {
                    if (getValueFromTableCell("table107", i, 0) == 'Applicant')
						    {
						        attrbList += "&<Place_of_issue>&" + getValueFromColumnName("table107",i,"Place of issue (Passport)");
						        //attrbList += "&<Customer_National_ID/CIF>&" + getValueFromColumnName("table107",i,"Applicant_National_ID ");  
							}
				}
					
					
	var Count = getGridRowCount("table108");
                for (var i = 0; i < Count; i++) {
					
								
					if (getValueFromTableCell("table108", i, 0) == 'Applicant')
							{
						       attrbList += "&<Address_Line:House_Number/Flat_no.>&" + getValueFromColumnName("table108",i,"Address Line 1: House number/Flat no.");
						       attrbList += "&<City>&" + getValueFromColumnName("table108",i,"City");//No. of Years at  Address
						 
						
							}
				}
	
	var Count = getGridRowCount("table117");
                for (var i = 0; i < Count; i++) {
					
								//attrbList += "&<Admin_Fees>&" + getValueFromColumnName("table117",i,"Admin Fees");
					if (getValueFromTableCell("table117", i, 0) == 'PL')
			         {
							{
						       attrbList += "&<Total_Requested_Amount>&" + getValueFromColumnName("table117",i,"Total Requested Amount");
						       attrbList += "&<Admin_Fees>&" + getValueFromColumnName("table117",i,"Admin Fees");
						       attrbList += "&<Tenor_Monthly>&" + getValueFromColumnName("table117",i,"Tenor Monthly");
						       attrbList += "&<Installment_Amount_Monthly>&" + getValueFromColumnName("table117",i,"Installment Amount Monthly");
						       attrbList += "&<Last_Installment_date_Monthly>&" + getValueFromColumnName("table117",i,"Last Installment date Monthly");
						       attrbList += "&<First_Installment_date_Monthly>&" + getValueFromColumnName("table117",i,"First Installment date Monthly");
						       attrbList += "&<Installment_Amount_Yearly>&" + getValueFromColumnName("table117",i,"First Installment Amount Yearly");
						       attrbList += "&<Tenor_Yearly>&" + getValueFromColumnName("table117",i,"Tenor Yearly");
							}
                     }

				}

					return attrbList;
}

//------------------------------------------------------------------------------------------------

function NG_Intl_Commodity_Murabaha_Agency_Indv()
{
	var attrbList="";
	var Product_Type = getValue('Product_Type');
	var Sub_Product_Type= getValue('Sub_Product_Type'); //Secured or unsecured
	var Request_Type =  getValue('Request_Type');
	attrbList += "&<Application_Date:>&" + getValue('CreatedDateTime');
	attrbList += "&<Customer_Name>&" + getValue('Applicant_Name');
	attrbList += "&<Customer_National_ID/CIF>&" + getValue('Applicant_National_ID');
	attrbList += "&<Finance_Account_Number>&" + getValue('CustomerLoanAccountNumber');
	
	var year3 = new Date().getFullYear();
	var month3 =  new Date().getMonth();
	var date3 = new Date().getDate();
	month3 = parseInt(month3) + parseInt("1");
	var current_date_time3 = date3 + "/"+ month3 + "/" +year3;
	attrbList += "&<Printed_Date>&" + current_date_time3 ;//needto check
	
	
	 var Count = getGridRowCount("table107");//customer details
                for (var i = 0; i < Count; i++) {
                    if (getValueFromTableCell("table107", i, 0) == 'Applicant')
						    {
						        attrbList += "&<Place_of_issue>&" + getValueFromColumnName("table107",i,"Place of issue (Passport)");
						        //attrbList += "&<Customer_National_ID/CIF>&" + getValueFromColumnName("table107",i,"Applicant_National_ID ");  
							}
				}
					
					
	var Count = getGridRowCount("table108");
                for (var i = 0; i < Count; i++) {
					
								
					if (getValueFromTableCell("table108", i, 0) == 'Applicant')
							{
						       attrbList += "&<Address_Line:House_Number/Flat_no.>&" + getValueFromColumnName("table108",i,"Address Line 1: House number/Flat no.");
						       attrbList += "&<City>&" + getValueFromColumnName("table108",i,"City");//No. of Years at  Address
						 
						
							}
				}
                return attrbList;
}

//----------------------------------------------------------------------------------------------------------

function NG_Car_Murabaha_Framework_LetterRecom()
{
    var attrbList = "";

    var Product_Type = getValue('Product_Type');
    var Sub_Product_Type = getValue('Sub_Product_Type');
    var Request_Type = getValue('Request_Type');

    // Application Date
    attrbList += "&<Application_Date>&" + getValue('CreatedDateTime');

    // Printed Date
    var today = new Date();
    var current_date_time3 =
        today.getDate() + "/" +
        (today.getMonth() + 1) + "/" +
        today.getFullYear();

    attrbList += "&<Printed_Date>&" + current_date_time3;

    // Grid Loop
    var Count = getGridRowCount("table117");

    for (var i = 0; i < Count; i++)
    {
        var prodtype = getValueFromTableCell("table117", i, 0);

        // BOTH AL and MR
        if (prodtype == 'AL')
        {
            attrbList += "&<Car_Model>&" + getValueFromColumnName("table117", i, "Car Model");
            attrbList += "&<Car_Brand>&" + getValueFromColumnName("table117", i, "Car Brand");
            attrbList += "&<Year_of_manufacture>&" + getValueFromColumnName("table117", i, "Year of manufacture");
            attrbList += "&<Vehicle_Price>&" + getValueFromColumnName("table117", i, "Vehicle Price");
            attrbList += "&<Chassis_No>&" + getValueFromColumnName("table117", i, "Chassis No");
            attrbList += "&<Car_plate_number>&" + getValueFromColumnName("table117", i, "Car plate number");
            attrbList += "&<Type_of_Vehicle>&" + getValueFromColumnName("table117", i, "Type of vehicle");
        }
    }

    return attrbList;
}

//----------------------------------------------------------------------------------------------------------------------

function NG_Car_Murabaha_Framework_CarPurchaseReq()
{
	var attrbList="";
	var Product_Type = getValue('Product_Type');
	var Sub_Product_Type= getValue('Sub_Product_Type'); //Secured or unsecured
	var Request_Type =  getValue('Request_Type');
	//var Application_Date =  getValue('CreatedDateTime');//Need to check
	attrbList += "&<Application_Date:>&" + getValue('CreatedDateTime');
	
	var year3 = new Date().getFullYear();
	var month3 =  new Date().getMonth();
	var date3 = new Date().getDate();
	month3 = parseInt(month3) + parseInt("1");
	var current_date_time3 = date3 + "/"+ month3 + "/" +year3;
	attrbList += "&<Printed_Date>&" + current_date_time3 ;//needto check
	
	
	 var Count = getGridRowCount("table117");
		for (var i = 0; i < Count; i++)
         {
             var prodtype = getValueFromTableCell("table117", i, 0);
	     
             // BOTH AL and MR
             if (prodtype == 'AL')
             {
                 attrbList += "&<Car_Model>&" + getValueFromColumnName("table117", i, "Car Model");
                 attrbList += "&<Car_Brand>&" + getValueFromColumnName("table117", i, "Car Brand");
                 attrbList += "&<Year_of_manufacture>&" + getValueFromColumnName("table117", i, "Year of manufacture");
                 attrbList += "&<Vehicle_Price>&" + getValueFromColumnName("table117", i, "Vehicle Price");
                 attrbList += "&<Chassis_No>&" + getValueFromColumnName("table117", i, "Chassis No");
                 attrbList += "&<Car_plate_number>&" + getValueFromColumnName("table117", i, "Car plate number");
                 attrbList += "&<Type_of_Vehicle>&" + getValueFromColumnName("table117", i, "Type of vehicle");
             }
         }


                return attrbList;
}
	
	



//--------------------------------------------------------------------
function ack_Of_Deposit_Or_Certificate_Against_Debt_Secured()
{
	var attrbList="";
	var Product_Type = getValue('Product_Type');
	var Sub_Product_Type= getValue('Sub_Product_Type'); //Secured or unsecured
	var Request_Type =  getValue('Request_Type');
	//var Application_Date =  getValue('CreatedDateTime');//Need to check
	attrbList += "&<Application_Date:>&" + getValue('CreatedDateTime');
	attrbList += "&<Customer_Name>&" + getValue('Applicant_Name');
	attrbList += "&<Customer_National_ID/CIF>&" + getValue('Applicant_National_ID');
	var year3 = new Date().getFullYear();
	var month3 =  new Date().getMonth();
	var date3 = new Date().getDate();
	month3 = parseInt(month3) + parseInt("1");
	var current_date_time3 = date3 + "/"+ month3 + "/" +year3;
	attrbList += "&<Printed_Date>&" + current_date_time3 ;//needto check	
	attrbList += "&<Outstanding>&" + getValue('Outstanding');
	//attrbList += "&<Balance>&" + getValueFromColumnName("table142",i,"Balance");
	attrbList += "&<Balance>&" + getValue('Balance');
	if(Applicant_Nationality == "EG")
						{
							attrbList += "&<Passport_Number>&" + getValue('Applicant_Passport_Number');
							
						}
                        else{
							attrbList += "&<Passport_Number>&" +   getValue('Applicant_Passport_Number');
						}
						
	attrbList += "&<Branch_Name>&" +getValue('Branch_Name');
	 attrbList +=  "&<Collateral_CIF>&" + getValue('Collateral_CIF');  
	 attrbList +=  "&<Collateral_Reference>&" + getValue('CollRefNo');  
		 	
	var Count = getGridRowCount("table108");
                for (var i = 0; i < Count; i++) {
					
								
					if (getValueFromTableCell("table108", i, 0) == 'Applicant')
							{
						       attrbList += "&<Address_Line:House_Number/Flat_no.>&" + getValueFromColumnName("table108",i,"Address Line 1: House number/Flat no.");
						       //attrbList += "&<City>&" + getValueFromColumnName("table108",i,"City");//No. of Years at  Address
						 
						
							}
				}
				
				
				var Count = getGridRowCount("table120");
                for (var i = 0; i < Count; i++) {
					
								
					if (getValueFromTableCell("table120", i, 0) == 'Applicant')
							{
						       attrbList += "&<Outstanding>&" + getValueFromColumnName("table120",i,"Outstanding");
						 
						
							}
				}
				
				
				var Count = getGridRowCount("table142");
                for (var i = 0; i < Count; i++) {
					
								
					if (getValueFromTableCell("table142", i, 0) == 'Applicant')
							{
						       attrbList += "&<Balance>&" + getValueFromColumnName("table142",i,"Balance");
						 
						
							}
				}

					return attrbList;
}



//--------------------------------------------------------------------
function murabaha_Car_Finance_Contract_Various_Terms()
{
	var attrbList="";
	var Product_Type = getValue('Product_Type');
	var Sub_Product_Type= getValue('Sub_Product_Type'); //Secured or unsecured
	var Request_Type =  getValue('Request_Type');
	//var Application_Date =  getValue('CreatedDateTime');//Need to check
	attrbList += "&<Application_Date:>&" + getValue('CreatedDateTime');
	attrbList += "&<Customer_Name>&" + getValue('Applicant_Name');
	attrbList += "&<Customer_National_ID/CIF>&" + getValue('Applicant_National_ID');
	var year3 = new Date().getFullYear();
	var month3 =  new Date().getMonth();
	var date3 = new Date().getDate();
	month3 = parseInt(month3) + parseInt("1");
	var current_date_time3 = date3 + "/"+ month3 + "/" +year3;
	attrbList += "&<Printed_Date>&" + current_date_time3 ;//needto check	
	attrbList += "&<Finance_Account_Number>&" + getValue('CustomerLoanAccountNumber');
			 	
	var Count = getGridRowCount("table108");
                for (var i = 0; i < Count; i++) {					
								
					if (getValueFromTableCell("table108", i, 0) == 'Applicant')
							{
						       attrbList += "&<Address_Line:House_Number/Flat_no.>&" + getValueFromColumnName("table108",i,"Address Line 1: House number/Flat no.");
						       attrbList += "&<City>&" + getValueFromColumnName("table108",i,"City");					 
						
							}
				}				
				
				var Count = getGridRowCount("table120");
                for (var i = 0; i < Count; i++) {
					
								
					if (getValueFromTableCell("table120", i, 0) == 'Applicant')
							{
						       attrbList += "&<Outstanding>&" + getValueFromColumnName("table120",i,"Outstanding");					 
						
							}
				}
								
				var Count = getGridRowCount("table142");
                for (var i = 0; i < Count; i++) {
					
								
					if (getValueFromTableCell("table142", i, 0) == 'Applicant')
							{
						       attrbList += "&<Balance>&" + getValueFromColumnName("table142",i,"Balance");					 
						
							}
				}
				
		        var Count = getGridRowCount("table117");
		        for (var i = 0; i < Count; i++)
                 {
                     var prodtype = getValueFromTableCell("table117", i, 0);
	             
                     // BOTH AL and MR
                     if (prodtype == 'AL')
                     {
                         attrbList += "&<Car_Model>&" + getValueFromColumnName("table117", i, "Car Model");
                         attrbList += "&<Car_Brand>&" + getValueFromColumnName("table117", i, "Car Brand");
                         attrbList += "&<Year_of_manufacture>&" + getValueFromColumnName("table117", i, "Year of manufacture");
                         //attrbList += "&<Vehicle_Price>&" + getValueFromColumnName("table117", i, "Vehicle Price");
                         attrbList += "&<Chassis_No>&" + getValueFromColumnName("table117", i, "Chassis No");
                         attrbList += "&<Car_plate_number>&" + getValueFromColumnName("table117", i, "Car plate number");
                         //attrbList += "&<Type_of_Vehicle>&" + getValueFromColumnName("table117", i, "Type of vehicle");
                     }
                 }
				 
				var Count = getGridRowCount("table117");
                for (var i = 0; i < Count; i++) {
					
								//attrbList += "&<Admin_Fees>&" + getValueFromColumnName("table117",i,"Admin Fees");
					if (getValueFromTableCell("table117", i, 0) == 'AL')
			         {
						{
								attrbList += "&<Total_Requested_Amount>&" + getValueFromColumnName("table117",i,"Total Requested Amount");
								attrbList += "&<Admin_Fees>&" + getValueFromColumnName("table117",i,"Admin Fees");
								attrbList += "&<Tenor_Monthly>&" + getValueFromColumnName("table117",i,"Tenor Monthly");
								attrbList += "&<Installment_Amount_Monthly>&" + getValueFromColumnName("table117",i,"Installment Amount Monthly");
								attrbList += "&<Last_Installment_date_Monthly>&" + getValueFromColumnName("table117",i,"Last Installment date Monthly");
								attrbList += "&<First_Installment_date_Monthly>&" + getValueFromColumnName("table117",i,"First Installment date Monthly");
								attrbList += "&<Installment_Amount_Yearly>&" + getValueFromColumnName("table117",i,"First Installment Amount Yearly");
								attrbList += "&<Tenor_Yearly>&" + getValueFromColumnName("table117",i,"Tenor Yearly");
						}
                     }

				}
				 
					return attrbList;
}




//--------------------------------------------------------------------
function murabaha_Car_Financing_Contract()
{
	var attrbList="";
	var Product_Type = getValue('Product_Type');
	var Sub_Product_Type= getValue('Sub_Product_Type'); //Secured or unsecured
	var Request_Type =  getValue('Request_Type');
	//var Application_Date =  getValue('CreatedDateTime');//Need to check
	attrbList += "&<Application_Date:>&" + getValue('CreatedDateTime');
	attrbList += "&<Customer_Name>&" + getValue('Applicant_Name');
	attrbList += "&<Customer_National_ID/CIF>&" + getValue('Applicant_National_ID');
	var year3 = new Date().getFullYear();
	var month3 =  new Date().getMonth();
	var date3 = new Date().getDate();
	month3 = parseInt(month3) + parseInt("1");
	var current_date_time3 = date3 + "/"+ month3 + "/" +year3;
	attrbList += "&<Printed_Date>&" + current_date_time3 ;//needto check	
	attrbList += "&<Finance_Account_Number>&" + getValue('CustomerLoanAccountNumber');
			 	
	var Count = getGridRowCount("table108");
                for (var i = 0; i < Count; i++) {					
								
					if (getValueFromTableCell("table108", i, 0) == 'Applicant')
							{
						       attrbList += "&<Address_Line:House_Number/Flat_no.>&" + getValueFromColumnName("table108",i,"Address Line 1: House number/Flat no.");
						       attrbList += "&<City>&" + getValueFromColumnName("table108",i,"City");					 
						
							}
				}
				
		        var Count = getGridRowCount("table117");
		        for (var i = 0; i < Count; i++)
                 {
                     var prodtype = getValueFromTableCell("table117", i, 0);
	             
                     // BOTH AL and MR
                     if (prodtype == 'AL')
                     {
                         attrbList += "&<Car_Model>&" + getValueFromColumnName("table117", i, "Car Model");
                         attrbList += "&<Car_Brand>&" + getValueFromColumnName("table117", i, "Car Brand");
                         attrbList += "&<Year_of_manufacture>&" + getValueFromColumnName("table117", i, "Year of manufacture");
                         //attrbList += "&<Vehicle_Price>&" + getValueFromColumnName("table117", i, "Vehicle Price");
                         attrbList += "&<Chassis_No>&" + getValueFromColumnName("table117", i, "Chassis No");
                         attrbList += "&<Car_plate_number>&" + getValueFromColumnName("table117", i, "Car plate number");
                         //attrbList += "&<Type_of_Vehicle>&" + getValueFromColumnName("table117", i, "Type of vehicle");
                     }
                 }
				 
				var Count = getGridRowCount("table117");
                for (var i = 0; i < Count; i++) {
					
								//attrbList += "&<Admin_Fees>&" + getValueFromColumnName("table117",i,"Admin Fees");
					if (getValueFromTableCell("table117", i, 0) == 'AL')
			         {
						{
								attrbList += "&<Total_Requested_Amount>&" + getValueFromColumnName("table117",i,"Total Requested Amount");
								attrbList += "&<Admin_Fees>&" + getValueFromColumnName("table117",i,"Admin Fees");
								attrbList += "&<Tenor_Monthly>&" + getValueFromColumnName("table117",i,"Tenor Monthly");
								attrbList += "&<Installment_Amount_Monthly>&" + getValueFromColumnName("table117",i,"Installment Amount Monthly");
								attrbList += "&<Last_Installment_date_Monthly>&" + getValueFromColumnName("table117",i,"Last Installment date Monthly");
								attrbList += "&<First_Installment_date_Monthly>&" + getValueFromColumnName("table117",i,"First Installment date Monthly");
								
						}
                     }

				}
				 
					return attrbList;
}



//--------------------------------------------------------------------
function murabaha_Service_Contract_Various_Terms()
{
	var attrbList="";
	var Product_Type = getValue('Product_Type');
	var Sub_Product_Type= getValue('Sub_Product_Type'); //Secured or unsecured
	var Request_Type =  getValue('Request_Type');
	attrbList += "&<Application_Date:>&" + getValue('CreatedDateTime');
	attrbList += "&<Customer_Name>&" + getValue('Applicant_Name');
	attrbList += "&<Customer_National_ID/CIF>&" + getValue('Applicant_National_ID');
	var year3 = new Date().getFullYear();
	var month3 =  new Date().getMonth();
	var date3 = new Date().getDate();
	month3 = parseInt(month3) + parseInt("1");
	var current_date_time3 = date3 + "/"+ month3 + "/" +year3;
	attrbList += "&<Printed_Date>&" + current_date_time3 ;//needto check	
	attrbList += "&<Finance_Account_Number>&" + getValue('CustomerLoanAccountNumber');
			 	
	var Count = getGridRowCount("table108");
                for (var i = 0; i < Count; i++) {					
								
					if (getValueFromTableCell("table108", i, 0) == 'Applicant')
							{
						       attrbList += "&<Address_Line:House_Number/Flat_no.>&" + getValueFromColumnName("table108",i,"Address Line 1: House number/Flat no.");
						       attrbList += "&<City>&" + getValueFromColumnName("table108",i,"City");					 
						
							}
				}
				 
				var Count = getGridRowCount("table117");
                for (var i = 0; i < Count; i++) {
					
								//attrbList += "&<Admin_Fees>&" + getValueFromColumnName("table117",i,"Admin Fees");
					if (getValueFromTableCell("table117", i, 0) == 'AL')
			         {
						{
								attrbList += "&<Total_Requested_Amount>&" + getValueFromColumnName("table117",i,"Total Requested Amount");
								attrbList += "&<Admin_Fees>&" + getValueFromColumnName("table117",i,"Admin Fees");
								attrbList += "&<Tenor_Monthly>&" + getValueFromColumnName("table117",i,"Tenor Monthly");
								attrbList += "&<Installment_Amount_Monthly>&" + getValueFromColumnName("table117",i,"Installment Amount Monthly");
								attrbList += "&<Last_Installment_date_Monthly>&" + getValueFromColumnName("table117",i,"Last Installment date Monthly");
								attrbList += "&<First_Installment_date_Monthly>&" + getValueFromColumnName("table117",i,"First Installment date Monthly");
								attrbList += "&<Installment_Amount_Yearly>&" + getValueFromColumnName("table117",i,"First Installment Amount Yearly");
								attrbList += "&<Tenor_Yearly>&" + getValueFromColumnName("table117",i,"Tenor Yearly");
								
						}
                     }

				}
				 
					return attrbList;
}


//--------------------------------------------------------------------
function murabaha_Services_Contract()
{
	var attrbList="";
	var Product_Type = getValue('Product_Type');
	var Sub_Product_Type= getValue('Sub_Product_Type'); //Secured or unsecured
	var Request_Type =  getValue('Request_Type');
	attrbList += "&<Application_Date:>&" + getValue('CreatedDateTime');
	attrbList += "&<Customer_Name>&" + getValue('Applicant_Name');
	attrbList += "&<Customer_National_ID/CIF>&" + getValue('Applicant_National_ID');
	var year3 = new Date().getFullYear();
	var month3 =  new Date().getMonth();
	var date3 = new Date().getDate();
	month3 = parseInt(month3) + parseInt("1");
	var current_date_time3 = date3 + "/"+ month3 + "/" +year3;
	attrbList += "&<Printed_Date>&" + current_date_time3 ;//needto check	
	attrbList += "&<Finance_Account_Number>&" + getValue('CustomerLoanAccountNumber');
			 	
	var Count = getGridRowCount("table108");
                for (var i = 0; i < Count; i++) {					
								
					if (getValueFromTableCell("table108", i, 0) == 'Applicant')
							{
						       attrbList += "&<Address_Line:House_Number/Flat_no.>&" + getValueFromColumnName("table108",i,"Address Line 1: House number/Flat no.");
						       attrbList += "&<City>&" + getValueFromColumnName("table108",i,"City");					 
						
							}
				}
				 
				var Count = getGridRowCount("table117");
                for (var i = 0; i < Count; i++) {

					if (getValueFromTableCell("table117", i, 0) == 'AL')
			         {
						{
								attrbList += "&<Total_Requested_Amount>&" + getValueFromColumnName("table117",i,"Total Requested Amount");
								attrbList += "&<Admin_Fees>&" + getValueFromColumnName("table117",i,"Admin Fees");
								attrbList += "&<Tenor_Monthly>&" + getValueFromColumnName("table117",i,"Tenor Monthly");
								attrbList += "&<Installment_Amount_Monthly>&" + getValueFromColumnName("table117",i,"Installment Amount Monthly");
								attrbList += "&<Last_Installment_date_Monthly>&" + getValueFromColumnName("table117",i,"Last Installment date Monthly");
								attrbList += "&<First_Installment_date_Monthly>&" + getValueFromColumnName("table117",i,"First Installment date Monthly");
								
						}
                     }

				}
				 
					return attrbList;
}


//--------------------------------------------------------------------
function services_Murabaha_Delivery_Authorization()
{
	var attrbList="";
	var Product_Type = getValue('Product_Type');
	var Sub_Product_Type= getValue('Sub_Product_Type'); //Secured or unsecured
	var Request_Type =  getValue('Request_Type');
	//var Application_Date =  getValue('CreatedDateTime');//Need to check
	attrbList += "&<Application_Date:>&" + getValue('CreatedDateTime');
	attrbList += "&<Customer_Name>&" + getValue('Applicant_Name');
	attrbList += "&<Customer_National_ID/CIF>&" + getValue('Applicant_National_ID');
	var year3 = new Date().getFullYear();
	var month3 =  new Date().getMonth();
	var date3 = new Date().getDate();
	month3 = parseInt(month3) + parseInt("1");
	var current_date_time3 = date3 + "/"+ month3 + "/" +year3;
	attrbList += "&<Printed_Date>&" + current_date_time3 ;//needto check
		
		return attrbList;
}

	
	
//--------------------------------------------------------------------
function services_Murabaha_Purchase_Request()
{
	var attrbList="";
	var Product_Type = getValue('Product_Type');
	var Sub_Product_Type= getValue('Sub_Product_Type'); //Secured or unsecured
	var Request_Type =  getValue('Request_Type');
	//var Application_Date =  getValue('CreatedDateTime');//Need to check
	attrbList += "&<Application_Date:>&" + getValue('CreatedDateTime');
	attrbList += "&<Customer_Name>&" + getValue('Applicant_Name');
	attrbList += "&<Customer_National_ID/CIF>&" + getValue('Applicant_National_ID');
	var year3 = new Date().getFullYear();
	var month3 =  new Date().getMonth();
	var date3 = new Date().getDate();
	month3 = parseInt(month3) + parseInt("1");
	var current_date_time3 = date3 + "/"+ month3 + "/" +year3;
	attrbList += "&<Printed_Date>&" + current_date_time3 ;//needto check
	
	return attrbList;
}
