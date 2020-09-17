package org.elasticsearch.httpseg.plugin.extend;

/**
 * @author ZhangSen
 */
import org.apache.lucene.analysis.Tokenizer;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.env.Environment;
import org.elasticsearch.index.IndexSettings;
import org.elasticsearch.index.analysis.AbstractTokenizerFactory;

public class HTTPSegTokenizerFactory extends AbstractTokenizerFactory{
    Environment environment;
    public HTTPSegTokenizerFactory(IndexSettings indexSettings,
                                   Environment environment,
                                   String ignored,
                                   Settings settings){
        super(indexSettings,ignored,settings);
        this.environment = environment;
    }

    @Override
    public Tokenizer create() {
        return new HTTPSegTokenizer(environment);
    }
}
