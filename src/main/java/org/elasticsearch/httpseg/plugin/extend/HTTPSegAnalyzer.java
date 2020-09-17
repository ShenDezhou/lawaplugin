package org.elasticsearch.httpseg.plugin.extend;

/**
 * @author BD-PC27
 */
import org.apache.lucene.analysis.Analyzer;

public class HTTPSegAnalyzer extends Analyzer {

    @Override
    protected TokenStreamComponents createComponents(String filedName) {
       return new TokenStreamComponents(new HTTPSegTokenizer());
    }
}
