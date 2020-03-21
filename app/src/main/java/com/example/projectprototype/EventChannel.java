package com.example.projectprototype;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.TextChannel;

public class EventChannel {
    private final JDA api;

    public EventChannel(JDA api) {
        this.api = api;
    }

    public void sendGeneral(String msg) {
        TextChannel generalChannel = api.getTextChannelsByName("general", true).get(0);
        generalChannel.sendMessage(msg).queue();
    }
}