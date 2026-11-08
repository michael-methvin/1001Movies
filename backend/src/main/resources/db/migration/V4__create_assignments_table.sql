CREATE TABLE movie_assignments (
    id BIGSERIAL PRIMARY KEY ,
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    movie_id BIGINT NOT NULL REFERENCES movies(id),
    assignment_date DATE NOT NULL ,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

ALTER TABLE movie_assignments
    ADD CONSTRAINT unique_assignment_date_constraint UNIQUE (user_id, assignment_date),
    ADD CONSTRAINT unique_assignment_movie_constraint UNIQUE (user_id, movie_id);
