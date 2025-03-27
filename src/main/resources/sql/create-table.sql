DROP TABLE IF EXISTS edge;

CREATE TABLE edge (
    from_id INT NOT NULL CHECK (from_id <> to_id) CHECK ( from_id > 0 ),
    to_id INT UNIQUE NOT NULL CHECK ( to_id > 0 )
);

CREATE OR REPLACE FUNCTION return_children(parent_id INT)
    RETURNS SETOF edge
    LANGUAGE plpgsql
AS $$
BEGIN
    RETURN QUERY
        WITH RECURSIVE sub_tree_from_root AS (
            SELECT e1.*
            FROM edge e1
            WHERE e1.from_id = parent_id
            UNION
            SELECT e2.*
            FROM edge e2
                     JOIN sub_tree_from_root str ON e2.from_id = str.to_id
        ) SELECT * from sub_tree_from_root;
END;
$$;

CREATE OR REPLACE FUNCTION check_cycle()
RETURNS TRIGGER
LANGUAGE plpgsql
AS $$
    BEGIN
        IF EXISTS (SELECT 1 FROM return_children(NEW.to_id) ch WHERE ch.to_id = NEW.from_id) THEN
            RAISE EXCEPTION 'Cycle detected';
        END IF;
        RETURN NEW;
    END;
$$;

CREATE OR REPLACE TRIGGER cycle_constraint
BEFORE INSERT OR UPDATE ON edge
FOR EACH ROW
    EXECUTE FUNCTION check_cycle();