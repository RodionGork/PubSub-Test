package pubsubtest;

import com.google.api.services.pubsub.Pubsub;
import com.google.api.services.pubsub.model.*;
import java.util.*;

public class Sub {
    
    static final String TOPIC_NAME = "projects/pubsubtest-989/topics/topic1";
    static final String SUB_NAME = "projects/pubsubtest-989/subscriptions/sub1";
    
    public static void main(String... args) throws Exception {
        Pubsub client = PubsubConfig.createPubsubClient();
        
        /*
        Subscription sub = new Subscription().setTopic(TOPIC_NAME)
                .setAckDeadlineSeconds(60);
        sub = client.projects().subscriptions()
                .create(SUB_NAME, sub).execute();
        System.out.println("Subscription created: " + sub.getName());
        */
        
        PullRequest req = new PullRequest().setReturnImmediately(false).setMaxMessages(10);
        PullResponse resp = client.projects().subscriptions().pull(SUB_NAME, req).execute();
        List<String> acks = new ArrayList<String>();
        List<ReceivedMessage> msgs = resp.getReceivedMessages();
        if (msgs == null) {
            msgs = Collections.EMPTY_LIST;
        }
        if (msgs.isEmpty()) {
            System.out.println("No messages!");
        }
        for (ReceivedMessage msg : msgs) {
            System.out.println("Message: " + new String(msg.getMessage().decodeData()));
            acks.add(msg.getAckId());
        }
        AcknowledgeRequest ack = new AcknowledgeRequest().setAckIds(acks);
        client.projects().subscriptions().acknowledge(SUB_NAME, ack).execute();
    }
    
}
