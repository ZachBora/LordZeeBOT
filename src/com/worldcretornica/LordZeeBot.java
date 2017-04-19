package com.worldcretornica;

import com.worldcretornica.entities.TwitchChannel;
import com.worldcretornica.entities.TwitchUser;
import java.util.HashMap;
import java.util.Map;
import org.jibble.pircbot.PircBot;

/**
 *
 * @author ZachBora
 */
public class LordZeeBot extends PircBot  {
    
    private boolean hasMembership;
    private boolean hasTags;
    private boolean hasCommands;
    private Map<String, TwitchChannel> channels;
    private Map<String, TwitchUser> users;
    
    public LordZeeBot() {
        this.setName("lordzeebot");
        this.setLogin("lordzeebot");
        hasMembership = false;
        hasTags = false;
        hasCommands = false;
        channels = new HashMap<>();
        users = new HashMap<>();
    }
    
    public void initialize() {
        sendRawLineViaQueue("CAP REQ :twitch.tv/membership");
        //sendRawLineViaQueue("CAP REQ :twitch.tv/tags");
        hasTags = true;
        sendRawLineViaQueue("CAP REQ :twitch.tv/commands");
    }


    @Override
    public void onMessage(String channel, String sender, String login, String hostname, String message)
    {

        System.out.print("[LOG]: onMessage!\n");
        if (message.equalsIgnoreCase("!botclose")) {
            sendMessage(channel, "going to sleep");
            this.disconnect();
        }
        if (message.equalsIgnoreCase("time")) {
            System.out.println("time " + channel);
            String time = new java.util.Date().toString();
            sendMessage(channel, sender + ": The time is now " + time);
        }
    }
    
    @Override
    public void handleLine(String line)
    {
        super.handleLine(line);

        if (!isInitialized())
        {
            if (line.equals(":tmi.twitch.tv CAP * ACK :twitch.tv/membership"))
            {
                hasMembership = true;
            }

            if (line.equals(":tmi.twitch.tv CAP * ACK :twitch.tv/tags"))
            {
                hasTags = true;
            }

            if (line.equals(":tmi.twitch.tv CAP * ACK :twitch.tv/commands"))
            {
                hasCommands = true;
            }
        }
    }
    
    public void joinToChannel(String channel)
    {
        joinChannel(channel);
        channels.put(channel, new TwitchChannel(channel));
    }

    public void partFromChannel(String channel)
    {
        partChannel(channel);
        channels.remove(channel);
    }
    
    public TwitchChannel getTwitchChannel(String channel) {
        return channels.get(channel);
    }
    
    public Map<String, TwitchChannel> getTwitchChannels()
    {
        return channels;
    }
    
    public TwitchUser getTwitchUser(String user) {
        return users.get(user);
    }
    
    public Map<String, TwitchUser> getTwitchUsers() {
        return users;
    }

    public boolean isInitialized()
    {
        return hasMembership & hasCommands & hasTags;
    }
}
