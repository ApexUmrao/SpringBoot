function disableControl(controlNameList)
{
	var controlNameArr = controlNameList.split(",");
	for(var idx=0;idx<controlNameArr.length;idx++)
	{
		try
		{
			setStyle(controlNameArr[idx],"disable","true");
		}
		catch(Exception)
		{}	
	}	
}

function enableControl(controlNameList)
{
	var controlNameArr = controlNameList.split(",");
	for(var idx=0;idx<controlNameArr.length;idx++)
	{
		try
		{
			setStyle(controlNameArr[idx],"disable","false");
		}
		catch(Exception)
		{}	
	}
}

function showControl(controlNameList)
{	
	var controlNameArr = controlNameList.split(",");
	for(var idx=0;idx<controlNameArr.length;idx++)
	{
		try
		{
			setStyle(controlNameArr[idx],"visible","true");
		}
		catch(Exception)
		{}	
	}
}

function hideControl(controlNameList)
{
	var controlNameArr = controlNameList.split(",");
	for(var idx=0;idx<controlNameArr.length;idx++)
	{
		try
		{
			setStyle(controlNameArr[idx],"visible","false");
		}
		catch(Exception)
		{}
	}
}

function lockControl(controlNameList)
{
	var controlNameArr = controlNameList.split(",");
	for(var idx=0;idx<controlNameArr.length;idx++)
	{
		try
		{
			setStyle(controlNameArr[idx],"readonly","true");
		}
		catch(Exception)
		{}	
	}
}

function unlockControl(controlNameList)
{
	var controlNameArr = controlNameList.split(",");
	for(var idx=0;idx<controlNameArr.length;idx++)
	{
		try
		{
			setStyle(controlNameArr[idx],"readonly","false");
		}
		catch(Exception)
		{}	
	}
}

function compareStringsIgnoreCase (string1, string2) 
{
	if(string1!=null && string2!=null)
    {
     string1 = string1.toLowerCase();
     string2 = string2.toLowerCase();
     return string1 === string2;
	}
    else
    	return false;
}

function mandatoryControl(controlNameList){
    var controlNameArr = controlNameList.split(",");
    for(var idx=0;idx<controlNameArr.length;idx++)
    {
        try
        {
            setStyle(controlNameArr[idx],"mandatory","true");
        }
        catch(Exception)
        {}  
    }
}

function nonMandatoryControl(controlNameList)
{
    var controlNameArr = controlNameList.split(",");
    for(var idx=0;idx<controlNameArr.length;idx++)
    {
        try
        {
            setStyle(controlNameArr[idx],"mandatory","false");
        }
        catch(Exception)
        {}  
    }
}

function clearValues(controlNameList)
{
    var controlNameArr = controlNameList.split(",");
    for(var idx=0;idx<controlNameArr.length;idx++)
    {
        try
        {
            clearValue(controlNameArr[idx]);
        }
        catch(Exception)
        {}  
    }
}

function setControlValue(controlName, controlValue)
{
    var controlObj = JSON.parse('{"'+controlName+'":"'+controlValue+'"}');
    setValues(controlObj,true);
}



function biasedHide(toHide, basedOnThis, value){
	if(getValue(basedOnThis) == value){
		hideControl(toHide);
	}else{
		showControl(toHide);
	}
}

function biasedDisable(toDisable, basedOnThis, value){
	if(getValue(basedOnThis) == value){
		disableControl(toDisable);
	}else{
		enableControl(toDisable);
	}
}
function biasedShow(toShow, basedOnThis, value){
	if(getValue(basedOnThis) == value){
		showControl(toShow);
	}else{
		hideControl(toShow);
	}
}

function biasedEnable(toDisable, basedOnThis, value){
	if(getValue(basedOnThis) == value){
		enableControl(toDisable);
	}else{
		disableControl(toDisable);
	}
}

function biasedUnlock(toDisable, basedOnThis, value){
	if(getValue(basedOnThis) == value){
		unlockControl(toDisable);
	}else{
		lockControl(toDisable);
	}
}

function biasedLock(toDisable, basedOnThis, value){
	if(getValue(basedOnThis) == value){
		lockControl(toDisable);
	}else{
		unlockControl(toDisable);
	}
}

