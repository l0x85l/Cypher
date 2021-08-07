package me.viserys.valorant;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

public class Wrapper {

    public final JsonParser jsonParser = new JsonParser();


    public JsonElement get(String url) throws IOException {
        HttpGet httpGet = new HttpGet(url);
        SSLContext sslContext = null;
        try {
            sslContext = SSLContexts.custom().loadTrustMaterial(new TrustSelfSignedStrategy()).build();
        } catch (NoSuchAlgorithmException | KeyManagementException | KeyStoreException e) {
            e.printStackTrace();
        }
        SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(Objects.requireNonNull(sslContext));
        Registry<ConnectionSocketFactory> reg = RegistryBuilder.<ConnectionSocketFactory>create().register("https", socketFactory).build();
        HttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(reg);
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm).build();


        CloseableHttpResponse response = httpClient.execute(httpGet);
        String responseJSON = EntityUtils.toString(response.getEntity());
        return this.jsonParser.parse(responseJSON);
    }

    public String getMapImage(String map) {
        String image = "";
        switch (map) {
            case "Ascent":
                image = "https://static.wikia.nocookie.net/valorant/images/e/e7/Loading_Screen_Ascent.png/revision/latest?cb=20200607180020";
                break;
            case "Haven":
                image = "https://static.wikia.nocookie.net/valorant/images/7/70/Loading_Screen_Haven.png/revision/latest/scale-to-width-down/1000?cb=20200620202335";
                break;
            case "Bind":
                image = "https://static.wikia.nocookie.net/valorant/images/2/23/Loading_Screen_Bind.png/revision/latest/scale-to-width-down/1000?cb=20200620202316";
                break;
            case "Split":
                image = "https://static.wikia.nocookie.net/valorant/images/d/d6/Loading_Screen_Split.png/revision/latest/scale-to-width-down/1000?cb=20200620202349";
                break;
            case "Icebox":
                image = "https://static.wikia.nocookie.net/valorant/images/3/34/Loading_Icebox.png/revision/latest/scale-to-width-down/1000?cb=20201015084446";
                break;
            case "Breeze":
                image = "https://static.wikia.nocookie.net/valorant/images/1/1e/Valorant_Loading_Breeze.png/revision/latest/scale-to-width-down/1000?cb=20210427160616";
                break;
        }
        return image;
    }


    public String getRankImage(String name) {
        String image;
        switch (name) {
            case "Unrated":
                image = "https://upload.kabasakalonline.com/kabasakal/ilan/2021-04/unranked-hesap-717620-2021-04-24-18-1.png";
                break;
            case "Iron 1":
                image = "https://static.wikia.nocookie.net/valorant/images/7/7f/TX_CompetitiveTier_Large_3.png/revision/latest/scale-to-width-down/256?cb=20200623203005";
                break;
            case "Iron 2":
                image = "https://static.wikia.nocookie.net/valorant/images/2/28/TX_CompetitiveTier_Large_4.png/revision/latest/scale-to-width-down/256?cb=20200623203053";
                break;
            case "Iron 3":
                image = "https://static.wikia.nocookie.net/valorant/images/b/b8/TX_CompetitiveTier_Large_5.png/revision/latest/scale-to-width-down/256?cb=20200623203101";
                break;
            case "Bronze 1":
                image = "https://static.wikia.nocookie.net/valorant/images/a/a2/TX_CompetitiveTier_Large_6.png/revision/latest/scale-to-width-down/250?cb=20200623203119";
                break;
            case "Bronze 2":
                image = "https://static.wikia.nocookie.net/valorant/images/e/e7/TX_CompetitiveTier_Large_7.png/revision/latest/scale-to-width-down/256?cb=20200623203140";
                break;
            case "Bronze 3":
                image = "https://static.wikia.nocookie.net/valorant/images/a/a8/TX_CompetitiveTier_Large_8.png/revision/latest/scale-to-width-down/256?cb=20200623203313";
                break;
            case "Silver 1":
                image = "https://static.wikia.nocookie.net/valorant/images/0/09/TX_CompetitiveTier_Large_9.png/revision/latest/scale-to-width-down/250?cb=20200623203408";
                break;
            case "Silver 2":
                image = "https://static.wikia.nocookie.net/valorant/images/c/ca/TX_CompetitiveTier_Large_10.png/revision/latest/scale-to-width-down/250?cb=20200623203410";
                break;
            case "Silver 3":
                image = "https://static.wikia.nocookie.net/valorant/images/1/1e/TX_CompetitiveTier_Large_11.png/revision/latest/scale-to-width-down/256?cb=20200623203413";
                break;
            case "Gold 1":
                image = "https://static.wikia.nocookie.net/valorant/images/9/91/TX_CompetitiveTier_Large_12.png/revision/latest/scale-to-width-down/250?cb=20200623203413";
                break;
            case "Gold 2":
                image = "https://static.wikia.nocookie.net/valorant/images/4/45/TX_CompetitiveTier_Large_13.png/revision/latest/scale-to-width-down/250?cb=20200623203415";
                break;
            case "Gold 3":
                image = "https://static.wikia.nocookie.net/valorant/images/c/c0/TX_CompetitiveTier_Large_14.png/revision/latest/scale-to-width-down/256?cb=20200623203417";
                break;
            case "Platinum 1":
                image = "https://static.wikia.nocookie.net/valorant/images/d/d3/TX_CompetitiveTier_Large_15.png/revision/latest/scale-to-width-down/256?cb=20200623203419";
                break;
            case "Platinum 2":
                image = "https://static.wikia.nocookie.net/valorant/images/a/a5/TX_CompetitiveTier_Large_16.png/revision/latest/scale-to-width-down/256?cb=20200623203606";
                break;
            case "Platinum 3":
                image = "https://static.wikia.nocookie.net/valorant/images/f/f2/TX_CompetitiveTier_Large_17.png/revision/latest/scale-to-width-down/256?cb=20200623203607";
                break;
            case "Diamond 1":
                image = "https://static.wikia.nocookie.net/valorant/images/b/b7/TX_CompetitiveTier_Large_18.png/revision/latest/scale-to-width-down/256?cb=20200623203609";
                break;
            case "Diamond 2":
                image = "https://static.wikia.nocookie.net/valorant/images/3/32/TX_CompetitiveTier_Large_19.png/revision/latest/scale-to-width-down/256?cb=20200623203610";
                break;
            case "Diamond 3":
                image = "https://static.wikia.nocookie.net/valorant/images/1/11/TX_CompetitiveTier_Large_20.png/revision/latest/scale-to-width-down/256?cb=20200623203611";
                break;
            case "Immortal":
                image = "https://static.wikia.nocookie.net/valorant/images/f/f9/TX_CompetitiveTier_Large_23.png/revision/latest/scale-to-width-down/275?cb=20200623203617";
                break;
            case "Radiant":
                image = "https://static.wikia.nocookie.net/valorant/images/2/24/TX_CompetitiveTier_Large_24.png/revision/latest/scale-to-width-down/256?cb=20200623203621";
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + name);
        }
        return image;
    }

    public String getRankEmoji(String name) {
        String emoji;
        switch (name) {
            case "Unrated":
                emoji = "<:unrated:869675118308262040>";
                break;
            case "Iron 1":
                emoji = "<:iron:869675118274674739>";
                break;
            case "Iron 2":
                emoji = "<:iron_2:869675118824140811>";
                break;
            case "Iron 3":
                emoji = "<:iron_3:869675119025479740>";
                break;
            case "Bronze 1":
                emoji = "<:bronze:869675560757002241>";
                break;
            case "Bronze 2":
                emoji = "<:bronze_2:869675562354999296>";
                break;
            case "Bronze 3":
                emoji = "<:bronze_3:869675562652799006>";
                break;
            case "Silver 1":
                emoji = "<:silver:869675562204012584>";
                break;
            case "Silver 2":
                emoji = "<:silver_2:869675562740903957>";
                break;
            case "Silver 3":
                emoji = "<:silver_3:869675562904457226>";
                break;
            case "Gold 1":
                emoji = "<:gold_1:869675648933834902>";
                break;
            case "Gold 2":
                emoji = "<:gold_2:869675647834914836>";
                break;
            case "Gold 3":
                emoji = "<:gold_3:869675648946409512>";
                break;
            case "Platinum 1":
                emoji = "<:plat_1:869675648824770600>";
                break;
            case "Platinum 2":
                emoji = "<:plat_2:869675648887689247>";
                break;
            case "Platinum 3":
                emoji = "<:plat_3:869675649181315142>";
                break;
            case "Diamond 1":
                emoji = "<:diamond_1:869675410844160031>";
                break;
            case "Diamond 2":
                emoji = "<:diamond_2:869675411435585536>";
                break;
            case "Diamond 3":
                emoji = "<:diamond_3:869675410835795988>";
                break;
            case "Immortal":
                emoji = "<:immortal:869675818593435700>";
                break;
            case "Radiant":
                emoji = "<:radiant:869675819126128660>";
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + name);
        }
        return emoji;
    }

    public String getAgentEmoji(String name) {
        String emoji;
        switch (name) {
            case "Astra":
                emoji = "<:astra:871372532278767619>";
                break;
            case "Breach":
                emoji = "<:breach:871372537517449227>";
                break;
            case "Brimstone":
                emoji = "<:brimstone:871372537588772874>";
                break;
            case "Cypher":
                emoji = "<:cypher:871372537580367923>";
                break;
            case "Jett":
                emoji = "<:jett:871372537643298847>";
                break;
            case "Omen":
                emoji = "<:omen:871372539153223741>";
                break;
            case "Phoenix":
                emoji = "<:phoenix:871372538331160636>";
                break;
            case "Raze":
                emoji = "<:raze:871372539715276810>";
                break;
            case "Reyna":
                emoji = "<:reyna:871372538570231808>";
                break;
            case "Sage":
                emoji = "<:sage:871372539669143562>";
                break;
            case "Skye":
                emoji = "<:skye:871372539232935959>";
                break;
            case "Sova":
                emoji = "<:sova:871372539597844520>";
                break;
            case "Viper":
                emoji = "<:viper:871372539648159744>";
                break;
            case "Yoru":
                emoji = "<:yoru:871372539346178051>";
                break;
            case "KAY/O":
                emoji = "<:kayo:871372539341987881>";
                break;
            case "Killjoy":
                emoji = "<:killjoy:871372539677532200>";
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + name);
        }
        return emoji;
    }
}
