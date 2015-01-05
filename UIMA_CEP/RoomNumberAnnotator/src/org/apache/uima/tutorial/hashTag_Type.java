
/* First created by JCasGen Tue Dec 23 09:25:47 IST 2014 */
package org.apache.uima.tutorial;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.cas.impl.CASImpl;
import org.apache.uima.cas.impl.FSGenerator;
import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.cas.impl.TypeImpl;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.impl.FeatureImpl;
import org.apache.uima.cas.Feature;
import org.apache.uima.jcas.tcas.Annotation_Type;

/** 
 * Updated by JCasGen Tue Dec 23 09:25:47 IST 2014
 * @generated */
public class hashTag_Type extends Annotation_Type {
  /** @generated 
   * @return the generator for this type
   */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (hashTag_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = hashTag_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new hashTag(addr, hashTag_Type.this);
  			   hashTag_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new hashTag(addr, hashTag_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = hashTag.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("org.apache.uima.tutorial.hashTag");
 
  /** @generated */
  final Feature casFeat_hashtag;
  /** @generated */
  final int     casFeatCode_hashtag;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getHashtag(int addr) {
        if (featOkTst && casFeat_hashtag == null)
      jcas.throwFeatMissing("hashtag", "org.apache.uima.tutorial.hashTag");
    return ll_cas.ll_getStringValue(addr, casFeatCode_hashtag);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setHashtag(int addr, String v) {
        if (featOkTst && casFeat_hashtag == null)
      jcas.throwFeatMissing("hashtag", "org.apache.uima.tutorial.hashTag");
    ll_cas.ll_setStringValue(addr, casFeatCode_hashtag, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public hashTag_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_hashtag = jcas.getRequiredFeatureDE(casType, "hashtag", "uima.cas.String", featOkTst);
    casFeatCode_hashtag  = (null == casFeat_hashtag) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_hashtag).getCode();

  }
}



    