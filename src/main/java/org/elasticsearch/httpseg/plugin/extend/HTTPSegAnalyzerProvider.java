package org.elasticsearch.httpseg.plugin.extend;

/**
 * @author ZhangSen
 */
import org.apache.lucene.analysis.Analyzer;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.env.Environment;
import org.elasticsearch.index.IndexSettings;
import org.elasticsearch.index.analysis.AbstractIndexAnalyzerProvider;
import org.elasticsearch.index.analysis.AnalyzerProvider;

public class HTTPSegAnalyzerProvider extends AbstractIndexAnalyzerProvider<HTTPSegAnalyzer> {
    private final HTTPSegAnalyzer HTTPSegAnalyzer;

    public HTTPSegAnalyzerProvider(IndexSettings indexSettings,
                                   Environment environment,
                                   String name,
                                   Settings settings) {
        super(indexSettings,name,settings);
        HTTPSegAnalyzer = new HTTPSegAnalyzer(environment);
    }

    @Override
    public HTTPSegAnalyzer get() {
        return HTTPSegAnalyzer;
    }

    public static AnalyzerProvider<? extends Analyzer> getHTTPSegAnalyzerProvider(IndexSettings indexSettings,
                                                                                  Environment env, String s, Settings settings) {
        HTTPSegAnalyzerProvider httpsegAnalyzerProvider = new HTTPSegAnalyzerProvider(indexSettings, env, s, settings);
        return httpsegAnalyzerProvider;
    }

}
