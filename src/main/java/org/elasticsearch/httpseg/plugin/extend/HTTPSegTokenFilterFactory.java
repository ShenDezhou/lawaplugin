package org.elasticsearch.httpseg.plugin.extend;

/**
 * @author BD-PC27
 */
import org.apache.lucene.analysis.TokenStream;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.env.Environment;
import org.elasticsearch.index.IndexSettings;
import org.elasticsearch.index.analysis.AbstractTokenFilterFactory;

public class HTTPSegTokenFilterFactory extends AbstractTokenFilterFactory {
    public HTTPSegTokenFilterFactory(IndexSettings indexSettings,
                                     Environment environment,
                                     String name,
                                     Settings settings) {
        super(indexSettings,name,settings);
    }

    @Override
    public TokenStream create(TokenStream tokenStream) {
        return new HTTPSegTokenFilter(tokenStream);
    }

}
