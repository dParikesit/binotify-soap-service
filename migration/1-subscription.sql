CREATE TABLE IF NOT EXISTS subs (
    creator_id INT NOT NULL,
    subscriber_id INT NOT NULL,
    status ENUM("PENDING", "ACCEPTED", "REJECTED") DEFAULT "PENDING" NOT NULL,
    CONSTRAINT PK_Subs PRIMARY KEY (creator_id, sub_id)
);
