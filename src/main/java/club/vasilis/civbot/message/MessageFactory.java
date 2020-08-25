package club.vasilis.civbot.message;

import club.vasilis.civbot.common.enums.Command;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 分为
 */
@Slf4j
@Component
public class MessageFactory {

    @Autowired
    private List<FriendMessage> friendMessageList;

    @Autowired
    private List<GroupMessage> groupMessageList;

    public GroupMessage getGroupMessage(Command command) {
        for (GroupMessage groupMessage : groupMessageList) {
            if (groupMessage.get().equals(command)) {
                return groupMessage;
            }
        }

        return null;
    }

    public FriendMessage getFriendMessage(Command command) {
        for (FriendMessage friendMessage : friendMessageList) {
            if (friendMessage.get().equals(command)) {
                return friendMessage;
            }
        }

        return null;
    }
}
