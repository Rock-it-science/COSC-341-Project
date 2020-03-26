package com.example.projectprototype.music;

import android.content.Context;
import android.widget.Toast;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;

import java.util.HashMap;
import java.util.Map;

public class musicMain extends ListenerAdapter {

    private final AudioPlayerManager playerManager;
    private final Map<Long, GuildMusicManager> musicManagers;
    private Guild guild;
    private static String playing;
    private VoiceChannel chan = null;

    public musicMain(Guild guild) {
        this.guild = guild;
        this.musicManagers = new HashMap<>();

        this.playerManager = new DefaultAudioPlayerManager();
        AudioSourceManagers.registerRemoteSources(playerManager);
        AudioSourceManagers.registerLocalSource(playerManager);
    }

    private synchronized GuildMusicManager getGuildAudioPlayer(Guild guild) {
        long guildId = Long.parseLong(guild.getId());
        GuildMusicManager musicManager = musicManagers.get(guildId);

        if (musicManager == null) {
            musicManager = new GuildMusicManager(playerManager);
            musicManagers.put(guildId, musicManager);
        }

        guild.getAudioManager().setSendingHandler(musicManager.getSendHandler());

        return musicManager;
    }

    public void setChan(VoiceChannel chan)
    {
        this.chan = chan;
    }

    public void playMusic(String song)
    {
        loadAndPlay(song);
    }
    public void skipMusic()
    {
        skipTrack();
    }

    public static void setPlaying(String newSong)
    {
        playing = newSong;
    }

    public String getPlaying()
    {
        return playing;
    }

    private void loadAndPlay(final String trackUrl) {
        GuildMusicManager musicManager = getGuildAudioPlayer(guild);

        playerManager.loadItemOrdered(musicManager, trackUrl, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack track) {
                play(musicManager, track);
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {
                AudioTrack firstTrack = playlist.getSelectedTrack();

                if (firstTrack == null) {
                    firstTrack = playlist.getTracks().get(0);
                }
                play(musicManager, firstTrack);
            }

            @Override
            public void noMatches() {
                System.out.println("Nothing found by " + trackUrl);
            }

            @Override
            public void loadFailed(FriendlyException exception) {
                System.out.println("Could not play: " + exception.getMessage());
            }
        });
    }

    private void play(GuildMusicManager musicManager, AudioTrack track) {

        connectToFirstVoiceChannel(guild.getAudioManager());

        musicManager.scheduler.queue(track);
    }

    private void skipTrack() {
        GuildMusicManager musicManager = getGuildAudioPlayer(guild);
        musicManager.scheduler.nextTrack();
        System.out.println("Skipped song");
    }

    private void connectToFirstVoiceChannel(AudioManager audioManager) {
        if(audioManager.getConnectedChannel() == chan)
        {
            return;
        }
        else if(audioManager.isConnected()) {
            audioManager.closeAudioConnection();
            if (!audioManager.isConnected() && !audioManager.isAttemptingToConnect()) {
                for (VoiceChannel voiceChannel : audioManager.getGuild().getVoiceChannels()) {
                    if (voiceChannel == chan && chan != null) {
                        audioManager.openAudioConnection(voiceChannel);
                        break;
                    } else if (chan == null) {
                        audioManager.openAudioConnection(voiceChannel);
                        break;
                    }
                }
            }
        }
        else
        {
            if (!audioManager.isConnected() && !audioManager.isAttemptingToConnect()) {
                for (VoiceChannel voiceChannel : audioManager.getGuild().getVoiceChannels()) {
                    if (voiceChannel == chan && chan != null) {
                        audioManager.openAudioConnection(voiceChannel);
                        break;
                    } else if (chan == null) {
                        audioManager.openAudioConnection(voiceChannel);
                        break;
                    }
                }
            }
        }
    }
}