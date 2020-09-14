package club.vasilis.civbot.message.impl.group.jx3;

import club.vasilis.civbot.common.enums.Command;
import club.vasilis.civbot.common.enums.FlowerEnum;
import club.vasilis.civbot.message.GroupMessage;
import club.vasilis.civbot.network.jx3.RetrofitUrl;
import club.vasilis.civbot.utils.MessageUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.SneakyThrows;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.Member;
import net.mamoe.mirai.message.data.MessageChain;
import okhttp3.ResponseBody;
import org.springframework.stereotype.Component;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

@Component
public class FlowerMessage implements GroupMessage {

    private String defaultServer = "绝代天骄";

    private final RetrofitUrl retrofitUrl;

    public FlowerMessage(RetrofitUrl retrofitUrl) {
        this.retrofitUrl = retrofitUrl;
    }

    @Override
    public Command get() {
        return Command.FLOWER;
    }

    @Override
    public void execute(Group group, Member sender, MessageChain messageChain) {
        String message = messageChain.contentToString();
        List<String> split = MessageUtils.split(message);
        if (split.size() == 2) {
            flowerQuery(group, MessageUtils.getKeyword(message, 2), defaultServer);

        } else if (split.size() == 1) {
            group.sendMessage("花价 [花名] [区服(可选)]");
        } else if (split.size() > 2) {
            flowerQuery(group, MessageUtils.getKeyword(message, 2), MessageUtils.getKeyword(message, 3));

        }
    }

    private void flowerQuery(Group group, String flowerName, String serverName) {

        FlowerEnum flower = FlowerEnum.getFlower(flowerName);
        if (flower == null) {
            group.sendMessage( "没有" + flowerName + "这种花哦！");
        }
        StringBuffer buffer = new StringBuffer();

        Call<ResponseBody> bodyCall = retrofitUrl.getFlower(serverName, flower.code);
        bodyCall.enqueue(new Callback<ResponseBody>() {
            @SneakyThrows
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String string = response.body().string();
                JSONArray array = JSON.parseArray(string);
                if (array.size() == 0) {
                    buffer.append("没有找到相应信息");
                }
                buffer.append("【花价-")
                        .append(serverName)
                        .append("】")
                        .append("\r\n");
                for (int i = 0; i < array.size(); i++) {
                    JSONObject jsonObject = array.getJSONObject(i);
                    String type_name = jsonObject.getString("type_name");
                    buffer.append(type_name)
                            .append("\r\n");
                    JSONArray list = jsonObject.getJSONArray("list");
                    for (int j = 0; j < list.size(); j++) {
                        JSONObject jsonObject1 = list.getJSONObject(j);
                        String line_name = jsonObject1.getString("line_name");
                        buffer.append(line_name)
                        .append(" ");
                        String price = jsonObject1.getString("price");
                        buffer.append(price).append("币; ");
                    }
                    buffer.append("\r\n");
                }
                group.sendMessage(buffer.toString());
            }


            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                group.sendMessage("网络请求异常");
            }
        });


    }
}
