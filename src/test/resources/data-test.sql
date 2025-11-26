-- COLORS
INSERT INTO COLORS (name) VALUES ('Red');
INSERT INTO COLORS (name) VALUES ('Blue');
INSERT INTO COLORS (name) VALUES ('Green');
INSERT INTO COLORS (name) VALUES ('Yellow');
INSERT INTO COLORS (name) VALUES ('Purple');
INSERT INTO COLORS (name) VALUES ('Orange');
INSERT INTO COLORS (name) VALUES ('Pink');
INSERT INTO COLORS (name) VALUES ('Brown');
INSERT INTO COLORS (name) VALUES ('Black');
INSERT INTO COLORS (name) VALUES ('White');
INSERT INTO COLORS (name) VALUES ('Cyan');
INSERT INTO COLORS (name) VALUES ('Magenta');
INSERT INTO COLORS (name) VALUES ('Teal');
INSERT INTO COLORS (name) VALUES ('Maroon');
INSERT INTO COLORS (name) VALUES ('Olive');
INSERT INTO COLORS (name) VALUES ('Navy');
INSERT INTO COLORS (name) VALUES ('Silver');
INSERT INTO COLORS (name) VALUES ('Gold');
INSERT INTO COLORS (name) VALUES ('Beige');
INSERT INTO COLORS (name) VALUES ('Turquoise');

-- NOUNS
INSERT INTO NOUNS (name) VALUES ('Lion');
INSERT INTO NOUNS (name) VALUES ('Eagle');
INSERT INTO NOUNS (name) VALUES ('Wolf');
INSERT INTO NOUNS (name) VALUES ('Shark');
INSERT INTO NOUNS (name) VALUES ('Falcon');
INSERT INTO NOUNS (name) VALUES ('Book');
INSERT INTO NOUNS (name) VALUES ('Chair');
INSERT INTO NOUNS (name) VALUES ('Mountain');
INSERT INTO NOUNS (name) VALUES ('River');
INSERT INTO NOUNS (name) VALUES ('Sword');
INSERT INTO NOUNS (name) VALUES ('Castle');
INSERT INTO NOUNS (name) VALUES ('Star');
INSERT INTO NOUNS (name) VALUES ('Car');
INSERT INTO NOUNS (name) VALUES ('Plane');
INSERT INTO NOUNS (name) VALUES ('Apple');
INSERT INTO NOUNS (name) VALUES ('Moon');
INSERT INTO NOUNS (name) VALUES ('Sun');
INSERT INTO NOUNS (name) VALUES ('Bridge');
INSERT INTO NOUNS (name) VALUES ('Dragon');
INSERT INTO NOUNS (name) VALUES ('Riverbank');
INSERT INTO NOUNS (name) VALUES ('Robot');
INSERT INTO NOUNS (name) VALUES ('Train');
INSERT INTO NOUNS (name) VALUES ('Flower');
INSERT INTO NOUNS (name) VALUES ('Crystal');
INSERT INTO NOUNS (name) VALUES ('Ocean');
INSERT INTO NOUNS (name) VALUES ('Diamond');
INSERT INTO NOUNS (name) VALUES ('Rocket');
INSERT INTO NOUNS (name) VALUES ('Glacier');
INSERT INTO NOUNS (name) VALUES ('Lantern');
INSERT INTO NOUNS (name) VALUES ('Temple');
INSERT INTO NOUNS (name) VALUES ('Tiger');
INSERT INTO NOUNS (name) VALUES ('Candle');
INSERT INTO NOUNS (name) VALUES ('Map');
INSERT INTO NOUNS (name) VALUES ('Helmet');
INSERT INTO NOUNS (name) VALUES ('Island');
INSERT INTO NOUNS (name) VALUES ('Crown');
INSERT INTO NOUNS (name) VALUES ('Horse');
INSERT INTO NOUNS (name) VALUES ('Shield');
INSERT INTO NOUNS (name) VALUES ('Clock');
INSERT INTO NOUNS (name) VALUES ('Boat');
INSERT INTO NOUNS (name) VALUES ('Forest');
INSERT INTO NOUNS (name) VALUES ('Key');
INSERT INTO NOUNS (name) VALUES ('Arrow');
INSERT INTO NOUNS (name) VALUES ('Phoenix');
INSERT INTO NOUNS (name) VALUES ('Swordfish');
INSERT INTO NOUNS (name) VALUES ('Lantern');
INSERT INTO NOUNS (name) VALUES ('Rocketship');
INSERT INTO NOUNS (name) VALUES ('Pearl');
INSERT INTO NOUNS (name) VALUES ('Gemstone');
INSERT INTO NOUNS (name) VALUES ('Volcano');
INSERT INTO NOUNS (name) VALUES ('Compass');

-- PATIENTS
INSERT INTO PATIENTS (id, first_name, last_name, street, city, zip, state) VALUES
('P001', 'John', 'Doe', '123 Main St', 'Springfield', '12345', 'IL'),
('P002', 'Jane', 'Smith', '456 Oak Ave', 'Metropolis', '67890', 'NY'),
('P003', 'Alice', 'Johnson', '789 Pine Rd', 'Gotham', '11223', 'CA');

-- TICKETS
INSERT INTO TICKETS (
    id,
    patient_id,
    priority,
    color,
    noun,
    color_code,
    risk_of_fall,
    breathing_difficulty,
    severe_pain,
    bleeding,
    unconscious,
    created_at,
    status
) VALUES
('11111111-1111-1111-1111-111111111111', 'P001', 'HIGH', 'Red', 'Lion', '#FF0000', TRUE, FALSE, TRUE, FALSE, FALSE, CURRENT_TIMESTAMP, 'OPEN'),
('22222222-2222-2222-2222-222222222222', 'P002', 'MEDIUM', 'Blue', 'Wolf', '#0000FF', FALSE, FALSE, FALSE, FALSE, FALSE, CURRENT_TIMESTAMP, 'IN_PROGRESS'),
('33333333-3333-3333-3333-333333333333', 'P003', 'LOW', 'Green', 'Eagle', '#00FF00', FALSE, FALSE, FALSE, FALSE, FALSE, CURRENT_TIMESTAMP, 'CLOSED');

