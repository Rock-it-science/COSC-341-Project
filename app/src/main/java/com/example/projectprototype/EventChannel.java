package com.example.projectprototype;


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
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.playback.AudioFrame;
import com.sedmelluq.discord.lavaplayer.track.playback.MutableAudioFrame;


public class EventChannel {
    private final JDA api;
    private List users;
    private List roles;
    private Guild server;

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

    public void testReactions(String msg)
    {
        TextChannel generalChannel = api.getTextChannelsByName("general", true).get(0);
        generalChannel.sendMessage(msg).queue(message -> {
            message.addReaction(":muscle:").queue();
            message.addReaction(":japanese_goblin:").queue();
            message.addReaction(":fried_shrimp:").queue();
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

    public Guild getGuild(){ return server; }

    public void setRole(Member member, Role role)
    {
    	server.addRoleToMember(member, role);
    }
/*
    public void playAudio(String name)
    {
        AudioManager manager = server.getAudioManager();
        manager.setSendingHandler(new AudioPlayerSendHandler<>());
        manager.openAudioConnection(server.getVoiceChannelsByName(name, true).get(0));

    }*/
}


