package club.vasilis.civbot.listener;

import club.vasilis.civbot.bot.CivBot;
import club.vasilis.civbot.common.enums.Command;
import club.vasilis.civbot.configuration.MineBot;
import club.vasilis.civbot.message.GroupMessage;
import club.vasilis.civbot.message.MessageFactory;
import club.vasilis.civbot.network.jx3.RetrofitUrl;
import club.vasilis.civbot.utils.MessageUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import kotlin.coroutines.CoroutineContext;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.Listener;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.message.GroupMessageEvent;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.PlainText;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class GroupListener extends SimpleListenerHost {
    @Autowired
    private RetrofitUrl retrofitUrl;

    @Autowired
    private MessageFactory messageFactory;

    @Autowired
    private MineBot mineBot;

    @EventHandler(priority = Listener.EventPriority.NORMAL)
    private void onMessage(GroupMessageEvent event) {
        log.info("groupMessage");
        MessageChain messageChain = event.getMessage();
        PlainText plainText = messageChain.first(PlainText.Key);
        if (plainText != null) {
            log.info(plainText.contentToString());
            Command command = Command.groupFind(MessageUtils.getKeyword(plainText.contentToString(), 1));
            if (command == null) {
                return;
            }
            log.info(command.keyWord);
            GroupMessage groupMessage = messageFactory.getGroupMessage(command);
            groupMessage.execute(event.getGroup(), event.getSender(), messageChain);
        }
    }

    @Override
    public void handleException(@NotNull CoroutineContext context, @NotNull Throwable exception) {
        log.error(exception.getMessage());
        CivBot.bot.getFriend(mineBot.getAdmin()).sendMessage("私聊消息处理错误!" + exception.getMessage());

    }

    @EventHandler(priority = Listener.EventPriority.LOW)
    private void yy(GroupMessageEvent event) throws IOException {
        double random = Math.random();
        random *= 100;
        log.info("随机数{}",random);
        if (random <= 1) {
            String string = retrofitUrl.yy().execute().body().string();
            JSONObject jsonObject = JSON.parseObject(string);
            event.getGroup().sendMessage(jsonObject.getString("hitokoto"));
        }
    }
}