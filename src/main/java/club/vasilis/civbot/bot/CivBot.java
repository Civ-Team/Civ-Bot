package club.vasilis.civbot.bot;

import lombok.Getter;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactoryJvm;
import net.mamoe.mirai.event.Events;
import net.mamoe.mirai.event.ListenerHost;
import net.mamoe.mirai.utils.BotConfiguration;

import java.util.List;

@Getter
public class CivBot {

    public static Bot bot = null;

    public CivBot(Long account, String pwd, List<ListenerHost> events) {
        bot = startBot(account, pwd, events);
    }

    public Bot startBot(Long account, String password, List<ListenerHost> listeneList) {
        bot = BotFactoryJvm.newBot(account, password, new BotConfiguration() {
            {
                //保存设备信息到文件
                fileBasedDeviceInfo("deviceInfo.json");
            }
        });
        bot.login();
        listeneList.forEach(listenerHost -> Events.registerEvents(bot,listenerHost));
        // 这里必须要启新线程去跑bot 不然会占用主线程
        new Thread(() -> {
            bot.join();
        }).start();
        return bot;
    }

}
