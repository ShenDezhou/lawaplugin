package org.elasticsearch.httpseg.plugin;

import org.apache.lucene.analysis.Analyzer;
import org.elasticsearch.httpseg.plugin.extend.HTTPSegAnalyzerProvider;
import org.elasticsearch.httpseg.plugin.extend.HTTPSegTokenizerFactory;
import org.elasticsearch.index.analysis.AnalyzerProvider;
import org.elasticsearch.index.analysis.TokenizerFactory;
import org.elasticsearch.indices.analysis.AnalysisModule;
import org.elasticsearch.plugins.AnalysisPlugin;
import org.elasticsearch.plugins.Plugin;

import java.util.Collections;
import java.util.Map;

/**
 * @author ZhangSen
 */

public class HTTPSegPlugin extends Plugin implements AnalysisPlugin {
    public static String PLUGIN_NAME = "analysis-lawa";

    public HTTPSegPlugin() {
        super();
    }

    @Override
    public Map<String,AnalysisModule.AnalysisProvider<TokenizerFactory>> getTokenizers() {
        return Collections.singletonMap("lawa-word", HTTPSegTokenizerFactory::new);
    }

    @Override
    public Map<String,AnalysisModule.AnalysisProvider<AnalyzerProvider<? extends Analyzer>>> getAnalyzers(){
        return Collections.singletonMap("lawa", HTTPSegAnalyzerProvider::new);
    }
}
