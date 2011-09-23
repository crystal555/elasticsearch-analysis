package org.elasticsearch.plugin.analysis.cn;

import org.elasticsearch.common.inject.Module;
import org.elasticsearch.index.analysis.AnalysisModule;
import org.elasticsearch.index.analysis.SogouAnalysisBinderProcessor;
import org.elasticsearch.plugins.AbstractPlugin;

public class SogouAnalyzerPlugin extends AbstractPlugin {

    @Override public String name() {
        return "analysis-smartchinese";
    }

    @Override public String description() {
        return "A better word segmenter for Chinese: TODO URL";
    }

    @Override public void processModule(Module module) {
        if (module instanceof AnalysisModule) {
            AnalysisModule analysisModule = (AnalysisModule) module;
            analysisModule.addProcessor(new SogouAnalysisBinderProcessor());
        }
    }
}


