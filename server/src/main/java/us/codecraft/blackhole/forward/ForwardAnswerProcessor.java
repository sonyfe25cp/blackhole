package us.codecraft.blackhole.forward;

import java.io.IOException;
import java.net.SocketAddress;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xbill.DNS.Message;
import org.xbill.DNS.Record;
import org.xbill.DNS.Section;
import org.xbill.DNS.Type;

import us.codecraft.blackhole.antipollution.BlackListManager;
import us.codecraft.blackhole.antipollution.SafeHostManager;
import us.codecraft.blackhole.cache.CacheManager;
import us.codecraft.blackhole.config.Configure;
import us.codecraft.blackhole.utils.RecordBuilder;
import us.codecraft.blackhole.utils.RecordUtils;

/**
 * User: cairne Date: 13-5-19 Time: 下午7:28
 */
@Service
public class ForwardAnswerProcessor {

	@Autowired
	private CacheManager cacheManager;
	@Autowired
	private BlackListManager blackListManager;
	@Autowired
	private DNSHostsContainer dnsHostsContainer;

	@Autowired
	private ConnectionTimer connectionTimer;

	@Autowired
	private SafeHostManager safeBoxService;

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private Configure configure;

	public void handleAnswer(byte[] answer, Message message,
			SocketAddress remoteAddress, ForwardAnswer forwardAnswer)
			throws IOException {
		// fake dns server return an answer, it must be dns pollution
		if (configure.getFakeDnsServer() != null
				&& remoteAddress.equals(configure.getFakeDnsServer())) {
			addToBlacklist(message);
			String domain = StringUtils.removeEnd(message.getQuestion()
					.getName().toString(), ".");
			safeBoxService.setPoisoned(domain);
			return;
		}
		if (logger.isTraceEnabled()) {
			logger.trace("get message from " + remoteAddress + "\n" + message);
		}
		
		if (forwardAnswer == null) {
			logger.info("Received messages for "
					+ message.getQuestion().getName().toString()
					+ " after timeout!");
			return;
		}
		if (configure.isEnableSafeBox()) {
			answer = removeFakeAddress(message, answer);
		}

		if (answer != null) {
			forwardAnswer.decrCountDown();
			int order = dnsHostsContainer.getOrder(remoteAddress);
			if (RecordUtils.hasAnswer(message)) {
				if (forwardAnswer.confirmProcess(order)) {
					forwardAnswer.getResponser().response(answer);
					if (logger.isDebugEnabled()) {
						logger.debug("response message "
								+ message.getHeader().getID()
								+ " from "
								+ remoteAddress
								+ " to "
								+ forwardAnswer.getResponser()
								.getInDataPacket().getPort());
					}
					cacheManager.setResponseToCache(message, answer);
				}
			} else {//没有找到对应的ip地址
				//创建私有404的dns解析//还有bug...
				String host = message.getQuestion().getName().toString();
				logger.debug("{} is parsed to {}", host, "10.0.1.2");
				Record record = new RecordBuilder().dclass(1)
						.name(message.getQuestion().getName()).answer("10.0.1.2").type(Type.A).toRecord();
				message.addRecord(record, Section.ANSWER);
				forwardAnswer.getResponser().response(answer);
				cacheManager.setResponseToCache(message, answer);
				
				/**
				 * 下面是原来的内容
				 */
//				forwardAnswer.setTempAnswer(message);
//				if (forwardAnswer.getCountDown() <= 0) {
//					forwardAnswer.getResponser().response(answer);
//					forwardAnswer.setTempAnswer(null);
//					logger.info("it's time to redirect the 404");
//				}
			}
		}
	}

	private void addToBlacklist(Message message) {
		for (Record answer : message.getSectionArray(Section.ANSWER)) {
			String address = StringUtils.removeEnd(answer.rdataToString(), ".");
			if (!blackListManager.inBlacklist(address)) {
				logger.info("detected dns poisoning, add address " + address
						+ " to blacklist");
				blackListManager.addToBlacklist(address);
			}
		}
	}

	private byte[] removeFakeAddress(Message message, byte[] bytes) {
		Record[] answers = message.getSectionArray(Section.ANSWER);
		boolean changed = false;
		for (Record answer : answers) {
			String address = StringUtils.removeEnd(answer.rdataToString(), ".");
			if ((answer.getType() == Type.A || answer.getType() == Type.AAAA)
					&& blackListManager.inBlacklist(address)) {
				if (!changed) {
					// copy on write
					message = (Message) message.clone();
				}
				message.removeRecord(answer, Section.ANSWER);
				changed = true;
			}
		}
		if (changed
				&& message.getQuestion().getType() == Type.A
				&& (message.getSectionArray(Section.ANSWER) == null || message
						.getSectionArray(Section.ANSWER).length == 0)
				&& (message.getSectionArray(Section.ADDITIONAL) == null || message
						.getSectionArray(Section.ADDITIONAL).length == 0)
				&& (message.getSectionArray(Section.AUTHORITY) == null || message
						.getSectionArray(Section.AUTHORITY).length == 0)) {
			logger.info("remove message " + message.getQuestion());
			return null;
		}
		if (changed) {
			return message.toWire();
		}
		return bytes;
	}

}
