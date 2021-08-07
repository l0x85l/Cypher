package me.viserys.valorant.command.commands;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.SneakyThrows;
import me.viserys.valorant.main.Tracker;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import pw.mihou.velen.interfaces.VelenEvent;
import pw.mihou.velen.pagination.Paginate;
import pw.mihou.velen.pagination.entities.Paginator;
import pw.mihou.velen.pagination.events.PaginateSimpleEvent;

import java.awt.*;
import java.time.Duration;
import java.util.List;
import java.util.*;

public class Matches implements VelenEvent {

    String map;
    String character;
    String game_start_patched;
    int game_length;
    String mode;
    StringBuilder redPlayers;
    StringBuilder redResult;
    StringBuilder bluePlayers;
    StringBuilder blueResult;

    @SneakyThrows
    @Override
    public void onEvent(MessageCreateEvent event, Message message, User user, String[] args) {
        if (args.length >= 3) {
            List<APaginationItem> list = new ArrayList<>();
            JsonArray data = Tracker.getInstance().getApi().fetchMatches(args[0], args[1], args[2]).getAsJsonArray("data");
            JsonObject account = Tracker.getInstance().getApi().fetchAccount(args[0], args[1]);
            for (int i = 0; i < data.size(); i++) {
                boolean MVP_Red = true;
                boolean MVP_Blue = true;

                map = data.get(i).getAsJsonObject().get("metadata").getAsJsonObject().get("map").getAsString();
                game_start_patched = data.get(i).getAsJsonObject().get("metadata").getAsJsonObject().get("game_start_patched").getAsString();
                game_length = data.get(i).getAsJsonObject().get("metadata").getAsJsonObject().get("game_length").getAsInt() / 60000;
                mode = data.get(i).getAsJsonObject().get("metadata").getAsJsonObject().get("mode").getAsString();

                redPlayers = new StringBuilder();
                redResult = new StringBuilder();
                bluePlayers = new StringBuilder();
                blueResult = new StringBuilder();


                if (data.get(i).getAsJsonObject().get("teams").getAsJsonObject().get("red").getAsJsonObject().get("has_won").getAsBoolean()) {
                    redResult.append("Victory (").append(data.get(i).getAsJsonObject().get("teams").getAsJsonObject().get("red").getAsJsonObject().get("rounds_won")).append(" - ").append(data.get(i).getAsJsonObject().get("teams").getAsJsonObject().get("red").getAsJsonObject().get("rounds_lost")).append(")");
                } else {
                    redResult.append("Defeat (").append(data.get(i).getAsJsonObject().get("teams").getAsJsonObject().get("red").getAsJsonObject().get("rounds_won")).append(" - ").append(data.get(i).getAsJsonObject().get("teams").getAsJsonObject().get("red").getAsJsonObject().get("rounds_lost")).append(")");
                }
                if (data.get(i).getAsJsonObject().get("teams").getAsJsonObject().get("blue").getAsJsonObject().get("has_won").getAsBoolean()) {
                    blueResult.append("Victory (").append(data.get(i).getAsJsonObject().get("teams").getAsJsonObject().get("blue").getAsJsonObject().get("rounds_won")).append(" - ").append(data.get(i).getAsJsonObject().get("teams").getAsJsonObject().get("blue").getAsJsonObject().get("rounds_lost")).append(")");
                } else {
                    blueResult.append("Defeat (").append(data.get(i).getAsJsonObject().get("teams").getAsJsonObject().get("blue").getAsJsonObject().get("rounds_won")).append(" - ").append(data.get(i).getAsJsonObject().get("teams").getAsJsonObject().get("blue").getAsJsonObject().get("rounds_lost")).append(")");
                }

                JsonArray all_players = data.get(i).getAsJsonObject().get("players").getAsJsonObject().getAsJsonArray("all_players");
                List<PlayerInfo> redList = new ArrayList<>();
                List<PlayerInfo> blueList = new ArrayList<>();
                for (int j = 0; j < all_players.size(); j++) {
                    String name = all_players.get(j).getAsJsonObject().get("name").getAsString() + "#" + all_players.get(j).getAsJsonObject().get("tag").getAsString();
                    String characterEmoji = Tracker.getInstance().getWrapper().getAgentEmoji(all_players.get(j).getAsJsonObject().get("character").getAsString());
                    String rank = Tracker.getInstance().getWrapper().getRankEmoji(all_players.get(j).getAsJsonObject().get("currenttier_patched").getAsString());
                    int score = all_players.get(j).getAsJsonObject().get("stats").getAsJsonObject().get("score").getAsInt();
                    int kills = all_players.get(j).getAsJsonObject().get("stats").getAsJsonObject().get("kills").getAsInt();
                    int deaths = all_players.get(j).getAsJsonObject().get("stats").getAsJsonObject().get("deaths").getAsInt();
                    int assists = all_players.get(j).getAsJsonObject().get("stats").getAsJsonObject().get("assists").getAsInt();

                    if (account.get("puuid").getAsString().contains(all_players.get(j).getAsJsonObject().get("puuid").getAsString())) {
                        name = "**" + all_players.get(j).getAsJsonObject().get("name").getAsString() + "**" + "#" + all_players.get(j).getAsJsonObject().get("tag").getAsString();
                        character = all_players.get(j).getAsJsonObject().get("character").getAsString();
                    }

                    boolean isRed = all_players.get(j).getAsJsonObject().get("team").getAsString().contains("Red");
                    if (isRed) {
                        redList.add(new PlayerInfo(name, characterEmoji, "Red", rank, score, kills, deaths, assists));
                    } else {
                        blueList.add(new PlayerInfo(name, characterEmoji, "Blue", rank, score, kills, deaths, assists));
                    }
                }

                redList.sort(Comparator.comparingInt(o -> o.score));
                blueList.sort(Comparator.comparingInt(o -> o.score));
                Collections.reverse(redList);
                Collections.reverse(blueList);

                int redMax = Collections.max(redList, Comparator.comparingInt(o -> o.score)).score;
                int blueMax = Collections.max(blueList, Comparator.comparingInt(o -> o.score)).score;

                boolean isMatchMVP = redMax > blueMax;

                for (PlayerInfo info : redList) {
                    if (MVP_Red) {
                        redPlayers.append(info.character).append(info.name).append(" [").append(info.kills).append("/").append(info.deaths).append("/").append(info.assists).append("] ").append(info.rank).append((isMatchMVP ? " <:matchmvp:873492907892572170>" : " <:teammvp:873489187062562826>")).append("\n");
                    } else {
                        redPlayers.append(info.character).append(info.name).append(" [").append(info.kills).append("/").append(info.deaths).append("/").append(info.assists).append("] ").append(info.rank).append("\n");
                    }
                    MVP_Red = false;
                }

                for (PlayerInfo info : blueList) {
                    if (MVP_Blue) {
                        bluePlayers.append(info.character).append(info.name).append(" [").append(info.kills).append("/").append(info.deaths).append("/").append(info.assists).append("] ").append(info.rank).append(isMatchMVP ? " <:teammvp:873489187062562826>" : " <:matchmvp:873492907892572170>").append("\n");
                    } else {
                        bluePlayers.append(info.character).append(info.name).append(" [").append(info.kills).append("/").append(info.deaths).append("/").append(info.assists).append("] ").append(info.rank).append("\n");
                    }
                    MVP_Blue = false;
                }

                APaginationItem item = new APaginationItem(map, character, game_start_patched, game_length, mode, redPlayers.toString(), redResult.toString(), bluePlayers.toString(), blueResult.toString());
                list.add(item);
            }
            new Paginate<>(list).paginate(event, new PaginateSimpleEvent<APaginationItem>() {

                private EmbedBuilder embed(APaginationItem currentItem, int arrow, int maximum) {
                    return new EmbedBuilder().setAuthor("Matches [" + (arrow + 1) + "/" + maximum + "] \n" + args[0] + "#" + args[1]).setDescription("**" + currentItem.map + "** (" + currentItem.game_length + " mins) \n" + currentItem.mode).addField("Team RED: " + currentItem.redResult, currentItem.redPlayers).addField("Team BLUE: " + currentItem.blueResult, currentItem.bluePlayers).setImage(Tracker.getInstance().getWrapper().getMapImage(currentItem.map)).setThumbnail(String.valueOf(user.getAvatar().getUrl())).setFooter(currentItem.game_start_patched + " | " + user.getName() + "#" + user.getDiscriminator()).setColor(Color.black);
                }

                @Override
                public MessageBuilder onInit(MessageCreateEvent event, APaginationItem currentItem, int arrow, Paginator<APaginationItem> paginator) {
                    return new MessageBuilder().setEmbed(embed(currentItem, arrow, paginator.size()));

                }

                @Override
                public void onPaginate(MessageCreateEvent event, Message paginateMessage, APaginationItem currentItem, int arrow, Paginator<APaginationItem> paginator) {
                    paginateMessage.edit(embed(currentItem, arrow, paginator.size()));

                }

                @Override
                public MessageBuilder onEmptyPaginator(MessageCreateEvent event) {
                    return new MessageBuilder().setContent("There are currently no items!");

                }
            }, Duration.ofMinutes(5));
        } else {
            message.reply(new EmbedBuilder().setAuthor("Usage error!").setDescription("matches <gameName> <tagLine> <gameMode>").addField("available gamemodes:", "competitive, spikerush, unrated"));
        }
    }

    static class APaginationItem {

        public String map;
        public String character;
        public String game_start_patched;
        public int game_length;
        public String mode;
        public String redPlayers;
        public String redResult;
        public String bluePlayers;
        public String blueResult;

        public APaginationItem(String map, String character, String game_start_patched, int game_length, String mode, String redPlayers, String redResult, String bluePlayers, String blueResult) {
            this.map = map;
            this.character = character;
            this.game_start_patched = game_start_patched;
            this.game_length = game_length;
            this.mode = mode;
            this.redPlayers = redPlayers;
            this.redResult = redResult;
            this.bluePlayers = bluePlayers;
            this.blueResult = blueResult;
        }
    }

    static class PlayerInfo {

        public String name;
        public String character;
        public String team;
        public String rank;
        public int score;
        public int kills;
        public int deaths;
        public int assists;

        public PlayerInfo(String name, String character, String team, String rank, int score, int kills, int deaths, int assist) {
            this.name = name;
            this.character = character;
            this.team = team;
            this.rank = rank;
            this.score = score;
            this.kills = kills;
            this.deaths = deaths;
            this.assists = assist;
        }
    }
}
