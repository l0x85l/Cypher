package me.viserys.valorant.main;

import lombok.Getter;
import me.viserys.valorant.Wrapper;
import me.viserys.valorant.api.ValorantAPI;
import me.viserys.valorant.command.commands.*;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.activity.ActivityType;
import org.javacord.api.entity.permission.Permissions;
import org.javacord.api.entity.user.UserStatus;
import pw.mihou.velen.interfaces.Velen;
import pw.mihou.velen.interfaces.VelenCommand;

import java.util.Base64;

public class Tracker {

    static Tracker INSTANCE;
    @Getter
    private ValorantAPI api;
    @Getter
    private Wrapper wrapper;
    @Getter
    private Velen velen;


    public static Tracker getInstance() {
        return INSTANCE == null ? (INSTANCE = new Tracker()) : INSTANCE;
    }


    public void initialize() {
        api = new ValorantAPI();
        wrapper = new Wrapper();
        velen = Velen.builder().setDefaultPrefix("!").build();
      
        DiscordApi discordApi = new DiscordApiBuilder().setToken("token").addListener(velen).login().join();
        VelenCommand.of("matches", "Gets the last 5 competitive matches.", velen, (new Matches())).setCategory("Ingame").attach();
        VelenCommand.of("mmr", "Shows the last mmr changes.", velen, (new MMR())).setCategory("Ingame").attach();
        VelenCommand.of("account", "Shows the last account info.", velen, (new Account())).setCategory("Ingame").attach();
        VelenCommand.of("leaderboard", "Gets the top 10 leaderboards.", velen, (new Leaderboard())).setCategory("Ingame").attach();
        VelenCommand.of("help", "List of all commands.", velen, (new Help())).attach();
        discordApi.updateStatus(UserStatus.IDLE);
        discordApi.updateActivity(ActivityType.WATCHING, discordApi.getServers().size() + " servers");
        System.out.println("bot was running on " + discordApi.getServers());
        System.out.println(discordApi.createBotInvite(Permissions.fromBitmask(8)));
    }

}
