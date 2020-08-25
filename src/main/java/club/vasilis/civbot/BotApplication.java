package club.vasilis.civbot;

import club.vasilis.civbot.bot.CivBot;
import club.vasilis.civbot.configuration.MineBot;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.event.ListenerHost;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;


@Slf4j
@Component
public class BotApplication implements ApplicationRunner {

    private final MineBot mineBot;

    private final List<ListenerHost> listenerList;

    public BotApplication(MineBot mineBot, List<ListenerHost> listenerList) {
        this.mineBot = mineBot;
        this.listenerList = listenerList;
    }

    @Override
    public void run(ApplicationArguments args){
        new CivBot(mineBot.getAccount(), mineBot.getPassword(), listenerList);
        log.info("启动成功！");
    }
}
