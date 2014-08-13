package us.codecraft.blackhole.answer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import us.codecraft.blackhole.context.RequestContext;
import us.codecraft.blackhole.utils.DoubleKeyMap;

/**
 * @author yihua.huang@dianping.com
 * @date Dec 14, 2012
 */
@Component
public class CustomTempAnswerProvider implements AnswerProvider {
	static Logger logger = LoggerFactory.getLogger(CustomTempAnswerProvider.class);
	private Map<String,DoubleKeyMap<String, Integer, String>> container;

	public CustomTempAnswerProvider() {
		container = new ConcurrentHashMap<String, DoubleKeyMap<String, Integer, String>>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * us.codecraft.blackhole.answer.AnswerProvider#getAnswer(java.lang.String,
	 * int)
	 */
	@Override
	public String getAnswer(String query, int type) {
        String ip = RequestContext.getClientIp();
        DoubleKeyMap<String, Integer, String> stringIntegerStringDoubleKeyMap = container.get(ip);
        if (stringIntegerStringDoubleKeyMap==null){
            return null;
        }
        return stringIntegerStringDoubleKeyMap.get(query, type);
    }

	public void add(String clientIp,String query, int type, String answer) {
        DoubleKeyMap<String, Integer, String> stringIntegerStringDoubleKeyMap = container.get(clientIp);
        if (stringIntegerStringDoubleKeyMap==null){
            stringIntegerStringDoubleKeyMap = new DoubleKeyMap<String, Integer, String>(ConcurrentHashMap.class);
            container.put(clientIp,stringIntegerStringDoubleKeyMap);
        }
        stringIntegerStringDoubleKeyMap.put(query, type, answer);
	}

}
