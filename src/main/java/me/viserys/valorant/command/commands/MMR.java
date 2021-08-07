package me.viserys.valorant.command.commands;

import com.google.gson.JsonObject;
import lombok.SneakyThrows;
import me.viserys.valorant.main.Tracker;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import pw.mihou.velen.interfaces.VelenEvent;

import java.awt.*;

public class MMR implements VelenEvent {


    @SneakyThrows
    @Override
    public void onEvent(MessageCreateEvent event, Message message, User user, String[] args) {
        if(args.length >= 2) {
            JsonObject mmr = Tracker.getInstance().getApi().fetchMMR(args[0], args[1]);
            String rank = mmr.get("currenttierpatched").getAsString();
            String last_rating_Change = mmr.get("mmr_change_to_last_game").getAsString();
            int elo = mmr.get("elo").getAsInt();
            int tier = mmr.get("ranking_in_tier").getAsInt();
            message.reply(new EmbedBuilder().setAuthor("MMR").addInlineField("Riot ID", mmr.get("name").getAsString() + "#" + mmr.get("tag").getAsString()).addField("Rank",  rank).addField("Rating", tier + "/100").addField("Last Rating Change", last_rating_Change).addField("Elo", String.valueOf(elo)).setThumbnail(Tracker.getInstance().getWrapper().getRankImage(rank)).setColor(Color.black));
        } else {
            message.reply(new EmbedBuilder().setAuthor("Usage error!").setDescription("mmr <gameName> <tagLine>"));
        }
    }
}
