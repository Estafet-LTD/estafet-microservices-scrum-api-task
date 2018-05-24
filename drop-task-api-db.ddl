alter table TASK drop constraint TASK_TO_STORY_FK;
drop table if exists MESSAGE_EVENT cascade;
drop table if exists STORY cascade;
drop table if exists TASK cascade;
drop sequence TASK_ID_SEQ;
