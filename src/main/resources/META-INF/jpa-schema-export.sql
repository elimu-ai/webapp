
    alter table Application 
       drop 
       foreign key FKn1pft600om9qs7dn754chjk67;

    alter table Application_literacySkills 
       drop 
       foreign key FK6m0x1m1hks48tio7hdlcyumqq;

    alter table Application_numeracySkills 
       drop 
       foreign key FK858mh1kg9x9w8oqip0mkf53tr;

    alter table ApplicationVersion 
       drop 
       foreign key FKf2y9evfvnfd82cot9dhk0ue54;

    alter table ApplicationVersion 
       drop 
       foreign key FKbmfakjprck5g1jlh74xpmp0j7;

    alter table Contributor_roles 
       drop 
       foreign key FKriv03x8alxet23b7b4ivk2vot;

    alter table Emoji_Word 
       drop 
       foreign key FKbkw2j0k8qfhx43docakb9j5ve;

    alter table Emoji_Word 
       drop 
       foreign key FKlwplucw359d2d5i4yelfkwy6f;

    alter table Image_Letter 
       drop 
       foreign key FK9pi4lblfl1s807tlif82cm5mt;

    alter table Image_Letter 
       drop 
       foreign key FK4gkpmcffc4upm7mrudt4clux4;

    alter table Image_literacySkills 
       drop 
       foreign key FKase42u556a5hkq1qau359sohp;

    alter table Image_Number 
       drop 
       foreign key FKitwj5vppcji5now11mp8icipa;

    alter table Image_Number 
       drop 
       foreign key FK70i88cwc0frkrx6lo2hjuav4v;

    alter table Image_numeracySkills 
       drop 
       foreign key FKlsmoxnf75p3c9bsstqxjy39cx;

    alter table Image_Word 
       drop 
       foreign key FKo4boxy1gphgg94k7gsyyf2gkh;

    alter table Image_Word 
       drop 
       foreign key FKm6hxhjn1og47ovf4xyt6uaqff;

    alter table ImageContributionEvent 
       drop 
       foreign key FKlw0g62yxana6js9f7hpc4hybc;

    alter table ImageContributionEvent 
       drop 
       foreign key FK9v3p0869ivi1jeohpkn8sttr0;

    alter table LetterContributionEvent 
       drop 
       foreign key FKrrosmtv3fcec9acjayfruop5;

    alter table LetterContributionEvent 
       drop 
       foreign key FK5do5q62aj1bilg8pwmphr4ugl;

    alter table LetterSound_Letter 
       drop 
       foreign key FKgfio6vxgyrx52nc0so389ibi1;

    alter table LetterSound_Letter 
       drop 
       foreign key FKgb32dkiivg3d8owk1e6hcyu78;

    alter table LetterSound_Sound 
       drop 
       foreign key FKjjypynbc4x7ij6vqsi9e0m3st;

    alter table LetterSound_Sound 
       drop 
       foreign key FKtlgjcxa3jtailq62jrgq1hgl6;

    alter table LetterSoundAssessmentEvent 
       drop 
       foreign key FKehf1rjbixnmdjol91didaj4b5;

    alter table LetterSoundAssessmentEvent 
       drop 
       foreign key FKr3r908t2wb4g6qrft3uheack6;

    alter table LetterSoundContributionEvent 
       drop 
       foreign key FK5uk320agfa13pvh52v6n6ncbs;

    alter table LetterSoundContributionEvent 
       drop 
       foreign key FKqmngc8gfw52jjv9gf9dey1urk;

    alter table LetterSoundLearningEvent 
       drop 
       foreign key FKdm16bp5gb29hsge3thngm1pli;

    alter table LetterSoundLearningEvent 
       drop 
       foreign key FKa7y3jjd44ki8l0ia94siviyn7;

    alter table LetterSoundPeerReviewEvent 
       drop 
       foreign key FK3wapf4y5anhgnjbqna2qjyie4;

    alter table LetterSoundPeerReviewEvent 
       drop 
       foreign key FKcnsd5pxijcu7qmjxs6qr2k2p6;

    alter table Number_Word 
       drop 
       foreign key FKspo8bt34fftva3y5jac7p2no1;

    alter table Number_Word 
       drop 
       foreign key FKim83prd786jsowiyrhdf1vo59;

    alter table NumberAssessmentEvent 
       drop 
       foreign key FKec7ikjyqnxjnkcmjfcv9iy67r;

    alter table NumberAssessmentEvent 
       drop 
       foreign key FK8xswt8aljh7hfnqsg4iyot3gj;

    alter table NumberContributionEvent 
       drop 
       foreign key FK8tr84kkqmavan1jxfmc1pq8h6;

    alter table NumberContributionEvent 
       drop 
       foreign key FKkfyssxfqg6x1vygyhjks96m4u;

    alter table NumberLearningEvent 
       drop 
       foreign key FKelrgxep39nss6majgtkv6pdem;

    alter table NumberLearningEvent 
       drop 
       foreign key FK14drkt4fawgjw0ve761bkt0q;

    alter table NumberPeerReviewEvent 
       drop 
       foreign key FKhcm34w6kojhaiqneed300n9p8;

    alter table NumberPeerReviewEvent 
       drop 
       foreign key FKtq7b81iqfw1dxuk79c2se2onu;

    alter table SoundContributionEvent 
       drop 
       foreign key FKbuah2o1ndo9kpbj39gr8tic3t;

    alter table SoundContributionEvent 
       drop 
       foreign key FK2vw08gkdxfbcp4ufji6nsuyoi;

    alter table StoryBook 
       drop 
       foreign key FKkwr1b53nrmjdvd874vsiql21a;

    alter table StoryBookChapter 
       drop 
       foreign key FKcnlksdikth82ec6nia8tb3cps;

    alter table StoryBookChapter 
       drop 
       foreign key FKaqnew7dipgs9ffkfh2js25l1g;

    alter table StoryBookContributionEvent 
       drop 
       foreign key FK3s4f77htgff63dqy4diam9fc9;

    alter table StoryBookContributionEvent 
       drop 
       foreign key FKpuk0ynailkg93eiaf62tn2phy;

    alter table StoryBookLearningEvent 
       drop 
       foreign key FK8f7cr6xa4n3kgrmffy7crtcrn;

    alter table StoryBookLearningEvent 
       drop 
       foreign key FKi731lr5iofppbd0lod4pjoufr;

    alter table StoryBookParagraph 
       drop 
       foreign key FKdna2npdcgkq74v2306anb4f6s;

    alter table StoryBookParagraph_Word 
       drop 
       foreign key FKecjq9ll62036jtbwl3g9vytg8;

    alter table StoryBookParagraph_Word 
       drop 
       foreign key FK6kx5ydfx49dfl5oy44bold81k;

    alter table StoryBookPeerReviewEvent 
       drop 
       foreign key FKjc6t09q4xff532a4s8jocuvs1;

    alter table StoryBookPeerReviewEvent 
       drop 
       foreign key FKe1bnu38w5m0dc0qkeuuxf4g5h;

    alter table Syllable_Sound 
       drop 
       foreign key FKlfp5s4xc4wi7s7fo980027tl1;

    alter table Syllable_Sound 
       drop 
       foreign key FK627nj3gkx5dn4xn9g4tmnmbdx;

    alter table Video_Letter 
       drop 
       foreign key FK426jmgm09qif0fhqke99ejl8c;

    alter table Video_Letter 
       drop 
       foreign key FKlmbmpg1y1jue79i9w0f3ejetp;

    alter table Video_literacySkills 
       drop 
       foreign key FKcp7t3km7xi2wkraaugjuk5xoo;

    alter table Video_Number 
       drop 
       foreign key FK5lrjvgqa5wnd49g6gsohlm0tg;

    alter table Video_Number 
       drop 
       foreign key FKr064lw8ryd35xks27348u2s9a;

    alter table Video_numeracySkills 
       drop 
       foreign key FKt545qojkqdegjg5muf41ltk86;

    alter table Video_Word 
       drop 
       foreign key FK2un2s9ljli58i2qkjmvdpwfc7;

    alter table Video_Word 
       drop 
       foreign key FKplswdv1whriquc00dsaxrqe0s;

    alter table VideoContributionEvent 
       drop 
       foreign key FKaqradfcqycsr34wswgjpv4x8o;

    alter table VideoContributionEvent 
       drop 
       foreign key FKe87qab0yt7p1gp8yb37v4t82e;

    alter table VideoLearningEvent 
       drop 
       foreign key FKoqqhe1r2epyv55g6jo79t251h;

    alter table VideoLearningEvent 
       drop 
       foreign key FK38rllate5mtlhi6fdiudffm4c;

    alter table Word 
       drop 
       foreign key FKd1ussioi3bpu2tmxm0cim5s5a;

    alter table Word_LetterSound 
       drop 
       foreign key FKnxxaf27n4dfiblvkg73ewiig5;

    alter table Word_LetterSound 
       drop 
       foreign key FKsx4fbojtfe17xitgiofdef23k;

    alter table WordAssessmentEvent 
       drop 
       foreign key FKlxj22iqgrsvw76fld5vsrhb8c;

    alter table WordAssessmentEvent 
       drop 
       foreign key FKeh2bf4xeskf6netv0nsy86m3d;

    alter table WordContributionEvent 
       drop 
       foreign key FKrsen7udud4svhc32e3rhkcmnu;

    alter table WordContributionEvent 
       drop 
       foreign key FKkwqjkxvg3rvmp1kys6fr8blwq;

    alter table WordLearningEvent 
       drop 
       foreign key FKb5jjaetgs99whlxywbi0palby;

    alter table WordLearningEvent 
       drop 
       foreign key FK221ytgbt7u8eajdics6a5sloe;

    alter table WordPeerReviewEvent 
       drop 
       foreign key FKp3i671x4kb823ayc73381gk33;

    alter table WordPeerReviewEvent 
       drop 
       foreign key FKjyi59inavblt0afri8vd3xhw1;

    drop table if exists Application;

    drop table if exists Application_literacySkills;

    drop table if exists Application_numeracySkills;

    drop table if exists ApplicationVersion;

    drop table if exists Contributor;

    drop table if exists Contributor_roles;

    drop table if exists DbMigration;

    drop table if exists Device;

    drop table if exists Emoji;

    drop table if exists Emoji_Word;

    drop table if exists Image;

    drop table if exists Image_Letter;

    drop table if exists Image_literacySkills;

    drop table if exists Image_Number;

    drop table if exists Image_numeracySkills;

    drop table if exists Image_Word;

    drop table if exists ImageContributionEvent;

    drop table if exists Letter;

    drop table if exists LetterContributionEvent;

    drop table if exists LetterSound;

    drop table if exists LetterSound_Letter;

    drop table if exists LetterSound_Sound;

    drop table if exists LetterSoundAssessmentEvent;

    drop table if exists LetterSoundContributionEvent;

    drop table if exists LetterSoundLearningEvent;

    drop table if exists LetterSoundPeerReviewEvent;

    drop table if exists Number;

    drop table if exists Number_Word;

    drop table if exists NumberAssessmentEvent;

    drop table if exists NumberContributionEvent;

    drop table if exists NumberLearningEvent;

    drop table if exists NumberPeerReviewEvent;

    drop table if exists Sound;

    drop table if exists SoundContributionEvent;

    drop table if exists StoryBook;

    drop table if exists StoryBookChapter;

    drop table if exists StoryBookContributionEvent;

    drop table if exists StoryBookLearningEvent;

    drop table if exists StoryBookParagraph;

    drop table if exists StoryBookParagraph_Word;

    drop table if exists StoryBookPeerReviewEvent;

    drop table if exists Student;

    drop table if exists Syllable;

    drop table if exists Syllable_Sound;

    drop table if exists Video;

    drop table if exists Video_Letter;

    drop table if exists Video_literacySkills;

    drop table if exists Video_Number;

    drop table if exists Video_numeracySkills;

    drop table if exists Video_Word;

    drop table if exists VideoContributionEvent;

    drop table if exists VideoLearningEvent;

    drop table if exists Word;

    drop table if exists Word_LetterSound;

    drop table if exists WordAssessmentEvent;

    drop table if exists WordContributionEvent;

    drop table if exists WordLearningEvent;

    drop table if exists WordPeerReviewEvent;

    create table Application (
       id bigint not null auto_increment,
        applicationStatus varchar(255),
        infrastructural bit not null,
        packageName varchar(255),
        repoName varchar(255),
        contributor_id bigint,
        primary key (id)
    ) engine=InnoDB;

    create table Application_literacySkills (
       Application_id bigint not null,
        literacySkills varchar(255)
    ) engine=InnoDB;

    create table Application_numeracySkills (
       Application_id bigint not null,
        numeracySkills varchar(255)
    ) engine=InnoDB;

    create table ApplicationVersion (
       id bigint not null auto_increment,
        checksumMd5 varchar(255),
        contentType varchar(255),
        fileSizeInKb integer,
        icon mediumblob,
        label varchar(255),
        minSdkVersion integer,
        timeUploaded datetime,
        versionCode integer,
        versionName varchar(255),
        application_id bigint,
        contributor_id bigint,
        primary key (id)
    ) engine=InnoDB;

    create table Contributor (
       id bigint not null auto_increment,
        email varchar(255),
        firstName varchar(255),
        imageUrl varchar(255),
        lastName varchar(255),
        motivation varchar(1000),
        providerIdDiscord varchar(255),
        providerIdGitHub varchar(255),
        providerIdWeb3 varchar(42),
        registrationTime datetime,
        usernameDiscord varchar(255),
        usernameGitHub varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table Contributor_roles (
       Contributor_id bigint not null,
        roles varchar(255)
    ) engine=InnoDB;

    create table DbMigration (
       id bigint not null auto_increment,
        calendar datetime,
        script varchar(10000),
        version integer,
        primary key (id)
    ) engine=InnoDB;

    create table Device (
       id bigint not null auto_increment,
        androidId varchar(255),
        deviceManufacturer varchar(255),
        deviceModel varchar(255),
        deviceSerial varchar(255),
        osVersion integer,
        remoteAddress varchar(255),
        timeRegistered datetime,
        primary key (id)
    ) engine=InnoDB;

    create table Emoji (
       id bigint not null auto_increment,
        contentStatus varchar(255),
        peerReviewStatus varchar(255),
        revisionNumber integer,
        usageCount integer,
        glyph varchar(4),
        unicodeEmojiVersion float(53),
        unicodeVersion float(53),
        primary key (id)
    ) engine=InnoDB;

    create table Emoji_Word (
       Emoji_id bigint not null,
        words_id bigint not null,
        primary key (Emoji_id, words_id)
    ) engine=InnoDB;

    create table Image (
       id bigint not null auto_increment,
        contentStatus varchar(255),
        peerReviewStatus varchar(255),
        revisionNumber integer,
        usageCount integer,
        attributionUrl varchar(1000),
        contentLicense varchar(255),
        contentType varchar(255),
        checksumGitHub varchar(255),
        checksumMd5 varchar(255),
        dominantColor varchar(255),
        fileSize integer,
        imageFormat varchar(255),
        title varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table Image_Letter (
       Image_id bigint not null,
        letters_id bigint not null,
        primary key (Image_id, letters_id)
    ) engine=InnoDB;

    create table Image_literacySkills (
       Image_id bigint not null,
        literacySkills varchar(255)
    ) engine=InnoDB;

    create table Image_Number (
       Image_id bigint not null,
        numbers_id bigint not null,
        primary key (Image_id, numbers_id)
    ) engine=InnoDB;

    create table Image_numeracySkills (
       Image_id bigint not null,
        numeracySkills varchar(255)
    ) engine=InnoDB;

    create table Image_Word (
       Image_id bigint not null,
        words_id bigint not null,
        primary key (Image_id, words_id)
    ) engine=InnoDB;

    create table ImageContributionEvent (
       id bigint not null auto_increment,
        comment varchar(1000),
        revisionNumber integer,
        timestamp datetime,
        contributor_id bigint,
        image_id bigint,
        primary key (id)
    ) engine=InnoDB;

    create table Letter (
       id bigint not null auto_increment,
        contentStatus varchar(255),
        peerReviewStatus varchar(255),
        revisionNumber integer,
        usageCount integer,
        diacritic bit not null,
        text varchar(4),
        primary key (id)
    ) engine=InnoDB;

    create table LetterContributionEvent (
       id bigint not null auto_increment,
        comment varchar(1000),
        revisionNumber integer,
        timestamp datetime,
        contributor_id bigint,
        letter_id bigint,
        primary key (id)
    ) engine=InnoDB;

    create table LetterSound (
       id bigint not null auto_increment,
        contentStatus varchar(255),
        peerReviewStatus varchar(255),
        revisionNumber integer,
        usageCount integer,
        primary key (id)
    ) engine=InnoDB;

    create table LetterSound_Letter (
       LetterSound_id bigint not null,
        letters_id bigint not null,
        letters_ORDER integer not null,
        primary key (LetterSound_id, letters_ORDER)
    ) engine=InnoDB;

    create table LetterSound_Sound (
       LetterSound_id bigint not null,
        sounds_id bigint not null,
        sounds_ORDER integer not null,
        primary key (LetterSound_id, sounds_ORDER)
    ) engine=InnoDB;

    create table LetterSoundAssessmentEvent (
       id bigint not null auto_increment,
        additionalData varchar(1024),
        androidId varchar(255),
        experimentGroup smallint,
        masteryScore float(23),
        packageName varchar(255),
        researchExperiment smallint,
        timeSpentMs bigint,
        timestamp datetime,
        letterSoundId bigint,
        letterSoundLetters varchar(255),
        letterSoundSounds varchar(255),
        application_id bigint,
        letterSound_id bigint,
        primary key (id)
    ) engine=InnoDB;

    create table LetterSoundContributionEvent (
       id bigint not null auto_increment,
        comment varchar(1000),
        revisionNumber integer,
        timestamp datetime,
        contributor_id bigint,
        letterSound_id bigint,
        primary key (id)
    ) engine=InnoDB;

    create table LetterSoundLearningEvent (
       id bigint not null auto_increment,
        additionalData varchar(1024),
        androidId varchar(255),
        experimentGroup smallint,
        learningEventType varchar(255),
        packageName varchar(255),
        researchExperiment smallint,
        timestamp datetime,
        letterSoundId bigint,
        letterSoundLetters varchar(255),
        letterSoundSounds varchar(255),
        application_id bigint,
        letterSound_id bigint,
        primary key (id)
    ) engine=InnoDB;

    create table LetterSoundPeerReviewEvent (
       id bigint not null auto_increment,
        approved bit,
        comment varchar(1000),
        timestamp datetime,
        contributor_id bigint,
        letterSoundContributionEvent_id bigint,
        primary key (id)
    ) engine=InnoDB;

    create table Number (
       id bigint not null auto_increment,
        contentStatus varchar(255),
        peerReviewStatus varchar(255),
        revisionNumber integer,
        usageCount integer,
        symbol varchar(255),
        value integer,
        primary key (id)
    ) engine=InnoDB;

    create table Number_Word (
       Number_id bigint not null,
        words_id bigint not null,
        words_ORDER integer not null,
        primary key (Number_id, words_ORDER)
    ) engine=InnoDB;

    create table NumberAssessmentEvent (
       id bigint not null auto_increment,
        additionalData varchar(1024),
        androidId varchar(255),
        experimentGroup smallint,
        masteryScore float(23),
        packageName varchar(255),
        researchExperiment smallint,
        timeSpentMs bigint,
        timestamp datetime,
        numberId bigint,
        numberSymbol varchar(255),
        numberValue integer,
        application_id bigint,
        number_id bigint,
        primary key (id)
    ) engine=InnoDB;

    create table NumberContributionEvent (
       id bigint not null auto_increment,
        comment varchar(1000),
        revisionNumber integer,
        timestamp datetime,
        contributor_id bigint,
        number_id bigint,
        primary key (id)
    ) engine=InnoDB;

    create table NumberLearningEvent (
       id bigint not null auto_increment,
        additionalData varchar(1024),
        androidId varchar(255),
        experimentGroup smallint,
        learningEventType varchar(255),
        packageName varchar(255),
        researchExperiment smallint,
        timestamp datetime,
        numberId bigint,
        numberSymbol varchar(255),
        numberValue integer,
        application_id bigint,
        number_id bigint,
        primary key (id)
    ) engine=InnoDB;

    create table NumberPeerReviewEvent (
       id bigint not null auto_increment,
        approved bit,
        comment varchar(1000),
        timestamp datetime,
        contributor_id bigint,
        numberContributionEvent_id bigint,
        primary key (id)
    ) engine=InnoDB;

    create table Sound (
       id bigint not null auto_increment,
        contentStatus varchar(255),
        peerReviewStatus varchar(255),
        revisionNumber integer,
        usageCount integer,
        consonantPlace varchar(255),
        consonantType varchar(255),
        consonantVoicing varchar(255),
        diacritic bit not null,
        lipRounding varchar(255),
        soundType varchar(255),
        valueIpa varchar(3),
        valueSampa varchar(5),
        vowelFrontness varchar(255),
        vowelHeight varchar(255),
        vowelLength varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table SoundContributionEvent (
       id bigint not null auto_increment,
        comment varchar(1000),
        revisionNumber integer,
        timestamp datetime,
        contributor_id bigint,
        sound_id bigint,
        primary key (id)
    ) engine=InnoDB;

    create table StoryBook (
       id bigint not null auto_increment,
        contentStatus varchar(255),
        peerReviewStatus varchar(255),
        revisionNumber integer,
        usageCount integer,
        attributionUrl varchar(1000),
        contentLicense varchar(255),
        description varchar(1024),
        readingLevel varchar(255),
        title varchar(255),
        coverImage_id bigint,
        primary key (id)
    ) engine=InnoDB;

    create table StoryBookChapter (
       id bigint not null auto_increment,
        sortOrder integer,
        image_id bigint,
        storyBook_id bigint,
        primary key (id)
    ) engine=InnoDB;

    create table StoryBookContributionEvent (
       id bigint not null auto_increment,
        comment varchar(1000),
        revisionNumber integer,
        timestamp datetime,
        paragraphTextAfter varchar(1000),
        paragraphTextBefore varchar(1000),
        contributor_id bigint,
        storyBook_id bigint,
        primary key (id)
    ) engine=InnoDB;

    create table StoryBookLearningEvent (
       id bigint not null auto_increment,
        additionalData varchar(1024),
        androidId varchar(255),
        experimentGroup smallint,
        learningEventType varchar(255),
        packageName varchar(255),
        researchExperiment smallint,
        timestamp datetime,
        storyBookId bigint,
        storyBookTitle varchar(255),
        application_id bigint,
        storyBook_id bigint,
        primary key (id)
    ) engine=InnoDB;

    create table StoryBookParagraph (
       id bigint not null auto_increment,
        originalText varchar(1024),
        sortOrder integer,
        storyBookChapter_id bigint,
        primary key (id)
    ) engine=InnoDB;

    create table StoryBookParagraph_Word (
       StoryBookParagraph_id bigint not null,
        words_id bigint not null,
        words_ORDER integer not null,
        primary key (StoryBookParagraph_id, words_ORDER)
    ) engine=InnoDB;

    create table StoryBookPeerReviewEvent (
       id bigint not null auto_increment,
        approved bit,
        comment varchar(1000),
        timestamp datetime,
        contributor_id bigint,
        storyBookContributionEvent_id bigint,
        primary key (id)
    ) engine=InnoDB;

    create table Student (
       id bigint not null auto_increment,
        androidId varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table Syllable (
       id bigint not null auto_increment,
        contentStatus varchar(255),
        peerReviewStatus varchar(255),
        revisionNumber integer,
        usageCount integer,
        text varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table Syllable_Sound (
       Syllable_id bigint not null,
        sounds_id bigint not null,
        sounds_ORDER integer not null,
        primary key (Syllable_id, sounds_ORDER)
    ) engine=InnoDB;

    create table Video (
       id bigint not null auto_increment,
        contentStatus varchar(255),
        peerReviewStatus varchar(255),
        revisionNumber integer,
        usageCount integer,
        attributionUrl varchar(1000),
        contentLicense varchar(255),
        contentType varchar(255),
        checksumGitHub varchar(255),
        checksumMd5 varchar(255),
        fileSize integer,
        thumbnail mediumblob,
        title varchar(255),
        videoFormat varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table Video_Letter (
       Video_id bigint not null,
        letters_id bigint not null,
        primary key (Video_id, letters_id)
    ) engine=InnoDB;

    create table Video_literacySkills (
       Video_id bigint not null,
        literacySkills varchar(255)
    ) engine=InnoDB;

    create table Video_Number (
       Video_id bigint not null,
        numbers_id bigint not null,
        primary key (Video_id, numbers_id)
    ) engine=InnoDB;

    create table Video_numeracySkills (
       Video_id bigint not null,
        numeracySkills varchar(255)
    ) engine=InnoDB;

    create table Video_Word (
       Video_id bigint not null,
        words_id bigint not null,
        primary key (Video_id, words_id)
    ) engine=InnoDB;

    create table VideoContributionEvent (
       id bigint not null auto_increment,
        comment varchar(1000),
        revisionNumber integer,
        timestamp datetime,
        contributor_id bigint,
        video_id bigint,
        primary key (id)
    ) engine=InnoDB;

    create table VideoLearningEvent (
       id bigint not null auto_increment,
        additionalData varchar(1024),
        androidId varchar(255),
        experimentGroup smallint,
        learningEventType varchar(255),
        packageName varchar(255),
        researchExperiment smallint,
        timestamp datetime,
        videoId bigint,
        videoTitle varchar(255),
        application_id bigint,
        video_id bigint,
        primary key (id)
    ) engine=InnoDB;

    create table Word (
       id bigint not null auto_increment,
        contentStatus varchar(255),
        peerReviewStatus varchar(255),
        revisionNumber integer,
        usageCount integer,
        spellingConsistency varchar(255),
        text varchar(255),
        wordType varchar(255),
        rootWord_id bigint,
        primary key (id)
    ) engine=InnoDB;

    create table Word_LetterSound (
       Word_id bigint not null,
        letterSounds_id bigint not null,
        letterSounds_ORDER integer not null,
        primary key (Word_id, letterSounds_ORDER)
    ) engine=InnoDB;

    create table WordAssessmentEvent (
       id bigint not null auto_increment,
        additionalData varchar(1024),
        androidId varchar(255),
        experimentGroup smallint,
        masteryScore float(23),
        packageName varchar(255),
        researchExperiment smallint,
        timeSpentMs bigint,
        timestamp datetime,
        wordId bigint,
        wordText varchar(255),
        application_id bigint,
        word_id bigint,
        primary key (id)
    ) engine=InnoDB;

    create table WordContributionEvent (
       id bigint not null auto_increment,
        comment varchar(1000),
        revisionNumber integer,
        timestamp datetime,
        contributor_id bigint,
        word_id bigint,
        primary key (id)
    ) engine=InnoDB;

    create table WordLearningEvent (
       id bigint not null auto_increment,
        additionalData varchar(1024),
        androidId varchar(255),
        experimentGroup smallint,
        learningEventType varchar(255),
        packageName varchar(255),
        researchExperiment smallint,
        timestamp datetime,
        wordId bigint,
        wordText varchar(255),
        application_id bigint,
        word_id bigint,
        primary key (id)
    ) engine=InnoDB;

    create table WordPeerReviewEvent (
       id bigint not null auto_increment,
        approved bit,
        comment varchar(1000),
        timestamp datetime,
        contributor_id bigint,
        wordContributionEvent_id bigint,
        primary key (id)
    ) engine=InnoDB;

    alter table Contributor 
       add constraint UK_se15thb3bqtr3sw28rgf1v8ia unique (email);

    alter table Contributor 
       add constraint UK_g3my0evexejqgidmkse6suxt3 unique (providerIdWeb3);

    alter table DbMigration 
       add constraint UK_gdl53lyf9qvi56fmbrk9nfrg9 unique (version);

    alter table Device 
       add constraint UK_c2646199whiqrkjbht7hwyr3v unique (androidId);

    alter table Student 
       add constraint UK_ac9n51iqy52mto0jrnkqlk3ld unique (androidId);

    alter table Application 
       add constraint FKn1pft600om9qs7dn754chjk67 
       foreign key (contributor_id) 
       references Contributor (id);

    alter table Application_literacySkills 
       add constraint FK6m0x1m1hks48tio7hdlcyumqq 
       foreign key (Application_id) 
       references Application (id);

    alter table Application_numeracySkills 
       add constraint FK858mh1kg9x9w8oqip0mkf53tr 
       foreign key (Application_id) 
       references Application (id);

    alter table ApplicationVersion 
       add constraint FKf2y9evfvnfd82cot9dhk0ue54 
       foreign key (application_id) 
       references Application (id);

    alter table ApplicationVersion 
       add constraint FKbmfakjprck5g1jlh74xpmp0j7 
       foreign key (contributor_id) 
       references Contributor (id);

    alter table Contributor_roles 
       add constraint FKriv03x8alxet23b7b4ivk2vot 
       foreign key (Contributor_id) 
       references Contributor (id);

    alter table Emoji_Word 
       add constraint FKbkw2j0k8qfhx43docakb9j5ve 
       foreign key (words_id) 
       references Word (id);

    alter table Emoji_Word 
       add constraint FKlwplucw359d2d5i4yelfkwy6f 
       foreign key (Emoji_id) 
       references Emoji (id);

    alter table Image_Letter 
       add constraint FK9pi4lblfl1s807tlif82cm5mt 
       foreign key (letters_id) 
       references Letter (id);

    alter table Image_Letter 
       add constraint FK4gkpmcffc4upm7mrudt4clux4 
       foreign key (Image_id) 
       references Image (id);

    alter table Image_literacySkills 
       add constraint FKase42u556a5hkq1qau359sohp 
       foreign key (Image_id) 
       references Image (id);

    alter table Image_Number 
       add constraint FKitwj5vppcji5now11mp8icipa 
       foreign key (numbers_id) 
       references Number (id);

    alter table Image_Number 
       add constraint FK70i88cwc0frkrx6lo2hjuav4v 
       foreign key (Image_id) 
       references Image (id);

    alter table Image_numeracySkills 
       add constraint FKlsmoxnf75p3c9bsstqxjy39cx 
       foreign key (Image_id) 
       references Image (id);

    alter table Image_Word 
       add constraint FKo4boxy1gphgg94k7gsyyf2gkh 
       foreign key (words_id) 
       references Word (id);

    alter table Image_Word 
       add constraint FKm6hxhjn1og47ovf4xyt6uaqff 
       foreign key (Image_id) 
       references Image (id);

    alter table ImageContributionEvent 
       add constraint FKlw0g62yxana6js9f7hpc4hybc 
       foreign key (contributor_id) 
       references Contributor (id);

    alter table ImageContributionEvent 
       add constraint FK9v3p0869ivi1jeohpkn8sttr0 
       foreign key (image_id) 
       references Image (id);

    alter table LetterContributionEvent 
       add constraint FKrrosmtv3fcec9acjayfruop5 
       foreign key (contributor_id) 
       references Contributor (id);

    alter table LetterContributionEvent 
       add constraint FK5do5q62aj1bilg8pwmphr4ugl 
       foreign key (letter_id) 
       references Letter (id);

    alter table LetterSound_Letter 
       add constraint FKgfio6vxgyrx52nc0so389ibi1 
       foreign key (letters_id) 
       references Letter (id);

    alter table LetterSound_Letter 
       add constraint FKgb32dkiivg3d8owk1e6hcyu78 
       foreign key (LetterSound_id) 
       references LetterSound (id);

    alter table LetterSound_Sound 
       add constraint FKjjypynbc4x7ij6vqsi9e0m3st 
       foreign key (sounds_id) 
       references Sound (id);

    alter table LetterSound_Sound 
       add constraint FKtlgjcxa3jtailq62jrgq1hgl6 
       foreign key (LetterSound_id) 
       references LetterSound (id);

    alter table LetterSoundAssessmentEvent 
       add constraint FKehf1rjbixnmdjol91didaj4b5 
       foreign key (application_id) 
       references Application (id);

    alter table LetterSoundAssessmentEvent 
       add constraint FKr3r908t2wb4g6qrft3uheack6 
       foreign key (letterSound_id) 
       references LetterSound (id);

    alter table LetterSoundContributionEvent 
       add constraint FK5uk320agfa13pvh52v6n6ncbs 
       foreign key (contributor_id) 
       references Contributor (id);

    alter table LetterSoundContributionEvent 
       add constraint FKqmngc8gfw52jjv9gf9dey1urk 
       foreign key (letterSound_id) 
       references LetterSound (id);

    alter table LetterSoundLearningEvent 
       add constraint FKdm16bp5gb29hsge3thngm1pli 
       foreign key (application_id) 
       references Application (id);

    alter table LetterSoundLearningEvent 
       add constraint FKa7y3jjd44ki8l0ia94siviyn7 
       foreign key (letterSound_id) 
       references LetterSound (id);

    alter table LetterSoundPeerReviewEvent 
       add constraint FK3wapf4y5anhgnjbqna2qjyie4 
       foreign key (contributor_id) 
       references Contributor (id);

    alter table LetterSoundPeerReviewEvent 
       add constraint FKcnsd5pxijcu7qmjxs6qr2k2p6 
       foreign key (letterSoundContributionEvent_id) 
       references LetterSoundContributionEvent (id);

    alter table Number_Word 
       add constraint FKspo8bt34fftva3y5jac7p2no1 
       foreign key (words_id) 
       references Word (id);

    alter table Number_Word 
       add constraint FKim83prd786jsowiyrhdf1vo59 
       foreign key (Number_id) 
       references Number (id);

    alter table NumberAssessmentEvent 
       add constraint FKec7ikjyqnxjnkcmjfcv9iy67r 
       foreign key (application_id) 
       references Application (id);

    alter table NumberAssessmentEvent 
       add constraint FK8xswt8aljh7hfnqsg4iyot3gj 
       foreign key (number_id) 
       references Number (id);

    alter table NumberContributionEvent 
       add constraint FK8tr84kkqmavan1jxfmc1pq8h6 
       foreign key (contributor_id) 
       references Contributor (id);

    alter table NumberContributionEvent 
       add constraint FKkfyssxfqg6x1vygyhjks96m4u 
       foreign key (number_id) 
       references Number (id);

    alter table NumberLearningEvent 
       add constraint FKelrgxep39nss6majgtkv6pdem 
       foreign key (application_id) 
       references Application (id);

    alter table NumberLearningEvent 
       add constraint FK14drkt4fawgjw0ve761bkt0q 
       foreign key (number_id) 
       references Number (id);

    alter table NumberPeerReviewEvent 
       add constraint FKhcm34w6kojhaiqneed300n9p8 
       foreign key (contributor_id) 
       references Contributor (id);

    alter table NumberPeerReviewEvent 
       add constraint FKtq7b81iqfw1dxuk79c2se2onu 
       foreign key (numberContributionEvent_id) 
       references NumberContributionEvent (id);

    alter table SoundContributionEvent 
       add constraint FKbuah2o1ndo9kpbj39gr8tic3t 
       foreign key (contributor_id) 
       references Contributor (id);

    alter table SoundContributionEvent 
       add constraint FK2vw08gkdxfbcp4ufji6nsuyoi 
       foreign key (sound_id) 
       references Sound (id);

    alter table StoryBook 
       add constraint FKkwr1b53nrmjdvd874vsiql21a 
       foreign key (coverImage_id) 
       references Image (id);

    alter table StoryBookChapter 
       add constraint FKcnlksdikth82ec6nia8tb3cps 
       foreign key (image_id) 
       references Image (id);

    alter table StoryBookChapter 
       add constraint FKaqnew7dipgs9ffkfh2js25l1g 
       foreign key (storyBook_id) 
       references StoryBook (id);

    alter table StoryBookContributionEvent 
       add constraint FK3s4f77htgff63dqy4diam9fc9 
       foreign key (contributor_id) 
       references Contributor (id);

    alter table StoryBookContributionEvent 
       add constraint FKpuk0ynailkg93eiaf62tn2phy 
       foreign key (storyBook_id) 
       references StoryBook (id);

    alter table StoryBookLearningEvent 
       add constraint FK8f7cr6xa4n3kgrmffy7crtcrn 
       foreign key (application_id) 
       references Application (id);

    alter table StoryBookLearningEvent 
       add constraint FKi731lr5iofppbd0lod4pjoufr 
       foreign key (storyBook_id) 
       references StoryBook (id);

    alter table StoryBookParagraph 
       add constraint FKdna2npdcgkq74v2306anb4f6s 
       foreign key (storyBookChapter_id) 
       references StoryBookChapter (id);

    alter table StoryBookParagraph_Word 
       add constraint FKecjq9ll62036jtbwl3g9vytg8 
       foreign key (words_id) 
       references Word (id);

    alter table StoryBookParagraph_Word 
       add constraint FK6kx5ydfx49dfl5oy44bold81k 
       foreign key (StoryBookParagraph_id) 
       references StoryBookParagraph (id);

    alter table StoryBookPeerReviewEvent 
       add constraint FKjc6t09q4xff532a4s8jocuvs1 
       foreign key (contributor_id) 
       references Contributor (id);

    alter table StoryBookPeerReviewEvent 
       add constraint FKe1bnu38w5m0dc0qkeuuxf4g5h 
       foreign key (storyBookContributionEvent_id) 
       references StoryBookContributionEvent (id);

    alter table Syllable_Sound 
       add constraint FKlfp5s4xc4wi7s7fo980027tl1 
       foreign key (sounds_id) 
       references Sound (id);

    alter table Syllable_Sound 
       add constraint FK627nj3gkx5dn4xn9g4tmnmbdx 
       foreign key (Syllable_id) 
       references Syllable (id);

    alter table Video_Letter 
       add constraint FK426jmgm09qif0fhqke99ejl8c 
       foreign key (letters_id) 
       references Letter (id);

    alter table Video_Letter 
       add constraint FKlmbmpg1y1jue79i9w0f3ejetp 
       foreign key (Video_id) 
       references Video (id);

    alter table Video_literacySkills 
       add constraint FKcp7t3km7xi2wkraaugjuk5xoo 
       foreign key (Video_id) 
       references Video (id);

    alter table Video_Number 
       add constraint FK5lrjvgqa5wnd49g6gsohlm0tg 
       foreign key (numbers_id) 
       references Number (id);

    alter table Video_Number 
       add constraint FKr064lw8ryd35xks27348u2s9a 
       foreign key (Video_id) 
       references Video (id);

    alter table Video_numeracySkills 
       add constraint FKt545qojkqdegjg5muf41ltk86 
       foreign key (Video_id) 
       references Video (id);

    alter table Video_Word 
       add constraint FK2un2s9ljli58i2qkjmvdpwfc7 
       foreign key (words_id) 
       references Word (id);

    alter table Video_Word 
       add constraint FKplswdv1whriquc00dsaxrqe0s 
       foreign key (Video_id) 
       references Video (id);

    alter table VideoContributionEvent 
       add constraint FKaqradfcqycsr34wswgjpv4x8o 
       foreign key (contributor_id) 
       references Contributor (id);

    alter table VideoContributionEvent 
       add constraint FKe87qab0yt7p1gp8yb37v4t82e 
       foreign key (video_id) 
       references Video (id);

    alter table VideoLearningEvent 
       add constraint FKoqqhe1r2epyv55g6jo79t251h 
       foreign key (application_id) 
       references Application (id);

    alter table VideoLearningEvent 
       add constraint FK38rllate5mtlhi6fdiudffm4c 
       foreign key (video_id) 
       references Video (id);

    alter table Word 
       add constraint FKd1ussioi3bpu2tmxm0cim5s5a 
       foreign key (rootWord_id) 
       references Word (id);

    alter table Word_LetterSound 
       add constraint FKnxxaf27n4dfiblvkg73ewiig5 
       foreign key (letterSounds_id) 
       references LetterSound (id);

    alter table Word_LetterSound 
       add constraint FKsx4fbojtfe17xitgiofdef23k 
       foreign key (Word_id) 
       references Word (id);

    alter table WordAssessmentEvent 
       add constraint FKlxj22iqgrsvw76fld5vsrhb8c 
       foreign key (application_id) 
       references Application (id);

    alter table WordAssessmentEvent 
       add constraint FKeh2bf4xeskf6netv0nsy86m3d 
       foreign key (word_id) 
       references Word (id);

    alter table WordContributionEvent 
       add constraint FKrsen7udud4svhc32e3rhkcmnu 
       foreign key (contributor_id) 
       references Contributor (id);

    alter table WordContributionEvent 
       add constraint FKkwqjkxvg3rvmp1kys6fr8blwq 
       foreign key (word_id) 
       references Word (id);

    alter table WordLearningEvent 
       add constraint FKb5jjaetgs99whlxywbi0palby 
       foreign key (application_id) 
       references Application (id);

    alter table WordLearningEvent 
       add constraint FK221ytgbt7u8eajdics6a5sloe 
       foreign key (word_id) 
       references Word (id);

    alter table WordPeerReviewEvent 
       add constraint FKp3i671x4kb823ayc73381gk33 
       foreign key (contributor_id) 
       references Contributor (id);

    alter table WordPeerReviewEvent 
       add constraint FKjyi59inavblt0afri8vd3xhw1 
       foreign key (wordContributionEvent_id) 
       references WordContributionEvent (id);
