package me.lukebingham.redishelper;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import me.lukebingham.core.database.Database;
import me.lukebingham.core.database.DatabaseModule;
import me.lukebingham.core.redis.JedisModule;
import me.lukebingham.core.redis.message.CreateServerMessage;
import me.lukebingham.core.redis.message.RemoveServerMessage;
import me.lukebingham.core.redis.message.ServerCreatedMessage;
import me.lukebingham.core.redis.message.ServerRemovedMessage;
import me.lukebingham.core.util.ServerType;
import me.lukebingham.core.util.ServerUtil;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.*;

/**
 * Created by LukeBingham on 17/03/2017.
 */
public class Main {

    private JedisModule jedisModule;
    private Database database;
    private Random random;

    public static void main(String[] args) {
        Main instance = new Main();

        instance.random = new Random();
        instance.database = new DatabaseModule("localhost", 27017, false);
        instance.jedisModule = new JedisModule("OPERATOR");

        final Runtime runtime = Runtime.getRuntime();
        runtime.addShutdownHook(new Thread(() -> {
            instance.database.close();
            instance.jedisModule.disable();
        }));

        System.out.println("RedisHelper Loaded!");

        instance.jedisModule.registerListener(CreateServerMessage.class, (sender, message) -> {
            System.out.println("Received message: " + message.getClass().getSimpleName() + " from '" + sender + "'");
            ServerType type = message.getServerType();
            try {
                int id = instance.getServerId(type);
                String template = "/root/minecraft/Template/" + type.name();
                String target = "/root/minecraft/" + type.name() + "/" + id + "/";
                File templateFile = new File(template);
                File targetFile = new File(target);

                if (!targetFile.exists())
                    targetFile.mkdirs();

                if (!templateFile.exists()) {
                    System.out.println("Template file for type " + type.name() + " cannot be found.");
                    return;
                }

                if (templateFile.listFiles() == null) {
                    System.out.println("Template file for type " + type.name() + " is empty..");
                    return;
                }

                //Move files to target location.
                FileUtils.copyDirectory(templateFile, targetFile);

                FileInputStream inputStream = new FileInputStream(target + "server.properties");
                Properties properties = new Properties();
                properties.load(inputStream);
                inputStream.close();

                int port = instance.getRandomPort();
                FileOutputStream outputStream = new FileOutputStream(target + "server.properties");
                properties.setProperty("server-port", String.valueOf(port));
                properties.setProperty("motd", ServerUtil.SERVER_NAME + "-" + message.getServerType().name() + "_" + id);
                properties.store(outputStream, null);
                outputStream.close();

                Writer output = new BufferedWriter(new FileWriter(target + "start.sh"));
                output.write(instance.getShellContent(type, type.getMb()));
                output.close();

                File start = new File(target + "start.sh");
                if (start.setWritable(true, false))
                    System.out.println("Start script writable permission set.");
                if (start.setReadable(true, false))
                    System.out.println("Start script readable permission set.");
                if (start.setExecutable(true, false))
                    System.out.println("Start script execute permission set.");

                runtime.exec("screen -d -m -S " + type.name() + "_" + id + " " + target + "start.sh");

                ServerCreatedMessage m = new ServerCreatedMessage(type, id, port);
                String to = "all";
                instance.jedisModule.sendMessage(m, to);
                System.out.println("Sent message: " + m.getClass().getSimpleName() + " to '" + to + "'");
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        });

        instance.jedisModule.registerListener(ServerCreatedMessage.class, (sender, message) -> {
            instance.database.getCollection("Network", "servers").insert(new BasicDBObject("type", message.getServerType().name())
                    .append("serverId", message.getServerId()).append("port", message.getPort())
                    .append("startTime", System.currentTimeMillis()).append("playerCount", 0));
        });

        instance.jedisModule.registerListener(RemoveServerMessage.class, (sender, message) -> {
            if (message.getServerType() == ServerType.LOBBY && message.getServerId() == 1)
                return;
            new Thread(() -> {
                try {
                    Thread.sleep(1000);
                    runtime.exec("screen -X -S " + message.getServerType().name() + "_" + message.getServerId() + " kill");
                    FileUtils.deleteDirectory(new File("/root/minecraft/" + message.getServerType().name() + "/" + message.getServerId()));
                    instance.database.getCollection("Network", "servers").remove(new BasicDBObject("port", message.getPort()));

                    instance.jedisModule.sendMessage(new ServerRemovedMessage(message.getServerType(), message.getServerId()), "all");
                    Thread.currentThread().interrupt();
                }
                catch (InterruptedException | IOException e) {
                    e.printStackTrace();
                }
            }).start();
        });
    }

    private int getRandomPort() {//23000 - 65500
        int port = random.nextInt(42500) + 23000;

        DBCursor dbCursor = database.getCollection("Network", "servers").find(new BasicDBObject("port", port));
        if (dbCursor.hasNext()) {
            return getRandomPort();
        }

        return port;
    }

    private int getServerId(ServerType serverType) {
        DBCursor dbCursor = database.getCollection("Network", "servers").find(new BasicDBObject("type", serverType.name()));
        List<Integer> ids = new ArrayList<>();
        int highest = 1, x = -1;
        for (DBObject aDbCursor : dbCursor) {
            BasicDBObject dbObject = (BasicDBObject) aDbCursor;
            int id = dbObject.getInt("serverId");
            ids.add(id);
            highest = id > highest ? id : highest;
        }

        for(int i = 1; i < highest; i++) {
            if(ids.contains(i)) continue;
            x = i;
        }

        return x == -1 ? dbCursor.size() + 1 : x;
    }

    private String getShellContent(ServerType serverType, int mb) {
        return "#!/bin/bash\n"
                + "BINDIR=$(dirname \"$(readlink -fn \"$0\")\")\n"
                + "cd \"$BINDIR\"\n"
                + "while true\n"
                + "do\n"
//                    + "cd plugins\n"
//                    + "wget -r -nH -nd -np -R index.html* https://lukebingham.me/abc/" + serverType.name() + "/\n"
//                    + "cd ../\n"
                    + "java -Xmx" + mb + "M -Xms" + mb + "M -XX:MaxPermSize=" + mb + "M  -jar spigot.jar\n"
                    + "for i in {5..1}\n"
                    + "do\n"
                        + "echo \"Server starting in: $i\"\n"
                        + "sleep 1\n"
                    + "done\n"
                    + "./start.sh\n"
                + "done";
    }
}
