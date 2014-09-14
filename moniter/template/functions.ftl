<script>
$(document).ready(function(){
	var today = formatDate(new Date());
	$('#todayShangwang').attr('href', "/pages/day/"+today+"/"+today+".sites.html");
	$('#todayIp').attr('href', "/pages/day/"+today+"/"+today+".ips.html");
});
</script>

<div class="col-sm-3 col-md-2 sidebar">
  <ul class="nav nav-sidebar">
    <li class="active"><a href="#">总体说明</a></li>
    <li><a href="#" id="todayNetWork">今日网络访问报告</a></li>
    <li><a href="#" id="todayIp">今日IP访问报告</a></li>
    <li><a href="#" id="todayShangwang">今日上网分析</a></li>
  </ul>
  
  <ul class="nav nav-sidebar">
    <li><a href="">按网站搜索</a></li>
    <li><a href="">按ip搜索</a></li>
    <!--
    <li><a href="">One more nav</a></li>
    <li><a href="">Another nav item</a></li>
    <li><a href="">More navigation</a></li>
    -->
  </ul>
  <#include "about.ftl">
</div>