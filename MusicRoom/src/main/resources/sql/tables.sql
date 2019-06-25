CREATE TABLE musicroom.mra_consecutivo_tb (
	tabla varchar(100) NOT NULL,
	valor int8 NOT NULL,
	CONSTRAINT mra_consecutivo_tb_pk PRIMARY KEY ("tabla")
);

CREATE TABLE musicroom.oauth_access_token (
	token_id varchar(256),
	token bytea,
	authentication_id varchar(256),
	user_name varchar(256),
	client_id varchar(256),
	authentication bytea,
	refresh_token varchar(256),
	CONSTRAINT oauth_access_token_pk PRIMARY KEY ("token_id")
);