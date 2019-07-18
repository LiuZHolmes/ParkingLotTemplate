    create sequence hibernate_sequence start with 1 increment by 1;
    create table parking_lot (
       id BIGINT not null,
        capacity integer not null,
        location varchar(255),
        name varchar(255),
        primary key (id)
    );
    alter table parking_lot 
       drop constraint if exists UK_2w49woqis4x25gei7vnre7x1i;
    
    alter table parking_lot 
       add constraint UK_2w49woqis4x25gei7vnre7x1i unique (name);