package club.vasilis.civbot.message;

import club.vasilis.civbot.common.enums.Command;
import net.mamoe.mirai.contact.Friend;
import net.mamoe.mirai.message.data.MessageChain;

public interface FriendMessage {

    Command get();

    void execute(Friend sender, MessageChain messageChain);
}
