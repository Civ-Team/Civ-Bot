package club.vasilis.civbot.message.impl.group.minecraft;

import club.vasilis.civbot.common.enums.Command;
import club.vasilis.civbot.message.GroupMessage;
import club.vasilis.civbot.network.minecraft.MinecraftClientLogin;
import club.vasilis.civbot.utils.MessageUtils;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.Member;
import net.mamoe.mirai.message.data.MessageChain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class PingMessage implements GroupMessage {
    @Autowired
    private MinecraftClientLogin minecraftClientLogin;

    @Override
    public Command get() {
        return Command.MINECRAFT_PING;
    }

    @Override
    public void execute(Group group, Member sender, MessageChain messageChain) {
        String message = messageChain.contentToString();
        String keyword = MessageUtils.getKeyword(message, 2);
        if (!Pattern.matches("(\\w+\\.)+(\\w)+(:\\d+)", keyword)) {
            group.sendMessage("地址错误");
        }
        if (message.startsWith("127") || message.startsWith("192")) {
            group.sendMessage("老子用 LOIC 把你妈的内网 ping 了");
        }
        try {
            minecraftClientLogin.statusAsync(group, keyword.split(":")[0], Integer.parseInt(keyword.split(":")[1]));
        }catch (NumberFormatException e){
            group.sendMessage(e.getLocalizedMessage());
        }

    }
}
