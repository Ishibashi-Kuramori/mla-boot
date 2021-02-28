/******************************************************
	会員管理テーブル(mla_member_tbl)
		会員ID
		会員名
		メールアドレス
		パスワード
		管理者区分(0:一般会員／1:管理者)
		プランID(プランマスタと紐付け)
		支払ID(支払いマスタと紐付け)
		累計ポイント
		ユーザアイコンIndex
		入会日時
		プラン変更日時
		作成日
		更新日
******************************************************/
DROP TABLE IF EXISTS mla_member_tbl;
CREATE TABLE mla_member_tbl
(
    member_id          SERIAL NOT NULL,
    member_name        VARCHAR(100) NOT NULL,
    email              VARCHAR(100) UNIQUE,
    password           VARCHAR(100),     
    admin              INT NOT NULL,
    plan_id            INT NOT NULL,
    pay_id             INT NOT NULL,
    total_point        INT NOT NULL,
    icon_idx           INT NOT NULL,
    join_date          DATE NOT NULL,
    make_date          DATE,
    update_date        DATE,
    PRIMARY KEY (member_id)
);
INSERT INTO mla_member_tbl VALUES
	(1, '山田太郎', 'a@a.com', 'aaaa', 1, 0, 0, 0, 2, NOW(), NOW(), NOW()),
	(2, '田中花子', 'b@b.com', 'bbbb', 0, 0, 0, 0, 7, NOW(), NOW(), NOW());

/******************************************************
	プランマスタ(mla_plan_mst)
		プランID(0:お試し／1:Bronze／2:Silver／3:Gold)
		プラン名
		プラン配色(BootStrapのcss名)
		月額
		上限レンタル枚数(1000以上は無限扱い)
		作成日
		更新日
******************************************************/
DROP TABLE IF EXISTS mla_plan_mst;
CREATE TABLE mla_plan_mst
(
    plan_id          INT NOT NULL,
    plan_name        VARCHAR(100) NOT NULL,
    plan_color       VARCHAR(100) NOT NULL,
    monthly          INT NOT NULL,
    limit_cnt        INT NOT NULL,
    make_date          DATE,
    update_date        DATE,
    PRIMARY KEY (plan_id)
);
INSERT INTO mla_plan_mst VALUES
	(0, 'お試し', 'info', 324, 2, NOW(), NOW()),
	(1, 'Bronze', 'orange', 1080, 6, NOW(), NOW()),
	(2, 'Silver', 'gray', 2160, 12, NOW(), NOW()),
	(3, 'Gold', 'warning', 5400, 2000, NOW(), NOW());

/******************************************************
	支払マスタ(mla_pay_mst)
		支払ID(0:Visa／1:Master／2:JCB／3:楽天)
		支払名
		作成日
		更新日
******************************************************/
DROP TABLE IF EXISTS mla_pay_mst;
CREATE TABLE mla_pay_mst
(
    pay_id          INT NOT NULL,
    pay_name        VARCHAR(100) NOT NULL,
    make_date          DATE,
    update_date        DATE,
    PRIMARY KEY (pay_id)
);
INSERT INTO mla_pay_mst VALUES
	(0, 'Visa', NOW(), NOW()),
	(1, 'Master', NOW(), NOW()),
	(2, 'JCB', NOW(), NOW()),
	(3, '楽天', NOW(), NOW());

/******************************************************
	商品管理テーブル(mla_item_tbl)
		商品ID
		商品名
		作者名
		媒体ID(媒体マスタと紐付け)
		在庫数
		発注点
		新旧区分(0:新作／1:旧作)
		レンタル可能日時
		付与ポイント
		作成日
		更新日
******************************************************/
DROP TABLE IF EXISTS mla_item_tbl;
CREATE TABLE mla_item_tbl
(
    item_id          SERIAL NOT NULL,
    item_name        VARCHAR(100) NOT NULL,
    author_name      VARCHAR(100),
    media_id         INT NOT NULL,
    stock_cnt        INT NOT NULL,
    order_cnt        INT NOT NULL,
    new_old          INT NOT NULL,
    can_rental_date  DATE NOT NULL,
    add_point        INT NOT NULL,
    make_date          DATE,
    update_date        DATE,
    PRIMARY KEY (item_id)
);
INSERT INTO mla_item_tbl VALUES
	(1, '今日から俺は！！劇場版', '福田雄一',                                    1, 10, 5, 0, cast('2021-02-03' as date ), 25, NOW(), NOW()),
	(2, 'ＴＥＮＥＴ　テネット', 'クリストファー・ノーラン',                      1, 10, 5, 0, cast('2021-02-02' as date ), 25, NOW(), NOW()),
	(3, 'コンフィデンスマンＪＰ　プリンセス編', '田中亮',                         2, 10, 5, 0, cast('2021-02-01' as date ), 25, NOW(), NOW()),
	(4, 'ミッドウェイ', 'ローランド・エメリッヒ',                                2, 10, 5, 0, cast('2021-01-31' as date ), 25, NOW(), NOW()),
	(5, 'スター・ウォーズ／スカイウォーカーの夜明け', 'Ｊ・Ｊ・エイブラムス',      1, 10, 5, 0, cast('2021-01-30' as date ), 25, NOW(), NOW()),
	(6, 'ドラえもん　のび太の新恐竜', '今井一暁',                                1, 10, 5, 0, cast('2021-01-30' as date ), 25, NOW(), NOW()),
	(7, 'アナと雪の女王２', '',                                                 2, 10, 5, 0, cast('2021-01-30' as date ), 25, NOW(), NOW()),
	(8, '劇場版コード・ブルー -ドクターヘリ緊急救命-','西浦正記',                 2, 10, 5, 1, cast('2020-02-03' as date ), 25, NOW(), NOW()),
	(9, 'MEG ザ・モンスター','ジョン・タートルトープ',                          1, 10, 5, 1, cast('2020-02-02' as date ), 25, NOW(), NOW()),
	(10, 'ボヘミアン・ラプソディ','ブライアン・シンガー',                         1, 10, 5, 1, cast('2020-02-01' as date ), 25, NOW(), NOW()),
	(11, '検察側の罪人','原田眞人',                                              2, 10, 5, 1, cast('2020-01-31' as date ), 25, NOW(), NOW()),
	(12, 'ファンタスティック・ビーストと黒い魔法使いの誕生','デヴィッド・イェーツ', 2, 10, 5, 1, cast('2020-01-30' as date ), 25, NOW(), NOW()),
	(13, 'ミッション:インポッシブル/フォールアウト','クリストファー・マッカリー',   1, 10, 5, 1, cast('2020-01-29' as date ), 25, NOW(), NOW()),
	(14, '未来のミライ','細田守',                                                1, 10, 5, 1, cast('2020-01-28' as date ), 25, NOW(), NOW()),
	(15, 'STRAY SHEEP','米津玄師',                                           0, 10, 5, 1, cast('2020-01-27' as date ), 25, NOW(), NOW()),
	(16, 'This is 嵐','嵐',                                                   0, 10, 5, 1, cast('2020-01-26' as date ), 25, NOW(), NOW()),
	(17, 'MAP OF THE SOUL:7 ～THE JOURNEY～','BTS',                    0, 10, 5, 1, cast('2020-01-25' as date ), 25, NOW(), NOW());

