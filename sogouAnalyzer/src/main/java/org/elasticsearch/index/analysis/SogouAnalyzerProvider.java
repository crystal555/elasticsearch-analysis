package org.elasticsearch.index.analysis;

import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.inject.assistedinject.Assisted;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.env.Environment;
import org.elasticsearch.index.Index;
import org.elasticsearch.index.settings.IndexSettings;
import org.elasticsearch.plugin.analysis.cn.SogouAnalyzer;


public class SogouAnalyzerProvider extends AbstractIndexAnalyzerProvider<SogouAnalyzer> {

    private final SogouAnalyzer analyzer;

    @Inject public SogouAnalyzerProvider(Index index, @IndexSettings Settings indexSettings, Environment env, @Assisted String name, @Assisted Settings settings) {
        super(index, indexSettings, name, settings);
        // use the default set of stop words (really just punctuation) provided by SmartChinese
        analyzer = new SogouAnalyzer();
    }

    @Override public SogouAnalyzer get() {
        return this.analyzer;
    }
    
}