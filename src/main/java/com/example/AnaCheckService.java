package com.example;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AnaCheckService {
    @Value("${ana.timesale.url:https://www.ana.co.jp/ja/jp/}")
    private String anaUrl; 

    private final SlackNotificationService slackNotificationService;

    public AnaCheckService(SlackNotificationService slackNotificationService) {
        this.slackNotificationService = slackNotificationService;
    }

    // @Scheduled(fixedRate = 30000)
    @Scheduled(cron = "0 30 10 * * *", zone = "Asia/Tokyo")
    public void checkSale() {
        try {
            Document doc = Jsoup.connect(anaUrl).get();

            // セール開催中のバナーがあるか確認する
            Elements saleBanner = doc.select("a[href='/ja/jp/domestic/theme/timesale/sale/']");

            // 通知の内容を決定して送信
            if (!saleBanner.isEmpty()) {
                log.info("タイムセール開催中");
                slackNotificationService.send("ANAのタイムセールが開催しています!確認してください!!\n" + anaUrl);
            } else {
                log.info("タイムセール終了");
                slackNotificationService.send("ANAのタイムセールは終了しています。待ちましょう。\n" + anaUrl);
            }
        } catch (IOException e) {
            log.error("接続に失敗しました：{}", e.getMessage());
        }
    }
}
