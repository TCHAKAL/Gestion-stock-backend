
create table categorie
(
    id                 integer      not null
        primary key,
    creation_date      timestamp,
    last_modified_date timestamp,
    code               varchar(255) not null,
    designation        varchar(255) not null,
    entreprise         integer
);
create table article
(
    id                 integer      not null
        primary key,
    creation_date      timestamp,
    last_modified_date timestamp,
    code               varchar(255) not null,
    designation        varchar(255) not null,
    entreprise         integer,
    photo              varchar(255),
    prix_unitaire_ht   numeric(19, 2),
    prix_unitaire_ttc  numeric(19, 2),
    taux_tva           numeric(19, 2),
    categorie          integer
        constraint fk88a69i1pttxhfl0661xrqdv63
            references categorie
);
create table client
(
    id                 integer      not null
        primary key,
    creation_date      timestamp,
    last_modified_date timestamp,
    adresse1           varchar(255),
    adresse2           varchar(255),
    code_postale       varchar(255),
    pays               varchar(255),
    ville              varchar(255),
    entreprise         integer,
    mail               varchar(255) not null,
    nom                varchar(255) not null,
    photo              varchar(255),
    prenom             varchar(255) not null,
    telephone          varchar(255)
);
create table commande_client
(
    id                 integer not null
        primary key,
    creation_date      timestamp,
    last_modified_date timestamp,
    code               varchar(255),
    date_commande      timestamp,
    entreprise         integer,
    id_client          integer
        constraint fkauhkp3uob3kt6qfu532budyxv
            references client
);


create table entreprise
(
    id                 integer not null
        primary key,
    creation_date      timestamp,
    last_modified_date timestamp,
    nom                varchar(255)
);
create table fournisseur
(
    id                 integer      not null
        primary key,
    creation_date      timestamp,
    last_modified_date timestamp,
    adresse1           varchar(255),
    adresse2           varchar(255),
    code_postale       varchar(255),
    pays               varchar(255),
    ville              varchar(255),
    entreprise         integer,
    mail               varchar(255) not null,
    nom                varchar(255) not null,
    photo              varchar(255),
    prenom             varchar(255) not null,
    telephone          varchar(255)
);
create table commande_fournisseur
(
    id                 integer not null
        primary key,
    creation_date      timestamp,
    last_modified_date timestamp,
    code               varchar(255),
    date_commande      timestamp,
    entreprise         integer,
    fournisseur        integer
        constraint fkkxhld4egque4qdq43hvuvuaif
            references fournisseur
);
create table ligne_commande_client
(
    id                 integer not null
        primary key,
    creation_date      timestamp,
    last_modified_date timestamp,
    entreprise         integer,
    prix_unitaire      numeric(19, 2),
    quantite           numeric(19, 2),
    id_article         integer
        constraint fkrb7qbc42dsrt66nkp9uqpslf8
            references article,
    id_commande_client integer
        constraint fktmlda3sbya50yx5n0ayhk3i4c
            references commande_client
);

create table ligne_commande_fournisseur
(
    id                   integer not null
        primary key,
    creation_date        timestamp,
    last_modified_date   timestamp,
    entreprise           integer,
    prix_unitaire        numeric(19, 2),
    quantite             numeric(19, 2),
    id_article           integer
        constraint fkqbg3tw23l88iq4toj6wn3qcit
            references article,
    commande_fournisseur integer
        constraint fkr291un4m90wtale1gtpj5ixt8
            references commande_fournisseur
);
create table mvt_stock
(
    id                 integer not null
        primary key,
    creation_date      timestamp,
    last_modified_date timestamp,
    date_mvt           timestamp,
    entreprise         integer,
    quantite           numeric(19, 2),
    type_mvt           integer,
    id_article         integer
        constraint fk6jwmjpes11vpomvbh3g4r9jqe
            references article
);
create table utilisateur
(
    id                 integer not null
        primary key,
    creation_date      timestamp,
    last_modified_date timestamp,
    adresse1           varchar(255),
    adresse2           varchar(255),
    code_postale       varchar(255),
    pays               varchar(255),
    ville              varchar(255),
    date_naissance     timestamp,
    email              varchar(255),
    mot_passe          varchar(255),
    nom                varchar(255),
    photo              varchar(255),
    prenom             varchar(255),
    entreprise         integer
        constraint fk2h57iu7c90jpykg6kk1e374a
            references entreprise
);

create table role
(
    id                 integer not null
        primary key,
    creation_date      timestamp,
    last_modified_date timestamp,
    role_name          varchar(255),
    utilisateur        integer
        constraint fkaluyk40q2w311ty010fblg7dn
            references utilisateur
);
create table vente
(
    id                 integer not null
        primary key,
    creation_date      timestamp,
    last_modified_date timestamp,
    code               varchar(255),
    commentaire        varchar(255),
    date_vente         timestamp,
    entreprise         integer
);
create table ligne_vente
(
    id                 integer not null
        primary key,
    creation_date      timestamp,
    last_modified_date timestamp,
    entreprise         integer,
    quantite           numeric(19, 2),
    article            integer
        constraint fksrd9abwg2vighwrah5l0i70c1
            references article,
    vente              integer
        constraint fks9kg69coxy6r5u3167mrmalul
            references vente
);