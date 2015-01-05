package org.apache.uima.tutorial.ex1;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.tutorial.hashTag;

public class HashtagAnnotator extends JCasAnnotator_ImplBase {


		private Pattern hashTagPattern  =
				Pattern.compile("[#]+[a-zA-Z]*");
		
		@Override
		public void process(JCas jcas) throws AnalysisEngineProcessException {
			
			// get document text from JCas
			String docText = jcas.getDocumentText();
			
			Matcher matcher = hashTagPattern.matcher(docText);
			int pos = 0;
			
			while(matcher.find(pos)){
					
					hashTag annotation = new hashTag(jcas);
					annotation.setBegin(matcher.start());
					annotation.setEnd(matcher.end());
					annotation.setHashtag(matcher.group());
//					System.out.println("*******////***** "+getHashTag(annotation));
//					System.out.println("******* "+matcher.group());
					annotation.addToIndexes();
					pos = matcher.end(); 
					
			}
			
			
		}
		
		public String getHashTag(hashTag hash){	
			return hash.getHashtag();
		}

}
