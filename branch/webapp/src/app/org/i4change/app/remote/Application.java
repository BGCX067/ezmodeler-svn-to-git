package org.i4change.app.remote;

 
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.LinkedList;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.red5.server.adapter.ApplicationAdapter;
import org.red5.server.api.IClient;
import org.red5.server.api.IConnection;
import org.red5.server.api.IScope;
import org.red5.server.api.Red5;
import org.red5.server.api.service.IPendingServiceCall;
import org.red5.server.api.service.IPendingServiceCallback;
import org.red5.server.api.service.IServiceCapableConnection;
import org.red5.server.api.stream.IBroadcastStream;
import org.red5.server.api.stream.IPlayItem;
import org.red5.server.api.stream.IPlaylistSubscriberStream;
import org.red5.server.api.stream.IStreamAwareScopeHandler;
import org.red5.server.api.stream.ISubscriberStream;
import org.i4change.app.quartz.scheduler.QuartzSessionClear;
import org.i4change.app.utils.crypt.ManageCryptStyle;
import org.i4change.app.session.beans.RoomClient;
import org.i4change.app.data.user.Usermanagement;
import org.i4change.app.hibernate.beans.user.Users;


/**
 * 
 * @author swagner
 * 
 */

public class Application extends ApplicationAdapter implements
		IPendingServiceCallback, IStreamAwareScopeHandler {

	private static final Log log = LogFactory.getLog(Application.class);

	private static HashMap<String,RoomClient> ClientList = new HashMap<String,RoomClient>();
	
	/*
	 * EMoticons FileList
	 */
	public static LinkedList<LinkedList<String>> emotfilesList = new LinkedList<LinkedList<String>>();
		
	//The Global WebApp Path
	public static String webAppPath = "";
	public static String configDirName = "conf";
	
	private static long broadCastCounter = 0;
	
	private static Application instance = null;
	
	public static synchronized Application getInstance(){
		return instance;
	}

	@Override
	public boolean appStart(IScope scope) {
		try {
			webAppPath = scope.getResource("/").getFile().getAbsolutePath();			
			scope.getResource("public/").getFile().getParentFile().getAbsolutePath();
			String filePath = scope.getResource("public/").getFile().getAbsolutePath();
			instance = this;
			// init your handler here
			//System.out.println("################## appStart    ");
			QuartzSessionClear bwHelp = new QuartzSessionClear();
			String jobName = addScheduledJob(300000,bwHelp);
			log.debug("jobName: "+jobName);
		} catch (Exception err) {
			log.error("[appStart]",err);
		}
		return true;
	}
	

	@Override
	public boolean roomConnect(IConnection conn, Object[] params) {
		log.debug("roomConnect    : " + conn.getHost() + " "+ conn.getClient() + " " + conn.getClient().getId());
		return true;
	}

	@Override
	public void roomDisconnect(IConnection conn) {
		log.debug("roomDisconnect: " + conn.getHost() + " "+ conn.getClient() + " " + conn.getClient().getId());
	}

	@Override
	public boolean roomStart(IScope room) {
		log.debug("roomStart " + room + room.getClients().size() + " "+ room.getContextPath() + " " + room.getName());
		return true;
	}

	@Override
	public void roomStop(IScope room) {
		log.debug("roomStop " + room + room.getClients().size() + " "+ room.getContextPath() + " " + room.getName());
	}

	@Override
	public boolean roomJoin(IClient client, IScope room) {
		try {
			
			IConnection conn = Red5.getConnectionLocal();
			IServiceCapableConnection service = (IServiceCapableConnection) conn;
			log.debug("Client connected xmlcrmred5 jar " + client.getId() + " conn "+ client);
			log.debug("Setting stream xmlcrmred5 xmlcrmred5 id: " + getClients().size()); // just a unique number
			service.invoke("setId", new Object[] { client.getId() },this);
	
			//Store the Connection into a bean and add it to the HashMap
			RoomClient rcm = new RoomClient();
			rcm.setConnectedSince(new Date());
			rcm.setStreamid(client.getId());
			rcm.setUserroom("");
			long thistime = new Date().getTime();
			rcm.setPublicSID(ManageCryptStyle.getInstance().getInstanceOfCrypt().createPassPhrase(String.valueOf(thistime).toString()));
			
			rcm.setUserport(conn.getRemotePort());
			rcm.setUserip(conn.getRemoteAddress());
			rcm.setSwfurl(conn.getConnectParams().get("swfUrl").toString());			
			log.debug("##### : " + rcm.getStreamid()); // just a unique number
			
			//Set the moderation for the CLient on startup
			log.debug("Current clients in this room: "+conn.getScope().getClients().size());		
			
			log.debug("This client is not the moderator"+rcm.getStreamid());
			rcm.setIsMod(new Boolean(false));
			
			ClientList.put(rcm.getStreamid(),rcm);

		} catch (Exception err){
			log.error("roomJoin",err);
		}		
		return true;
	}
	
	/**
	 * this function is invoked directly after initial connecting
	 * @return
	 */
	public String getPublicSID() {
		IConnection current = Red5.getConnectionLocal();
		RoomClient currentClient = ClientList.get(current.getClient().getId());	
		return currentClient.getPublicSID();
	}
	
	/**
	 * this function is invoked after a reconnect
	 * @param newPublicSID
	 */
	public void overwritePublicSID(String newPublicSID) {
		IConnection current = Red5.getConnectionLocal();
		RoomClient currentClient = ClientList.get(current.getClient().getId());	
		currentClient.setPublicSID(newPublicSID);
		ClientList.put(current.getClient().getId(), currentClient);
	}
	
	/**
	 * @deprecated
	 * @return
	 */
	public RoomClient logicalRoomEnter(){
		try {
			IConnection current = Red5.getConnectionLocal();
			RoomClient currentClient = ClientList.get(current.getClient().getId());	
			String roomname = currentClient.getUserroom();
			String orgdomain = currentClient.getDomain();				
			String streamid = currentClient.getStreamid();
			
			
			//check if room has no Moderation yet
			//get all Clients of room
			Set<String> keys = ClientList.keySet();
			Iterator<String> iter = keys.iterator();
			int roomcount = 0;
			while (iter.hasNext()) {
				String key = (String) iter.next();
				RoomClient rcl = ClientList.get(key);
				
				log.debug("#+#+#+#+##+## logicalRoomEnter ClientList key: "+rcl.getStreamid());
				log.debug("#+#+#+#+##+## logicalRoomEnter ClientList key: "+rcl.getUserroom());
				
				//Check if the Client is in the same room and same domain 
				//and is not the same like we have just declared to be moderating this room
				if(roomname.equals(rcl.getUserroom()) && orgdomain.equals(rcl.getDomain()) && !streamid.equals(rcl.getStreamid())){
					log.debug("set to ++ for client: "+rcl.getStreamid()+" "+roomcount);
					roomcount++;
				}				
			}
			
			if (roomcount==0){
				log.debug("Room is empty so set this user to be moderation role");
				currentClient.setIsMod(true);
				ClientList.put(streamid, currentClient);
				return currentClient;
			} else {
				log.debug("Room is already somebody so set this user not to be moderation role");
				currentClient.setIsMod(false);
				ClientList.put(streamid, currentClient);
				return currentClient;
			}
			
		} catch (Exception err) {
			log.error("[logicalRoomEnter] ",err);
		}
		return null;
	}

	/**
	 * Logic must be before roomDisconnect cause otherwise you cannot throw a message to each one
	 * 
	 */
	@Override
	public void roomLeave(IClient client, IScope room) {
		log.debug("roomLeave " + client.getId() + " "+ room.getClients().size() + " " + room.getContextPath() + " "+ room.getName());
		try {
			IConnection current = Red5.getConnectionLocal();
			RoomClient currentClient = ClientList.get(current.getClient().getId());
			String roomname = currentClient.getUserroom();
			String orgdomain = currentClient.getDomain();	
			//String streamid = currentClient.getStreamid();
			
			log.debug("##### roomLeave :. " + currentClient.getStreamid()); // just a unique number
			log.debug("removing USername "+currentClient.getUsername()+" "+currentClient.getConnectedSince()+" streamid: "+currentClient.getStreamid());

			this.disconnectUser(currentClient);


			
		} catch (Exception err){
			log.error("[roomDisconnect]",err);
		}		
	}
	
	/**
	 * This Method will be called every time a Client connects
	 * 
	 * @return boolean
	 */
	@Override
	public boolean appConnect(IConnection conn, Object[] params) {
		log.debug("appConnect " + conn + " "+ conn.getClient().getId() + " ");
		return true;
	}
	
	/**
	 * this method is called every time a client disconencts
	 */
	@Override
	public void appDisconnect(IConnection conn){
		
		log.debug("appDisconnect ID: "+conn.getClient().getId());
		//key = streamid
		
	}

	/**
	 * Get all connected Clients
	 * @return
	 */
	public HashMap<String,RoomClient> getClientListAll(){
		IClient myclient = Red5.getConnectionLocal().getClient();
		log.debug("getClientListAll: "+myclient.getAttribute("role"));
		if(myclient.getAttribute("role").equals("admin"))
			return ClientList;
		else 
			return null;
	}
	
	/**
	 * update the Session Object after changing the user-record
	 * @param users_id
	 */
	public void updateUserSessionObject(Long users_id, String pictureuri){
		try {			
			Users us = Usermanagement.getInstance().getUser(users_id);
			for (Iterator<String> itList = ClientList.keySet().iterator();itList.hasNext();) {
				String red5Id  = itList.next();
				RoomClient rcl = ClientList.get(red5Id);
				
				if (rcl.getUser_id().equals(users_id)){
					log.info("updateUserSessionObject #### FOUND USER rcl1: "+rcl.getUser_id()+ " NEW PIC: "+pictureuri);
					rcl.setPicture_uri(pictureuri);
					rcl.setUsername(us.getLogin());
					rcl.setFirstname(us.getFirstname());
					rcl.setLastname(us.getLastname());
					ClientList.put(red5Id, rcl);
				}
			}
		} catch (Exception err) {
			log.error("[updateUserSessionObject]",err);
		}
	}

	public void sendMessageWithClientByUserId(Object message, String userId) {
		try {
			
			IScope scopeHibernate = scope.getScope("hibernate");
			
			if (scopeHibernate!=null){
				//Notify the clients of the same scope (room) with user_id
				Iterator<IConnection> it = scope.getScope("hibernate").getConnections();
				if (it!=null) {
					while (it.hasNext()) {
						IConnection conn = it.next();				
						RoomClient rcl = ClientList.get(conn.getClient().getId());
						if (rcl.getUser_id().equals(userId)){
							((IServiceCapableConnection) conn).invoke("newMessageByRoomAndDomain",new Object[] { message }, this);
						}
					}
				}
			} else {
				//Scope not yet started
			}
		} catch (Exception err) {
			log.error("[sendMessageWithClient] ",err);
			err.printStackTrace();
		}		
	}	
	
	public void streamBroadcastStart(IBroadcastStream stream) {
		log.debug("### streamBroadcastStart Name: " + stream.getName());
	}

	public void streamPlaylistItemPlay(IPlaylistSubscriberStream stream,IPlayItem item, boolean isLive) {
	}

	public void streamPlaylistItemStop(IPlaylistSubscriberStream stream,IPlayItem item) {

	}

	public void streamPlaylistVODItemPause(IPlaylistSubscriberStream stream,IPlayItem item, int position) {

	}

	public void streamPlaylistVODItemResume(IPlaylistSubscriberStream stream,IPlayItem item, int position) {

	}

	public void streamPlaylistVODItemSeek(IPlaylistSubscriberStream stream,IPlayItem item, int position) {

	}

	public void streamSubscriberClose(ISubscriberStream stream) {
		log.debug("### streamSubscriberClose Name: " + stream.getName() + "|"+ stream.getStreamId() + "|"+ stream.getConnection().getScope() + "|" + stream.hashCode());
	}

	public void streamSubscriberStart(ISubscriberStream stream) {
		log.debug("### streamSubscriberStart Name: " + stream.getName());
	}
	
	/**
	 * This Method will return all streams of the parent stream (which should be all including
	 * the child-scope streams independend from room/domain
	 * 
	 * @return
	 */
	public List<String> getAllStreams(){
		return getBroadcastStreamNames(scope);
	}
	  

	/**
	 * Handle callback from service call.
	 */
	public void resultReceived(IPendingServiceCall call) {
		log.debug("Received result " + call.getResult() + " for "+ call.getServiceMethodName());
	}

	/* (non-Javadoc)
	 * @see org.red5.server.api.stream.IStreamAwareScopeHandler#streamRecordStart(org.red5.server.api.stream.IBroadcastStream)
	 */
	public void streamRecordStart(IBroadcastStream stream) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @return the clientList
	 */
	public static HashMap<String, RoomClient> getClientList() {
		return ClientList;
	}
	
	public void disconnectUser(RoomClient rcl){
		try {
			ClientList.remove(rcl.getStreamid());
		} catch (Exception err) {
			log.error("[disconnectUser]",err);
		}
	}
	
}