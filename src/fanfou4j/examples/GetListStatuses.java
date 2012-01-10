package fanfou4j.examples;

import java.util.List;

import fanfou4j.Fanfou;
import fanfou4j.Status;

/**
 * Example of get statuses of list's members.
 * 
 * @author		bwl (刘道儒)
 */
public class GetListStatuses {

	/**
	 * Usage: java -DWeibo4j.oauth.consumerKey=[consumer key] -DWeibo4j.oauth.consumerSecret=[consumer secret] Weibo4j.examples.GetListStatuses [accessToken] [accessSecret] [Uid] [listId]
	 * @param args message
	 */
	public static void main(String[] args) {
		try {
			if (args.length < 4) {
				System.out.println("No Token/TokenSecret/(Uid or ScreenName)/(ListId or Slug) specified.");
				System.out.println("Usage: java Weibo4j.examples.GetListStatuses Token TokenSecret Uid ListId");
				System.exit(-1);
			}
			//
			System.setProperty("Fanfou4j.oauth.consumerKey", fanfou4j.Fanfou.CONSUMER_KEY);
			System.setProperty("Fanfou4j.oauth.consumerSecret", fanfou4j.Fanfou.CONSUMER_SECRET);

			Fanfou fanfou = new Fanfou();
			fanfou.setToken(args[0], args[1]);

			String screenName = args[2];
			String listId = args[3];

			try {
				List<Status> statuses = fanfou.getListStatuses(screenName, listId, true);
				for (Status status : statuses) {
					System.out.println(status.toString());
				}
				//
				System.out.println("Successfully get statuses of [" + listId + "].");
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
