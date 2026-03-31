package jar.com.example.demo;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.slack.api.Slack;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SlackNotificationService {

    private final String botToken;
    private final String channelId;

    public SlackNotificationService() {
        // 環境変数を確認
        String envToken = System.getenv("SLACK_BOT_TOKEN");
        String envChannel = System.getenv("SLACK_CHANNEL_ID");
        // 環境変数があれば使う。なければ.envから読み込む
        if (envToken != null && envChannel != null) {
            this.botToken = envToken;
            this.channelId = envChannel;
        } else {
            Dotenv dotenv = Dotenv.configure()
                .ignoreIfMissing()
                .load();
            this.botToken = dotenv.get("SLACK_BOT_TOKEN");
            this.channelId = dotenv.get("SLACK_CHANNEL_ID");
        }
    }

    /**
     * 指定メッセージをSlackに通知する
     * @param message 送信するメッセージ
     */
    public void send(String message) {
        try (Slack slack = new Slack()) {
            ChatPostMessageRequest request = ChatPostMessageRequest.builder()
                .channel(channelId)
                .text(message)
                .build();

            ChatPostMessageResponse response = slack.methods(botToken).chatPostMessage(request);

            if (response.isOk()) {
                log.info("Slackへの通知に成功しました");
            } else {
                log.error("Slackへの通知失敗：{}", response.getError());
            }
        } catch (SlackApiException | IOException e) {
            log.error("Slackへの通知に失敗しました", e);
        } catch (Exception e) {
            log.error("Slackクライアントのクローズに失敗しました", e);
        }
    }
}
