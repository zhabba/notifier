/**
 * Temporary stub taken from Google
 */

package com.xzha.push.gcm;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class GcmSender {
    private static final Logger LOG = Logger.getLogger(GcmSender.class);
    private static final String DEFAULT_TOPIC = "/topics/xzha"; //TODO:: define in config
    public static final String API_KEY = "API_KEY"; //TODO:: define in config

    public String send(String message, String topic) {
        LOG.debug("Sending message to GCM");
        LOG.debug("Message body is: " + message);
        try {
            // Prepare JSON containing the GCM message content. What to send and where to send.
            JSONObject jGcmData = new JSONObject();
            JSONObject jData = new JSONObject();
            jData.put("message", message.trim());
            // Where to send GCM message.
            if (topic != null && !topic.isEmpty()) {
                jGcmData.put("to", topic.trim());
            } else {
                jGcmData.put("to", DEFAULT_TOPIC);//TODO:: define in config
            }
            // What to send in GCM message.
            jGcmData.put("data", jData);

            // Create connection to send GCM Message request.
            URL url = new URL("https://android.googleapis.com/gcm/send");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Authorization", "key=" + API_KEY);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            // Send GCM message content.
            OutputStream outputStream = conn.getOutputStream();
            outputStream.write(jGcmData.toString().getBytes());

            // Read GCM response.
            InputStream inputStream = conn.getInputStream();
            String resp = IOUtils.toString(inputStream);
            return resp;
        } catch (IOException e) {
            LOG.error("Error sending message" + message + " to topic: " + topic, e);
            return null;
        }
    }
}
