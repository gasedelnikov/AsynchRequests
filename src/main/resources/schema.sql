CREATE TABLE QUEUE(
    UUID text NOT NULL primary key,
    TYPE text NOT NULL,
    BODY text NOT NULL,
    CREATED_DATE timestamp with time zone NOT NULL
);
