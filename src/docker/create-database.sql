-- Installazione dell'estensione pgcrypto per generare gli UUID
CREATE EXTENSION IF NOT EXISTS pgcrypto;

-- Tabelle di lookup (al posto degli enum)
CREATE TABLE user_types (
                            id SERIAL PRIMARY KEY,
                            type_name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE resource_types (
                                id SERIAL PRIMARY KEY,
                                type_name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE utility_meter_types (
                               id SERIAL PRIMARY KEY,
                              type_name VARCHAR(255) NOT NULL UNIQUE
);

-- Inserimento dei valori predefiniti nelle tabelle di lookup
INSERT INTO user_types (type_name) VALUES ('NORMAL'), ('ADMIN');
INSERT INTO resource_types (type_name) VALUES ('WATER'), ('GAS'), ('ELECTRICITY');
INSERT INTO utility_meter_types (type_name) VALUES ('MANUAL'), ('AUTOMATIC');

-- Tabella Utenti
CREATE TABLE users (
                       id UUID DEFAULT gen_random_uuid() NOT NULL PRIMARY KEY,
                       name VARCHAR(255) NOT NULL,
                       surname VARCHAR(255) NOT NULL,
                       email VARCHAR(255) NOT NULL UNIQUE CHECK (email LIKE '%@%.%'), -- check if email contains has the right pattern
                       password VARCHAR(255) NOT NULL,
                       user_type_id INTEGER NOT NULL REFERENCES user_types(id) DEFAULT 1, --1 = 'NORMAL'
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabella Indirizzi
CREATE TABLE addresses (
                           id UUID DEFAULT gen_random_uuid() NOT NULL PRIMARY KEY,
                           street_address VARCHAR(255) NOT NULL,
                           city VARCHAR(255) NOT NULL,
                           postal_code VARCHAR(10) NOT NULL,
                           country VARCHAR(255) NOT NULL,
                           created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabella Contatori
CREATE TABLE utility_meters (
                                id UUID DEFAULT gen_random_uuid() NOT NULL PRIMARY KEY,
                                user_id UUID NOT NULL, -- Chiave esterna verso users
                                address_id UUID,
                                resource_type_id INTEGER NOT NULL REFERENCES resource_types(id),
                                utility_meter_type_id INTEGER NOT NULL REFERENCES utility_meter_types(id) DEFAULT 1, -- 1 è 'MANUAL'
                                service_point_identifier VARCHAR(255) UNIQUE, -- Campo generico per PDR/POD/Codice Acqua
                                serial_number VARCHAR(255),
                                meter_name VARCHAR(255), -- Un nome assegnato dall'utente per identificarlo più facilmente
                                installation_date DATE,
                                is_active BOOLEAN NOT NULL DEFAULT TRUE,
                                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

                                FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
                                FOREIGN KEY (address_id) REFERENCES addresses(id) ON DELETE SET NULL
);

-- Indice per user_id
CREATE INDEX idx_user_id ON utility_meters(user_id);

-- Tabella Letture
CREATE TABLE readings (
                          id UUID DEFAULT gen_random_uuid() NOT NULL PRIMARY KEY,
                          meter_id UUID NOT NULL,
                          value_recorded DECIMAL(12, 3) NOT NULL,
                          reading_timestamp TIMESTAMP NOT NULL,
                          notes TEXT,
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

                          FOREIGN KEY (meter_id) REFERENCES utility_meters(id) ON DELETE CASCADE,

                          CHECK (value_recorded >= 0),
                          UNIQUE (meter_id, reading_timestamp)
);

-- Indice per meter_id e reading_timestamp
CREATE INDEX idx_meter_timestamp ON readings(meter_id, reading_timestamp);

-- Funzione per aggiornare updated_at
CREATE OR REPLACE FUNCTION update_updated_at_column()
    RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Creazione del trigger per la tabella utility_meters
CREATE TRIGGER update_utility_meters_updated_at
    BEFORE UPDATE ON utility_meters
    FOR EACH ROW
EXECUTE FUNCTION update_updated_at_column();
