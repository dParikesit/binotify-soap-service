CREATE TABLE IF NOT EXISTS logs (
    log_id SERIAL PRIMARY KEY,
    ip_addr CHAR(16) NOT NULL,
    endpoint VARCHAR(256) NOT NULL,
    description VARCHAR(256) NOT NULL,
    requested_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
