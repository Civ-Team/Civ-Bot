package club.vasilis.civbot.network.minecraft;

import com.github.steveice10.mc.protocol.MinecraftConstants;
import com.github.steveice10.mc.protocol.MinecraftProtocol;
import com.github.steveice10.mc.protocol.data.SubProtocol;
import com.github.steveice10.mc.protocol.data.status.handler.ServerInfoHandler;
import com.github.steveice10.mc.protocol.data.status.handler.ServerPingTimeHandler;
import com.github.steveice10.packetlib.Client;
import com.github.steveice10.packetlib.Session;
import com.github.steveice10.packetlib.tcp.TcpSessionFactory;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import net.mamoe.mirai.utils.ExternalImage;
import net.mamoe.mirai.utils.ExternalImageJvmKt;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.net.Proxy;

@Slf4j
@Component
public class MinecraftClientLogin {

    private MinecraftProtocol protocol = new MinecraftProtocol(SubProtocol.STATUS);

    @Async
    public void statusAsync(Contact contact,String host,Integer post){
        try {
            status(contact,host,post);
        }catch (Exception e){
            contact.sendMessage("连接失败"+e.getLocalizedMessage());
        }
    }


    public void status(Contact contact, String host, Integer post) {
        Client client = new Client(host, post, new MinecraftProtocol(SubProtocol.STATUS), new TcpSessionFactory(null));
        client.getSession().setFlag(MinecraftConstants.AUTH_PROXY_KEY, Proxy.NO_PROXY);
        client.getSession().setFlag(MinecraftConstants.SERVER_INFO_HANDLER_KEY,
                (ServerInfoHandler) (session, info) -> {
                    log.info(info.toString());
                    Image icon = null;
                    if (info.getIconPng() != null) {
                        icon = contact.uploadImage(ExternalImageJvmKt.toExternalImage(new ByteArrayInputStream(info.getIconPng())));
                    }
                    MessageChainBuilder builder = new MessageChainBuilder();
                    if (icon != null) {
                        builder.add(icon);
                    }
                    builder.add(info.getDescription().getFullText().replaceAll("§[\\w\\d]", ""));
                    builder.add("," + info.getVersionInfo().getVersionName().replaceAll("((thermos|cauldron|craftbukkit|mcpc|kcauldron|fml),?)+", "") + "\n");
                    builder.add("玩家" + info.getPlayerInfo().getOnlinePlayers() + "/" + info.getPlayerInfo().getMaxPlayers());
                    contact.sendMessage(builder.asMessageChain());
//                    if (info.playerInfo.onlinePlayers > 0) {
//                    add(info.playerInfo.players.joinToString(", ") { it.name })
//                }
                });
        client.getSession().setFlag(MinecraftConstants.SERVER_PING_TIME_HANDLER_KEY, (ServerPingTimeHandler) (session, pingTime) -> {

            contact.sendMessage("服务器延迟为" + pingTime + "ms");
        });
        client.getSession().connect();
        while (client.getSession().isConnected()) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        log.info("success");

    }

}
