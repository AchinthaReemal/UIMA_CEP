

/* First created by JCasGen Tue Dec 23 09:25:47 IST 2014 */
package org.apache.uima.tutorial;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** 
 * Updated by JCasGen Tue Dec 23 09:25:47 IST 2014
 * XML source: /home/achintha/workspace/RoomNumberAnnotator/desc/HashTagDescriptor.xml
 * @generated */
public class hashTag extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(hashTag.class);
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int type = typeIndexID;
  /** @generated
   * @return index of the type  
   */
  @Override
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected hashTag() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public hashTag(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public hashTag(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public hashTag(JCas jcas, int begin, int end) {
    super(jcas);
    setBegin(begin);
    setEnd(end);
    readObject();
  }   

  /** 
   * <!-- begin-user-doc -->
   * Write your own initialization here
   * <!-- end-user-doc -->
   *
   * @generated modifiable 
   */
  private void readObject() {/*default - does nothing empty block */}
     
 
    
  //*--------------*
  //* Feature: hashtag

  /** getter for hashtag - gets 
   * @generated
   * @return value of the feature 
   */
  public String getHashtag() {
    if (hashTag_Type.featOkTst && ((hashTag_Type)jcasType).casFeat_hashtag == null)
      jcasType.jcas.throwFeatMissing("hashtag", "org.apache.uima.tutorial.hashTag");
    return jcasType.ll_cas.ll_getStringValue(addr, ((hashTag_Type)jcasType).casFeatCode_hashtag);}
    
  /** setter for hashtag - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setHashtag(String v) {
    if (hashTag_Type.featOkTst && ((hashTag_Type)jcasType).casFeat_hashtag == null)
      jcasType.jcas.throwFeatMissing("hashtag", "org.apache.uima.tutorial.hashTag");
    jcasType.ll_cas.ll_setStringValue(addr, ((hashTag_Type)jcasType).casFeatCode_hashtag, v);}    
  }

    