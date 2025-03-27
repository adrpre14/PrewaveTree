CREATE TABLE edge (
    from_id INT NOT NULL CHECK (from_id <> to_id),
    to_id INT UNIQUE NOT NULL
);

CREATE OR REPLACE FUNCTION check_cycle()
RETURNS TRIGGER
LANGUAGE plpgsql
AS $$
    DECLARE
        parents INT;
    BEGIN
        SELECT count(e.from_id) INTO parents FROM edge e WHERE e.from_id = NEW.to_id;
        IF parents > 0 THEN
            RAISE EXCEPTION 'Cycle detected';
        END IF;
        RETURN NEW;
    END;
$$;

CREATE OR REPLACE TRIGGER cycle_constraint
BEFORE INSERT OR UPDATE ON edge
FOR EACH ROW
    EXECUTE FUNCTION check_cycle();