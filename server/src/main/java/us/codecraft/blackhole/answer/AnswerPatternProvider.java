package us.codecraft.blackhole.answer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xbill.DNS.Address;
import org.xbill.DNS.Type;

/**
 * Read the config to domainPatterns and process the request record.
 * 
 * @author yihua.huang@dianping.com
 * @date Dec 14, 2012
 */
@Component
public class AnswerPatternProvider implements AnswerProvider {
	

	private DomainPatternsContainer domainPatternsContainer = new DomainPatternsContainer();

	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * When the address configured as "DO_NOTHING",it will not return any
	 * address.
	 */
	public static final String DO_NOTHING = "do_nothing";
	private static final String FAKE_MX_PREFIX = "mail.";
	private static final String FAKE_CANME_PREFIX = "cname.";

	@Autowired
	private TempAnswerProvider tempAnswerContainer;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * us.codecraft.blackhole.answer.AnswerProvider#getAnswer(java.lang.String,
	 * int)
	 */
	@Override
	public String getAnswer(String host, int type) {
		if (type == Type.PTR) {
			return null;
		}
		String ip = domainPatternsContainer.getIp(host);
		if (ip == null || ip.equals(DO_NOTHING)) {
			return null;
		}
		if (type == Type.MX) {
			String fakeMXHost = fakeMXHost(host);
			tempAnswerContainer.add(fakeMXHost, Type.A, ip);
			return fakeMXHost;
		}
		if (type == Type.CNAME) {
			String fakeCNAMEHost = fakeCNAMEHost(host);
			tempAnswerContainer.add(fakeCNAMEHost, Type.A, ip);
			return fakeCNAMEHost;
		}
		try {
			String reverseIp = reverseIp(ip);
			logger.debug("reverseIp:{}", reverseIp);
			tempAnswerContainer.add(reverseIp(ip), Type.PTR, host);//增加一条反查ptr记录
		} catch (Throwable e) {
			logger.info("not a ip, ignored");
		}
		return ip;
	}

	/**
	 * generate a fake MX host
	 * 
	 * @param domain
	 * @return
	 */
	private String fakeMXHost(String domain) {
		return FAKE_MX_PREFIX + domain;
	}

	/**
	 * @param domain
	 * @return
	 */
	private String fakeCNAMEHost(String domain) {
		return FAKE_CANME_PREFIX + domain;
	}

	private String reverseIp(String ip) {
		int[] array = Address.toArray(ip);
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = array.length - 1; i >= 0; i--) {
			stringBuilder.append(array[i] + ".");
		}
		stringBuilder.append("in-addr.arpa.");
		return stringBuilder.toString();
	}

	public void setDomainPatternsContainer(DomainPatternsContainer domainPatternsContainer) {
		this.domainPatternsContainer = domainPatternsContainer;
	}
}
