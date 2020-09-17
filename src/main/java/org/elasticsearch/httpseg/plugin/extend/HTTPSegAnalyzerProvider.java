package org.elasticsearch.httpseg.plugin.extend;

/**
 * @author ZhangSen
 */
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.env.Environment;
import org.elasticsearch.index.IndexSettings;
import org.elasticsearch.index.analysis.AbstractIndexAnalyzerProvider;

public class HTTPSegAnalyzerProvider extends AbstractIndexAnalyzerProvider<HTTPSegAnalyzer> {
    private final HTTPSegAnalyzer HTTPSegAnalyzer;

    public HTTPSegAnalyzerProvider(IndexSettings indexSettings,
                                   Environment environment,
                                   String name,
                                   Settings settings) {
        super(indexSettings,name,settings);
        HTTPSegAnalyzer = new HTTPSegAnalyzer();
    }

    @Override
    public HTTPSegAnalyzer get() {
        return HTTPSegAnalyzer;
    }
}
