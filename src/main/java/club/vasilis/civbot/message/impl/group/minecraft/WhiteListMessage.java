package club.vasilis.civbot.message.impl.group.minecraft;

import club.vasilis.civbot.common.enums.Command;
import club.vasilis.civbot.message.GroupMessage;
import club.vasilis.civbot.utils.MessageUtils;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.Member;
import net.mamoe.mirai.contact.MemberPermission;
import net.mamoe.mirai.message.data.MessageChain;

import static net.mamoe.mirai.contact.MemberPermission.MEMBER;

public class WhiteListMessage implements GroupMessage {
    @Override
    public Command get() {
        return null;
    }

    @Override
    public void execute(Group group, Member sender, MessageChain messageChain) {
        if (group.getId() != 1138778773L) {
            return;
        }
        String message = messageChain.contentToString();
        if (MessageUtils.split(message).size() != 2){
            group.sendMessage("格式错误; 白名单 [id]");
            return;
        }
        String id = MessageUtils.getKeyword(message,2);
        if (MEMBER == sender.getPermission()) {
            String nameCard = sender.getNameCard();
            if (!nameCard.contains(id)) {
                group.sendMessage("请改群名片为游戏ID");
                return;
            }
        }

    }
}
