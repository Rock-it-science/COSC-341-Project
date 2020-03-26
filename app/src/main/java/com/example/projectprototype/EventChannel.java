package com.example.projectprototype;


import android.speech.tts.Voice;
import android.util.Log;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.audio.AudioSendHandler;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.List;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.managers.AudioManager;
import net.dv8tion.jda.api.audio.AudioSendHandler;

import com.example.projectprototype.music.musicMain;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.playback.AudioFrame;
import com.sedmelluq.discord.lavaplayer.track.playback.MutableAudioFrame;


public class EventChannel {
    private final JDA api;
    private List users;
    private List roles;
    private Guild server;
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
            message.addReaction("✅").queue();
            message.addReaction("❌").queue();
        });
    }


    public List getRoles()
    {
    	return roles;
    }

    public List getMembers()
    {
    	return users;
    }


    public Guild getGuild()
    {
        return server;
    }

    public List getVoice()
    {
        return server.getVoiceChannels();
    }

    public void setRole(Member member, Role role)
    {
    	server.addRoleToMember(member, role);
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

            public void setMusicChannel(VoiceChannel chan)
            {
                if(musicPlayer == null)
                {
                    musicPlayer = new musicMain(server);
                }

                musicPlayer.setChan(chan);

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



