create sequence TASK_ID_SEQ start 1 increment 1;
create table TASK (TASK_ID int4 not null, DESCRIPTION varchar(255) not null, INITIAL_HOURS int4 not null, REMAINING_HOURS int4 not null, REMAINING_UPDATED varchar(255), STATUS varchar(255) not null, STORY_ID int4 not null, TITLE varchar(255) not null, primary key (TASK_ID));