function biasedUnlockAndClear(toUnlock, basedOnThis, value){
	if(getValue(basedOnThis) == value){
		unlockControl(toUnlock);
	}else{
		lockControl(toUnlock);
		clearValues(toUnlock);
	}
}

function biasedHideAndClear(toHide, basedOnThis, value){
	if(getValue(basedOnThis) == value){
		hideControl(toHide);
		clearValues(toHide);
	}else{
		showControl(toHide);
	}
}

function biasedDisableAndClear(toDisable, basedOnThis, value){
	if(getValue(basedOnThis) == value){
		disableControl(toDisable);
		clearValues(toDisable);
	}else{
		enableControl(toDisable);
	}
}
function biasedShowAndClear(toShow, basedOnThis, value){
	if(getValue(basedOnThis) == value){
		showControl(toShow);
	}else{
		hideControl(toShow);
		clearValues(toShow);
	}
}

function biasedEnableAndClear(toDisable, basedOnThis, value){
	if(getValue(basedOnThis) == value){
		enableControl(toDisable);
	}else{
		disableControl(toDisable);
		clearValues(toDisable);
	}
}


function checkMandatoryDocuments() {

    if(ActivityName=="Excellency_RM" || ActivityName=="Wholesale_RM" || ActivityName=="Branch_Maker" ){
    for(i=0;i<documents.length;i++){
        var out=checkAttachedDocument(documents[i]);
        if(out==false){
            showMessage("","Please attached the mandatory document:"+documents[i],"error");
            return false;
        }
    }
}
    return true;
}

function checkAttachedDocument(doc) {
 var output = window.parent.isMandatoryDocUploaded(doc);
 return output;
}

function biasedMandatory(toMandat, basedOnThis, value){
	if(getValue(basedOnThis) == value){
		mandatoryControl(toMandat);
	}else{
		nonMandatoryControl(toMandat);
	}
}

function biasedNonMandatory(toMandat, basedOnThis, value){
	if(getValue(basedOnThis) == value){
		nonMandatoryControl(toMandat);
	}else{
		mandatoryControl(toMandat);
	}
}

function biasedMandatoryAndUnlock(toMandat, basedOnThis, value){
	if(getValue(basedOnThis) == value){
		mandatoryControl(toMandat);
		unlockControl(toMandat);
	}else{
		nonMandatoryControl(toMandat);
		lockControl(toMandat);
	}
}


function getSelectedRowIndex(tableID){
	for(var i=0; i < getGridRowCount(tableID); i++){
		if(document.getElementById(tableID).getElementsByClassName("selectRow")[i].checked){
			return i;
		}
	}
}

function disableGridColumn(tableID, colIndex){
	for(var i = 0; i < getGridRowCount(tableID); i++){
				setCellDisabled(tableID,i,colIndex,"true");
			}
}

function unlockIfEmpty(controlNameList){
	var controlNameArr = controlNameList.split(",");
    for(var idx=0;idx<controlNameArr.length;idx++)
    {
        try
        {
            if(getValue(controlNameArr[idx]) == ""){
				unlockControl(controlNameArr[idx]);
			}
        }
        catch(Exception)
        {}  
    }
}

function checkMandatPresent(controlNameList){
	var controlNameArr = controlNameList.split(",");
	for(var idx=0;idx<controlNameArr.length;idx++)
	{
		if(getValue(controlNameArr[idx])=="")
		{
			showMessage(controlNameArr[idx],"Please enter "+getValue(controlNameArr[idx]+"_label"),"error",true);
			return false;
		}
	}
	return true;
}

function pad(n){return n<10 ? '0'+n : n}

function getTodayDate(){
	var date = new Date();  // 2009-11-10	
	var month = date.toLocaleString('default', { month: 'short' }).toUpperCase();
	var day = pad(date.getUTCDate());
	var year = date.getUTCFullYear();
	return day+" "+month+" "+year;
}

function getDateAfterInputDays(x){
		var date = new Date();
		date.setDate(date.getDate()+30);
		var month = date.toLocaleString('default', { month: 'short' }).toUpperCase();
		var day = pad(date.getUTCDate());
		var year = date.getUTCFullYear();
		return day+" "+month+" "+year;
}


