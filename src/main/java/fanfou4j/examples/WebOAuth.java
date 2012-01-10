package fanfou4j.examples;

import fanfou4j.Fanfou;
import fanfou4j.Status;
import fanfou4j.FanfouException;
import fanfou4j.http.AccessToken;
import fanfou4j.http.RequestToken;

public class WebOAuth {

	public static RequestToken request(String backUrl) {
		try {
			System.setProperty("Fanfou4j.oauth.consumerKey", fanfou4j.Fanfou.CONSUMER_KEY);
			System.setProperty("Fanfou4j.oauth.consumerSecret",
					fanfou4j.Fanfou.CONSUMER_SECRET);

			Fanfou fanfou = new Fanfou();
			// set callback url, desktop app please set to null
			// http://callback_url?oauth_token=xxx&oauth_verifier=xxx
			RequestToken requestToken = fanfou.getOAuthRequestToken(backUrl);

			System.out.println("Got request token.");
			System.out.println("Request token: " + requestToken.getToken());
			System.out.println("Request token secret: "
					+ requestToken.getTokenSecret());
			return requestToken;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static AccessToken requstAccessToken(RequestToken requestToken,
			String verifier) {
		try {
			System.setProperty("Fanfou4j.oauth.consumerKey", fanfou4j.Fanfou.CONSUMER_KEY);
			System.setProperty("Fanfou4j.oauth.consumerSecret",
					fanfou4j.Fanfou.CONSUMER_SECRET);

			Fanfou fanfou = new Fanfou();
			// set callback url, desktop app please set to null
			// http://callback_url?oauth_token=xxx&oauth_verifier=xxx
			AccessToken accessToken = fanfou.getOAuthAccessToken(requestToken
					.getToken(), requestToken.getTokenSecret(), verifier);

			System.out.println("Got access token.");
			System.out.println("access token: " + accessToken.getToken());
			System.out.println("access token secret: "
					+ accessToken.getTokenSecret());
			return accessToken;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void update(AccessToken access, String content) {
		try {
			Fanfou fanfou = new Fanfou();

			fanfou.setToken(access.getToken(), access.getTokenSecret());

			Status status = fanfou.updateStatus(content);
			System.out.println("Successfully updated the status to ["
					+ status.getText() + "].");
		} catch (FanfouException e) {
			e.printStackTrace();
		}
	}
}
