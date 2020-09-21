package org.elasticsearch.httpseg.plugin;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.elasticsearch.httpseg.plugin.extend.HTTPSegAnalyzer;

import org.elasticsearch.httpseg.plugin.extend.HTTPSegAnalyzerProvider;
import org.elasticsearch.httpseg.plugin.extend.Segmenter;
import org.junit.Test;

/**
 * @author ZhangSen
 */
public class HTTPSegAnalyzerTest {

    @Test
    public void testLoadFile() throws Exception {
        String val = Segmenter.getPath("configs/core.config","modelserver");
        System.out.println(val);
    }

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
