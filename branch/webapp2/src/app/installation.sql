
    alter table Diagraminstanceobject 
        drop 
        foreign key FK247207872C1AFC2;

    alter table Diagraminstanceobject 
        drop 
        foreign key FK24720787317179E4;

    alter table Diagraminstanceobject 
        drop 
        foreign key FK247207878FE7B982;

    alter table Diagraminstanceobject 
        drop 
        foreign key FK24720787AD2A2C5D;

    alter table adresses 
        drop 
        foreign key FKCE8EE5A467466E58;

    alter table adresses_emails 
        drop 
        foreign key FKA30D5F5231F52DC7;

    alter table adresses_emails 
        drop 
        foreign key FKA30D5F52A40D68C7;

    alter table baseobject 
        drop 
        foreign key FKFE08895061624732;

    alter table diagram 
        drop 
        foreign key FK62A370D386DB55E2;

    alter table diagramobject 
        drop 
        foreign key FKB312E9D227D6E572;

    alter table diagramobject 
        drop 
        foreign key FKB312E9D22E111932;

    alter table errorvalues 
        drop 
        foreign key FKF576C92A7942E9DD;

    alter table navimain 
        drop 
        foreign key FK7D543E5F363F971D;

    alter table navisub 
        drop 
        foreign key FK6723D8DA8D368C1D;

    alter table object 
        drop 
        foreign key FKC300A33FA90713D2;

    alter table objectmarker 
        drop 
        foreign key FKDEEF8C395E0D9ED2;

    alter table organisation_users 
        drop 
        foreign key FKA4E4666381ED7911;

    alter table organisation_users 
        drop 
        foreign key FKA4E46663E2E18827;

    alter table users 
        drop 
        foreign key FK6A68E08A40D68C7;

    drop table if exists Diagraminstanceobject;

    drop table if exists adresses;

    drop table if exists adresses_emails;

    drop table if exists adresses_phones;

    drop table if exists baseobject;

    drop table if exists configuration;

    drop table if exists diagram;

    drop table if exists diagramobject;

    drop table if exists diagramrevision;

    drop table if exists emails;

    drop table if exists errortypes;

    drop table if exists errorvalues;

    drop table if exists fieldlanguage;

    drop table if exists fieldlanguagesvalues;

    drop table if exists fieldvalues;

    drop table if exists markercategory;

    drop table if exists naviglobal;

    drop table if exists navimain;

    drop table if exists navisub;

    drop table if exists object;

    drop table if exists objectidentifier;

    drop table if exists objectmarker;

    drop table if exists objecttype;

    drop table if exists organisation;

    drop table if exists organisation_users;

    drop table if exists phones;

    drop table if exists salutations;

    drop table if exists sessiondata;

    drop table if exists states;

    drop table if exists userdata;

    drop table if exists usergroups;

    drop table if exists userlevel;

    drop table if exists users;

    drop table if exists users_usergroups;

    create table Diagraminstanceobject (
        diagraminstanceobject_id bigint not null,
        diagram_id bigint,
        diagramobject_id bigint,
        inserted datetime,
        insertedby bigint,
        startdiagramobject_id bigint,
        enddiagramobject_id bigint,
        graphObject text,
        primary key (diagraminstanceobject_id)
    ) type=InnoDB;

    create table adresses (
        adresses_id bigint not null,
        additionalname varchar(255),
        comment varchar(255),
        fax varchar(255),
        starttime datetime,
        state_id bigint,
        street varchar(255),
        town varchar(255),
        updatetime datetime,
        zip varchar(255),
        deleted varchar(255),
        primary key (adresses_id)
    ) type=InnoDB;

    create table adresses_emails (
        adresses_emails_id bigint not null,
        adresses_id bigint,
        mail_id bigint,
        starttime datetime,
        updatetime datetime,
        deleted varchar(255),
        primary key (adresses_emails_id)
    ) type=InnoDB;

    create table adresses_phones (
        adresses_phone_id bigint not null,
        adresses_id bigint,
        phone_id bigint,
        starttime datetime,
        updatetime datetime,
        deleted varchar(255),
        primary key (adresses_phone_id)
    ) type=InnoDB;

    create table baseobject (
        baseobject_id bigint not null,
        objecttype_id bigint,
        name varchar(255),
        comment varchar(255),
        deleted varchar(255),
        insertedby bigint,
        updatedby bigint,
        inserted datetime,
        updated datetime,
        primary key (baseobject_id)
    ) type=InnoDB;

    create table configuration (
        configuration_id bigint not null,
        comment varchar(255),
        conf_key varchar(255),
        conf_value varchar(255),
        starttime datetime,
        updatetime datetime,
        deleted varchar(255),
        user_id bigint,
        primary key (configuration_id)
    ) type=InnoDB;

    create table diagram (
        diagram_id bigint not null,
        diagramrevision_id bigint,
        name varchar(255),
        description varchar(255),
        updatedby bigint,
        insertedby bigint,
        updated datetime,
        inserted datetime,
        revisionNumber bigint,
        deleted varchar(255),
        parentdiagram_id bigint,
        diagram_no bigint,
        diagramtype_id bigint,
        primary key (diagram_id)
    ) type=InnoDB;

    create table diagramobject (
        diagramobject_id bigint not null,
        object_id bigint,
        objectmarker_id bigint,
        marker_id bigint,
        deleted varchar(255),
        inserted datetime,
        insertedby bigint,
        name varchar(255),
        updated datetime,
        updatedby bigint,
        objecttypename varchar(255),
        primary key (diagramobject_id)
    ) type=InnoDB;

    create table diagramrevision (
        diagramrevision_id bigint not null,
        insertedby bigint,
        inserted datetime,
        comment varchar(255),
        updatedby bigint,
        updated datetime,
        deleted varchar(255),
        primary key (diagramrevision_id)
    ) type=InnoDB;

    create table emails (
        mail_id bigint not null,
        comment varchar(255),
        email varchar(255),
        startdate datetime,
        updatedate datetime,
        deleted varchar(255),
        primary key (mail_id)
    ) type=InnoDB;

    create table errortypes (
        errortype_id bigint not null,
        starttime datetime,
        updatetime datetime,
        deleted varchar(255),
        label_number bigint,
        primary key (errortype_id)
    ) type=InnoDB;

    create table errorvalues (
        errorvalues_id bigint not null,
        starttime datetime,
        updatetime datetime,
        deleted varchar(255),
        errortype_id bigint,
        label_number bigint,
        primary key (errorvalues_id)
    ) type=InnoDB;

    create table fieldlanguage (
        language_id bigint not null,
        name varchar(255),
        starttime datetime,
        updatetime datetime,
        deleted varchar(255),
        primary key (language_id)
    ) type=InnoDB;

    create table fieldlanguagesvalues (
        fieldlanguagesvalues_id bigint not null,
        starttime datetime,
        updatetime datetime,
        deleted varchar(255),
        label_number bigint not null,
        language_id bigint not null,
        value varchar(255),
        primary key (fieldlanguagesvalues_id)
    ) type=InnoDB;

    create table fieldvalues (
        fieldvalues_id bigint not null,
        label_number bigint,
        name varchar(255),
        starttime datetime,
        updatetime datetime,
        deleted varchar(255),
        primary key (fieldvalues_id)
    ) type=InnoDB;

    create table markercategory (
        markercategory_id bigint not null,
        name varchar(255),
        comment varchar(255),
        insertedby bigint,
        updatedby bigint,
        inserted datetime,
        updated datetime,
        deleted varchar(255),
        primary key (markercategory_id)
    ) type=InnoDB;

    create table naviglobal (
        global_id bigint not null,
        action varchar(255),
        comment varchar(255),
        icon varchar(255),
        isleaf bit,
        isopen bit,
        starttime datetime,
        updatetime datetime,
        deleted varchar(255),
        name varchar(255),
        naviorder integer,
        level_id bigint,
        label_number bigint,
        primary key (global_id)
    ) type=InnoDB;

    create table navimain (
        main_id bigint not null,
        action varchar(255),
        level_id bigint,
        global_id bigint,
        comment varchar(255),
        icon varchar(255),
        isleaf bit,
        isopen bit,
        starttime datetime,
        updatetime datetime,
        deleted varchar(255),
        name varchar(255),
        naviorder integer,
        label_number bigint,
        primary key (main_id)
    ) type=InnoDB;

    create table navisub (
        sub_id bigint not null,
        main_id bigint,
        level_id bigint,
        action varchar(255),
        comment varchar(255),
        icon varchar(255),
        isleaf bit,
        isopen bit,
        name varchar(255),
        naviorder integer,
        starttime datetime,
        updatetime datetime,
        deleted varchar(255),
        label_number bigint,
        primary key (sub_id)
    ) type=InnoDB;

    create table object (
        object_id bigint not null,
        baseobject_id bigint,
        name varchar(255),
        deleted varchar(255),
        updatedBy bigint,
        insertedby bigint,
        updated datetime,
        inserted datetime,
        category_id bigint,
        primary key (object_id)
    ) type=InnoDB;

    create table objectidentifier (
        objectidentifier_id bigint not null,
        organization_id bigint,
        currentid bigint,
        updated datetime,
        primary key (objectidentifier_id)
    ) type=InnoDB;

    create table objectmarker (
        objectmarker_id bigint not null,
        markercategory_id bigint,
        name varchar(255),
        comment varchar(255),
        priority bigint,
        insertedby bigint,
        updatedby bigint,
        inserted datetime,
        updated datetime,
        deleted varchar(255),
        primary key (objectmarker_id)
    ) type=InnoDB;

    create table objecttype (
        objecttype_id bigint not null,
        name varchar(255),
        description varchar(255),
        updatedby bigint,
        insertedby bigint,
        updated datetime,
        inserted datetime,
        deleted varchar(255),
        primary key (objecttype_id)
    ) type=InnoDB;

    create table organisation (
        organisation_id bigint not null,
        insertedby bigint,
        name varchar(255),
        updatedby bigint,
        starttime datetime,
        updatetime datetime,
        deleted varchar(255),
        primary key (organisation_id)
    ) type=InnoDB;

    create table organisation_users (
        organisation_users_id bigint not null,
        organisation_id bigint,
        user_id bigint,
        starttime datetime,
        updatetime datetime,
        deleted varchar(255),
        comment varchar(255),
        primary key (organisation_users_id)
    ) type=InnoDB;

    create table phones (
        phone_id bigint not null,
        comment varchar(255),
        phonevalue varchar(255),
        starttime datetime,
        updatetime datetime,
        deleted varchar(255),
        primary key (phone_id)
    ) type=InnoDB;

    create table salutations (
        salutations_id bigint not null,
        name varchar(255),
        starttime datetime,
        updatetime datetime,
        deleted varchar(255),
        fieldvalues_id bigint,
        primary key (salutations_id)
    ) type=InnoDB;

    create table sessiondata (
        id bigint not null,
        refresh_time datetime,
        session_id varchar(255),
        starttermin_time datetime,
        user_id bigint,
        primary key (id)
    ) type=InnoDB;

    create table states (
        state_id bigint not null,
        name varchar(255),
        starttime datetime,
        updatetime datetime,
        deleted varchar(255),
        primary key (state_id)
    ) type=InnoDB;

    create table userdata (
        data_id bigint not null,
        comment varchar(255),
        data varchar(255),
        data_key varchar(255),
        starttime datetime,
        updatetime datetime,
        deleted varchar(255),
        user_id bigint,
        primary key (data_id)
    ) type=InnoDB;

    create table usergroups (
        usergroup_id bigint not null,
        level_id bigint,
        name varchar(255),
        starttime datetime,
        updatetime datetime,
        deleted varchar(255),
        comment varchar(255),
        description varchar(255),
        user_id bigint,
        primary key (usergroup_id)
    ) type=InnoDB;

    create table userlevel (
        level_id bigint not null,
        description varchar(255),
        starttime datetime,
        updatetime datetime,
        deleted varchar(255),
        statuscode integer,
        primary key (level_id)
    ) type=InnoDB;

    create table users (
        user_id bigint not null,
        adresses_id bigint,
        age datetime,
        availible integer,
        firstname varchar(255),
        lastlogin datetime,
        lastname varchar(255),
        lasttrans bigint,
        level_id bigint,
        login varchar(255),
        password varchar(255),
        regdate datetime,
        status integer,
        title_id integer,
        starttime datetime,
        updatetime datetime,
        deleted varchar(255),
        pictureuri varchar(255),
        language_id bigint,
        resethash varchar(255),
        primary key (user_id)
    ) type=InnoDB;

    create table users_usergroups (
        users_usergroups_id bigint not null,
        comment varchar(255),
        starttime datetime,
        updatetime datetime,
        deleted varchar(255),
        user_id bigint,
        usergroup_id bigint,
        primary key (users_usergroups_id)
    ) type=InnoDB;

    alter table Diagraminstanceobject 
        add index FK247207872C1AFC2 (diagram_id), 
        add constraint FK247207872C1AFC2 
        foreign key (diagram_id) 
        references diagram (diagram_id);

    alter table Diagraminstanceobject 
        add index FK24720787317179E4 (startdiagramobject_id), 
        add constraint FK24720787317179E4 
        foreign key (startdiagramobject_id) 
        references diagramobject (diagramobject_id);

    alter table Diagraminstanceobject 
        add index FK247207878FE7B982 (diagramobject_id), 
        add constraint FK247207878FE7B982 
        foreign key (diagramobject_id) 
        references diagramobject (diagramobject_id);

    alter table Diagraminstanceobject 
        add index FK24720787AD2A2C5D (enddiagramobject_id), 
        add constraint FK24720787AD2A2C5D 
        foreign key (enddiagramobject_id) 
        references diagramobject (diagramobject_id);

    alter table adresses 
        add index FKCE8EE5A467466E58 (state_id), 
        add constraint FKCE8EE5A467466E58 
        foreign key (state_id) 
        references states (state_id);

    alter table adresses_emails 
        add index FKA30D5F5231F52DC7 (mail_id), 
        add constraint FKA30D5F5231F52DC7 
        foreign key (mail_id) 
        references emails (mail_id);

    alter table adresses_emails 
        add index FKA30D5F52A40D68C7 (adresses_id), 
        add constraint FKA30D5F52A40D68C7 
        foreign key (adresses_id) 
        references adresses (adresses_id);

    alter table baseobject 
        add index FKFE08895061624732 (objecttype_id), 
        add constraint FKFE08895061624732 
        foreign key (objecttype_id) 
        references objecttype (objecttype_id);

    alter table diagram 
        add index FK62A370D386DB55E2 (diagramrevision_id), 
        add constraint FK62A370D386DB55E2 
        foreign key (diagramrevision_id) 
        references diagramrevision (diagramrevision_id);

    alter table diagramobject 
        add index FKB312E9D227D6E572 (object_id), 
        add constraint FKB312E9D227D6E572 
        foreign key (object_id) 
        references object (object_id);

    alter table diagramobject 
        add index FKB312E9D22E111932 (objectmarker_id), 
        add constraint FKB312E9D22E111932 
        foreign key (objectmarker_id) 
        references objectmarker (objectmarker_id);

    alter table errorvalues 
        add index FKF576C92A7942E9DD (errortype_id), 
        add constraint FKF576C92A7942E9DD 
        foreign key (errortype_id) 
        references errortypes (errortype_id);

    alter table navimain 
        add index FK7D543E5F363F971D (global_id), 
        add constraint FK7D543E5F363F971D 
        foreign key (global_id) 
        references naviglobal (global_id);

    alter table navisub 
        add index FK6723D8DA8D368C1D (main_id), 
        add constraint FK6723D8DA8D368C1D 
        foreign key (main_id) 
        references navimain (main_id);

    alter table object 
        add index FKC300A33FA90713D2 (baseobject_id), 
        add constraint FKC300A33FA90713D2 
        foreign key (baseobject_id) 
        references baseobject (baseobject_id);

    alter table objectmarker 
        add index FKDEEF8C395E0D9ED2 (markercategory_id), 
        add constraint FKDEEF8C395E0D9ED2 
        foreign key (markercategory_id) 
        references markercategory (markercategory_id);

    alter table organisation_users 
        add index FKA4E4666381ED7911 (user_id), 
        add constraint FKA4E4666381ED7911 
        foreign key (user_id) 
        references users (user_id);

    alter table organisation_users 
        add index FKA4E46663E2E18827 (organisation_id), 
        add constraint FKA4E46663E2E18827 
        foreign key (organisation_id) 
        references organisation (organisation_id);

    alter table users 
        add index FK6A68E08A40D68C7 (adresses_id), 
        add constraint FK6A68E08A40D68C7 
        foreign key (adresses_id) 
        references adresses (adresses_id);
