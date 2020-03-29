package com.example.projectprototype;

import net.dv8tion.jda.api.JDA;
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

import com.example.projectprototype.music.musicMain;


public class EventChannel {
    private final JDA api;
    private List<Member> users;
    private List<Role> roles;
    private Guild server;
    public Message poolMessage;
    private musicMain musicPlayer = null;
    private String poolText;
    private List<TextChannel> textChans;
    private List<Guild> servers;

    public EventChannel(JDA api) {
        this.api = api;
        servers = api.getGuilds();
        poolText = "";
        System.out.println(servers);
        server = servers.get(0);
        users = server.getMembers();
        System.out.println("MEMBERS ============" + users);
        roles = server.getRoles();
        textChans = server.getTextChannels();
    }

    public void setServer(String serString)
    {
        System.out.println(serString);
        //System.out.println(api.getGuildsByName(serString, true));
        //server = api.getGuildsByName(serString, true).get(0);
        users = server.getMembers();
        roles = server.getRoles();
        textChans = server.getTextChannels();
    }

    public List<Guild> getServers()
    {
        return servers;
    }

    public void newUsers()
    {
        for(int i = 0 ; i < textChans.size() ; i++)
        {
            if(textChans.get(i).getName().equals("roles"))
            {
                MessageHistory h = textChans.get(i).getHistory();
                List<Message> mess = h.retrievePast(100).complete();

                for(int j = 0 ; j < mess.size() ; j++)
                {
                    System.out.println(users.toString());
                    if(!users.contains(mess.get(j).getAuthor()))
                    {
                        System.out.println(mess.get(j).getAuthor().getName());
                        System.out.println("GOT HERE G");
                        System.out.println(server.addRoleToMember(mess.get(j).getAuthor().getId(), server.getRolesByName("baseRole",true).get(0)).complete());
                        //setRole(mess.get(j).getMember(), "member");
                    }
                }
                break;
            }
        }
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
            if (i.getName().equals("baseRole"))
            {
                has = true;
                break;
            }
        }
        if (!has)
            server.createRole().setName("baseRole").complete();
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
        System.out.println("THE SIZE OF USERS IS : " + users.size() + " SERVER IS : " + getGuild().getName());
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

    public void setRole(String member, String role)
    {
        System.out.println(member + " =========== " + server.getMembersByEffectiveName(member, false).toString());
    	server.addRoleToMember(server.getMembersByEffectiveName(member, false).get(0), server.getRolesByName(role, true).get(0)).queue();
    }

    public void ban(String member, String reason)
    {
        server.getMemberById(member).ban(0, reason);
    }
    public void ban(String member)
    {
        server.getMemberById(member).ban(0);
    }

    public void kick(String member)
    {
        server.getMemberById(member).kick();
    }
    public void kick(String member, String reason)
    {
        server.getMemberById(member).kick(reason);
    }

    public String[] getUserRoles(String user)
    {
        List<Role> ls = server.getMembersByEffectiveName(user,true ).get(0).getRoles();
        String[] output = new String[ls.size()];

        for(int i = 0 ; i < ls.size() ; i++)
        {
            output[i] = ls.get(i).getName();
        }
        return output;
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



