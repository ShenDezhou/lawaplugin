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
        if (this.environment!=null) {
            HTTPSegTokenizer st = new HTTPSegTokenizer(this.environment);
            st.setCutMode(1); // 1 for smart cut mode.
            return new TokenStreamComponents(st);
        }
        HTTPSegTokenizer st = new HTTPSegTokenizer();
        st.setCutMode(1);
        return new TokenStreamComponents(st);
    }
}
