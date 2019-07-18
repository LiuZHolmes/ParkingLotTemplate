 
    
    drop table criminal_case if exists;
 
    
    drop table criminal_elements if exists;
 
    
    drop table procuratorate if exists;
 
    
    drop table procuratorate_prosecutors if exists;
 
    
    drop table prosecutor if exists;
 
    
    create table criminal_case (
       id varchar(255) not null,
        name varchar(255) not null,
        time bigint not null,
        criminal_elements_id varchar(255),
        procuratorate_id varchar(255) not null,
        primary key (id)
    );
 
    
    create table criminal_elements (
       id varchar(255) not null,
        objective_element_description varchar(255) not null,
        subjective_element_description varchar(255) not null,
        primary key (id)
    );
 
    
    create table procuratorate (
       id varchar(255) not null,
        name varchar(50) not null,
        primary key (id)
    );
 
    
    create table procuratorate_prosecutors (
       procuratorate_id varchar(255) not null,
        prosecutors_id varchar(255) not null
    );
 
    
    create table prosecutor (
       id varchar(255) not null,
        idnumber varchar(255),
        age integer not null,
        birthday timestamp,
        experience integer not null,
        gender varchar(255),
        name varchar(255) not null,
        primary key (id)
    );
 
    
    alter table procuratorate 
       add constraint UK_ec3a9dinq8c8yg3isw4216urg unique (name);
 
    
    alter table procuratorate_prosecutors 
       add constraint UK_tim45ki935nlr2063dj09wrul unique (prosecutors_id);
 
    
    alter table criminal_case 
       add constraint FKo5xxwx25njqm6i6krkbyyg5mx 
       foreign key (criminal_elements_id) 
       references criminal_elements;
 
    
    alter table criminal_case 
       add constraint FKnlvv52hcrep9s3vevtv6dl4te 
       foreign key (procuratorate_id) 
       references procuratorate;
 
    
    alter table procuratorate_prosecutors 
       add constraint FK6enw9nch5lglf4niojxdekrys 
       foreign key (prosecutors_id) 
       references prosecutor;
 
    
    alter table procuratorate_prosecutors 
       add constraint FKt5lv0ghjoodq20qsrjogvi6ln 
       foreign key (procuratorate_id) 
       references procuratorate;