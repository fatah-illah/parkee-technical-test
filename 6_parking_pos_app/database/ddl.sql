-- Create the database (if not exists)
CREATE DATABASE parking_system;

-- Connect to the database
\c parking_system;

-- Members table
CREATE TABLE members (
                         member_id SERIAL PRIMARY KEY,
                         member_name VARCHAR(100),
                         parking_type VARCHAR(50),
                         expiry_date DATE,
                         member_unit VARCHAR(50),
                         is_active BOOLEAN DEFAULT TRUE
);

-- Vehicles table
CREATE TABLE vehicles (
                          vehicle_id SERIAL PRIMARY KEY,
                          plate_number VARCHAR(20) NOT NULL,
                          vehicle_type VARCHAR(50) NOT NULL
);

-- Parking tickets table
CREATE TABLE parking_tickets (
                                 ticket_id SERIAL PRIMARY KEY,
                                 vehicle_id INTEGER,
                                 slip_number VARCHAR(20),
                                 check_in_time TIMESTAMP NOT NULL,
                                 check_out_time TIMESTAMP,
                                 duration INTERVAL,
                                 gate_system VARCHAR(50),
                                 status VARCHAR(20) DEFAULT 'active',
                                 FOREIGN KEY (vehicle_id) REFERENCES vehicles(vehicle_id)
);

-- Payments table
CREATE TABLE payments (
                          payment_id SERIAL PRIMARY KEY,
                          ticket_id INTEGER,
                          payment_method VARCHAR(50),
                          session_fee NUMERIC(10,2),
                          discount NUMERIC(10,2) DEFAULT 0.00,
                          promo NUMERIC(10,2) DEFAULT 0.00,
                          total_price NUMERIC(10,2),
                          voucher_code VARCHAR(50),
                          payment_time TIMESTAMP,
                          FOREIGN KEY (ticket_id) REFERENCES parking_tickets(ticket_id)
);

-- Camera captures table
CREATE TABLE camera_captures (
                                 capture_id SERIAL PRIMARY KEY,
                                 ticket_id INTEGER,
                                 entry_camera_img BYTEA,
                                 exit_camera_img BYTEA,
                                 face_entry_camera_img BYTEA,
                                 face_exit_camera_img BYTEA,
                                 capture_time TIMESTAMP,
                                 FOREIGN KEY (ticket_id) REFERENCES parking_tickets(ticket_id)
);

-- Operators table
CREATE TABLE operators (
                           operator_id SERIAL PRIMARY KEY,
                           username VARCHAR(50) NOT NULL,
                           role VARCHAR(50) NOT NULL,
                           is_active BOOLEAN DEFAULT TRUE
);

-- Activity logs table
CREATE TABLE activity_logs (
                               log_id SERIAL PRIMARY KEY,
                               operator_id INTEGER,
                               ticket_id INTEGER,
                               activity_type VARCHAR(50),
                               activity_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                               FOREIGN KEY (operator_id) REFERENCES operators(operator_id),
                               FOREIGN KEY (ticket_id) REFERENCES parking_tickets(ticket_id)
);

-- Create indexes for better performance
CREATE INDEX idx_parking_tickets_vehicle_id ON parking_tickets(vehicle_id);
CREATE INDEX idx_parking_tickets_check_in_time ON parking_tickets(check_in_time);
CREATE INDEX idx_payments_ticket_id ON payments(ticket_id);
CREATE INDEX idx_vehicles_plate_number ON vehicles(plate_number);
