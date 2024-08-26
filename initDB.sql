DROP DATABASE IF EXISTS bird_db;

CREATE DATABASE IF NOT EXISTS bird_db;
USE bird_db;

CREATE TABLE bird (
    id bigint NOT NULL auto_increment,
    name varchar(50) NOT NULL,
    color varchar(50) NOT NULL,
    weight float,
    height float,
    PRIMARY KEY (id)
);

CREATE TABLE sighting (
    id bigint NOT NULL auto_increment,
    bird_id bigint NOT NULL,
    location varchar(100) NOT NULL,
    timestamp DATETIME, -- ‘YYYY-MM-DD hh:mm:ss’
    PRIMARY KEY (id),
    FOREIGN KEY (bird_id) REFERENCES bird(id)
);

-- mysql -u root -p