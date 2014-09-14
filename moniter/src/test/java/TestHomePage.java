import com.google.gson.Gson;
import com.omartech.mmaker.template.HomePage;

public class TestHomePage {

	public static void main(String[] args) {
		Gson gson = new Gson();
		String title = "网络监控仪";
		String body = "<p>网络分析仪是一款通过优化网络请求协议，智能缓存本地网络的网络请求，达到优化网络访问速度的高科技网络加速产品。本产品同时提供对本地网络的上网记录监控和分析功能，便于了解和分析本地上网用户的需求。</p><p>本产品的特点：</p><p>1. 优化网络请求，提高上网速度。</p><p>2. 监控上网情况，分析上网需求。</p>";
		String about = "<p>"+title+"致力于加速网络访问，监控网络请求，提供上网数据分析。</p>";
		String contact = "<p>客服支持：</p><p>工作时间：周一到周五，10:00至17:00</p>";
		HomePage page = new HomePage();
		page.setBody(body);
		page.setTitle(title);
		page.setAbout(about);
		page.setContact(contact);
		String json = gson.toJson(page);
		System.out.println(json);
	}

}
