
package com.worldcretornica;

import com.worldcretornica.entities.TwitchChannel;
import com.worldcretornica.entities.TwitchUser;


/**
 *
 * @author ZachBora
 */
public class LordZeeBotMain {

    protected static LordZeeBot bot = null;

    public static void main(String[] args) throws Exception {
        
        bot = new LordZeeBot();
        
        bot.setVerbose(true);
        
        bot.connect("irc.twitch.tv", 6667, "oauth:sa9ki2h8jyp1vjuy3y289nt09fdrpl");
        //Thread.sleep(1000);
        bot.initialize();
        
        while(!bot.isInitialized()) {
            Thread.sleep(1000);
        }
        
        bot.joinChannel("#lordzachbora");
        bot.sendMessage("#lordzachbora", "4Head");
        
        float time;
        long timeStart, timeEnd;
        
        while(bot.isConnected()) {
            //System.out.println("looping!");
            timeStart = System.nanoTime();
            
            for (TwitchChannel c : bot.getTwitchChannels().values())
            {
               /* if (c.getCmdSent() > 0)
                {
                    c.setCmdSent(c.getCmdSent() - 1);
                }*/
            }

            for (TwitchUser u : bot.getTwitchUsers().values())
            {
               /* if (u.getCmdTimer() > 0)
                {
                    u.setCmdTimer(u.getCmdTimer() - 1);
                }*/
            }
            
            timeEnd = System.nanoTime();
            time = (float) (timeEnd - timeStart) / 1000000.0f;
            
            Thread.sleep((long) (1000.0f - time));
        }
    }
}
