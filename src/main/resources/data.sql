CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

INSERT INTO Player (id, name)
VALUES (uuid_generate_v4(), 'seekheart');


INSERT INTO Team (id, name)
VALUES (uuid_generate_v4(), 'KOA');
INSERT INTO Team (id, name)
VALUES (uuid_generate_v4(), 'LOA');
INSERT INTO Team (id, name)
VALUES (uuid_generate_v4(), 'SB');
INSERT INTO Team (id, name)
VALUES (uuid_generate_v4(), 'BN');

INSERT INTO Assignment (player_id, team_id)
VALUES ((SELECT ID
         FROM PLAYER
         WHERE name is
               'seekheart'), 1);