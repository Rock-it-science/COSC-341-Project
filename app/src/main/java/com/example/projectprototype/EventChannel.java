package com.example.projectprototype;


import android.speech.tts.Voice;
import android.util.Log;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.audio.AudioSendHandler;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.JDABuilder;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.managers.AudioManager;
import net.dv8tion.jda.api.audio.AudioSendHandler;

import net.dv8tion.jda.api.requests.restaction.pagination.ReactionPaginationAction;
import com.example.projectprototype.music.musicMain;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.playback.AudioFrame;
import com.sedmelluq.discord.lavaplayer.track.playback.MutableAudioFrame;


public class EventChannel {
    private final JDA api;
    private List users;
    private List roles;
    private Guild server;
    private Message poolMessage;
    private musicMain musicPlayer = null;


    public EventChannel(JDA api) {
        this.api = api;
        server = api.getGuilds().get(0);
        users = server.getMembers();
        roles = server.getRoles();
    }


    public void sendGeneral(String msg) {
        TextChannel generalChannel = api.getTextChannelsByName("general", true).get(0);
        generalChannel.sendMessage(msg).queue();
    }

    public void inThePoolWithTheBoys(String msg)
    {
        TextChannel generalChannel = api.getTextChannelsByName("general", true).get(0);
        generalChannel.sendMessage(msg).queue(message -> {
            poolMessage = message;
            message.addReaction("0️⃣").queue();
            message.addReaction("1️⃣").queue();
            message.addReaction("2️⃣").queue();
            message.addReaction("3️⃣").queue();
            message.addReaction("4️⃣").queue();
        });
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

            users = poolMessage.retrieveReactionUsers("0️⃣");
            list.add(users.cacheSize());
            users = poolMessage.retrieveReactionUsers("1️⃣");
            list.add(users.cacheSize());
            users = poolMessage.retrieveReactionUsers("2️⃣");
            list.add(users.cacheSize());
            users = poolMessage.retrieveReactionUsers("3️⃣");
            list.add(users.cacheSize());
            users = poolMessage.retrieveReactionUsers("4️⃣");
            list.add(users.cacheSize());

            //Log.d("Test", "results5: " + list.toString());
        }
        return null;
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

    public List getMembers()
    {
    	return users;
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
    	server.addRoleToMember(member, server.getRolesByName(role, true).get(0));
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



