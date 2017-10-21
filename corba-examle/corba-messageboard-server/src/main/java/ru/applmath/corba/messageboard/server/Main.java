package ru.applmath.corba.messageboard.server;

import org.omg.CORBA.ORB;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

import MessageBoard.MessagePostBox;
import MessageBoard.MessagePostBoxHelper;

public class Main {
	public static void main(String[] args) throws Throwable {
		
		ORB orb = ORB.init(args, null);
		
	    POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
	    rootpoa.the_POAManager().activate();
	    
	    MessagePostBoxImpl messageBostBoxImpl = new MessagePostBoxImpl();
	    
	    org.omg.CORBA.Object ref = rootpoa.servant_to_reference(messageBostBoxImpl);
	    MessagePostBox href = MessagePostBoxHelper.narrow(ref);
	    
	    System.out.println("Object reference: " + href);
	    
	    orb.run();
	}
}
