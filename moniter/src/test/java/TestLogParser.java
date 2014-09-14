import junit.framework.TestCase;

import org.junit.Test;

import com.omartech.mmaker.utils.DNSLogParser;



public class TestLogParser  extends TestCase{

    public void testParse(){
        
    }
    
    @Test
    public void testUseful1(){
    		String url1 = "asdfasdf.";
    		boolean userful1 = DNSLogParser.isUserful(url1);
    		assertEquals(false, userful1);
    		String url2 = "a.asdfasdf.";
    		boolean userful2 = DNSLogParser.isUserful(url2);
    		assertEquals(true, userful2);
    		String url3 = "a.a.asdfasdf.";
    		boolean userful3 = DNSLogParser.isUserful(url3);
    		assertEquals(false, userful3);
    }
}
