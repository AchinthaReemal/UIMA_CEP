package org.apache.uima.tutorial.ex1;
import java.io.InputStream;

import opennlp.tools.chunker.ChunkerME;
import opennlp.tools.chunker.ChunkerModel;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.Span;

import org.apache.commons.io.IOUtils;
import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.tutorial.NounPhraseAnnotation;

/**
 * Annotate noun phrases in sentences from within blocks of
 * text (marked up with TextAnnotation) from either HTML or
 * plain text documents. Using the OpenNLP library and models,
 * the incoming text is tokenized into sentences, then each 
 * sentence is tokenized to words and POS tagged, and finally
 * tokens are grouped together into chunks. Of these chunks,
 * only the noun phrases are annotated. 
 */

public class NounPhraseAnnotator extends JCasAnnotator_ImplBase {

  private SentenceDetectorME sentenceDetector;
  private TokenizerME tokenizer;
  private POSTaggerME posTagger;
  private ChunkerME chunker;
  
  @Override
  public void initialize(UimaContext ctx) 
      throws ResourceInitializationException {
    super.initialize(ctx);
    InputStream smis = null;
    InputStream tmis = null;
    InputStream pmis = null;
    InputStream cmis = null;
    try {
      smis = getContext().getResourceAsStream("SentenceModel");
      SentenceModel smodel = new SentenceModel(smis);
      sentenceDetector = new SentenceDetectorME(smodel);
      smis.close();
      
      tmis = getContext().getResourceAsStream("TokenizerModel");
      TokenizerModel tmodel = new TokenizerModel(tmis);
      tokenizer = new TokenizerME(tmodel);
      tmis.close();
      
      pmis = getContext().getResourceAsStream("POSModel");
      POSModel pmodel = new POSModel(pmis);
      posTagger = new POSTaggerME(pmodel);
      pmis.close();
      
      cmis = getContext().getResourceAsStream("ChunkerModel");
      ChunkerModel cmodel = new ChunkerModel(cmis);
      chunker = new ChunkerME(cmodel);
      cmis.close();
      
    } catch (Exception e) {
      throw new ResourceInitializationException(e);
    } finally {
      IOUtils.closeQuietly(cmis);
      IOUtils.closeQuietly(pmis);
      IOUtils.closeQuietly(tmis);
      IOUtils.closeQuietly(smis);
    }
  }
  
  @Override
  public void process(JCas jcas) throws AnalysisEngineProcessException {
    String text = jcas.getDocumentText();
    Span[] sentSpans = sentenceDetector.sentPosDetect(jcas.getDocumentText());
    for (Span sentSpan : sentSpans) {
      String sentence = sentSpan.getCoveredText(text).toString();
      int start = sentSpan.getStart();
      Span[] tokSpans = tokenizer.tokenizePos(sentence);
      String[] tokens = new String[tokSpans.length];
      for (int i = 0; i < tokens.length; i++) {
        tokens[i] = tokSpans[i].getCoveredText(sentence).toString();
      }
      String[] tags = posTagger.tag(tokens);
      Span[] chunks = chunker.chunkAsSpans(tokens, tags);
      for (Span chunk : chunks) {
        if ("NP".equals(chunk.getType())) {
          NounPhraseAnnotation annotation = new NounPhraseAnnotation(jcas);
          annotation.setBegin(start + 
            tokSpans[chunk.getStart()].getStart());
          annotation.setEnd(
            start + tokSpans[chunk.getEnd() - 1].getEnd());
          annotation.addToIndexes(jcas);
        }
      }
    }
  }
}
