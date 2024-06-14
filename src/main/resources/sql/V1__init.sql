CREATE TABLE superhero (
    id BIGSERIAL PRIMARY KEY,  -- Primary key with bigserial auto-increment
    alias VARCHAR(100),
    name VARCHAR(100),
    origin VARCHAR(255)
);

CREATE INDEX idx_superhero_alias ON superhero(alias);  -- Index on alias for faster searches

CREATE TABLE superhero_powers (
    superhero_id BIGINT,
    power VARCHAR(50),
    FOREIGN KEY (superhero_id) REFERENCES superhero(id)
);

CREATE INDEX idx_superhero_powers_superhero_id ON superhero_powers(superhero_id);  -- Index on foreign key

CREATE TABLE superhero_weapons (
    superhero_id BIGINT,
    weapon VARCHAR(50),
    FOREIGN KEY (superhero_id) REFERENCES superhero(id)
);

CREATE INDEX idx_superhero_weapons_superhero_id ON superhero_weapons(superhero_id);  -- Index on foreign key

CREATE TABLE superhero_associations (
    superhero_id BIGINT,
    association VARCHAR(50),
    FOREIGN KEY (superhero_id) REFERENCES superhero(id)
);

CREATE INDEX idx_superhero_associations_superhero_id ON superhero_associations(superhero_id);  -- Index on foreign key