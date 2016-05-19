function getXMLFromString(xmlString) {
	var xmlDoc = null;
	try {
		xmlDoc = document.implementation.createDocument("", "", null);
		var parser = new DOMParser();
		xmlDoc = parser.parseFromString(xmlString, "text/xml");
	} catch (e) {
		try {
			xmlDoc = new ActiveXObject("Microsoft.XMLDOM");
			xmlDoc.async = this.async;
			xmlDoc["loadXML"](xmlString);
		} catch (e) {
			alert(e.message)
		}
	}
	return (xmlDoc);
}

/**
 * @aNode: Node that will be used
 * @aExpr: xpath that will be used
 */
/*function evaluateXPath(aNode, aExpr) {
	var found = new Array();
	try {
		found = aNode.selectNodes(aExpr);
	} catch (e) {
		try {
			// var xpe = new XPathEvaluator();
			var xpe = aNode.ownerDocument || aNode;
			// var nsResolver = xpe.createNSResolver(aNode.ownerDocument == null
			// ? aNode.documentElement : aNode.ownerDocument.documentElement);
			var nsResolver = xpe.createNSResolver(xpe.documentElement);
			var result = xpe.evaluate(aExpr, aNode, nsResolver, 0, null);
			var res;
			while (res = result.iterateNext())
				found.push(res);
		} catch (e) {
			// alert(e.message);
			return;
		}
	}
	return found;
}*/

// Evaluate an XPath expression aExpression against a given DOM node
// or Document object (aNode), returning the results as an array
// thanks wanderingstan at morethanwarm dot mail dot com for the
// initial work.
function evaluateXPath(aNode, aExpr) {
	var xpe = new XPathEvaluator();
	var nsResolver = xpe
			.createNSResolver(aNode.ownerDocument == null ? aNode.documentElement
					: aNode.ownerDocument.documentElement);
	var result = xpe.evaluate(aExpr, aNode, nsResolver, 0, null);
	var found = [];
	var res;
	while (res = result.iterateNext())
		found.push(res);
	return found;
}

function editForm(formName,paramsToIgnore){
	  formId = document.getElementById(formName);
	  formElements = formId.elements;
	  for(i=0;i<formElements.length;i++){
		  if(paramsToIgnore.indexOf(formElements[i].name+",") == -1){
			  formElements[i].removeAttribute("readonly");			  
			  }
		  if(formElements[i].type == "submit"){
			  formElements[i].removeAttribute("disabled");
		  }
		  }
	  
	  }

function openLink(message,link){
	if(confirm(message)){
		parent.location=link;
		i=2;
	}	


function openLinkWithOutMessage(link){	
		parent.location=link;
	}	
}

// check if the text box is empty

function checkEmpty(element)
{ 
	if((element.value.trim()== "")||(element.value.trim()== "0"))
	{ 
		return true; 
	}	
	return false; 
}

//check if the text box is empty

function emailValidator(element)
{ 
	if(checkEmpty(element)==false)
		{
			var emailvalid = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/; 
			if(element.value.match(emailvalid)) 
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

//check if it has only numbers
function numbersCheck(element)  
{  
	
	if(checkEmpty(element)==false)
	{
			var numbers = /^[-+]?[0-9]+$/;  
		   if(element.value.match(numbers))  
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