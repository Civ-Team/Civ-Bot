package club.vasilis.civbot;

import club.vasilis.civbot.network.jx3.RetrofitUrl;
import com.github.steveice10.mc.protocol.MinecraftConstants;
import com.github.steveice10.mc.protocol.MinecraftProtocol;
import com.github.steveice10.mc.protocol.data.SubProtocol;
import com.github.steveice10.mc.protocol.data.status.handler.ServerInfoHandler;
import com.github.steveice10.mc.protocol.data.status.handler.ServerPingTimeHandler;
import com.github.steveice10.packetlib.Client;
import com.github.steveice10.packetlib.tcp.TcpSessionFactory;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.message.data.Image;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.net.Proxy;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
class MiraiRobotApplicationTests {
    @Autowired
    private RetrofitUrl retrofitUrl;

    @Test
    void name() throws IOException {
//        JSONObject  js= retrofitUrl.getFlower("绝代天骄", 1);
//        System.out.println(js.toJSONString());
    }

    @Test
    void contextLoads() {
        for (int i = 0; i < 10; i++) {
//            MinecraftClientLogin.status(null, "mc.vasilis.club", 10080);
        }
    }

    @Test
    void test() {
        MinecraftProtocol protocol = new MinecraftProtocol(SubProtocol.STATUS);
        Client client = new Client("mc.vasilis.club", 10080, protocol, new TcpSessionFactory(null));
        client.getSession().setFlag(MinecraftConstants.AUTH_PROXY_KEY, Proxy.NO_PROXY);
        client.getSession().setFlag(MinecraftConstants.SERVER_INFO_HANDLER_KEY,
                (ServerInfoHandler) (session, info) -> {
                    log.info(info.toString());
                    Image icon = null;

//                    if (info.playerInfo.onlinePlayers > 0) {
//                    add(info.playerInfo.players.joinToString(", ") { it.name })
//                }
                });
        client.getSession().setFlag(MinecraftConstants.SERVER_PING_TIME_HANDLER_KEY, (ServerPingTimeHandler) (session, pingTime) -> {

            log.info("服务器延迟为" + pingTime + "ms");
        });
        client.getSession().connect();
        while (client.getSession().isConnected()) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
