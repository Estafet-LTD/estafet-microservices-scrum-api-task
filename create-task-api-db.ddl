create sequence TASK_ID_SEQ start 1 increment 1;
create table MESSAGE_EVENT (TOPIC_ID varchar(255) not null, MESSAGE_REFERENCE varchar(255) not null, VERSION int4, primary key (TOPIC_ID));
create table STORY (STORY_ID int4 not null, SPRINT_ID int4, STATUS varchar(255) not null, primary key (STORY_ID));
create table TASK (TASK_ID int4 not null, DESCRIPTION varchar(255) not null, INITIAL_HOURS int4 not null, REMAINING_HOURS int4 not null, REMAINING_UPDATED varchar(255), STATUS varchar(255) not null, TITLE varchar(255) not null, STORY_ID int4 not null, primary key (TASK_ID));
alter table TASK add constraint FK2fjocyiwq3pnx8ihjw323nex0 foreign key (STORY_ID) references STORY;
