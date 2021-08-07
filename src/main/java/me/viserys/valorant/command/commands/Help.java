package me.viserys.valorant.command.commands;

import lombok.SneakyThrows;
import me.viserys.valorant.main.Tracker;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import pw.mihou.velen.interfaces.VelenCommand;
import pw.mihou.velen.interfaces.VelenEvent;

import java.awt.*;
import java.util.stream.Collectors;

public class Help implements VelenEvent {


    @SneakyThrows
    @Override
    public void onEvent(MessageCreateEvent event, Message message, User user, String[] args) {
        message.reply(build());
    }

    private EmbedBuilder build() {
        EmbedBuilder builder = new EmbedBuilder()
                .setTitle("List of all commands!")
                .setColor(Color.BLACK);
        builder.addInlineField("Ingame", Tracker.getInstance().getVelen().getCategoryIgnoreCasing("Ingame").stream().map(VelenCommand::getName)
                .map(s -> "`" + s + "`")
                .collect(Collectors.joining(", ")));
        return builder;
    }
}
