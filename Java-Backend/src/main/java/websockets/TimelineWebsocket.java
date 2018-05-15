package websockets;

import DTO.TweetDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import model.Account;
import model.Tweet;
import org.primefaces.json.JSONObject;
import service.AuthService;
import service.UserService;
import util.DTOMapper;

import java.io.IOException;
import java.util.*;

import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.json.Json;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/websocket/{clientId}")
@Singleton
@Startup
public class TimelineWebsocket {

  private static Map<Integer, Session> clients = Collections.synchronizedMap(new HashMap<Integer, Session>());

  public void UpdateTweetTimeline(Tweet tweet) throws IOException {
      for(Account follower :tweet.getTweetedBy().getFollowers()){
          synchronized(clients){

              if(clients.containsKey(follower.getId())){
              TweetDTO tweetDTO = DTOMapper.getTweetDTO(tweet);
              Gson gson = new GsonBuilder().setPrettyPrinting().create();
              String jsonTweet = gson.toJson(tweetDTO);
              clients.get(follower.getId()).getBasicRemote().sendText(jsonTweet.toString());
          }
          }
      }
  }

    @OnOpen
    public void onOpen(@PathParam("clientId") String clientId, Session session) throws IOException {
  // Add session to the connected sessions set
        Claims claims = Jwts.parser()
                .setSigningKey("kwetter".getBytes("UTF-8"))
                .parseClaimsJws(clientId).getBody();


        final String id = claims.getSubject();
    clients.put(Integer.parseInt(id), session);
  }

  @OnClose
  public void onClose (Session session) {
    // Remove session from the connected sessions set
      for   (Map.Entry<Integer, Session> entry: clients.entrySet()) {
          if(entry.getValue().equals(session)){
                clients.remove(entry.getKey());
          }
      }
  }

}