package accountHelpers;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.time.Instant;
import java.util.Base64;
import java.util.Random;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.HttpsURLConnection;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import org.apache.taglibs.standard.tag.common.core.Util;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Kevin
 */
public class TwitterHelper {

    private final String OAUTH_CONSUMER_KEY = "giMrHgUN8BMB3DubThdlrSBZx";
    private final String OAUTH_SIGNATURE_METHOD = "HMAC-SHA1";
    private final String OAUTH_VERSION = "1.0";
    private final String OAUTH_CONSUMER_SECRET = "tS4r7Bw4XvcS2heRooe3ZPAuwENq9s240LK84xMb016ydsUstA";
    private String oauth_nonce;
    private String oauth_timestamp;
    private String oauth_callback;
    private ServletContext context;
    private HttpServletRequest request;

    public TwitterHelper(ServletContext context, HttpServletRequest request) {
        this.context = context;
        this.request = request;
    }

    private void setNonce() {
        String alphabet
                = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM1234567890";
        int n = alphabet.length();
        Random ran = new Random();
        oauth_nonce = "";
        for (int a = 0; a < 32; a++) {
            oauth_nonce += alphabet.charAt(ran.nextInt(n));
        }
    }

    public String getNonce() {
        return oauth_nonce;
    }

    private void setTimestamp() {
        oauth_timestamp = String.valueOf(Instant.now().getEpochSecond());
    }

    public String getTimestamp() {
        return oauth_timestamp;
    }

    private void setCallback(String oauth_callback) {
        this.oauth_callback = percentEncode(oauth_callback);
    }

    public String getCallback() {
        return oauth_callback;
    }

    public String createBaseString(String oauth_token, String httpMethod, String baseURL, String[][] parameters) {
        String[][] params;
        if (parameters == null) {
            return "oauth_token=\""
                    + percentEncode(oauth_token)
                    + "\"";
        }
        if (oauth_token != null) {
            params = new String[parameters.length + 1][];
            for (int a = 0; a < parameters.length; a++) {
                params[a] = parameters[a];
            }
            params[parameters.length] = new String[]{"oauth_token", oauth_token};
        } else {
            params = parameters;
        }

        for (int a = 0; a < params.length; a++) {
            params[a][0] = percentEncode(params[a][0]);
            params[a][1] = percentEncode(params[a][1]);
        }

        if (params.length > 1) {
            java.util.Arrays.sort(params, new java.util.Comparator<String[]>() {
                @Override
                public int compare(String[] a, String[] b) {
                    return a[0].compareTo(b[0]);
                }
            });
        }

        String parameterString = "";
        if (params.length > 0) {
            parameterString += (params[0][0]
                    + "="
                    + params[0][1]);
            for (int a = 1; a < params.length; a++) {
                parameterString += ("&"
                        + params[a][0]
                        + "="
                        + params[a][1]);
            }
        }

        String sigBaseString = (httpMethod.toUpperCase()
                + "&"
                + percentEncode(baseURL)
                + "&"
                + percentEncode(parameterString));

        return sigBaseString;
    }

    public String createSignature(String oauth_token, String oauth_token_secret,
            String httpMethod, String baseURL, String[][] parameters) {

        String sigBaseString = createBaseString(oauth_token, httpMethod, baseURL, parameters);

        String key = percentEncode(OAUTH_CONSUMER_SECRET)
                + "&";
        if (oauth_token_secret != null) {
            key += percentEncode(oauth_token_secret);
        }

        try {
            SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(), "HmacSHA1");
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(signingKey);
            byte[] result = mac.doFinal(sigBaseString.getBytes());
            return Base64.getEncoder().encodeToString(result);
        } catch (Exception e) {

        }

