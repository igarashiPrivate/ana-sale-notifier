package jar.com.example.demo;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class AnaCheckService {
    @Value("${ana.timesale.url:https://www.ana.co.jp/ja/jp/domestic/theme/timesale/sv/?dom=1}")
    private String anaUrl; 

    private final SlackNotificationService slackNotificationService;

    public AnaCheckService(SlackNotificationService slackNotificationService) {
        this.slackNotificationService = slackNotificationService;
    }

    // 30秒ごとに動かすテスト用
    @Scheduled(fixedRate = 30000)
    // @Scheduled(cron = "0 0 9 * * *", zone = "Asia/Tokyo")
    public void checkSale() {
        try {
            Document doc = Jsoup.connect(anaUrl).get();

            // サイトの文字を取得する
            String getText = doc.text();

            // 現在の状況を判定する
            boolean isSaleEnded = getText.contains("国内線航空券タイムセールは終了しました。");

            // 「タイムセール」の文字列があるか判定する
            if (isSaleEnded) {
                log.info("タイムセールは終了しています。待ちましょう。");
                slackNotificationService.send("ANAのタイムセールは終了しています。待ちましょう。");
            } else {
                log.info("タイムセールが開催しています！確認してください！！");
                // Slackに通知する
                slackNotificationService.send("ANAのタイムセールが開催しています!確認してください!!");
            }
        } catch (IOException e) {
            log.error("接続に失敗しました：{}", e.getMessage());
        }
    }
}
