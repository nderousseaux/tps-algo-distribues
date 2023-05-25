import io.jbotsim.core.Message;
import io.jbotsim.core.Color;
import io.jbotsim.core.Node;

public class ElectionNode extends Node {

    long myRandomId = 0;

    @Override
    public void onStart() { 
        // Appelé au début et quand on fait "clic droit > restart"
        this.myRandomId = Math.round(Math.random() * 1000000);
        setColor(Color.green);
    }

    @Override
    public void onSelection() { 
        // Appelé quand on fait "CTRL + clic" sur ce noeud

        setColor(Color.blue);
        sendAll(new Message(this.myRandomId));
    }


    @Override
    public void onMessage(Message message) {

        long receivedID = (Long) message.getContent();
        // Que faire quand on reçoit un message avec cet ID ?
    }
}