# smartlane-java-client

This Java library provides access to the Smart Lane of the BEinCPPS platform to delivery, or acquire, events based on the AMQP protocol. Events, as managed through this library, can be enriched via semantically meaningful tags.

This library provides Java classes, to be used by event’s sources (called publishers) or event’s processors (called consumers), to perform different operations via the AMQP protocol:
-	establish connections with the events’ broker as a publisher  or a subscriber;
-	on the publisher’s side, structure the event’s data according to the specific context needs;
-	publish, or subscribe to, events;
-	on the consumer’s side, specify callback listeners to actually receive events.

tbc...
  
