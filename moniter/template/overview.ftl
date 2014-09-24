<#include "header.ftl">
<#include "functions.ftl">

<div class="container-fluid">
  <div class="row">
    <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
        ${homepage.overview}
        
        
        <div class="panel panel-primary">
		  <div class="panel-heading">
		    <h3 class="panel-title">配置说明</h3>
		  </div>
		  <div class="panel-body">
	        <ul class="list-group">
	        	<li class="list-group-item">1. 登陆局域网的路由器管理界面</li>
	        	<li class="list-group-item">2. 找到DNS配置页面</li>
	        	<li class="list-group-item">3. 将第一行DNS地址配置为本服务器的IP地址</li>
	        	<li class="list-group-item">4. 将网络提供商的DNS地址配置为备用DNS地址</li>
	        	<li class="list-group-item">5. 保存设置，重启路由器</li>
	        	<li class="list-group-item">6. 保持该服务器正常运行，若遭遇停电等意外情况，重启之后，系统将自动恢复。</li>
	        </ul>
		  </div>
		</div>
        
        
        <div class="panel panel-primary">
		  <div class="panel-heading">
		    <h3 class="panel-title">使用说明</h3>
		  </div>
		  <div class="panel-body">
	        <ul class="list-group">
	        	<li class="list-group-item">1. '今日网络访问报告' 提供今天截止到当前时间，每个小时的网络请求数量</li>
	        	<li class="list-group-item">2. '今日IP访问报告' 提供今天截止到当前时间，网络内不同IP的访问地址分析和IP请求次数的分析。</li>
	        	<li class="list-group-item">3. '今日网站访问报告' 提供今天截止到当前时间，每个小时的网络访问情况，包括具体的网站访问记录和相关统计分析。</li>
	        	<li class="list-group-item">4. '历史IP访问数据' 提供截止到今天，每天的IP访问数据记录。</li>
	        	<li class="list-group-item">5. '历史网站访问数据' 提供截止到今天，每天的网站访问数据记录。</li>
	        </ul>
		  </div>
		</div>
        
        
        <div class="panel panel-primary">
		  <div class="panel-heading">
		    <h3 class="panel-title">性能说明</h3>
		  </div>
		  <div class="panel-body">
	        <ul class="list-group">
	        	<li class="list-group-item">1. 采用网页静态化技术，可以有效防止sql注入攻击。</li>
	        	<li class="list-group-item">2. 采用DNS代理技术，可以有效检测非法网络请求和攻击。</li>
	        </ul>
		  </div>
		</div>
        
        
    </div>
  </div>
</div>
<#include "footer.ftl">
