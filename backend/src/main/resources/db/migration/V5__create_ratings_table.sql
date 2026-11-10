CREATE TABLE ratings (
    id BIGSERIAL PRIMARY KEY ,
    movie_assignment_id BIGINT NOT NULL UNIQUE REFERENCES movie_assignments(id) ON DELETE CASCADE,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    score SMALLINT NOT NULL CHECK (score between 1 and 10),
    review VARCHAR(2000)

);