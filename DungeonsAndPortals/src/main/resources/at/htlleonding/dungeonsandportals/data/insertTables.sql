-- Scene Images
INSERT INTO Image (I_id, I_imgName, I_directoryName) VALUES (0, 'Market.png', null);
INSERT INTO Image (I_id, I_imgName, I_directoryName) VALUES (1, 'Grass.png', null);
INSERT INTO Image (I_id, I_imgName, I_directoryName) VALUES (2, 'GrassPathRight.png', null);
INSERT INTO Image (I_id, I_imgName, I_directoryName) VALUES (3, 'Desert.png', null);
INSERT INTO Image (I_id, I_imgName, I_directoryName) VALUES (4, 'ForestPath.png', null);
INSERT INTO Image (I_id, I_imgName, I_directoryName) VALUES (5, 'ForestKeyPlace.png', null);
INSERT INTO Image (I_id, I_imgName, I_directoryName) VALUES (6, 'CaveEntrance.png', null);
INSERT INTO Image (I_id, I_imgName, I_directoryName) VALUES (7, 'MainBossFight.png', null);

-- Animation Images
INSERT INTO Image (I_id, I_imgName, I_directoryName) VALUES (8, 'playerGoesLeft.png', 'player');
INSERT INTO Image (I_id, I_imgName, I_directoryName) VALUES (9, 'playerGoesRight.png', 'player');
INSERT INTO Image (I_id, I_imgName, I_directoryName) VALUES (10, 'fire.png', 'fire');
INSERT INTO Image (I_id, I_imgName, I_directoryName) VALUES (11, 'fire_slime.png', 'fire_slime');
INSERT INTO Image (I_id, I_imgName, I_directoryName) VALUES (12, 'slimeJump.png', 'fire_slime');

-- Object images
INSERT INTO Image (I_id, I_imgName, I_directoryName) VALUES (13, 'hit.png', null);
INSERT INTO Image (I_id, I_imgName, I_directoryName) VALUES (14, 'ice-wall.png', null);
INSERT INTO Image (I_id, I_imgName, I_directoryName) VALUES (15, 'key.png', null);
INSERT INTO Image (I_id, I_imgName, I_directoryName) VALUES (16, 'lamp-post.png', null);
INSERT INTO Image (I_id, I_imgName, I_directoryName) VALUES (17, 'stone.png', null);

-- Scenes
INSERT INTO Scene (S_id, S_I_img) VALUES (0, 0);
INSERT INTO Scene (S_id, S_I_img) VALUES (1, 1);
INSERT INTO Scene (S_id, S_I_img) VALUES (2, 2);
INSERT INTO Scene (S_id, S_I_img) VALUES (3, 3);
INSERT INTO Scene (S_id, S_I_img) VALUES (4, 4);
INSERT INTO Scene (S_id, S_I_img) VALUES (5, 5);
INSERT INTO Scene (S_id, S_I_img) VALUES (6, 6);
INSERT INTO Scene (S_id, S_I_img) VALUES (7, 7);

-- Paths between the scenes
-- market
INSERT INTO Paths (P_S_from, P_S_to, P_directionX, P_directionY) VALUES (0, 1, 1, 0);
-- grass
INSERT INTO Paths (P_S_from, P_S_to, P_directionX, P_directionY) VALUES (1, 0, -1, 0);
INSERT INTO Paths (P_S_from, P_S_to, P_directionX, P_directionY) VALUES (1, 2, 0, 1);
INSERT INTO Paths (P_S_from, P_S_to, P_directionX, P_directionY) VALUES (1, 4, 0, -1);
INSERT INTO Paths (P_S_from, P_S_to, P_directionX, P_directionY) VALUES (1, 6, 1, 0);
-- grassPathRight
INSERT INTO Paths (P_S_from, P_S_to, P_directionX, P_directionY) VALUES (2, 1, 0, -1);
INSERT INTO Paths (P_S_from, P_S_to, P_directionX, P_directionY) VALUES (2, 3, 1, 0);
-- desert
INSERT INTO Paths (P_S_from, P_S_to, P_directionX, P_directionY) VALUES (3, 2, -1, 0);
-- forestPath
INSERT INTO Paths (P_S_from, P_S_to, P_directionX, P_directionY) VALUES (4, 1, 0, 1);
INSERT INTO Paths (P_S_from, P_S_to, P_directionX, P_directionY) VALUES (4, 5, 0, -1);
-- forestKeyPlace
INSERT INTO Paths (P_S_from, P_S_to, P_directionX, P_directionY) VALUES (5, 4, 0, 1);
-- caveEntrance
INSERT INTO Paths (P_S_from, P_S_to, P_directionX, P_directionY) VALUES (6, 1, -1, 0);
INSERT INTO Paths (P_S_from, P_S_to, P_directionX, P_directionY) VALUES (6, 7, 1, 0);
-- mainBossFight
INSERT INTO Paths (P_S_from, P_S_to, P_directionX, P_directionY) VALUES (7, 6, -1, 0);
