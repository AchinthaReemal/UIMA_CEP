package org.apache.uima.tutorial.ex1;

import java.net.MalformedURLException;
import java.util.Iterator;

import org.apache.uima.UIMAException;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.cas.FSIndex;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.tutorial.NounPhraseAnnotation;
import org.apache.uima.tutorial.hashTag;
import org.wso2.carbon.databridge.agent.thrift.DataPublisher;
import org.wso2.carbon.databridge.agent.thrift.exception.AgentException;
import org.wso2.carbon.databridge.commons.Event;
import org.wso2.carbon.databridge.commons.exception.AuthenticationException;
import org.wso2.carbon.databridge.commons.exception.DifferentStreamDefinitionAlreadyDefinedException;
import org.wso2.carbon.databridge.commons.exception.MalformedStreamDefinitionException;
import org.wso2.carbon.databridge.commons.exception.NoStreamDefinitionExistException;
import org.wso2.carbon.databridge.commons.exception.StreamDefinitionException;
import org.wso2.carbon.databridge.commons.exception.TransportException;
import org.wso2.carbon.sample.twitterfeeds.KeyStoreUtil;

import twitter4j.FilterQuery;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TestWithTwitterStreamingAPI {
	
	public static final String STREAM_NAME1 = "org.wso2.sample.service.data";
	public static final String VERSION1 = "1.0.0";
	public static String streamID="org.wso2.sample.service.data:1.0.0";
	public static DataPublisher dataPublisher;
	
	public static void main(String[] args) throws MalformedURLException, AgentException, AuthenticationException, TransportException, StreamDefinitionException, MalformedStreamDefinitionException, DifferentStreamDefinitionAlreadyDefinedException{
		
		final AnalysisEngine analysisEngine = null;
	    String streamId1 = null;
		
		
		
		
		
		System.out.println("Starting Statistics Agent");

        KeyStoreUtil.setTrustStoreParams();

//        String host = args[0];
//        String port = args[1];
//        String username = args[2];
//        String password = args[3];
//        int events = Integer.parseInt(args[4]);
        
        String host = "";
      String port = "";
      String username = "";
      String password = "";
//      int events = Integer.parseInt(args[4]);

        //create data publisher
//      dataPublisher = new DataPublisher("tcp://" + host + ":" + port, username, password);
        dataPublisher = new DataPublisher("tcp://localhost:7611", "admin", "admin");
        


        try {
            streamId1 = dataPublisher.findStream(STREAM_NAME1, VERSION1);
            System.out.println("Stream already defined");

        } catch (NoStreamDefinitionExistException e) {
            streamId1 = dataPublisher.defineStream("{" +
                                                   "  'name':'" + STREAM_NAME1 + "'," +
                                                   "  'version':'" + VERSION1 + "'," +
                                                   "  'nickName': 'Statistics'," +
                                                   "  'description': 'Service statistics'," +
                                                   "  'metaData':[" +
                                                   "          {'name':'request_url','type':'STRING'}," +
                                                   "          {'name':'content_type','type':'STRING'}," +
                                                   "          {'name':'user_agent','type':'STRING'}," +
                                                   "          {'name':'referer','type':'STRING'}" +
                                                   "  ]," +
                                                   "  'payloadData':[" +
                                                   "          {'name':'service_name','type':'STRING'}," +
                                                   "          {'name':'annotation_1','type':'STRING'}," +
                                                   "          {'name':'annotation_2','type':'STRING'}" +
                                                   "  ]" +
                                                   "}");
            
            streamID=streamId1;
        }

      		
	    
		StatusListener listener = new StatusListener(){
			public void onStatus(Status status) {
				
				 AnalysisEngineInitiator uimaPipeline = null;
		    		try {
		    			uimaPipeline = new AnalysisEngineInitiator();
		    		} catch (ResourceInitializationException e) {
		    			e.printStackTrace();
		    		}

		    		// run the sample document through the pipeline
		    		JCas output = null;
		    		try {
		    		output = uimaPipeline.process(status.getText());
		    		} catch (UIMAException e) {
		    			e.printStackTrace();
		    		}
		      
		      String annotationString1="", annotationString2="";
		      
		      FSIndex index1 = output.getAnnotationIndex(NounPhraseAnnotation.type);
		      for (Iterator<NounPhraseAnnotation> it = index1.iterator(); it.hasNext();) {
		        NounPhraseAnnotation annotation = it.next();
//		        System.out.println("AN1...(" + annotation.getBegin() + "," + 
//		          annotation.getEnd() + "): " + 
//		          annotation.getCoveredText());
		        annotationString1 = annotationString1 + annotation.getCoveredText();
		      }	
		    		
		      FSIndex index2 = output.getAnnotationIndex(hashTag.type);
		      for (Iterator<hashTag> it = index2.iterator(); it.hasNext();) {
		    	hashTag annotation = it.next();
//		        System.out.println("AN2...(" + annotation.getBegin() + "," + 
//		          annotation.getEnd() + "): " + 
//		          annotation.getCoveredText());
		    	
		    	annotationString2 = annotationString2 + annotation.getCoveredText();
   		      }		
		      
		      
		      //Publish event for a valid stream
		        if (!streamID.isEmpty()) {
		            System.out.println("Stream ID: " + streamID);

		            
		                try {
							publishEvents(dataPublisher, streamID, annotationString1, annotationString2);
						} catch (AgentException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
//		                System.out.println("Events published : " + sentEventCount);
		                
		            try {
		                Thread.sleep(3000);
		            } catch (InterruptedException e) {
		                //ignore
		            }

//		            dataPublisher.stop();
		        }
		      
		      
		      
		      
		      
		      
		      
	        }
			
	        public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {}
	        public void onTrackLimitationNotice(int numberOfLimitedStatuses) {}
	        public void onException(Exception ex) {
	            ex.printStackTrace();
	        }
			@Override
			public void onScrubGeo(long arg0, long arg1) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void onStallWarning(StallWarning arg0) {
				// TODO Auto-generated method stub
				
			}	
		
	    };  
	    
//	    
	    ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
		  .setOAuthConsumerKey("AmFn6IAaLuECzgNYYDCQkTrrc")
		  .setOAuthConsumerSecret("u2Zhv2pdhck093E0yLN8UUbCvUq5CmsItMbUVdo0QO7qXWUwSf")
		  .setOAuthAccessToken("232027476-xudOB6AamrcKbkhDM7IgKMkYsBkTAmwUbo86lsh2")
		  .setOAuthAccessTokenSecret("ttvt2JfZFsHfAK63UKBVCBdySITqMVhuexDkmUgOeK7yB");
		
		TwitterStreamFactory tf = new TwitterStreamFactory(cb.build());
		TwitterStream twitterStream = tf.getInstance();
	    twitterStream.addListener(listener);
	    
	    FilterQuery filtre = new FilterQuery();
	    String[] keywordsArray = { "football", "cricket" };
	    filtre.track(keywordsArray);
	    twitterStream.filter(filtre);
	    
	}
	
	
	private static void publishEvents(DataPublisher dataPublisher, String streamId, String annotation1, String annotation2)
            throws AgentException {

//        Random rand = new Random();
//
//        for (int i = 0; i < 3; i++) {
//            int hostIndex = rand.nextInt(5);
//
//            String host = hosts[hostIndex];
//
//            for (int j = 0; j < 3; j++) {
//                int serviceIndex = rand.nextInt(4);
//
//                Iterator<String> serviceIterator = services.keySet().iterator();
//
//                int k = 0;
//                String service = null;
//                while (serviceIterator.hasNext() && k < serviceIndex) {
//                    service = serviceIterator.next();
//                }
//
//                if (service == null) {
//                    service = serviceIterator.next();
//                }
//
//                List<String> operations = services.get(service);
//
//                int operationIndex = rand.nextInt(operations.size());
//
//                String operation = operations.get(operationIndex);

                Object[] meta = new Object[]{
                        "http://services/twitterfeedextraction",
                        "application/xml",
                        "http-components/client",
                        "http://example.org"
                };

//                int response = rand.nextInt(2);
//                int fault = 0;
//
//                if (response == 0) {
//                    fault = 1;
//                }

                Object[] payload = new Object[]{
                        "Extracted Twitter feeds",
                        annotation1,
                        annotation2
                };

                Object[] correlation = null;
//                        new Object[] {
//                        UUID.randomUUID().toString()
//                };

                Event statisticsEvent = new Event(streamId, System.currentTimeMillis(),
                                                  meta, correlation, payload);
                dataPublisher.publish(statisticsEvent);
//                if(++sentEventCount >= eventLimit) {
//                    return;
//                }
            }
    }
	
	

