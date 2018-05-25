alter table STORY drop constraint STORY_TO_SPRINT_FK;
alter table TASK drop constraint TASK_TO_STORY_FK;
drop table if exists MESSAGE_EVENT cascade;
drop table if exists SPRINT cascade;
drop table if exists STORY cascade;
drop table if exists TASK cascade;
drop sequence TASK_ID_SEQ;
