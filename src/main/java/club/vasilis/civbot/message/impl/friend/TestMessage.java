package club.vasilis.civbot.message.impl.friend;

import club.vasilis.civbot.common.enums.Command;
import club.vasilis.civbot.message.FriendMessage;
import net.mamoe.mirai.contact.Friend;
import net.mamoe.mirai.message.data.MessageChain;
import org.springframework.stereotype.Component;

@Component
public class TestMessage implements FriendMessage {
    @Override
    public Command get() {
        return Command.PRIVATE_DEFAULT;
    }

    @Override
    public void execute(Friend sender, MessageChain messageChain) {
        sender.sendMessage("123");
    }
}
