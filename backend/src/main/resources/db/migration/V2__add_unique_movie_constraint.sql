ALTER TABLE movies
    ADD CONSTRAINT unique_movie_constraint UNIQUE (title, release_year);