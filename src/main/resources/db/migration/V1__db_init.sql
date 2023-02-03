CREATE TABLE bookmarks
(
    cartoon_id BIGINT NOT NULL,
    user_id    BIGINT NOT NULL,
    CONSTRAINT pk_bookmarks PRIMARY KEY (cartoon_id, user_id)
);

CREATE TABLE cartoon_categories
(
    cartoon_id  BIGINT NOT NULL,
    category_id BIGINT NOT NULL
);

CREATE TABLE cartoons
(
    cartoon_id         BIGINT AUTO_INCREMENT NOT NULL,
    name               VARCHAR(255)          NULL,
    type               INT                   NULL,
    author             VARCHAR(255)          NULL,
    available_chapters INT                   NULL,
    publication_year   SMALLINT              NULL,
    CONSTRAINT pk_cartoons PRIMARY KEY (cartoon_id)
);

CREATE TABLE categories
(
    category_id   BIGINT AUTO_INCREMENT NOT NULL,
    name          VARCHAR(255)          NOT NULL,
    `description` VARCHAR(255)          NOT NULL,
    CONSTRAINT pk_categories PRIMARY KEY (category_id)
);

CREATE TABLE chapters
(
    chapter_id       BIGINT AUTO_INCREMENT NOT NULL,
    chapter_number   INT                   NULL,
    name             VARCHAR(255)          NULL,
    publication_date date                  NULL,
    total_pages      INT                   NULL,
    cartoon_id       BIGINT                NULL,
    CONSTRAINT pk_chapters PRIMARY KEY (chapter_id)
);

CREATE TABLE comments
(
    comment_id BIGINT AUTO_INCREMENT NOT NULL,
    user_id    BIGINT                NOT NULL,
    cartoon_id BIGINT                NOT NULL,
    parent_id  BIGINT                NULL,
    content    VARCHAR(255)          NULL,
    created_at datetime              NOT NULL,
    CONSTRAINT pk_comments PRIMARY KEY (comment_id)
);

CREATE TABLE likes
(
    cartoon_id BIGINT NOT NULL,
    user_id    BIGINT NOT NULL,
    CONSTRAINT pk_likes PRIMARY KEY (cartoon_id, user_id)
);

CREATE TABLE manic_user_roles
(
    manic_user_user_id BIGINT NOT NULL,
    roles              INT    NULL
);

CREATE TABLE manic_users
(
    user_id  BIGINT AUTO_INCREMENT NOT NULL,
    username VARCHAR(255)          NOT NULL,
    email    VARCHAR(255)          NOT NULL,
    password VARCHAR(255)          NOT NULL,
    CONSTRAINT pk_manic_users PRIMARY KEY (user_id)
);

CREATE TABLE pages
(
    page_id     BIGINT AUTO_INCREMENT NOT NULL,
    page_number INT                   NULL,
    image_url   VARCHAR(255)          NULL,
    chapter_id  BIGINT                NULL,
    CONSTRAINT pk_pages PRIMARY KEY (page_id)
);

CREATE TABLE suggestions
(
    suggestion_id BIGINT AUTO_INCREMENT NOT NULL,
    user_id       BIGINT                NOT NULL,
    content       VARCHAR(255)          NULL,
    created_at    datetime              NOT NULL,
    CONSTRAINT pk_suggestions PRIMARY KEY (suggestion_id)
);

ALTER TABLE manic_users
    ADD CONSTRAINT uc_141a7ede503063d2f29e01ce9 UNIQUE (username, email);

ALTER TABLE chapters
    ADD CONSTRAINT FK_CHAPTERS_ON_CARTOON FOREIGN KEY (cartoon_id) REFERENCES cartoons (cartoon_id);

ALTER TABLE comments
    ADD CONSTRAINT FK_COMMENTS_ON_CARTOON FOREIGN KEY (cartoon_id) REFERENCES cartoons (cartoon_id);

ALTER TABLE comments
    ADD CONSTRAINT FK_COMMENTS_ON_PARENT FOREIGN KEY (parent_id) REFERENCES comments (comment_id);

ALTER TABLE comments
    ADD CONSTRAINT FK_COMMENTS_ON_USER FOREIGN KEY (user_id) REFERENCES manic_users (user_id);

ALTER TABLE pages
    ADD CONSTRAINT FK_PAGES_ON_CHAPTER FOREIGN KEY (chapter_id) REFERENCES chapters (chapter_id);

ALTER TABLE suggestions
    ADD CONSTRAINT FK_SUGGESTIONS_ON_USER FOREIGN KEY (user_id) REFERENCES manic_users (user_id);

ALTER TABLE bookmarks
    ADD CONSTRAINT fk_bookmarks_on_cartoon FOREIGN KEY (cartoon_id) REFERENCES cartoons (cartoon_id);

ALTER TABLE bookmarks
    ADD CONSTRAINT fk_bookmarks_on_manic_user FOREIGN KEY (user_id) REFERENCES manic_users (user_id);

ALTER TABLE cartoon_categories
    ADD CONSTRAINT fk_carcat_on_cartoon FOREIGN KEY (cartoon_id) REFERENCES cartoons (cartoon_id);

ALTER TABLE cartoon_categories
    ADD CONSTRAINT fk_carcat_on_category FOREIGN KEY (category_id) REFERENCES categories (category_id);

ALTER TABLE likes
    ADD CONSTRAINT fk_likes_on_cartoon FOREIGN KEY (cartoon_id) REFERENCES cartoons (cartoon_id);

ALTER TABLE likes
    ADD CONSTRAINT fk_likes_on_manic_user FOREIGN KEY (user_id) REFERENCES manic_users (user_id);

ALTER TABLE manic_user_roles
    ADD CONSTRAINT fk_manicuser_roles_on_manic_user FOREIGN KEY (manic_user_user_id) REFERENCES manic_users (user_id);
