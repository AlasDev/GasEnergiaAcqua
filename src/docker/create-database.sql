-- Installazione dell’estensione pgcrypto per generare gli UUID
CREATE EXTENSION IF NOT EXISTS pgcrypto;

-- Creazione dei tipi ENUM personalizzati
CREATE TYPE user_type_enum AS ENUM ('NORMAL', 'ADMIN');
CREATE TYPE resource_type_enum AS ENUM ('WATER', 'GAS', 'ELECTRICITY');
CREATE TYPE input_method_enum AS ENUM ('MANUAL', 'AUTOMATIC');

-- Tabella Utenti
CREATE TABLE users (
                       uuid VARCHAR(36) DEFAULT gen_random_uuid() NOT NULL PRIMARY KEY,
                       name VARCHAR(100) NOT NULL,
                       surname VARCHAR(100) NOT NULL,
                       email VARCHAR(255) NOT NULL UNIQUE,
                       password_hash VARCHAR(60) NOT NULL, -- Per hash bcrypt
                       user_type user_type_enum NOT NULL DEFAULT 'NORMAL',
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabella Indirizzi
CREATE TABLE addresses (
                           uuid VARCHAR(36) DEFAULT gen_random_uuid() NOT NULL PRIMARY KEY,
                           street_address VARCHAR(255) NOT NULL,
                           city VARCHAR(100) NOT NULL,
                           postal_code VARCHAR(10) NOT NULL,
                           country VARCHAR(50) NOT NULL,
                           created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabella Contatori
CREATE TABLE utility_meters (
                                uuid VARCHAR(36) DEFAULT gen_random_uuid() NOT NULL PRIMARY KEY,
                                user_uuid VARCHAR(36) NOT NULL, -- Chiave esterna verso users
                                address_uuid VARCHAR(36),
                                resource_type resource_type_enum NOT NULL,
                                input_method input_method_enum NOT NULL DEFAULT 'MANUAL',
                                service_point_identifier VARCHAR(50) UNIQUE, -- Campo generico per PDR/POD/Codice Acqua
                                serial_number VARCHAR(100),
                                meter_name VARCHAR(100), -- Un nome assegnato dall'utente per identificarlo più facilmente
                                installation_date DATE,
                                is_active BOOLEAN NOT NULL DEFAULT TRUE,
                                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

                                FOREIGN KEY (user_uuid) REFERENCES users(uuid) ON DELETE CASCADE,
                                FOREIGN KEY (address_uuid) REFERENCES addresses(uuid) ON DELETE SET NULL
);

-- Aggiungere un indice per user_uuid
CREATE INDEX idx_user_id ON utility_meters(user_uuid);

-- Tabella Letture
CREATE TABLE readings (
                          uuid VARCHAR(36) DEFAULT gen_random_uuid() NOT NULL PRIMARY KEY,
                          meter_uuid VARCHAR(36) NOT NULL,
                          value_recorded DECIMAL(12, 3) NOT NULL,
                          reading_timestamp TIMESTAMP NOT NULL,
                          notes TEXT,
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

                          FOREIGN KEY (meter_uuid) REFERENCES utility_meters(uuid) ON DELETE CASCADE,

                          CHECK (value_recorded >= 0),
                          UNIQUE (meter_uuid, reading_timestamp)
);

-- Aggiungere un indice per meter_uuid e reading_timestamp
CREATE INDEX idx_meter_timestamp ON readings(meter_uuid, reading_timestamp);

-- Creare una funzione per aggiornare updated_at
CREATE OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Applicare il trigger alla tabella utility_meters
CREATE TRIGGER update_utility_meters_updated_at
    BEFORE UPDATE ON utility_meters
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at_column();
