package org.elasticsearch.httpseg.plugin.extend;

/**
 * @author ZhangSen
 */
import org.apache.lucene.analysis.Analyzer;

public class HTTPSegAnalyzer extends Analyzer {

    @Override
    protected TokenStreamComponents createComponents(String filedName) {
       return new TokenStreamComponents(new HTTPSegTokenizer());
    }
}
