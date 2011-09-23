package org.elasticsearch.plugin.analysis.cn;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Set;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.LowerCaseTokenizer;
import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.util.Version;

public final class SogouAnalyzer extends Analyzer {
	private Set stopWords;
	public static final String[] CHINESE_ENGLISH_STOP_WORDS = { "a", "an",
			"and", "are", "as", "at", "be", "but", "by", "for", "if", "in",
			"into", "is", "it", "no", "not", "of", "on", "or", "s", "such",
			"t", "that", "the", "their", "then", "there", "these", "they",
			"this", "to", "was", "will", "with", "我", "我们" };

	public SogouAnalyzer() {
		this.stopWords = StopFilter.makeStopSet(Version.LUCENE_33,
				CHINESE_ENGLISH_STOP_WORDS);
	}

	public SogouAnalyzer(String[] stopWordList) {
		this.stopWords = StopFilter
				.makeStopSet(Version.LUCENE_33, stopWordList);
	}

	public TokenStream tokenStream(String fieldName, Reader reader) {
		TokenStream result = null;
		try {
			String URL = "http://10.10.99.4:10085/simpleseg";
			String encoding = "utf8";
			SogouSegmantation sgsg = new SogouSegmantation(URL, encoding);
			String resultString = sgsg.doSogouSplit(reader);
			result = new LowerCaseTokenizer(Version.LUCENE_33,
					new StringReader(resultString));
			result = new StopFilter(Version.LUCENE_33, result, stopWords);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static void main(String[] args) {
		// 好像英文的结束符号标点.,StandardAnalyzer不能识别
		String string = new String("中华人民共和国");
		Analyzer analyzer = new SogouAnalyzer();
		TokenStream ts = analyzer.tokenStream("dummy", new StringReader(string));
		CharTermAttribute termAtt = (CharTermAttribute) ts.getAttribute(CharTermAttribute.class);
		try {
			while (ts.incrementToken()) {
				System.out.println(termAtt.toString());	
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(' ');

	}
}








