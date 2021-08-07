package me.viserys.valorant.command.commands;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.SneakyThrows;
import me.viserys.valorant.main.Tracker;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import pw.mihou.velen.interfaces.VelenEvent;

import java.awt.*;

public class Account implements VelenEvent {


    @SneakyThrows
    @Override
    public void onEvent(MessageCreateEvent event, Message message, User user, String[] args) {
        if(args.length >= 2) {
            JsonObject account = Tracker.getInstance().getApi().fetchAccount(args[0], args[1]);
            String name = account.get("name").getAsString();
            String tag = account.get("tag").getAsString();
            String puuid = account.get("puuid").getAsString();
            String region = account.get("region").getAsString();
            String cardImage = "";
            int account_level = account.get("account_level").getAsInt();

            JsonObject matches = Tracker.getInstance().getApi().fetchMatches(args[0], args[1], "competitive");
            JsonArray data = matches.getAsJsonArray("data");
            for (int i = 0; i < data.size(); i++) {
                JsonObject players = data.get(i).getAsJsonObject().get("players").getAsJsonObject();
                JsonArray all_players = players.getAsJsonArray("all_players").getAsJsonArray();
                for (int j = 0; j < all_players.size(); j++) {
                    if(puuid.contains(all_players.get(j).getAsJsonObject().get("puuid").getAsString())) {
                        cardImage = Tracker.getInstance().getApi().fetchCard(all_players.get(j).getAsJsonObject().get("player_card").getAsString()).get("displayIcon").getAsString();
                    }
                }

            }

            message.reply(new EmbedBuilder().setAuthor("MMR").addInlineField("PUUID", puuid).addField("Riot ID", name + "#" + tag).addField("Region", region).addField("Level", String.valueOf(account_level)).setThumbnail(cardImage).setColor(Color.black));
        } else {
            message.reply(new EmbedBuilder().setAuthor("Usage error!").setDescription("account <gameName> <tagLine>"));
        }
    }
}
