<script>
$(document).ready(function(){
	var today = formatDate(new Date());
	$('#todayNetWork').attr('href', "/pages/day/"+today+"/"+today+".stats.html");
	$('#todayShangwang').attr('href', "/pages/day/"+today+"/"+today+".sites.html");
	$('#todayIp').attr('href', "/pages/day/"+today+"/"+today+".ips.html");
	
	
	
	var url = window.location.href;
    
    if(/.*overview\.html$/.test(url)){
    	$('li.active').removeClass('active');
    	$('#l1').addClass('active');
    }
    if(/.*\.stats\.html$/.test(url)){
    	$('li.active').removeClass('active');
    	$('#l2').addClass('active');
    }
    if(/.*ips\.html$/.test(url)){
    	$('li.active').removeClass('active');
    	$('#l3').addClass('active');
    }
    if(/.*sites\.html$/.test(url)){
    	$('li.active').removeClass('active');
    	$('#l4').addClass('active');
    }
    if(/.*ips-history\.html$/.test(url)){
    	$('li.active').removeClass('active');
    	$('#h1').addClass('active');
    }
    if(/.*sites-history\.html$/.test(url)){
    	$('li.active').removeClass('active');
    	$('#h2').addClass('active');
    }
    if(/.*about\.html$/.test(url)){
    	$('li.active').removeClass('active');
    	$('#c1').addClass('active');
    }
    if(/.*contact\.html$/.test(url)){
    	$('li.active').removeClass('active');
    	$('#c2').addClass('active');
    }
	
	
});
</script>

<div class="col-sm-3 col-md-2 sidebar">
  <ul class="nav nav-sidebar">
    <li id='l1'><a href="/overview.html">系统说明</a></li>
    <li id='l2'><a href="#" id="todayNetWork">今日网络访问报告</a></li>
    <li id='l3'><a href="#" id="todayIp">今日IP访问报告</a></li>
    <li id='l4'><a href="#" id="todayShangwang">今日网站访问报告</a></li>
  </ul>
  
  <ul class="nav nav-sidebar">
    <li id='h1'><a href="/ips-history.html">历史IP访问数据</a></li>
    <li id='h2'><a href="/sites-history.html">历史网站访问数据</a></li>
    <!--
    <li><a href="">One more nav</a></li>
    <li><a href="">Another nav item</a></li>
    <li><a href="">More navigation</a></li>
    -->
  </ul>
  <#include "about.ftl">
</div>