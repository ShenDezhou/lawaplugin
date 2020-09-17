package org.elasticsearch.httpseg.plugin;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.elasticsearch.httpseg.plugin.extend.HTTPSegAnalyzer;

import org.junit.Test;

/**
 * @author BD-PC27
 */
public class HTTPSegAnalyzerTest {
    @Test
    public void testAnalyzer() throws Exception {
        HTTPSegAnalyzer analyzer = new HTTPSegAnalyzer();
        TokenStream ts = analyzer.tokenStream("text","我可能得到了正确的结果！");
        CharTermAttribute term = ts.addAttribute(CharTermAttribute.class);
        ts.reset();
        while (ts.incrementToken()) {
            System.out.println(term.toString());
        }
        ts.end();
        ts.close();
    }

    @Test
    public void testAnalyzerNull() throws Exception {
        HTTPSegAnalyzer analyzer = new HTTPSegAnalyzer();
        TokenStream ts = analyzer.tokenStream("text","");
        CharTermAttribute term = ts.addAttribute(CharTermAttribute.class);
        ts.reset();
        while (ts.incrementToken()) {
            System.out.println(term.toString());
        }
        ts.end();
        ts.close();
    }
}
