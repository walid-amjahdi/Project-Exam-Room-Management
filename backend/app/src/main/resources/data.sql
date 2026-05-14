-- ============================================================
-- SEED DATA — Comptes de test (tous les mots de passe: password123)
-- ============================================================

-- Teachers
INSERT INTO app_users (user_id, email, name, password, role) VALUES (1, 'ahaytar@teacher.com', 'Ahaytar', 'password123', 'TEACHER');
INSERT INTO teachers (teacher_id, department, grade) VALUES (1, 'Informatique', 'MCF');

INSERT INTO app_users (user_id, email, name, password, role) VALUES (2, 'amjahdi@teacher.com', 'Amjahdi', 'password123', 'TEACHER');
INSERT INTO teachers (teacher_id, department, grade) VALUES (2, 'Mathématiques', 'PR');

-- Admins
INSERT INTO app_users (user_id, email, name, password, role) VALUES (11, 'ahaytar@admin.com', 'Ahaytar Admin', 'password123', 'ADMIN');
INSERT INTO admins (admin_id, is_sudo) VALUES (11, TRUE);

INSERT INTO app_users (user_id, email, name, password, role) VALUES (12, 'amjahdi@admin.com', 'Amjahdi Admin', 'password123', 'ADMIN');
INSERT INTO admins (admin_id, is_sudo) VALUES (12, FALSE);

-- ============================================================
-- Salles (8 salles réparties dans 3 bâtiments)
-- ============================================================
INSERT INTO rooms (room_id, name, capacity, building, location, available) VALUES (1, 'Amphi A', 200, 'Batiment A', 'Rez-de-chaussee', TRUE);
INSERT INTO rooms (room_id, name, capacity, building, location, available) VALUES (2, 'Salle TD 1', 40, 'Batiment A', '1er etage', TRUE);
INSERT INTO rooms (room_id, name, capacity, building, location, available) VALUES (3, 'Salle TP 1', 25, 'Batiment A', '2e etage', TRUE);
INSERT INTO rooms (room_id, name, capacity, building, location, available) VALUES (4, 'Amphi B', 150, 'Batiment B', 'Rez-de-chaussee', TRUE);
INSERT INTO rooms (room_id, name, capacity, building, location, available) VALUES (5, 'Salle TD 2', 35, 'Batiment B', '1er etage', TRUE);
INSERT INTO rooms (room_id, name, capacity, building, location, available) VALUES (6, 'Salle TP 2', 20, 'Batiment B', '2e etage', TRUE);
INSERT INTO rooms (room_id, name, capacity, building, location, available) VALUES (7, 'Salle de reunion', 15, 'Batiment C', 'Rez-de-chaussee', TRUE);
INSERT INTO rooms (room_id, name, capacity, building, location, available) VALUES (8, 'Labo Langues', 30, 'Batiment C', '1er etage', TRUE);

-- ============================================================
-- Seances de rattrapage (MakeupSession)
-- ============================================================
INSERT INTO makeup_sessions (session_id, module_name, student_group, academic_year) VALUES (1, 'Mathematiques', 'G1', '2025/2026');
INSERT INTO makeup_sessions (session_id, module_name, student_group, academic_year) VALUES (2, 'Physique', 'G2', '2025/2026');
INSERT INTO makeup_sessions (session_id, module_name, student_group, academic_year) VALUES (3, 'Chimie', 'G3', '2025/2026');
INSERT INTO makeup_sessions (session_id, module_name, student_group, academic_year) VALUES (4, 'Informatique', 'G4', '2025/2026');

-- ============================================================
-- Reservations (4 exemples couvrant tous les statuts)
-- ============================================================
INSERT INTO reservations (reservation_id, reservation_date, start_time, end_time, status, reason, session_id, reservation_teacher, reservation_admin, reservation_room)
VALUES (1, '2026-05-20', '08:00:00', '10:00:00', 'PENDING', 'Rattrapage Maths G1', 1, 1, NULL, 1);

INSERT INTO reservations (reservation_id, reservation_date, start_time, end_time, status, reason, session_id, reservation_teacher, reservation_admin, reservation_room)
VALUES (2, '2026-05-20', '10:00:00', '12:00:00', 'CONFIRMED', 'Rattrapage Physique G2', 2, 2, 11, 5);

INSERT INTO reservations (reservation_id, reservation_date, start_time, end_time, status, reason, session_id, reservation_teacher, reservation_admin, reservation_room)
VALUES (3, '2026-05-21', '14:00:00', '16:00:00', 'REJECTED', 'Rattrapage Chimie G3', 3, 1, 11, 8);

INSERT INTO reservations (reservation_id, reservation_date, start_time, end_time, status, reason, session_id, reservation_teacher, reservation_admin, reservation_room)
VALUES (4, '2026-05-22', '16:00:00', '18:00:00', 'PENDING', 'Rattrapage Info G4', 4, 2, NULL, 6);
