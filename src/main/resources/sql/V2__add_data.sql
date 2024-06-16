-- superhero
INSERT INTO super_hero (id, alias, name, origin) VALUES (1, 'Superman', 'Clark Kent', 'Krypton');
INSERT INTO super_hero (id, alias, name, origin) VALUES (2, 'Iron Man', 'Tony Stark', 'Kidnapped in Afghanistan, created the first iron-man suit to escape.');

-- superhero_powers
INSERT INTO super_hero_powers (super_hero_id, power) VALUES (1, 'super-strength');
INSERT INTO super_hero_powers (super_hero_id, power) VALUES (2, 'genius-intelligence');
INSERT INTO super_hero_powers (super_hero_id, power) VALUES (2, 'wealth');

-- weapons
INSERT INTO super_hero_weapons (super_hero_id, weapon) VALUES (1, null);
INSERT INTO super_hero_weapons (super_hero_id, weapon) VALUES (2, 'arc-reactor');
INSERT INTO super_hero_weapons (super_hero_id, weapon) VALUES (2, 'iron-man-suit');
INSERT INTO super_hero_weapons (super_hero_id, weapon) VALUES (2, 'iron-legion');

-- associations
INSERT INTO super_hero_associations (super_hero_id, association) VALUES (1, 'justice-league');
INSERT INTO super_hero_associations (super_hero_id, association) VALUES (2, 'war-machine');
INSERT INTO super_hero_associations (super_hero_id, association) VALUES (2, 'avengers');
INSERT INTO super_hero_associations (super_hero_id, association) VALUES (2, 'jarvis');
INSERT INTO super_hero_associations (super_hero_id, association) VALUES (2, 'thanos');
INSERT INTO super_hero_associations (super_hero_id, association) VALUES (2, 'pepper-potts');

-- Adjust the sequence after manually inserting data
SELECT setval('super_hero_id_seq', (SELECT MAX(id) FROM super_hero));