/******************************************************
	媒体マスタ(mla_media_mst)
		媒体ID(0:CD／1:DVD／2:Blu-ray)
		媒体名
		媒体配色(BootStrapのcss名)
		作成日
		更新日
******************************************************/
DROP TABLE IF EXISTS mla_media_mst;
CREATE TABLE mla_media_mst
(
    media_id           INT NOT NULL,
    media_name         VARCHAR(100) NOT NULL,
    media_color        VARCHAR(100) NOT NULL,
    make_date          DATE,
    update_date        DATE,
    PRIMARY KEY (media_id)
);
INSERT INTO mla_media_mst VALUES
	(0, 'CD', 'success', NOW(), NOW()),
	(1, 'DVD', 'warning', NOW(), NOW()),
	(2, 'Blu-ray', 'info', NOW(), NOW());

/******************************************************
	レンタル管理テーブル(mla_rental_tbl)
		レンタルID
		会員ID(会員管理テーブルと紐付け)
		商品ID(商品管理テーブルと紐付け)
		発注日時
		希望順
		発送区分(0:未発送／1:発送済)
		返却区分(0:未返却／1:返却済)
		作成日
		更新日
******************************************************/
DROP TABLE IF EXISTS mla_rental_tbl;
CREATE TABLE mla_rental_tbl
(
    rental_id        SERIAL NOT NULL,
    member_id        INT NOT NULL,
    item_id          INT NOT NULL,
    order_date       DATE NOT NULL,
    hope_order       INT NOT NULL,
    send_flag        INT NOT NULL,
    return_flag      INT NOT NULL,
    make_date        DATE,
    update_date      DATE,
    PRIMARY KEY (rental_id)
);
INSERT INTO mla_rental_tbl VALUES
	(1, 1, 1, cast('2021-02-04' as date ), 1, 0, 0, NOW(), NOW()),
	(2, 1, 2, cast('2021-02-04' as date ), 1, 0, 0, NOW(), NOW());

/******************************************************
	発送管理テーブル(mla_send_tbl)
		発送ID
		会員ID(会員管理テーブルと紐付け)
		レンタルID1(レンタル管理テーブルと紐付け)
		レンタルID2(レンタル管理テーブルと紐付け)
		発送日
		レンタルID1返却日
		レンタルID2返却日
		獲得ポイント
		作成日
		更新日
******************************************************/
DROP TABLE IF EXISTS mla_send_tbl;
CREATE TABLE mla_send_tbl
(
    send_id          SERIAL NOT NULL,
    member_id        INT NOT NULL,
    rental_id1       INT NOT NULL,
    rental_id2       INT NOT NULL,
    send_date        DATE,
    return_date1     DATE,
    return_date2     DATE,
    point            INT NOT NULL,
    make_date        DATE,
    update_date      DATE,
    PRIMARY KEY (send_id)
);
INSERT INTO mla_send_tbl VALUES
	(1, 1, 0, 1, cast('2021-02-05' as date ), NULL, NULL, 0, NOW(), NOW());

/******************************************************
	履歴テーブル(mla_history_tbl)
		履歴ID
		会員ID(会員管理テーブルと紐付け)
		レンタルID(レンタル管理テーブルと紐付け)
		作成日
		更新日
******************************************************/
DROP TABLE IF EXISTS mla_history_tbl;
CREATE TABLE mla_history_tbl
(
    history_id       SERIAL NOT NULL,
    member_id        INT NOT NULL,
    rental_id        INT NOT NULL,
    make_date        DATE,
    update_date      DATE,
    PRIMARY KEY (history_id)
);
INSERT INTO mla_history_tbl VALUES
	(1, 1, 0, NOW(), NOW());

