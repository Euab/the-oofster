package com.euii.jager.commands.music;

import com.euii.jager.Jager;
import com.euii.jager.audio.AudioHandler;
import com.euii.jager.audio.GuildAudioController;
import com.euii.jager.contracts.commands.AbstractCommand;
import com.euii.jager.factories.MessageFactory;
import com.euii.jager.utilities.EmoteReference;
import net.dv8tion.jda.api.entities.Message;

import java.util.Collections;
import java.util.List;

public class ResumeCommand extends AbstractCommand {

    public ResumeCommand(Jager jager) {
        super(jager, false);
    }

    @Override
    public String getName() {
        return "Resume";
    }

    @Override
    public String getDescription() {
        return "Resume playback of the current track.";
    }

    @Override
    public List<String> getUsageInstructions() {
        return Collections.singletonList("`:command resume` - Resumes playback.");
    }

    @Override
    public String getExampleUsage() {
        return null;
    }

    @Override
    public List<String> getTriggers() {
        return Collections.singletonList("resume");
    }

    @Override
    public boolean onCommand(Message message, String[] args) {
        GuildAudioController controller = AudioHandler.getGuildAudioPlayer(message.getGuild());

        if (controller.getPlayer().getPlayingTrack() == null)
            return sendErrorMessage(message, "Nothing is playing in the voice channel.");

        if (!controller.getPlayer().isPaused()) {
            MessageFactory.makeWarning(message, EmoteReference.WARNING + "Already resumed. Use `!pause` to pause playback.")
                    .queue();
            return true;
        }

        controller.getPlayer().setPaused(false);
        message.getChannel().sendMessage(EmoteReference.PLAY.toString()).queue();

        return true;
    }

}
