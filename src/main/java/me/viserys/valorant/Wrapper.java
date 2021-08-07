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
        if (map.equalsIgnoreCase("Ascent")) {
            image = "https://static.wikia.nocookie.net/valorant/images/e/e7/Loading_Screen_Ascent.png/revision/latest?cb=20200607180020";
        } else if (map.equalsIgnoreCase("Haven")) {
            image = "https://static.wikia.nocookie.net/valorant/images/7/70/Loading_Screen_Haven.png/revision/latest/scale-to-width-down/1000?cb=20200620202335";
        } else if (map.equalsIgnoreCase("Bind")) {
            image = "https://static.wikia.nocookie.net/valorant/images/2/23/Loading_Screen_Bind.png/revision/latest/scale-to-width-down/1000?cb=20200620202316";
        } else if (map.equalsIgnoreCase("Split")) {
            image = "https://static.wikia.nocookie.net/valorant/images/d/d6/Loading_Screen_Split.png/revision/latest/scale-to-width-down/1000?cb=20200620202349";
        } else if (map.equalsIgnoreCase("Icebox")) {
            image = "https://static.wikia.nocookie.net/valorant/images/3/34/Loading_Icebox.png/revision/latest/scale-to-width-down/1000?cb=20201015084446";
        } else if (map.equalsIgnoreCase("Breeze")) {
            image = "https://static.wikia.nocookie.net/valorant/images/1/1e/Valorant_Loading_Breeze.png/revision/latest/scale-to-width-down/1000?cb=20210427160616";
        }
        return image;
    }


    public String getRankImage(String name) {
        String image = "";
        if (name.equalsIgnoreCase("Unrated")) {
            image = "https://upload.kabasakalonline.com/kabasakal/ilan/2021-04/unranked-hesap-717620-2021-04-24-18-1.png";
        } else if (name.equalsIgnoreCase("Iron 1")) {
            image = "https://static.wikia.nocookie.net/valorant/images/7/7f/TX_CompetitiveTier_Large_3.png/revision/latest/scale-to-width-down/256?cb=20200623203005";
        } else if (name.equalsIgnoreCase("Iron 2")) {
            image = "https://static.wikia.nocookie.net/valorant/images/2/28/TX_CompetitiveTier_Large_4.png/revision/latest/scale-to-width-down/256?cb=20200623203053";
        } else if (name.equalsIgnoreCase("Iron 3")) {
            image = "https://static.wikia.nocookie.net/valorant/images/b/b8/TX_CompetitiveTier_Large_5.png/revision/latest/scale-to-width-down/256?cb=20200623203101";
        } else if (name.equalsIgnoreCase("Bronze 1")) {
            image = "https://static.wikia.nocookie.net/valorant/images/a/a2/TX_CompetitiveTier_Large_6.png/revision/latest/scale-to-width-down/250?cb=20200623203119";
        } else if (name.equalsIgnoreCase("Bronze 2")) {
            image = "https://static.wikia.nocookie.net/valorant/images/e/e7/TX_CompetitiveTier_Large_7.png/revision/latest/scale-to-width-down/256?cb=20200623203140";
        } else if (name.equalsIgnoreCase("Bronze 3")) {
            image = "https://static.wikia.nocookie.net/valorant/images/a/a8/TX_CompetitiveTier_Large_8.png/revision/latest/scale-to-width-down/256?cb=20200623203313";
        } else if (name.equalsIgnoreCase("Silver 1")) {
            image = "https://static.wikia.nocookie.net/valorant/images/0/09/TX_CompetitiveTier_Large_9.png/revision/latest/scale-to-width-down/250?cb=20200623203408";
        } else if (name.equalsIgnoreCase("Silver 2")) {
            image = "https://static.wikia.nocookie.net/valorant/images/c/ca/TX_CompetitiveTier_Large_10.png/revision/latest/scale-to-width-down/250?cb=20200623203410";
        } else if (name.equalsIgnoreCase("Silver 3")) {
            image = "https://static.wikia.nocookie.net/valorant/images/1/1e/TX_CompetitiveTier_Large_11.png/revision/latest/scale-to-width-down/256?cb=20200623203413";
        } else if (name.equalsIgnoreCase("Gold 1")) {
            image = "https://static.wikia.nocookie.net/valorant/images/9/91/TX_CompetitiveTier_Large_12.png/revision/latest/scale-to-width-down/250?cb=20200623203413";
        } else if (name.equalsIgnoreCase("Gold 2")) {
            image = "https://static.wikia.nocookie.net/valorant/images/4/45/TX_CompetitiveTier_Large_13.png/revision/latest/scale-to-width-down/250?cb=20200623203415";
        } else if (name.equalsIgnoreCase("Gold 3")) {
            image = "https://static.wikia.nocookie.net/valorant/images/c/c0/TX_CompetitiveTier_Large_14.png/revision/latest/scale-to-width-down/256?cb=20200623203417";
        } else if (name.equalsIgnoreCase("Platinum 1")) {
            image = "https://static.wikia.nocookie.net/valorant/images/d/d3/TX_CompetitiveTier_Large_15.png/revision/latest/scale-to-width-down/256?cb=20200623203419";
        }  else if (name.equalsIgnoreCase("Platinum 2")) {
            image = "https://static.wikia.nocookie.net/valorant/images/a/a5/TX_CompetitiveTier_Large_16.png/revision/latest/scale-to-width-down/256?cb=20200623203606";
        } else if (name.equalsIgnoreCase("Platinum 3")) {
            image = "https://static.wikia.nocookie.net/valorant/images/f/f2/TX_CompetitiveTier_Large_17.png/revision/latest/scale-to-width-down/256?cb=20200623203607";
        } else if (name.equalsIgnoreCase("Diamond 1")) {
            image = "https://static.wikia.nocookie.net/valorant/images/b/b7/TX_CompetitiveTier_Large_18.png/revision/latest/scale-to-width-down/256?cb=20200623203609";
        } else if (name.equalsIgnoreCase("Diamond 2")) {
            image = "https://static.wikia.nocookie.net/valorant/images/3/32/TX_CompetitiveTier_Large_19.png/revision/latest/scale-to-width-down/256?cb=20200623203610";
        } else if (name.equalsIgnoreCase("Diamond 3")) {
            image = "https://static.wikia.nocookie.net/valorant/images/1/11/TX_CompetitiveTier_Large_20.png/revision/latest/scale-to-width-down/256?cb=20200623203611";
        } else if (name.equalsIgnoreCase("Immortal")) {
            image = "https://static.wikia.nocookie.net/valorant/images/f/f9/TX_CompetitiveTier_Large_23.png/revision/latest/scale-to-width-down/275?cb=20200623203617";
        } else if (name.equalsIgnoreCase("Radiant")) {
            image = "https://static.wikia.nocookie.net/valorant/images/2/24/TX_CompetitiveTier_Large_24.png/revision/latest/scale-to-width-down/256?cb=20200623203621";
        }
        return image;
    }

    public String getRankEmoji(String name) {
        String emoji = "";
        if (name.equalsIgnoreCase("Unrated")) {
            emoji = "<:unrated:869675118308262040>";
        } else if (name.equalsIgnoreCase("Iron 1")) {
            emoji = "<:iron:869675118274674739>";
        } else if (name.equalsIgnoreCase("Iron 2")) {
            emoji = "<:iron_2:869675118824140811>";
        } else if (name.equalsIgnoreCase("Iron 3")) {
            emoji = "<:iron_3:869675119025479740>";
        } else if (name.equalsIgnoreCase("Bronze 1")) {
            emoji = "<:bronze:869675560757002241>";
        } else if (name.equalsIgnoreCase("Bronze 2")) {
            emoji = "<:bronze_2:869675562354999296>";
        } else if (name.equalsIgnoreCase("Bronze 3")) {
            emoji = "<:bronze_3:869675562652799006>";
        } else if (name.equalsIgnoreCase("Silver 1")) {
            emoji = "<:silver:869675562204012584>";
        } else if (name.equalsIgnoreCase("Silver 2")) {
            emoji = "<:silver_2:869675562740903957>";
        } else if (name.equalsIgnoreCase("Silver 3")) {
            emoji = "<:silver_3:869675562904457226>";
        } else if (name.equalsIgnoreCase("Gold 1")) {
            emoji = "<:gold_1:869675648933834902>";
        } else if (name.equalsIgnoreCase("Gold 2")) {
            emoji = "<:gold_2:869675647834914836>";
        } else if (name.equalsIgnoreCase("Gold 3")) {
            emoji = "<:gold_3:869675648946409512>";
        } else if (name.equalsIgnoreCase("Platinum 1")) {
            emoji = "<:plat_1:869675648824770600>";
        }  else if (name.equalsIgnoreCase("Platinum 2")) {
            emoji = "<:plat_2:869675648887689247>";
        } else if (name.equalsIgnoreCase("Platinum 3")) {
            emoji = "<:plat_3:869675649181315142>";
        } else if (name.equalsIgnoreCase("Diamond 1")) {
            emoji = "<:diamond_1:869675410844160031>";
        } else if (name.equalsIgnoreCase("Diamond 2")) {
            emoji = "<:diamond_2:869675411435585536>";
        } else if (name.equalsIgnoreCase("Diamond 3")) {
            emoji = "<:diamond_3:869675410835795988>";
        } else if (name.equalsIgnoreCase("Immortal")) {
            emoji = "<:immortal:869675818593435700>";
        } else if (name.equalsIgnoreCase("Radiant")) {
            emoji = "<:radiant:869675819126128660>";
        }
        return emoji;
    }

    public String getAgentEmoji(String name) {
        String emoji = "";
        if (name.equalsIgnoreCase("Astra")) {
            emoji = "<:astra:871372532278767619>";
        } else if (name.equalsIgnoreCase("Breach")) {
            emoji = "<:breach:871372537517449227>";
        } else if (name.equalsIgnoreCase("Brimstone")) {
            emoji = "<:brimstone:871372537588772874>";
        } else if (name.equalsIgnoreCase("Cypher")) {
            emoji = "<:cypher:871372537580367923>";
        } else if (name.equalsIgnoreCase("Jett")) {
            emoji = "<:jett:871372537643298847>";
        } else if (name.equalsIgnoreCase("Omen")) {
            emoji = "<:omen:871372539153223741>";
        } else if (name.equalsIgnoreCase("Phoenix")) {
            emoji = "<:phoenix:871372538331160636>";
        } else if (name.equalsIgnoreCase("Raze")) {
            emoji = "<:raze:871372539715276810>";
        } else if (name.equalsIgnoreCase("Reyna")) {
            emoji = "<:reyna:871372538570231808>";
        } else if (name.equalsIgnoreCase("Sage")) {
            emoji = "<:sage:871372539669143562>";
        } else if (name.equalsIgnoreCase("Skye")) {
            emoji = "<:skye:871372539232935959>";
        } else if (name.equalsIgnoreCase("Sova")) {
            emoji = "<:sova:871372539597844520>";
        } else if (name.equalsIgnoreCase("Viper")) {
            emoji = "<:viper:871372539648159744>";
        } else if (name.equalsIgnoreCase("Yoru")) {
            emoji = "<:yoru:871372539346178051>";
        }  else if (name.equalsIgnoreCase("KAY/O")) {
            emoji = "<:kayo:871372539341987881>";
        }  else if (name.equalsIgnoreCase("Killjoy")) {
            emoji = "<:killjoy:871372539677532200>";
        }
        return emoji;
    }
}
