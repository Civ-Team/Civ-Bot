package club.vasilis.civbot.message;

import club.vasilis.civbot.common.enums.Command;
import net.mamoe.mirai.contact.Member;
import net.mamoe.mirai.message.data.MessageChain;

public interface TempMessage {

    Command get();

    void execute(Member sender, MessageChain messageChain);
}
