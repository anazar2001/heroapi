CREATE TABLE super_hero (
    id BIGSERIAL PRIMARY KEY,  -- Primary key with bigserial auto-increment
    alias VARCHAR(100) NOT NULL,
    name VARCHAR(100) NOT NULL,
    origin VARCHAR(255) NOT NULL
);

CREATE INDEX idx_super_hero_alias ON super_hero(alias);  -- Index on alias for faster searches
CREATE INDEX idx_super_hero_name ON super_hero(name);  -- Index on name for faster searches

CREATE TABLE super_hero_powers (
    super_hero_id BIGINT NOT NULl,
    power VARCHAR(50) NOT NULL,
    FOREIGN KEY (super_hero_id) REFERENCES super_hero(id)
);

CREATE INDEX idx_super_hero_powers_superhero_id_and_power ON super_hero_powers(super_hero_id,power);  -- Index on power for faster searches

CREATE TABLE super_hero_weapons (
    super_hero_id BIGINT NOT NULL,
    weapon VARCHAR(50) NULL,
    FOREIGN KEY (super_hero_id) REFERENCES super_hero(id)
);

CREATE INDEX idx_super_hero_weapons_superhero_id_and_weapon ON super_hero_weapons(super_hero_id,weapon);  -- Index on weapon for faster searches

CREATE TABLE super_hero_associations (
    super_hero_id BIGINT NOT NULL,
    association VARCHAR(50) NOT NULL,
    FOREIGN KEY (super_hero_id) REFERENCES super_hero(id)
);

CREATE INDEX idx_super_hero_associations_superhero_id_and_association ON super_hero_associations(super_hero_id,association);  -- Index on association for faster searches