package com.fincons.rabbitmq.client.test;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Map;

import com.fincons.rabbitmq.client.RabbitMqClient;
import com.fincons.rabbitmq.client.factory.RequestFactory;
import com.fincons.rabbitmq.event.Event;
import com.fincons.rabbitmq.event.EventListener;
import com.fincons.rabbitmq.subscriber.Subscriber;


/**
 * Provide a test case about the use of RabbitMQ Client API Library subscribe functionalities
 * 
 * @author Fincons Group AG
 * 
 */
public class TestSubscribe {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		RabbitMqClient client =  RequestFactory.startGuestApplication("subscribe");
		
		if (client instanceof Subscriber) {
			Subscriber sub = (Subscriber) client;
			
			if(sub.isConnected()){
				sub.registerEventListener(new SubscriberListener());
            	sub.subscribe();
			}
		}

	}
	

	private static class SubscriberListener implements EventListener {
	    private static final String TIME_FORMAT = "HH:mm:ss.SSS Z";
        private DateFormat dateFormatter = new SimpleDateFormat(TIME_FORMAT);
        private int i = 0;
        public void onEvent(Event event) {
            i++;
            StringBuilder msg = new StringBuilder();
            msg.append("-----------------------------------------------------\n");
            msg.append("Message #" + i + "\n");
            msg.append("PublisherID:: " + event.getApplicationID() + "\n");
            msg.append("Timestamp:: " + dateFormatter.format(event.getTimestamp()) + "\n");
            msg.append("Namespace pattern:: " + event.getPattern() + "\n");
            msg.append("Persistent? " + event.isPersistent() + "\n");
            msg.append("Payload media type:: " + event.getContentType() + "\n");
            msg.append("Payload encoding:: " + event.getContentEncoding() + "\n");
            byte[] payload = event.getPayload();
            if (payload == null)
                msg.append("No payload");
            else if (payload.length == 0) 
                msg.append("Empty payload");
            else if (event.getContentType().startsWith("text")) {
                try {
                    String body = new String(payload, event.getContentEncoding());
                    msg.append("Payload:: " + body + "\n");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                
            } else
                msg.append("Payload size:: " + payload.length + " bytes");
            Map<String,Object> headers = event.getHeaders();
            if (headers == null) {
                msg.append("No headers");
            } else {
                msg.append("Headers:");
                for (String key: headers.keySet()) {
                    msg.append("\t\n");
                    msg.append(key);
                    msg.append(": ");
                    msg.append(headers.get(key));
                }
            }
            msg.append("\n-----------------------------------------------------\n");

            System.out.println(msg.toString());
           
        }
	}

}
