package us.codecraft.blackhole.answer;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xbill.DNS.DClass;
import org.xbill.DNS.Name;
import org.xbill.DNS.Record;
import org.xbill.DNS.Section;
import org.xbill.DNS.Type;

import us.codecraft.blackhole.container.Handler;
import us.codecraft.blackhole.container.MessageWrapper;
import us.codecraft.blackhole.utils.RecordBuilder;

/**
 * @author yihua.huang@dianping.com <br>
 * @date: 13-7-14 <br>
 * Time: 下午4:36 <br>
 */
public abstract class AbstractAnswerHandler implements Handler {

    protected abstract List<AnswerProvider> getaAnswerProviders();

    protected Logger logger = LoggerFactory.getLogger(AbstractAnswerHandler.class);

    // b._dns-sd._udp.0.129.37.10.in-addr.arpa.
    private final Pattern filterPTRPattern = Pattern
            .compile(".*\\.(\\d+\\.\\d+\\.\\d+\\.\\d+\\.in-addr\\.arpa\\.)");

    private String filterPTRQuery(String query) {
        Matcher matcher = filterPTRPattern.matcher(query);
        if (matcher.matches()) {
            return matcher.group(1);
        } else {
            return query;
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see us.codecraft.blackhole.server.Handler#handle(org.xbill.DNS.Message,
     * org.xbill.DNS.Message)
     */
    @Override
    public boolean handle(MessageWrapper request, MessageWrapper response) {
        Record question = request.getMessage().getQuestion();
        String host = question.getName().toString();//请求的域名
        int type = question.getType();
        if (type == Type.PTR) {
            host = filterPTRQuery(host);
        }
        // some client will query with any
        if (type == Type.ANY) {
            type = Type.A;
        }
        if (logger.isDebugEnabled()) {
            logger.debug("query \t" + Type.string(type) + "\t"
                    + DClass.string(question.getDClass()) + "\t" + host);
        }
        logger.debug("host:{}, type:{}, DClass:{}", new String[]{host, type+"", question.getDClass()+""});
        for (AnswerProvider answerProvider : getaAnswerProviders()) {
            String answer = answerProvider.getAnswer(host, type);
            String className = answerProvider.getClass().getName();
            logger.debug("{} get answer :{}", className, answer);
            if (answer != null) {
                try {
                    Record record = new RecordBuilder()
                            .dclass(question.getDClass())
                            .name(question.getName()).answer(answer).type(type)
                            .toRecord();
                    response.getMessage().addRecord(record, Section.ANSWER);
                    if (logger.isDebugEnabled()) {
                        logger.debug("answer\t" + Type.string(type) + "\t"
                                + DClass.string(question.getDClass()) + "\t"
                                + answer);
                    }
                    response.setHasRecord(true);
                    return false;
                } catch (Throwable e) {
                    logger.warn("handling exception " + e);
                }
            }
        }
        return true;
    }
}