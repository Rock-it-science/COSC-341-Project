package com.example.projectprototype;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;
import java.util.List;


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


    public List getRoles()
    {
    	return roles;
    }

    public List getMembers()
    {
    	return users;
    }

    public void setRole(Member member, Role role)
    {
    	server.addRoleToMember(member, role);
    }



}