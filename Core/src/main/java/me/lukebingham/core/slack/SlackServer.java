package me.lukebingham.core.slack;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import com.google.gson.JsonObject;

import me.lukebingham.core.util.C;
import me.lukebingham.core.util.Callback;
import me.lukebingham.core.util.ServerUtil;
import org.bukkit.Bukkit;

/**
 * Created by LukeBingham on 22/02/2017.
 */
public class SlackServer {

    /**
     * POST tags
     */
    private static final String POST = "POST";
    private static final String PAYLOAD = "payload=";
    private static final String UTF_8 = "UTF-8";

    /**
     * The API service url to call
     */
    private final String serviceURL;
    /**
     * Timeout request in msecs
     */
    private final int timeout;

    /**
     * Create a SlackServer with the specified API URL hook.
     *
     * @param serviceURL - the service URL for the API calls
     * @param timeout    - the timeout, in msecs, to try to send messages before
     *                   failing
     */
    public SlackServer(String serviceURL, int timeout) {
        this.serviceURL = serviceURL;
        this.timeout = timeout;
    }

    /**
     * Create a SlackServer with the specified API URL hook.
     * <p>
     * Note: The default timeout is used, which is 5000 msecs.
     *
     * @param serviceURL - the service URL for the API calls
     */
    public SlackServer(String serviceURL) {
        this(serviceURL, 5000);
    }

    /**
     * Sends the message to the Slack server.
     * <p>
     * Note: This is not thread safe, so call it on an async thread.
     *
     * @param message - the message to send
     * @return The response from the Slack server.
     */
    public void send(SlackMessage message, boolean async, Callback<String> callback) {
        if (async) {
            Bukkit.getScheduler().runTaskAsynchronously(ServerUtil.getJavaPlugin(), () -> {
                HttpURLConnection connection = null;

                // the message in json format
                JsonObject json = message.toJSON();

                try {
                    // Create connection
                    final URL url = new URL(serviceURL);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod(POST);
                    connection.setConnectTimeout(timeout);
                    connection.setUseCaches(false);
                    connection.setDoInput(true);
                    connection.setDoOutput(true);

                    final String payload = PAYLOAD + URLEncoder.encode(json.toString(), UTF_8);

                    // Send request
                    final DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
                    wr.writeBytes(payload);
                    wr.flush();
                    wr.close();

                    // Get Response
                    final InputStream is = connection.getInputStream();
                    final BufferedReader rd = new BufferedReader(new InputStreamReader(is));
                    String line;
                    StringBuilder response = new StringBuilder();
                    while ((line = rd.readLine()) != null) {
                        response.append(line);
                        response.append('\n');
                    }

                    rd.close();
                    callback.call(response.toString());
                }
                catch (Exception e) {
                    ServerUtil.log(C.RED + "[ERROR] " + C.WHITE + "Unable to send the " + message.toString() + " to the Slack Server.");
                    throw new RuntimeException(e);
                }
                finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            });
        }
        else {
            HttpURLConnection connection = null;

            // the message in json format
            JsonObject json = message.toJSON();

            try {
                // Create connection
                final URL url = new URL(serviceURL);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod(POST);
                connection.setConnectTimeout(timeout);
                connection.setUseCaches(false);
                connection.setDoInput(true);
                connection.setDoOutput(true);

                final String payload = PAYLOAD + URLEncoder.encode(json.toString(), UTF_8);

                // Send request
                final DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
                wr.writeBytes(payload);
                wr.flush();
                wr.close();

                // Get Response
                final InputStream is = connection.getInputStream();
                final BufferedReader rd = new BufferedReader(new InputStreamReader(is));
                String line;
                StringBuilder response = new StringBuilder();
                while ((line = rd.readLine()) != null) {
                    response.append(line);
                    response.append('\n');
                }

                rd.close();
                callback.call(response.toString());
            }
            catch (Exception e) {
                ServerUtil.log(C.RED + "[ERROR] " + C.WHITE + "Unable to send the " + message.toString() + " to the Slack Server.");
                throw new RuntimeException(e);
            }
            finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
        }
    }
}
