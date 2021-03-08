# はじめに
本プロジェクトはKENスクール Webアプリ開発科 の最終課題、
「レンタルサイト新規構築 要望書」を元にログイン機能＋1機能を
新規で構築するという課題の成果物です。
元々は[こちらのプロジェクト](https://github.com/Ishibashi-Kuramori/mla)にて開発しておりましたが、
herokuのリモートサーバー上で動作出来ない
そもそもSpringレガシープロジェクトは実践向きではない
という問題にてSpring Bootプロジェクトにてリメイクしております。

## DEMO
[https://mla-boot.herokuapp.com/](https://mla-boot.herokuapp.com/)
※数時間誰もアクセスしてない場合、起動に数秒かかる場合があります。

## 前プロジェクトとの相違点
* Springレガシープロジェクト **→** Spring Boot プロジェクトに変更
* MySQL **→** PostgreSQL に変更
* JSP & XML **→** HTML & Thymeleaf に変更
* hibernate **→** JDBC & HikariCP に変更

## 開発環境
* [Windows10 64bit](https://www.microsoft.com/ja-jp/store/b/windows)
* [Java SE Ver15.0.2](https://www.oracle.com/java/technologies/javase-downloads.html)（Java Development Kit）
* [Eclipse 2020 Pleiades All in One 最新版](https://mergedoc.osdn.jp/) 
	* spring framework boot Ver2.3.0.RELEASE (Java Webシステム構築用)
	* DBViewer Plugin for Eclipse(PostgreSQL DB参照／編集用)
	* [Apache Maven Ver3.6.3](https://maven.apache.org/download.cgi)(パッケージ管理、リモートビルド用)
* [PostgreSQL Ver11.11](https://dev.mysql.com/)（DateBase）
* [AdminLTE 3](https://adminlte.io/themes/v3/)(管理画面特化型CSSフレームワーク)
	* [jQuery Ver3.5.1](https://jquery.com/)(JavaScriptライブラリ)
	* [Bootstrap Ver4.5.3](https://getbootstrap.com/)(WEBフレームワーク)
* [Git Hub](https://github.com/)(開発コード管理／公開用)
* [heroku](https://jp.heroku.com/) (DEMOページ公開用クラウドサーバー構築用) 

## ローカル実行環境構築手順
1. 上記開発環境の[Java SE Ver15.0.2](https://www.oracle.com/java/technologies/javase-downloads.html) 〜 [PostgreSQL Ver11.11](https://dev.mysql.com/)までを全てインストールする。
2. PostgreSQLにUser名:**postgres** パスワード:**root**で管理者を設定し、**spring_database**というデータベースを設定。
3. 本ページのプロジェクト一式をGit Clone 又は Download ZIPにて入手
4. Eclipseにプロジェクトをインポートする。
5. 本プロジェクトTOPの**db_setup.sql**をEclipseのDBViewer、又はコマンドから実行する。
6. Eclipseから本プロジェクトを右クリックし
  **実行(R) → Spring Bootアプリケーション**を選択する。
8. ブラウザにて[http://localhost:5000](http://localhost:5000)を開く

## Webアプリ操作手順
### 【1. TOP画面(未ログイン)】
![1](https://user-images.githubusercontent.com/78581467/107622705-8f9b9180-6c9b-11eb-8cce-7bd58ed67881.png)
* 検索欄に作品名or作者名の一部を入力すると検索結果が表示されます。
* 右上の**ログイン**ボタンをクリックすると【2. ログイン画面】に遷移します。

### 【2. ログイン画面】
![2](https://user-images.githubusercontent.com/78581467/107622708-90342800-6c9b-11eb-88d5-40cf3119b54a.png)
* Email:**a@a.com** Password: **aaa** を入力し、**ログイン**ボタンをクリックすると【5. TOP画面(管理者ログイン)】に遷移します。
* Email:**b@b.com** Password: **bbb** を入力し、**ログイン**ボタンをクリックすると【4. TOP画面(会員ログイン)】に遷移します。
* **新規登録**のリンクを クリックすると【3. 新規会員登録画面】に遷移します。

### 【3. 新規会員登録画面】
![3](https://user-images.githubusercontent.com/78581467/107622709-90ccbe80-6c9b-11eb-8851-4222d2bf17ea.png)
* 各項目を入力後、**登録**ボタンをクリックすると【4. TOP画面(会員ログイン)】に遷移します。

### 【4. TOP画面(会員ログイン)】
![4](https://user-images.githubusercontent.com/78581467/107622712-91655500-6c9b-11eb-93d0-d4e658f9b61f.png)
* 会員ユーザが表示され、プラン、ポイントの確認、会員権限メニューが使用可能になります。
* サイドメニューの**個人情報管理**のリンクを クリックすると【6. 個人情報管理画面】に遷移します。
* 右上の**ログアウト**ボタンをクリックすると【2. ログイン画面】に遷移します。

### 【5. TOP画面(管理者ログイン)】
![5](https://user-images.githubusercontent.com/78581467/107622713-91655500-6c9b-11eb-9617-ab96c6dfb01a.png)
* 管理者ユーザが表示され、プラン、ポイントの確認、管理者権限メニューが使用可能になります。
* サイドメニューの**個人情報管理**のリンクを クリックすると【6. 個人情報管理画面】に遷移します。
* 右上の**ログアウト**ボタンをクリックすると【2. ログイン画面】に遷移します。

### 【6. 個人情報管理画面】
![6](https://user-images.githubusercontent.com/78581467/107622716-91fdeb80-6c9b-11eb-92ab-caa7ee68b452.png)
* 各項目を入力後、**更新**ボタンをクリックするとユーザ情報が更新され前の画面に遷移します。
* **退会**ボタンをクリックすると、確認後に【1. TOP画面(未ログイン)】に遷移します。

### 【7. その他の画面】
![7](https://user-images.githubusercontent.com/78581467/107622721-92968200-6c9b-11eb-8dbb-b55ea2745b33.png)
誠意製作中です。現在上記に加え以下の機能を実装中
* TOP画面にて商品の「借りる」ボタンクリックにて「発送準備中」に切り替わり、レンタル管理画面で確認可能となる。再度クリックで確認メッセージ表示後、キャンセルが出来る。
* 管理者ユーザがメニューの「個人情報管理」を開くと、会員一覧画面が表示され、他ユーザ情報の編集や、管理者権限の付与等が出来る。

## 自身で作成した主なファイル
* /mla-boot/src/main/java/jp/ken/mla 配下
	* javaで作成したコード。Controller, db, model 等
* /mla-boot/src/main/resources/templates 配下
	* HTML & Thymeleaf で作成したWebページ一式 
* /mla-boot/db_setup.sql
	* 本プロジェクトで使用するDB構成一式
