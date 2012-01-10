package fanfou4j;

import fanfou4j.http.Response;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import fanfou4j.org.json.JSONArray;
import fanfou4j.org.json.JSONException;
import fanfou4j.org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A data class representing one single status of a user.
 * @author Yusuke Yamamoto - yusuke at mac.com
 */
public class Comment extends FanfouResponse implements java.io.Serializable {

    private Date createdAt;
    private long id;
    private String text;
    private String source;
    private boolean isTruncated;
    private long inReplyToStatusId;
    private int inReplyToUserId;
    private boolean isFavorited;
    private String inReplyToScreenName;
    private double latitude = -1;
    private double longitude = -1;

    private RetweetDetails retweetDetails;
    private static final long serialVersionUID = 1608000492860584608L;

    /*package*/Comment(Response res, Fanfou fanfou) throws FanfouException {
        super(res);
        Element elem = res.asDocument().getDocumentElement();
        init(res, elem, fanfou);
    }
    
    /*modify by sycheng add json define */
    /*package*/Comment(Response res) throws FanfouException {
        super(res);
        JSONObject json =res.asJSONObject();
        try {
			id = json.getLong("id");
			text = json.getString("text");
			source = json.getString("source");
			createdAt = parseDate(json.getString("created_at"), "EEE MMM dd HH:mm:ss z yyyy");
			
			if(!json.isNull("user"))
				user = new User(json.getJSONObject("user"));
		} catch (JSONException je) {
			throw new FanfouException(je.getMessage() + ":" + json.toString(), je);
		}
    }
    
    /* modify by hezhou add some field*/
    public Comment(JSONObject json)throws FanfouException, JSONException{
    	id = json.getLong("id");
		text = json.getString("text");
		source = json.getString("source");
		createdAt = parseDate(json.getString("created_at"), "EEE MMM dd HH:mm:ss z yyyy");
		if(!json.isNull("user"))
			user = new User(json.getJSONObject("user"));
    }

    /*package*/Comment(Response res, Element elem, Fanfou fanfou) throws
            FanfouException {
        super(res);
        init(res, elem, fanfou);
    }

    public Comment(String str) throws FanfouException, JSONException {
        // StatusStream uses this constructor
        super();
        JSONObject json = new JSONObject(str);
        id = json.getLong("id");
        text = json.getString("text");
        source = json.getString("source");
        createdAt = parseDate(json.getString("created_at"), "EEE MMM dd HH:mm:ss z yyyy");

        user = new User(json.getJSONObject("user"));
    }

    private void init(Response res, Element elem, Fanfou fanfou) throws
            FanfouException {
        ensureRootNodeNameIs("comment", elem);
        user = new User(res, (Element) elem.getElementsByTagName("user").item(0)
                , fanfou);
        id = getChildLong("id", elem);
        text = getChildText("text", elem);
        source = getChildText("source", elem);
        createdAt = getChildDate("created_at", elem);
    }

    /**
     * Return the created_at
     *
     * @return created_at
     * @since Weibo4J 1.1.0
     */

    public Date getCreatedAt() {
        return this.createdAt;
    }

    /**
     * Returns the id of the status
     *
     * @return the id
     */
    public long getId() {
        return this.id;
    }

    /**
     * Returns the text of the status
     *
     * @return the text
     */
    public String getText() {
        return this.text;
    }

    /**
     * Returns the source
     *
     * @return the source
     * @since Weibo4J 1.0.4
     */
    public String getSource() {
        return this.source;
    }


    /**
     * Test if the status is truncated
     *
     * @return true if truncated
     * @since Weibo4J 1.0.4
     */
    public boolean isTruncated() {
        return isTruncated;
    }

    /**
     * Returns the in_reply_tostatus_id
     *
     * @return the in_reply_tostatus_id
     * @since Weibo4J 1.0.4
     */
    public long getInReplyToStatusId() {
        return inReplyToStatusId;
    }

    /**
     * Returns the in_reply_user_id
     *
     * @return the in_reply_tostatus_id
     * @since Weibo4J 1.0.4
     */
    public int getInReplyToUserId() {
        return inReplyToUserId;
    }

    /**
     * Returns the in_reply_to_screen_name
     *
     * @return the in_in_reply_to_screen_name
     * @since Weibo4J 2.0.4
     */
    public String getInReplyToScreenName() {
        return inReplyToScreenName;
    }

    /**
     * returns The location's latitude that this tweet refers to.
     *
     * @since Weibo4J 2.0.10
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * returns The location's longitude that this tweet refers to.
     *
     * @since Weibo4J 2.0.10
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * Test if the status is favorited
     *
     * @return true if favorited
     * @since Weibo4J 1.0.4
     */
    public boolean isFavorited() {
        return isFavorited;
    }


    private User user = null;

    /**
     * Return the user
     *
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     *
     * @since Weibo4J 2.0.10
     */
    public boolean isRetweet(){
        return null != retweetDetails;
    }

    /**
     *
     * @since Weibo4J 2.0.10
     */
    public RetweetDetails getRetweetDetails() {
        return retweetDetails;
    }


    /*package*/
    static List<Comment> constructStatuses(Response res,
                                          Fanfou fanfou) throws FanfouException {
        Document doc = res.asDocument();
        if (isRootNodeNilClasses(doc)) {
            return new ArrayList<Comment>(0);
        } else {
            try {
                ensureRootNodeNameIs("statuses", doc);
                NodeList list = doc.getDocumentElement().getElementsByTagName(
                        "status");
                int size = list.getLength();
                List<Comment> statuses = new ArrayList<Comment>(size);
                for (int i = 0; i < size; i++) {
                    Element status = (Element) list.item(i);
                    statuses.add(new Comment(res, status, fanfou));
                }
                return statuses;
            } catch (FanfouException te) {
                ensureRootNodeNameIs("nil-classes", doc);
                return new ArrayList<Comment>(0);
            }
        }
    }
    
    /* modify by hezhou add some field*/
    static List<Comment> constructComments(Response res) throws FanfouException {
   	 try {
            JSONArray list = res.asJSONArray();
            int size = list.length();
            List<Comment> comments = new ArrayList<Comment>(size);
            for (int i = 0; i < size; i++) {
            	comments.add(new Comment(list.getJSONObject(i)));
            }
            return comments;
        } catch (JSONException jsone) {
            throw new FanfouException(jsone);
        } catch (FanfouException te) {
            throw te;
        }  
      
   }

    @Override
    public int hashCode() {
        return (int) id;
    }

    @Override
    public boolean equals(Object obj) {
        if (null == obj) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        return obj instanceof Comment && ((Comment) obj).id == this.id;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "createdAt=" + createdAt +
                ", id=" + id +
                ", text='" + text + '\'' +
                ", source='" + source + '\'' +
                ", isTruncated=" + isTruncated +
                ", inReplyToStatusId=" + inReplyToStatusId +
                ", inReplyToUserId=" + inReplyToUserId +
                ", isFavorited=" + isFavorited +
                ", inReplyToScreenName='" + inReplyToScreenName + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", retweetDetails=" + retweetDetails +
                ", user=" + user +
                '}';
    }
}
