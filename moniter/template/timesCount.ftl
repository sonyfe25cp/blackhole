<#include "header.ftl">
<#include "functions.ftl">

<div class="container-fluid">
  <div class="row">
    <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
      
        <script type="text/javascript" src="/js/highcharts.js"></script>
  		<script type="text/javascript" src="/js/exporting.js"></script>
         <script>
	    $(function () {
    $('#today').highcharts({
        chart: {
            type: 'line'
        },
        title: {
            text: '${title}'
        },
        subtitle: {
            text: 'Source: 系统统计'
        },
        xAxis: {
        	title :{
	        	text :'时间'    
            },
            categories: [${dates}]
        },
        yAxis: {
            title: {
                text: '个数'
            }
        },
        tooltip: {
            enabled: false,
            formatter: function() {
                return '<b>'+ this.series.name +'</b><br/>'+this.x +': '+ this.y +'°C';
            }
        },
        plotOptions: {
            line: {
                dataLabels: {
                    enabled: true
                },
                enableMouseTracking: false
            }
        },
        series: [
        	${data}
        ]
    });
});
	  </script>
      <div id="today" style="min-width:700px;height:400px"></div>
    </div>
  </div>
</div>
<#include "footer.ftl">
