package org.elasticsearch.plugin.analysis.cn;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SogouSegmantation {
	//分词接口的url，编码，要分词的句子
	private String segmantationUrl;
	private String encoding;
	
	public String getSegmantationUrl() {
		return segmantationUrl;
	}
	public void setSegmantationUrl(String segmantationUrl) {
		this.segmantationUrl = segmantationUrl;
	}
	public String getEncoding() {
		return encoding;
	}
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
	
	//构造方法
	public SogouSegmantation(String segmantationUrl,String encoding){
		this.setSegmantationUrl(segmantationUrl);
		this.setEncoding(encoding);
	}
	
	//分词方法
	public String doSogouSplit(Reader sentance){
		String result=null;
		String sentanceString = readerToString(sentance);
		result=getSogouSplitResult(sentanceString);
		result=normalizeResult(result);
		return result;		
	}
	/**
	 * 将搜狗分词中没用的内容剔除
	 * @param result
	 * @return
	 */
	private String normalizeResult(String result) {
		// TODO Auto-generated method stub
		String[] result1=result.split("%20");
		StringBuffer finalResult = new StringBuffer();
		for(String s:result1){
			finalResult=finalResult.append(s.split("%2c")[1]).append(" ");
		}
		return finalResult.toString();
	}
	/**
	 * 进行分词
	 * @param sentanceString
	 * @return
	 */
	private String getSogouSplitResult(String sentanceString) {
		// TODO Auto-generated method stub
		String result = null;
		String url=this.getSegmantationUrl();
		String encoding=this.getEncoding();
		String postData="e="+encoding+"&s=\""+sentanceString+"\"";
		try {
			URL dataUrl = new URL(url);
			HttpURLConnection con = (HttpURLConnection) dataUrl
					.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("Proxy-Connection", "Keep-Alive");
			con.setDoOutput(true);
			con.setDoInput(true);

			OutputStream os = con.getOutputStream();
			DataOutputStream dos = new DataOutputStream(os);
			dos.write(postData.getBytes());
			dos.flush();
			dos.close();
			
			InputStream is = con.getInputStream();			
			DataInputStream dis = new DataInputStream(is);
			byte d[] = new byte[dis.available()];
			dis.read(d);
			result = new String(d);
			con.disconnect();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}
	/**
	 * 将reader句子转换成String类型
	 * @param sentance
	 * @return
	 */
	private String readerToString(Reader reader) {
		// TODO Auto-generated method stub
		BufferedReader br = new BufferedReader(reader); 
		StringBuilder b = new StringBuilder(); 
		String line; 
		try {
			while((line=br.readLine())!=null) { 
				b.append(line); b.append("\r\n"); 
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return b.toString();
	}

	public static void main(String[] args) {
		// Post Data 为Form提交的内容。
		String URL="http://10.10.99.4:10085/simpleseg";
		SogouSegmantation ss = new SogouSegmantation(URL, "utf8");
		StringReader postData = new StringReader("i love china!我爱中华人民共和国");
		String result = ss.doSogouSplit(postData);
		System.out.println(result);
	}
	
}
















