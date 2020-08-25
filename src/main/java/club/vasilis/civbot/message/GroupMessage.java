package club.vasilis.civbot.message;

import club.vasilis.civbot.common.enums.Command;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.Member;
import net.mamoe.mirai.message.data.MessageChain;

public interface GroupMessage {


    Command get();

    void execute(Group group, Member sender, MessageChain messageChain);
}
