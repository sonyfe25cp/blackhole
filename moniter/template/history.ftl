<#include "header.ftl">
<#include "functions.ftl">

<div class="container-fluid">
  <div class="row">
    <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
    
    <script>
	$(document).ready(
		function(){
		
		    var now = new Date();
		    var thismonth = now.getMonth()+1;
		    var today = now.getDate();
		    var year = now.getFullYear();
		    
		    var html = "";
		    
		    for(var i = thismonth ; i > 0; i --){
		    	
		    	var end = 31;
		    
		    	if( i == thismonth){
		    		end = today;
		    	}
		    	
		    	var tmp_html = "<tr><td>"+i+"月</td><td>";
		    	
		    	for(var j = 1; j <= end; j ++){
		    	
		    		var month = i < 10 ? "0"+i: i;
		    		var day = j < 10 ? "0"+j : j;
		    		
		    		var ht = "${hrefType}";
		    		
		    		var tmp_day =  year + "-" + month +"-" + day;
		    		
		    		var link = "/pages/day/" + tmp_day + "/" + tmp_day + "." + ht + ".html";
		    		
		    		var line = "<a href='"+link+"' class='btn btn-info' style='margin-bottom:2px; margin-right:2px'>" + year + "-" + month +"-" + day +"</a>";
		    		
		    		tmp_html += line;
		    		
		    	}
		    	tmp_html += "</td></tr>";
		    	
		    	html += tmp_html;
		    }
	    $('#pastdays').append(html);
		
		}
	);    
    
    
    </script>
    
    	<h3>${title}</h3>
    
		<table id = 'pastdays' class='table'>
			<tr>
				<td style='width:60px;'>月份</td>
				<td>日期</td>
			</tr>
		</table>
	
	</div>

    
    </div>
  </div>
</div>
<#include "footer.ftl">
