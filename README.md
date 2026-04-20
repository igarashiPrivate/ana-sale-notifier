# ana-sale-notifier

ANAの国内線タイムセール開催状況を定期チェックし、結果をSlackに通知するSpring Bootアプリケーション。

## 機能

- ANAタイムセールページを毎日10:30（JST）にスクレイピング
- タイムセール開催中・終了中に応じたメッセージをSlackに通知

## 技術スタック

- Java 21
- Spring Boot 4.0.3
- Jsoup 1.18.1（Webスクレイピング）
- Slack API Client 1.44.2（Slack通知）
- dotenv-java 3.0.0（環境変数管理）
- Gradle（ビルドツール）

## セットアップ

### 1. 環境変数の設定

プロジェクトルートに `.env` ファイルを作成し、以下を設定する。

```env
SLACK_BOT_TOKEN=xoxb-xxxxxxxxxxxx
SLACK_CHANNEL_ID=CXXXXXXXXXX
```

### 2. ローカル実行

```bash
# ビルド
./gradlew build -x test

# 実行
./gradlew bootRun
```

### 3. GitHub Actions

毎日 10:30 JST に自動実行されます。手動実行も可能です（Actions タブ > Run workflow）。

GitHub Secrets に以下を設定してください：
- `SLACK_BOT_TOKEN`
- `SLACK_CHANNEL_ID`