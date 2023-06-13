drop table SceneObjects;
drop table SceneMobs;
drop table Player;
drop table Paths;
drop table Scene;
drop table Mob;
drop table BehaviourPattern;
drop table Object;
drop table Image;

create table Image
(
    I_id integer not null primary key,
    I_imgName text not null,
    I_directoryName text
) strict;

create table Scene
(
    S_id integer not null primary key,
    S_I_img integer not null,
    constraint constraint_scene_image_foreign_key foreign key (S_I_img) references Image(I_id)
) strict;

create table Paths
(
    P_S_from integer not null,
    P_S_to integer not null,
    P_directionX integer not null,
    P_directionY integer not null,
    constraint constraint_paths_primary_key primary key (P_S_from, P_S_to),
    constraint constraint_paths_scene_from_foreign_key foreign key (P_S_from) references Scene(S_id),
    constraint constraint_paths_scene_to_foreign_key foreign key (P_S_to) references Scene(S_id)
) strict;

create table Player(
                       P_id integer not null primary key autoincrement,
                       P_name text not null,
                       P_health integer not null,
                       P_manaCapacity integer not null,
                       P_killedMobs integer not null,
                       P_S_sceneLoc integer not null,
                       constraint constraint_player_scene foreign key (P_S_sceneLoc) references Scene(S_id)
) strict;

create table Mob(
                    M_id integer not null primary key,
                    M_name text not null,
                    M_health integer not null,
                    M_damage integer not null,
                    M_I_img integer not null,
                    constraint constraint_mob_image_foreign_key foreign key (M_I_img) references Image(I_id)
) strict;

create table Object(
                       O_id integer not null primary key,
                       M_I_img integer not null,
                       constraint constraint_scene_image_foreign_key foreign key (M_I_img) references Image(I_id)
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