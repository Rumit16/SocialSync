/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package accountHelpers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.Instant;
import javax.net.ssl.HttpsURLConnection;
import javax.servlet.ServletContext;

/**
 *
 * @author Kevin
 */
public class InstagramHelper {

    public String getInstagramPics(ServletContext context, String userId, int count) {
        HttpsURLConnection con = null;
        URL instagramURL;

        try {
            if (userId == null) {
                instagramURL = new URL("https://api.instagram.com/v1/media/popular?access_token=1999651154.c17e29d.2aa7bab26f624a9b96c4865fe71fd464");
            } else {
                instagramURL = new URL("https://api.instagram.com/v1/users/" 
                        + userId + "/media/recent/?access_token=1999651154.c17e29d.2aa7bab26f624a9b96c4865fe71fd464&count=" 
                        + count);
            } 
        /*
            else {
                instagramURL = new URL("https://api.instagram.com/v1/media/search?access_token=1999651154.c17e29d.2aa7bab26f624a9b96c4865fe71fd464&min_timestamp" 
                + (Instant.now().getEpochSecond() - 86400));
            }
        */
            con = (HttpsURLConnection) instagramURL.openConnection();
            con.setRequestMethod("GET");

            BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                line = br.readLine();
            }
            br.close();
            
            return sb.toString();
        } catch (Exception e) {
            context.log("*** exception: " + e.getMessage());
        } finally {
            if (con != null) {
                con.disconnect();
            }
        }
        return null;
    }
}
