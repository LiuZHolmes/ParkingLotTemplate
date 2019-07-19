 
    
    create table car (
       id bigint not null,
        license_plate_number varchar(255),
        primary key (id)
    );
 
    
    create table orders (
       id bigint not null,
        closed_time timestamp,
        created_time timestamp,
        license_plate_number varchar(255),
        status varchar(255),
        parking_lot_id bigint,
        primary key (id)
    );
 
    
    alter table orders 
       add constraint FK5ygmnhrys3000ku8hwvmfss17 
       foreign key (parking_lot_id) 
       references parking_lot;