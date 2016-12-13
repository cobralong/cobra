
alter table appstore_funny_column_detail_edit add application_sets_type_id int default 0 after reference_column_id;

alter table appstore_funny_column_detail_edit add application_sets_id int default 0 after reference_column_id;
alter table appstore_funny_column_detail add application_sets_id int default 0 after reference_column_id;

insert into funny_client_android_data_parsed value(2,'安卓应用集详情转换草稿数据',0,0,now(),now());

alter table appstore_funny_column_detail_edit modify column no_bundle_ids blob;

alter table appstore_funny_column_detail add application_description blob default null after reference_column_id;
alter table appstore_funny_column_detail_edit add application_description blob default null after reference_column_id;