        return "";
    }

    public String createHeader(String oauth_token, String oauth_token_secret,
            String httpMethod, String baseURL, String[][] parameters) {

        String header = "OAuth ";
        header += "oauth_consumer_key=\"";
        header += OAUTH_CONSUMER_KEY;
        header += "\", oauth_nonce=\"";
        header += getNonce();
        header += "\", oauth_signature=\"";
        header += percentEncode(createSignature(oauth_token, oauth_token_secret,
                httpMethod, baseURL, parameters));
        header += "\", oauth_signature_method=\"";
        header += OAUTH_SIGNATURE_METHOD;
        header += "\", oauth_timestamp=\"";
        header += getTimestamp();
        if (oauth_token != null) {
            header += "\", oauth_token=\"";
            header += percentEncode(oauth_token);
        }
        header += "\", oauth_version=\"";
        header += OAUTH_VERSION;
        header += "\"";

        return header;
    }

    public String requestToken() {
        setNonce();
        setTimestamp();
//        setCallback("http://localhost:8080/SocialSync/SocialMediaAccounts");
        String[][] parameters = new String[][]{
            //            new String[]{"oauth_callback", "http://webpages.uncc.edu/kcalhou6/"},
            new String[]{"oauth_consumer_key", OAUTH_CONSUMER_KEY},
            new String[]{"oauth_nonce", getNonce()},
            new String[]{"oauth_signature_method", OAUTH_SIGNATURE_METHOD},
            new String[]{"oauth_timestamp", getTimestamp()},
            new String[]{"oauth_version", OAUTH_VERSION}
        };

        context.log("*** " + request.getRequestURL().toString());

        return executeRequest("https://api.twitter.com/oauth/request_token",
                "POST",
                createHeader(null, null, "POST",
                        "https://api.twitter.com/oauth/request_token",
                        parameters));
    }

    public String searchTweets(String since_id, String max_id, String oauth_token, String oauth_token_secret) {
        String[][] parameters = new String[][]{
            new String[]{"oauth_consumer_key", OAUTH_CONSUMER_KEY},
            new String[]{"oauth_nonce", getNonce()},
            new String[]{"oauth_signature_method", OAUTH_SIGNATURE_METHOD},
            new String[]{"oauth_timestamp", getTimestamp()},
            new String[]{"oauth_version", OAUTH_VERSION},
            new String[]{"q", "23freebandnames"},
            new String[]{"since_id", String.valueOf(since_id)},
            new String[]{"max_id", String.valueOf(max_id)},
            new String[]{"result_type", "mixed"},
            new String[]{"count", "4"}
        };

        String header = createHeader(oauth_token, oauth_token_secret, "GET",
                "https://api.twitter.com/1.1/search/tweets.json",
                parameters);
        String correctHeader = "OAuth oauth_consumer_key=\"giMrHgUN8BMB3DubThdlrSBZx\", oauth_nonce=\"06c1fa33d2b6a71e9713f6cf533037ac\", oauth_signature=\"kQ6bVoQgoyGhzgDnBDckaCyeZVE%3D\", oauth_signature_method=\"HMAC-SHA1\", oauth_timestamp=\"1430358483\", oauth_token=\"409671757-dKrWBSvq0vQ1WrFEUGw69BkuRh2QLc23pkkjKVix\", oauth_version=\"1.0\"";
        context.log("*** " + header.equals(correctHeader));

        return executeRequest("https://api.twitter.com/1.1/search/tweets.json",
                "GET",
                header);
    }

    public String getTweets(String url, String oauth_access_token, int count, String screen_name) {
        URL twitterURL;
        HttpsURLConnection con = null;

        try {
            if (url == null) {
                twitterURL = new URL("https://api.twitter.com/1.1/search/tweets.json?q=software&count=10&result_type=popular");
            } else {
                if (url.equals("https://api.twitter.com/1.1/statuses/user_timeline.json")) {
                    twitterURL = new URL((url + "?count=" + count + "&screen_name="
                            + screen_name));
                } else {
                    twitterURL = new URL(url + "&count=" + count);
                }
            }

            con = (HttpsURLConnection) twitterURL.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Host", "api.twitter.com");
            con.setRequestProperty("User-Agent", "SocialSync_SSDI");
            con.setRequestProperty("Authorization", "Bearer " + oauth_access_token);
            
            if (con.getResponseCode() == 200) {
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
            } else {
                context.log("*** response code: " + con.getResponseCode());
                context.log("*** response message: " + con.getResponseMessage());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            if (con != null) {
                con.disconnect();
            }
        }

        return null;
    }

    //Adapted from method in http://oauth.googlecode.com/svn/code/java/core/commons/src/main/java/net/oauth/OAuth.java
    public String percentEncode(String str) {
        if (str == null) {
            return "";
        }
        try {
            return URLEncoder.encode(str, "UTF-8")
                    .replace("+", "%20")
                    .replace("*", "%2A")
                    .replace("%7E", "~");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public String executeRequest(String url, String urlMethod, String header) {

        URL twitterURL;
        HttpsURLConnection con = null;
        try {
            twitterURL = new URL(url);
            con = (HttpsURLConnection) twitterURL.openConnection();
            con.setRequestMethod(urlMethod);
            if (urlMethod.equals("POST")) {
                con.setRequestProperty("Accept", "*/*");
            }
            con.setRequestProperty("Authorization", header);

            InputStream iS = con.getInputStream();
            BufferedReader bR = new BufferedReader(new InputStreamReader(iS));
            String lN;
            StringBuffer result = new StringBuffer();
            while ((lN = bR.readLine()) != null) {
                result.append(lN);
            }

            bR.close();

            String res = result.toString();

            if (res.substring(res.lastIndexOf("=") + 1)
                    .equals("true")) {
                return res;
            }

        } catch (Exception ex) {
            context.log("*** " + ex.getMessage());
        } finally {
            if (con != null) {
                con.disconnect();
            }
        }
        return "";
    }

    private String encodeConsumerKeyAndSecret() {
        String url = Util.URLEncode(OAUTH_CONSUMER_KEY, "UTF-8")
                + ":"
                + Util.URLEncode(OAUTH_CONSUMER_SECRET, "UTF-8");
        return Base64.getEncoder().encodeToString(url.getBytes());
    }

    public String obtainBearerToken() {
        URL twitterURL;
        HttpsURLConnection con = null;
        try {
            twitterURL = new URL("https://api.twitter.com/oauth2/token");
            con = (HttpsURLConnection) twitterURL.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Host", "api.twitter.com");
            con.setRequestProperty("User-Agent", "SocialSync_SSDI");
            con.setRequestProperty("Authorization", "Basic " + encodeConsumerKeyAndSecret());
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");

            con.setDoOutput(true);

            OutputStream oS = con.getOutputStream();
            oS.write("grant_type=client_credentials".getBytes());
            oS.flush();
            oS.close();

            if (con.getResponseCode() == 200) {
                BufferedReader bR = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));
                String lN;
                StringBuffer result = new StringBuffer();
                while ((lN = bR.readLine()) != null) {
                    result.append(lN);
                }
                bR.close();

                JSONObject jObj = parseTwitterResponse(result);
                return ((String) jObj.get("access_token"));
            }
        } catch (Exception ex) {
            context.log("*** " + ex.getMessage());
        } finally {
            if (con != null) {
                con.disconnect();
            }
        }
        return null;
    }

    public JSONObject parseTwitterResponse(StringBuffer response) throws ParseException {
        JSONParser parser = new JSONParser();
        return ((JSONObject) parser.parse(response.toString()));
    }

    public boolean invalidateToken(String token, HttpServletRequest request) {
        URL twitterURL;
        HttpsURLConnection con = null;
        boolean result = false;
        try {

            twitterURL = new URL("https://api.twitter.com/oauth2/invalidate_token");
            con = (HttpsURLConnection) twitterURL.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Authorization", "Basic " + encodeConsumerKeyAndSecret());
            con.setRequestProperty("User-Agent", "SocialSync_SSDI");
            con.setRequestProperty("Host", "api.twitter.com");
            con.setRequestProperty("Accept", "*/*");
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            con.setDoOutput(true);

            try (OutputStream oS = con.getOutputStream()) {
                oS.write(("access_token=" + token).getBytes());
                oS.flush();
            }

            if (con.getResponseCode() == 200) {
                result = true;
            } else {
                context.log("*** response code: " + con.getResponseCode());
                context.log("*** response message: " + con.getResponseMessage());
            }
        } catch (Exception ex) {
            context.log("*** " + ex.getMessage());
        } finally {
            if (con != null) {
                con.disconnect();
            }
            return result;
        }
    }
}
