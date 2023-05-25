import io.jbotsim.core.Node;

public abstract class PPNode extends Node {
    int nbNodes = 1;
    int idLeader = -1;


    // Assign default initial state to underlying node
    public abstract void onStart();
    // Assign distinguished initial state to underlying node
    public abstract void onSelection();
    // Execute interaction (from the initiator side)
    public abstract void interactWith(Node responder);
    
}
