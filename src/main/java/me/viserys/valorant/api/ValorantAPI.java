package me.viserys.valorant.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import me.viserys.valorant.main.Tracker;

import java.io.IOException;
public class ValorantAPI {




    public JsonObject fetchMatches(String name, String tag, String mode) {
        try {
            return Tracker.getInstance().getWrapper().get("https://api.henrikdev.xyz/valorant/v3/matches/" + fetchAccount(name, tag).get("region").getAsString() + "/" + name + "/" + tag + "?filter=" + mode).getAsJsonObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public JsonObject fetchAccount(String name, String tag) {
        try {
            return Tracker.getInstance().getWrapper().get("https://api.henrikdev.xyz/valorant/v1/account/" + name + "/" + tag).getAsJsonObject().get("data").getAsJsonObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public JsonObject fetchMMR(String name, String tag) {
        try {
            return Tracker.getInstance().getWrapper().get("https://api.henrikdev.xyz/valorant/v1/mmr/" + fetchAccount(name, tag).get("region").getAsString() + "/" + name + "/" + tag).getAsJsonObject().get("data").getAsJsonObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public JsonArray fetchLeaderboard(String region) {
        try {
            return Tracker.getInstance().getWrapper().get("https://api.henrikdev.xyz/valorant/v1/leaderboard/" + region).getAsJsonArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public JsonObject fetchCard(String cardID) {
        try {
            return Tracker.getInstance().getWrapper().get("https://valorant-api.com/v1/playercards/" + cardID).getAsJsonObject().get("data").getAsJsonObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
