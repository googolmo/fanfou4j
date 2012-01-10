package fanfou4j.examples;

import java.util.List;

import fanfou4j.Fanfou;
import fanfou4j.ListObject;
import fanfou4j.ListObjectWapper;

public class GetListObjects {

	/**
	 * Usage: java -DWeibo4j.oauth.consumerKey=[consumer key] -DWeibo4j.oauth.consumerSecret=[consumer secret] Weibo4j.examples.GetListObjects [accessToken] [accessSecret] [uid]
	 * @param args message
	 */
	public static void main(String[] args) {
		try {
			if (args.length < 3) {
				System.out.println("No Token/TokenSecret/(Uid or ScreenName) specified.");
				System.out.println("Usage: java Weibo4j.examples.GetListObjects Token TokenSecret Uid");
				System.exit(-1);
			}
			//
			System.setProperty("Fanfou4j.oauth.consumerKey", fanfou4j.Fanfou.CONSUMER_KEY);
			System.setProperty("Fanfou4j.oauth.consumerSecret", fanfou4j.Fanfou.CONSUMER_SECRET);

			Fanfou fanfou = new Fanfou();
			fanfou.setToken(args[0], args[1]);

			String screenName = args[2]; 

			try {
				ListObjectWapper wapper = fanfou.getUserLists(screenName, true);
				List<ListObject> lists = wapper.getListObjects();
				for (ListObject listObject : lists) {
					System.out.println(listObject.toString());
				}
				System.out.println("previous_cursor: " + wapper.getPreviousCursor());
				System.out.println("next_cursor: " + wapper.getNextCursor());
				//
				System.out.println("Successfully get lists of [" + screenName + "].");
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			System.exit(0);
		} catch (Exception ioe) {
			System.out.println("Failed to read the system input.");
			System.exit(-1);
		}
	}
}