function convertDateFormat(d){
	var arr = d.split("/")
	var year = arr[2];
	var month = arr[1];
	var day = arr[0];
	var date = new Date(year+"-"+month+"-"+day);  // 2009-11-10	
	var month = date.toLocaleString('default', { month: 'short' }).toUpperCase();
	var day = pad(date.getUTCDate());
	var year = date.getUTCFullYear();
	return day+" "+month+" "+year;
}
function addItemsToComboFromMaster(dropDownId,tableName,columnName,whereColumn,whereValue){
	var currValue = getValue(dropDownId);
	var dataString = dropDownId+","+tableName+","+columnName+","+whereColumn+","+whereValue;
	var res = executeServerEvent(dropDownId, "addItemsToComboFromMaster",dataString, true);
	setControlValue(dropDownId, currValue);
}

function addItemsToComboCustom(toAddToThis,addTheseValues){
	var currValue = getValue(toAddToThis);
	var combo_count = getItemCountInCombo(toAddToThis);
			for(var i = combo_count - 1; i > 0; i--){
					removeItemFromCombo(toAddToThis,i);			
			}
	var addTheseValuesArr = addTheseValues.split(",");
	for (var j = 0; j < addTheseValuesArr.length; i++){
		addItemInCombo(toAddToThis,addTheseValuesArr[i],addTheseValuesArr[i],'','');
	}
	setControlValue(toAddToThis, currValue);
}

function showTheseOnWorkstep(curr, el){
	if(ActivityName == curr){
		showControl(el);
	}else{
		hideControl(el);
	}
}

function hideTheseOnWorkstep(curr, el){
	if(ActivityName == curr){
		hideControl(el);
	}else{
		showControl(el);
	}
}

function getValuesInObj(str){
	var arr = str.split(",");
	var obj = {};
	for(var i = 0; i < arr.length; i++){
		obj[arr[i]] = getValue(arr[i]);
	}
	return obj;
}


function setValuesFromAnotherFeilds(toThese, fromThese){
	var toTheseArr = toThese.split(",");
	var fromTheseArr = fromThese.split(",");
	if(toTheseArr.length == fromTheseArr.length){
		for(var i = 0; i < toTheseArr.length; i++){
			setControlValue(toTheseArr[i],getValue(fromTheseArr[i]));
		}
	}
}

function getColumnSum(tableID, columnName){
	var sum = 0;
	for(var i = 0; i < getGridRowCount(tableID); i++){
		
		sum = sum + parseFloat(getValueFromColumnName(tableID, i, columnName) != "" ? getValueFromColumnName(tableID, i, columnName) : 0);
	}
	return toFixed(sum,2);
}

function toFixed(value, precision) {
    var precision = precision || 0,
        power = Math.pow(10, precision),
        absValue = Math.abs(Math.round(value * power)),
        result = (value < 0 ? '-' : '') + String(Math.floor(absValue / power));

    if (precision > 0) {
        var fraction = String(absValue % power),
            padding = new Array(Math.max(precision - fraction.length, 0) + 1).join('0');
        result += '.' + padding + fraction;
    }
    return result;
}

function removeThisFromCombo(fromThis,value){
	var currValue = getValue(fromThis);
	var dropdownElement = document.getElementById(fromThis);
	for (var i=0; i< dropdownElement.length; i++) {
		if (dropdownElement.options[i].value == value)
			dropdownElement.remove(i);
	}
	setControlValue(fromThis, currValue);
}

function isThisExistInCombo(comboId, value){
	var dropdownElement = document.getElementById(comboId);
	for (var i=0; i< dropdownElement.length; i++) {
		if (dropdownElement.options[i].value == value)
			return true;
	}
	return false;
}

function checkMandatory(controlNameList){
    var controlNameArr = controlNameList.split(",");
    for(var idx=0;idx<controlNameArr.length;idx++)
    {
        try
        {
			var cntrlID=controlNameArr[idx];
		if(getValue(cntrlID)=='' ||getValue(cntrlID)==undefined)
			{
				showAlertDialog(controlNameArr[idx]+ " is blank!");
				return false;
			}
            
        }
        catch(Exception)
        {
			console.log(Exception)
		}  
    }
}
