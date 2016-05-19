<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<s:head theme="xhtml" />

<table>
  <th>Operation Type</th>
  <th>Date and Time</th>
  <th></th>
  <th></th>
  <c:forEach  items="${operationList}" var="operation">
    <tr>
       <td>${operation.operationType}</td>
       <td>${operation.time}</td>
       <td><img src="/${initParam.appName}/assets/images/revertButton.jpg" onclick="revertUpdate('${operation.operationId}');"></img></td>
       <%--<c:if test="${operation.operationType =='CSTAT UPDATE'}">--%>
              <td><img src="/${initParam.appName}/assets/images/deleteArchiveButton.jpg" onclick="deleteArchive('${operation.operationId}');"></img></td>
       
    </tr>
  </c:forEach>
</table>

<script>

	function revertUpdate(operationId){
		if(confirm("Do you want to revert the update operation")){
			document.body.innerHTML += '<form id="dynForm" action="deletePrevUpdate" method="post">'
		        +' <input type="hidden" name="operationId" value="' + operationId + '"> </form>';
		        document.getElementById("dynForm").submit();
			}
	}

	function deleteArchive(operationId){
		if(confirm("Do you want to delete the archived records")){
			document.body.innerHTML += '<form id="dynForm" action="deleteArchive" method="post">'
		        +' <input type="hidden" name="operationId" value="' + operationId + '"> </form>';
		        document.getElementById("dynForm").submit();
			}
	}

</script>