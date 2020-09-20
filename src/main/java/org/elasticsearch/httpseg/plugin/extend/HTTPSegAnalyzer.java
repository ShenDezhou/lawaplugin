package org.elasticsearch.httpseg.plugin.extend;

/**
 * @author ZhangSen
 */
import org.apache.lucene.analysis.Analyzer;
import org.elasticsearch.env.Environment;

public class HTTPSegAnalyzer extends Analyzer {
    Environment environment;
    public HTTPSegAnalyzer(){}
    public HTTPSegAnalyzer(Environment environment) {
        this.environment = environment;
    }

    @Override
    protected TokenStreamComponents createComponents(String filedName) {
        if(this.environment!=null){
            return new TokenStreamComponents(new HTTPSegTokenizer(this.environment));
        }
       return new TokenStreamComponents(new HTTPSegTokenizer());
    }
}
