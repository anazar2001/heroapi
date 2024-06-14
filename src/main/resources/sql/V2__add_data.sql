-- superhero
INSERT INTO superhero (id, alias, name, origin) VALUES (1, 'Superman', 'Clark Kent', 'Krypton');
INSERT INTO superhero (id, alias, name, origin) VALUES (2, 'Iron Man', 'Tony Stark', 'Kidnapped in Afghanistan, created the first iron-man suit to escape.');

-- superhero_powers
INSERT INTO superhero_powers (superhero_id, power) VALUES (1, 'super-strength');
INSERT INTO superhero_powers (superhero_id, power) VALUES (2, 'genius-intelligence');
INSERT INTO superhero_powers (superhero_id, power) VALUES (2, 'wealth');

-- weapons
INSERT INTO superhero_weapons (superhero_id, weapon) VALUES (1, null);
INSERT INTO superhero_weapons (superhero_id, weapon) VALUES (2, 'arc-reactor');
INSERT INTO superhero_weapons (superhero_id, weapon) VALUES (2, 'iron-man-suit');
INSERT INTO superhero_weapons (superhero_id, weapon) VALUES (2, 'iron-legion');

-- associations
INSERT INTO superhero_associations (superhero_id, association) VALUES (1, 'justice-league');
INSERT INTO superhero_associations (superhero_id, association) VALUES (2, 'war-machine');
INSERT INTO superhero_associations (superhero_id, association) VALUES (2, 'avengers');
INSERT INTO superhero_associations (superhero_id, association) VALUES (2, 'jarvis');
INSERT INTO superhero_associations (superhero_id, association) VALUES (2, 'thanos');
INSERT INTO superhero_associations (superhero_id, association) VALUES (2, 'pepper-potts');