package club.vasilis.civbot.listener;

import club.vasilis.civbot.bot.CivBot;
import club.vasilis.civbot.common.enums.Command;
import club.vasilis.civbot.configuration.MineBot;
import club.vasilis.civbot.message.FriendMessage;
import club.vasilis.civbot.message.MessageFactory;
import club.vasilis.civbot.utils.MessageUtils;
import kotlin.coroutines.CoroutineContext;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.message.FriendMessageEvent;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.PlainText;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class FriendListener extends SimpleListenerHost {

    @Autowired
    private MessageFactory messageFactory;

    @Autowired
    private MineBot mineBot;

    @EventHandler
    private void onMessage(FriendMessageEvent event) {
        MessageChain messageChain = event.getMessage();
        PlainText plainText = messageChain.first(PlainText.Key);
        if (plainText != null) {
            Command command = Command.privateFind(MessageUtils.getKeyword(plainText.contentToString(), 1));
            if (command == null) {
                return;
            }
            FriendMessage friendMessage = messageFactory.getFriendMessage(command);
            friendMessage.execute(event.getFriend(), messageChain);
        }
    }

    @Override
    public void handleException(@NotNull CoroutineContext context, @NotNull Throwable exception) {
        log.error(exception.getMessage());
        CivBot.bot.getFriend(mineBot.getAdmin()).sendMessage("私聊消息处理错误!" + exception.getMessage());

    }
}
