CREATE TABLE IF NOT EXISTS apikey (
    key_id SERIAL PRIMARY KEY,
    api_key CHAR(128) NOT NULL,
    client_type ENUM("REST", "FRONTEND") NOT NULL
);

INSERT INTO apikey (api_key, client_type) 
VALUES('qaJ8sjU1ilXSdnkCUzpIrPExSK4d7UIlr4BZj2EobWfS2yJqHwSlVPMQIQfDx9OWPCNqbagqQeJ3at0BcRhqqpMDiXBBhdXzbTGP3WYNr2aDgwa4tDAMjvuUuNUU72KI', 'REST'), ('N6GnHem1dDdtPjsXPzHxUAnWWtZG1ECLIMHgOlv7ine1skabTYLlP9WDYtr7se3lHHihPVdIZ6rg9yHQTcIabpy7qfl94GEYaopDTACMQrxjLsUmJaMd1VqikRWkJNpb
', 'FRONTEND');