create schema `4156service`;

--
-- create table `facility`
--
create table if not exists facility
(
    facilityID int auto_increment,
    latitude   float        not null,
    longitude  float        not null,
    public     boolean      not null,
    email      varchar(255) null,
    phone      varchar(255) null,
    hours      varchar(255) null,
    constraint facility_pk
        primary key (facilityID)
);

--
-- create table `client`
--
create table if not exists client
(
    clientID              int auto_increment,
    authentication        varchar(255) not null,
    type                  varchar(255) not null,
    associated_facilityID int          null,
    constraint client_pk
        primary key (clientID),
    constraint client_facility_facilityID_fk
        foreign key (associated_facilityID) references facility (facilityID)
            on delete set null,
    constraint check_type
        check (type IN ('Distributor', 'not Distributor'))
);

--
-- create table `listings`
--
create table if not exists listings
(
    listingID             int auto_increment,
    associated_facilityID int          null,
    public                boolean      not null,
    group_code            int          null,
    item_list             text         not null,
    age_requirement       int          null,
    veteran_status        boolean      null,
    gender                varchar(255) null,
    constraint listings_pk
        primary key (listingID),
    constraint listings_fk
        foreign key (associated_facilityID) references facility (facilityID),
    constraint check_group_code
        check (((group_code is not null) and (public is true)) or (public is false))
);

alter table client
    drop constraint check_type;

ALTER TABLE client
    ADD CONSTRAINT check_type CHECK (type IN ('DISTRIBUTOR', 'NON_DISTRIBUTOR'));






