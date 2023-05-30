drop table Scene;
drop table Player;
drop table Mob;
drop table BehaviourPattern;
drop table SceneMobs;
drop table Object;
drop table SceneObjects;

create table Scene
(
    S_id integer not null primary key,
    S_backgroundImgName text not null
) strict;

create table Player(
                       P_id integer not null primary key,
                       P_name text not null,
                       P_health integer not null,
                       P_manaCapacity integer not null,
                       P_imgName text not null,
                       P_S_sceneLoc integer not null,
                       constraint constraint_player_scene foreign key (P_S_sceneLoc) references Scene(S_id)
) strict;

create table Mob(
                    M_id integer not null primary key,
                    M_name text not null,
                    M_health integer not null,
                    M_damage integer not null,
                    M_imgName text not null
) strict;

create table Object(
                       O_id integer not null primary key,
                       O_imgName text not null
) strict;

create table BehaviourPattern(
                                 BP_id integer not null primary key,
                                 BP_name text not null
) strict;

create table SceneMobs(
                          SM_id integer not null primary key,
                          SM_S_id integer not null,
                          SM_M_id integer not null,
                          SM_BP_id integer not null,
                          SM_locX integer not null,
                          SM_locY integer not null,
                          constraint constraint_scenemobs_scene foreign key (SM_S_id) references Scene(S_id),
                          constraint constraint_scenemobs_mob foreign key (SM_M_id) references Mob(M_id),
                          constraint constraint_scenemobs_behaviourpattern foreign key (SM_BP_id) references BehaviourPattern(BP_id)
) strict;

create table SceneObjects(
                             SO_id integer not null primary key,
                             SO_O_id integer not null,
                             SO_locX integer not null,
                             SO_locY integer not null,
                             constraint constraint_sceneobjects_object foreign key (SO_O_id) references Object(O_id)
) strict;