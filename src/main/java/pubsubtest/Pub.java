package pubsubtest;

import com.google.api.services.pubsub.Pubsub;
import com.google.api.services.pubsub.model.*;
import java.util.*;

public class Pub {
    
    static final String TOPIC_NAME = "projects/pubsubtest-989/topics/topic1";
    
    public static void main(String... args) throws Exception {
        Pubsub client = PubsubConfig.createPubsubClient();
        /*
        Topic topic = client.projects().topics()
                .create(TOPIC_NAME, new Topic()).execute();
        System.out.println("Topic created: " + topic.getName());
        */
        String msg = "Hi Peoplez: " + System.currentTimeMillis();
        PubsubMessage psmsg = new PubsubMessage();
        psmsg.encodeData(msg.getBytes("UTF-8"));
        PublishRequest req = new PublishRequest().setMessages(Arrays.asList(psmsg));
        PublishResponse resp = client.projects().topics()
                .publish(TOPIC_NAME, req).execute();
        System.out.println("Message IDs: " + resp.getMessageIds());
    }
    
}
