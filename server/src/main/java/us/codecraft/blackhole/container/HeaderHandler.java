package us.codecraft.blackhole.container;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.xbill.DNS.Flags;
import org.xbill.DNS.Record;
import org.xbill.DNS.Section;

/**
 * @author yihua.huang@dianping.com
 * @date Dec 14, 2012
 */
@Component
public class HeaderHandler implements Handler {
	static Logger logger = LoggerFactory.getLogger(HeaderHandler.class);
	/*
	 * (non-Javadoc)
	 * 
	 * @see us.codecraft.blackhole.server.Handler#handle(org.xbill.DNS.Message,
	 * org.xbill.DNS.Message)
	 */
	@Override
	public boolean handle(MessageWrapper request, MessageWrapper response) {
		logger.info("win");
		response.getMessage().getHeader().setFlag(Flags.QR);
		if (request.getMessage().getHeader().getFlag(Flags.RD)) {
			response.getMessage().getHeader().setFlag(Flags.RD);
		}
		Record queryRecord = request.getMessage().getQuestion();
		response.getMessage().addRecord(queryRecord, Section.QUESTION);
		return true;
	}
}
