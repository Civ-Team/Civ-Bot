package club.vasilis.civbot.listener;

import club.vasilis.civbot.bot.CivBot;
import club.vasilis.civbot.common.enums.Command;
import club.vasilis.civbot.configuration.MineBot;
import club.vasilis.civbot.message.GroupMessage;
import club.vasilis.civbot.message.MessageFactory;
import club.vasilis.civbot.utils.MessageUtils;
import kotlin.coroutines.CoroutineContext;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.message.GroupMessageEvent;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.PlainText;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class GroupListener extends SimpleListenerHost {

    @Autowired
    private MessageFactory messageFactory;

    @Autowired
    private MineBot mineBot;

    @EventHandler
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
}