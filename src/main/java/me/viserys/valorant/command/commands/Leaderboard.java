package me.viserys.valorant.command.commands;

import com.google.gson.JsonArray;
import lombok.SneakyThrows;
import me.viserys.valorant.main.Tracker;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import pw.mihou.velen.interfaces.VelenEvent;

import java.awt.*;

public class Leaderboard implements VelenEvent {


    @SneakyThrows
    @Override
    public void onEvent(MessageCreateEvent event, Message message, User user, String[] args) {
        if (args.length >= 1) {
            JsonArray array = Tracker.getInstance().getApi().fetchLeaderboard(args[0]);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 10; i++) {
                sb.append(array.get(i).getAsJsonObject().get("leaderboardRank").getAsInt()).append(" -) ").append(array.get(i).getAsJsonObject().get("gameName").getAsString()).append("#").append(array.get(i).getAsJsonObject().get("tagLine").getAsString()).append(" [").append(array.get(i).getAsJsonObject().get("rankedRating")).append("]").append("\n");
            }
            event.getChannel().sendMessage(new EmbedBuilder().setAuthor("Leaderboard " + args[0].toUpperCase()).addField("TOP 10:", sb.toString()).setColor(Color.black));
        } else {
            message.reply(new EmbedBuilder().setAuthor("Usage error!").setDescription("leaderboard <region> \n" + "available regions: eu, na, ap, kr"));
        }
    }


}
