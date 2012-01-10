package fanfou4j.examples;

import java.util.List;

import fanfou4j.Fanfou;
import fanfou4j.User;

public class GetFriends {

	    /**
	     * Usage: java -DWeibo4j.oauth.consumerKey=[consumer key] -DWeibo4j.oauth.consumerSecret=[consumer secret] Weibo4j.examples.GetFriends [accessToken] [accessSecret]
	     * @param args message
	     */
	    public static void main(String[] args) {
	        try {
	        	System.setProperty("Fanfou4j.oauth.consumerKey", fanfou4j.Fanfou.CONSUMER_KEY);
	        	System.setProperty("Fanfou4j.oauth.consumerSecret", fanfou4j.Fanfou.CONSUMER_SECRET);
	        	
	            Fanfou fanfou = new Fanfou();
	            
	            fanfou.setToken(args[0], args[1]);
	  
				 try {

						List<User> list= fanfou.getFriendsStatuses();
						
						System.out.println("Successfully get Friends to [" + list + "].");
					} catch (Exception e1) {
						e1.printStackTrace();
					}
	            System.exit(0);
	        } catch (Exception ioe) {
	            System.out.println("Failed to read the system input.");
	            System.exit( -1);
	        }
	    }
}
