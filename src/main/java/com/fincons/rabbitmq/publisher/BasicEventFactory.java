package com.fincons.rabbitmq.publisher;

import java.util.Date;
import java.util.Map;

import com.fincons.rabbitmq.event.AbstractEvent;
import com.fincons.rabbitmq.event.Event;

/**
 * Provides the default implementation of {@link EventFactory}.
 * 
 * @author Fincons Group AG
 *
 */
public class BasicEventFactory implements EventFactory {

	public BasicEventFactory () {
		super();
	}


	private class EventToBePublished extends AbstractEvent {

		/**
		 * The EventToBePublished constructor
		 * 
		 * @param pattern
		 * @param headers
		 * @param payload
		 * @param contentType
		 * @param contentEncoding
		 * @param priority
		 * @param timestamp
		 * @param isPersistent
		 * @param applicationID
		 */
		public EventToBePublished(String pattern,
				Map<String, Object> headers, byte[] payload,
				String contentType, String contentEncoding, int priority,
				boolean isPersistent, String applicationID) {
			super(pattern, headers, payload, contentType, contentEncoding, priority,
					new Date(), isPersistent, applicationID);
		}

		/* (non-Javadoc)
		 * @see com.fincons.rabbitmq.event.AbstractEvent#validate(java.lang.String, java.util.Map, byte[], java.lang.String, java.lang.String, int, java.util.Date, boolean, java.lang.String)
		 */
		@Override
		protected void validate(String pattern, Map<String, Object> headers,
				byte[] payload, String contentType, String contentEncoding,
				int priority, Date timestamp, boolean isPersistent,
				String applicationID) throws IllegalArgumentException {
			// TODO
		}

	}

	/* (non-Javadoc)
	 * @see com.fincons.rabbitmq.publisher.EventFactory#create(java.util.Map, byte[], boolean)
	 */
	@Override
	public Event create(Map<String, Object> headers, byte[] payload,
			boolean isPersistent) {
		return create(headers, payload, Event.DEFAULT_CONTENT_TYPE, Event.DEFAULT_CONTENT_ENCODING,
				Event.DEFAULT_PRIORITY, isPersistent);
	}

	/* (non-Javadoc)
	 * @see com.fincons.rabbitmq.publisher.EventFactory#create(java.util.Map, byte[], java.lang.String, java.lang.String, int, boolean)
	 */
	@Override
	public Event create(Map<String, Object> headers, byte[] payload,
			String contentType, String contentEncoding, int priority,
			boolean isPersistent) {
		return new EventToBePublished(null, headers, payload, contentType, contentEncoding,
				priority, isPersistent, null);
	}

}

