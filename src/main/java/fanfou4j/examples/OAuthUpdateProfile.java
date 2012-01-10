package fanfou4j.examples;

import java.io.File;

import fanfou4j.Fanfou;
import fanfou4j.User;

public class OAuthUpdateProfile {

	/**
	 * Usage: java -DWeibo4j.oauth.consumerKey=[consumer key]
	 * -DWeibo4j.oauth.consumerSecret=[consumer secret]
	 * Weibo4j.examples.OAuthUpdateProfile [accessToken] [accessSecret]
	 * [imageFilePath]
	 * 
	 * @param args
	 *            message
	 */
	public static void main(String[] args) {
		try {
			if (args.length < 3) {
	            System.out.println(
	                "Usage: java OAuthUpdateProfile token tokenSecret filePath");
	            System.exit( -1);
	        }
			
			System.setProperty("Fanfou4j.oauth.consumerKey", fanfou4j.Fanfou.CONSUMER_KEY);
			System.setProperty("Fanfou4j.oauth.consumerSecret", fanfou4j.Fanfou.CONSUMER_SECRET);

			Fanfou fanfou = new Fanfou();
			
			/*
			 * 此处需要填写AccessToken的key和Secret，可以从OAuthUpdate的执行结果中拷贝过来
             */
			fanfou.setToken(args[0], args[1]);
			try {
				File file=new File(args[2]);
				if(file==null){
					System.out.println("file is null");
					System.exit(-1);
				}
				
				User user = fanfou.updateProfileImage(file);

				System.out.println("Successfully upload the status to ["
						+ user.getName() + "].");
				
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		} catch (Exception ioe) {
			System.out.println("Failed to read the system input.");
		}
	}

}
