<#include "header.ftl">
<#include "functions.ftl">

<div class="container-fluid">
  <div class="row">
    <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
      
        <script type="text/javascript" src="http://cdn.hcharts.cn/highcharts/highcharts.js"></script>
  		<script type="text/javascript" src="http://cdn.hcharts.cn/highcharts/exporting.js"></script>
         <script>
	    $(function () {
	    $('#pieContainer').highcharts({
	        chart: {
	            plotBackgroundColor: null,
	            plotBorderWidth: null,
	            plotShadow: false
	        },
	        title: {
	            text: '${title}  时间:${today}'
	        },
	        tooltip: {
	    	    pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
	        },
	        plotOptions: {
	            pie: {
	                allowPointSelect: true,
	                cursor: 'pointer',
	                dataLabels: {
	                    enabled: true,
	                    color: '#000000',
	                    connectorColor: '#000000',
	                    format: '<b>{point.name}</b>: {point.percentage:.1f} %'
	                }
	            }
	        },
	        series: [{
	            type: 'pie',
	            name: 'Browser share',
	            data: [
	                ${json}
	            ]
	        }]
	    });
	});	
	  </script>
      <div id="pieContainer" style="min-width:700px;height:400px"></div>
      
      <script>
      $(document).ready(function(){
      	  var url = window.location.href;
      	  if(url.endWith(".ips.html")){
		      var hour=new Date().getHours();
		      if(hour > 0){
		      	$('#hours').append("<legend>按时间分割</legend>");
		      }
		      for(var i = hour; i >0; i --){
		         var b = i-1;
		     	 var curl = url.replace('.ips.html', '-'+b+'-ips.html');
		      	var link = "<div class='col-md-2' style='margin-bottom:2px;'><a href="+curl+" class='btn btn-warning'>"+i+":00 -- "+b+":00<a></div>";
		      	$('#hours').append(link);
		      }
      	  }else if(url.endWith(".sites.html")){
      	  	var hour=new Date().getHours();
		      if(hour > 0){
		      	$('#hours').append("<legend>按时间分割</legend>");
		      }
		      for(var i = hour; i >0; i --){
		        var b = i-1;
	      	  	var curl = url.replace('.sites.html', '-'+b+'-sites.html');
		      	var link = "<div class='col-md-2' style='margin-bottom:2px;'><a href="+curl+" class='btn btn-warning'>"+i+":00 -- "+b+":00<a></div>";
		      	$('#hours').append(link);
		      }
      	  }
      });
     	
      </script>
      
      <div id="hours" class="row">
      </div>
      <legend>详情</legend>
      <table class="table">
      	<thead>
      		<tr>
      			<th>时间</th>
      			<th>站点</th>
      			<th>IP</th>
      		</tr>
      	</thead>
      	<tbody>
      		<#list dnsLogs as log>
      			<#if log.useful>
      			<tr>
      				<td>${log.time?datetime}</td>
      				<td><a href='http://${log.host}' target="_blank">${log.host}</a></td>
      				<td>${log.requestIp}</td>
      			</tr>
      			</#if>
      		</#list>
      	</tbody>
      </table>
    </div>
  </div>
</div>
<#include "footer.ftl">
