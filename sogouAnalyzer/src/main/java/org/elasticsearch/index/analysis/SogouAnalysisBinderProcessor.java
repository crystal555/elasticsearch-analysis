package org.elasticsearch.index.analysis;


/**
* @author thmttch (matt.chu@gmail.com)
*/
public class SogouAnalysisBinderProcessor extends AnalysisModule.AnalysisBinderProcessor {

    @Override public void processAnalyzers(AnalyzersBindings analyzersBindings) {
        analyzersBindings.processAnalyzer("smartcn", SogouAnalyzerProvider.class);
    }

}