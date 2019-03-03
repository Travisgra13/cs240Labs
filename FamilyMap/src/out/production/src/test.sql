CREATE TABLE User (
	UserName text PRIMARY KEY NOT NULL,
	Password text NOT NULL,
	Email text NOT NULL,
	FirstName text NOT NULL,
	LastName text NOT NULL,
	Gender text,
	PersonID text NOT NULL
);

CREATE TABLE Person (
	PersonID text PRIMARY KEY NOT NULL,
	Descendant text NOT NULL,
	FirstName text NOT NULL,
	LastName text NOT NULL,
	Gender text NOT NULL,
	Father text,
	Mother text,
	Spouse text
);

CREATE TABLE Event (
	EventID text PRIMARY KEY NOT NULL,
	Descendant text,
	Person text,
	Latitude text,
	Longitude text,
	Country text,
	City text,
	EventType text,
	Year integer
);

CREATE TABLE Tokens (
	Key text PRIMARY KEY NOT NULL,
	User text NOT NULL
);