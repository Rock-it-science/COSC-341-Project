package com.example.projectprototype;
import android.util.Log;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Category;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageHistory;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;
import java.util.ArrayList;
import java.util.List;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.requests.restaction.pagination.ReactionPaginationAction;
import net.dv8tion.jda.internal.requests.Route;

import com.example.projectprototype.music.musicMain;

import org.w3c.dom.Text;


public class EventChannel {
    private final JDA api;
    private List<Member> users;
    private List<Role> roles;
    private Guild server;
    public Message poolMessage;
    private musicMain musicPlayer = null;
    private String poolText;
    private List<TextChannel> textChans;

    public EventChannel(JDA api) {
        this.api = api;
        server = api.getGuilds().get(0);
        users = server.getMembers();
        roles = server.getRoles();
        poolText = "";

        textChans = server.getTextChannels();

        for(int i = 0 ; i < textChans.size() ; i++)
        {
            if(textChans.get(i).getName().equals("roles"))
            {
                MessageHistory h = textChans.get(i).getHistory();
                List<Message> mess = h.retrievePast(100).complete();

                for(int j = 0 ; j < mess.size() ; j++)
                {
                    if(!users.contains(mess.get(j).getAuthor()))
                    {
                        //setRole(mess.get(j).getMember(), "member");
                    }
                }
                break;
            }
        }
        System.out.println(server.getMembers().stream().filter(member -> !member.getUser().isBot()).count());
    }

    public void setupServer() {

        boolean has = false;
        for (TextChannel i : textChans) {
            if (i.getName().equals("roles")) {
                has = true;
                break;
            }
        }
        if (!has)
            server.createTextChannel("roles").setParent(textChans.get(0).getParent()).complete();

        has = false;
        System.out.println(users.toString());
        System.out.println(roles.toString());
        for (Role i : roles) {
            if (i.getName().equals("member"))
            {
                has = true;
                break;
            }
        }
        if (!has)
            server.createRole().setName("member").complete();
    }

    public void setMessage(Message message)
    {
        poolMessage = message;
    }

    public void sendGeneral(String msg) {
        TextChannel generalChannel = api.getTextChannelsByName("general", true).get(0);
        generalChannel.sendMessage(msg).queue();
    }

    public void inThePoolWithTheBoys(String msg)
    {
        TextChannel generalChannel = api.getTextChannelsByName("general", true).get(0);
        generalChannel.sendMessage(msg).queue(message -> {
            message.addReaction("1️⃣").queue();
            message.addReaction("2️⃣").queue();
            message.addReaction("3️⃣").queue();
            message.addReaction("4️⃣").queue();
            message.addReaction("5️⃣").queue();

            setMessage(message);
        });
    }

    public String getPoolText()
    {
        return poolText;
    }

    public void setPoolText(String txt)
    {
        poolText = txt;
    }

    public void poolYesNo(String msg)
    {
        TextChannel generalChannel = api.getTextChannelsByName("general", true).get(0);
        generalChannel.sendMessage(msg).queue(message -> {
            poolMessage = message;
            message.addReaction("✅").queue();
            message.addReaction("❌").queue();
        });
    }

    public ArrayList<Integer> resultsYN()
    {
        if(poolMessage != null)
        {
            ArrayList<Integer> list = new ArrayList<>();

            ReactionPaginationAction users = poolMessage.retrieveReactionUsers("✅");
            list.add(users.cacheSize());
            users = poolMessage.retrieveReactionUsers("❌");
            list.add(users.cacheSize());
        }
        return null;
    }

    public ArrayList<Integer> results5()
    {

        if(poolMessage != null)
        {
            ArrayList<Integer> list = new ArrayList<>();

            ReactionPaginationAction users;

            users = poolMessage.retrieveReactionUsers("1️⃣");
            list.add(users.cacheSize());
            users = poolMessage.retrieveReactionUsers("2️⃣");
            list.add(users.cacheSize());
            users = poolMessage.retrieveReactionUsers("3️⃣");
            list.add(users.cacheSize());
            users = poolMessage.retrieveReactionUsers("4️⃣");
            list.add(users.cacheSize());
            users = poolMessage.retrieveReactionUsers("5️⃣");
            list.add(users.cacheSize());

            return list;
        }

        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);list.add(1);list.add(1);list.add(1);list.add(1);
        return list;
    }

    public String[] getRoles()
    {
    	String[] output = new String[roles.size()];
    	for(int i = 0; i < roles.size() ; i++)
        {
            output[i] = ((Role)roles.get(i)).getName();
        }
        return output;
    }

    public String[] getMembers()
    {
        String[] output = new String[users.size()];
        System.out.println("THE SIZE OF USERS IS : " + users.size() + "SERVER IS : " + getGuild().getName());
        for(int i = 0; i< users.size() ; i++)
        {
            output[i] = (((Member)users.get(i)).getEffectiveName());
            System.out.println(output[i]);
        }
    	return output;
    }


    public Guild getGuild()
    {
        return server;
    }

    public String[] getVoice()
    {

        List<VoiceChannel> voices = server.getVoiceChannels();
        String[] output = new String[voices.size()];
        for(int i = 0; i < voices.size() ; i++)
        {
            output[i] = voices.get(i).getName();
        }
        return output;
    }

    public void setRole(Member member, String role)
    {
    	server.addRoleToMember(member, server.getRolesByName(role, true).get(0)).queue();
    }

    public void ban(Member member, String reason)
    {
        member.ban(0, reason);
    }
    public void ban(Member member)
    {
        member.ban(0);
    }

    public void kick(Member member)
    {
        member.kick();
    }
    public void kick(Member member, String reason)
    {
        member.kick(reason);
    }

    public String[] getText()
    {
        List chan = server.getTextChannels();
        String[] output = new String[chan.size()];
        for(int i = 0 ; i < chan.size() ; i++)
        {
            output[i] = ((TextChannel)chan.get(i)).getName();
        }
        return output;
    }

    public void writeToText(String message, String name)
    {
        TextChannel chan = server.getTextChannelsByName(name, true).get(0);
        chan.sendMessage(message);
    }


    //MUSIC COMMANDS

            public void setMusicChannel(String name)
            {
                VoiceChannel channel = server.getVoiceChannelsByName(name, true).get(0);
                if(musicPlayer == null)
                {
                    musicPlayer = new musicMain(server);
                }
                musicPlayer.setChan(channel);
            }

            public void addSong(String song)
            {
                musicPlayer.playMusic(song);
            }

            public void skipSong()
            {
                musicPlayer.skipMusic();
            }

            public String getPlaying()
            {
                return musicPlayer.getPlaying();
            }

            public void createMusicConnection(String name)
            {
                musicPlayer = new musicMain(server);
            }

}



