INSERT INTO Instructors (instructor_id, instructor_name, specialization, hourly_rate) VALUES
(1, 'John Doe', 'Yoga & Meditation', 45.0),
(2, 'Jane Smith', 'Pilates Core', 50.0),
(3, 'Alex Jones', 'CrossFit Conditioning', 60.0);

INSERT INTO Classes (class_id, instructor_id, class_name, category, price_per_session) VALUES
(1, 1, 'Vinyasa Flow Yoga', 'Mindfulness', 15.0),
(2, 1, 'Hatha Balance', 'Mindfulness', 20.0),
(3, 2, 'Mat Pilates Basics', 'Core Strength', 25.0),
(4, 3, 'Power CrossFit Intro', 'High Intensity', 30.0);

INSERT INTO Members (member_id, name, city, join_date, membership_type) VALUES
(1, 'Member 1', 'New York', '2026-01-15', 'Standard'),
(2, 'Member 2', 'Los Angeles', '2026-01-15', 'Standard'),
(3, 'Member 3', 'New York', '2026-01-15', 'Standard'),
(4, 'Member 4', 'Los Angeles', '2026-01-15', 'Standard'),
(5, 'Member 5', 'New York', '2026-01-15', 'Standard'),
(6, 'Member 6', 'Los Angeles', '2026-01-15', 'Standard'),
(7, 'Member 7', 'New York', '2026-01-15', 'Standard'),
(8, 'Member 8', 'Los Angeles', '2026-01-15', 'Standard'),
(9, 'Member 9', 'New York', '2026-01-15', 'Standard'),
(10, 'Member 10', 'Los Angeles', '2026-01-15', 'Standard'),
(11, 'Member 11', 'New York', '2026-01-15', 'Standard'),
(12, 'Member 12', 'Los Angeles', '2026-01-15', 'Standard'),
(13, 'Member 13', 'New York', '2026-01-15', 'Standard'),
(14, 'Member 14', 'Los Angeles', '2026-01-15', 'Standard'),
(15, 'Member 15', 'New York', '2026-01-15', 'Standard'),
(16, 'Member 16', 'Los Angeles', '2026-01-15', 'Standard'),
(17, 'Member 17', 'New York', '2026-01-15', 'Standard'),
(18, 'Member 18', 'Los Angeles', '2026-01-15', 'Standard'),
(19, 'Member 19', 'New York', '2026-01-15', 'Standard'),
(20, 'Member 20', 'Los Angeles', '2026-01-15', 'Standard');

INSERT INTO Bookings (member_id, class_id, booking_date, attendance_status) VALUES
(1, 1, '2026-05-28', 'Attended'), (2, 1, '2026-05-28', 'Attended'),
(3, 1, '2026-05-28', 'Attended'), (4, 1, '2026-05-28', 'Attended'),
(5, 1, '2026-05-28', 'Attended'), (6, 1, '2026-05-28', 'Attended'),
(7, 1, '2026-05-28', 'Attended'), (8, 1, '2026-05-28', 'Attended'),
(9, 1, '2026-05-28', 'Attended'), (10, 1, '2026-05-28', 'Attended'),
(11, 1, '2026-05-28', 'Attended'), (12, 1, '2026-05-28', 'Attended'),
(13, 1, '2026-05-28', 'Attended'), (14, 1, '2026-05-28', 'Attended'),
(15, 1, '2026-05-28', 'Attended'), (16, 1, '2026-05-28', 'Attended'),
(17, 1, '2026-05-28', 'Attended'), (18, 1, '2026-05-28', 'Attended'),
(19, 1, '2026-05-28', 'Attended'), (20, 1, '2026-05-28', 'Attended'),
(1, 1, '2026-05-29', 'Attended'), (2, 1, '2026-05-29', 'Attended'),
(3, 1, '2026-05-29', 'Attended'), (4, 1, '2026-05-29', 'Attended'),
(5, 1, '2026-05-29', 'Attended'), (6, 1, '2026-05-29', 'Attended');

INSERT INTO Bookings (member_id, class_id, booking_date, attendance_status) VALUES
(1, 2, '2026-05-28', 'Attended'), (2, 2, '2026-05-28', 'Attended'),
(3, 2, '2026-05-28', 'Attended'), (4, 2, '2026-05-28', 'Attended'),
(5, 2, '2026-05-28', 'Attended'), (6, 2, '2026-05-28', 'Attended'),
(7, 2, '2026-05-28', 'Attended'), (8, 2, '2026-05-28', 'Attended'),
(9, 2, '2026-05-28', 'Attended'), (10, 2, '2026-05-28', 'Attended'),
(11, 2, '2026-05-28', 'Attended'), (12, 2, '2026-05-28', 'Attended'),
(13, 2, '2026-05-28', 'Attended'), (14, 2, '2026-05-28', 'Attended'),
(15, 2, '2026-05-28', 'Attended'), (16, 2, '2026-05-28', 'Attended'),
(17, 2, '2026-05-28', 'Attended'), (18, 2, '2026-05-28', 'Attended'),
(19, 2, '2026-05-28', 'Attended'), (20, 2, '2026-05-28', 'Attended'),
(1, 2, '2026-05-29', 'Attended'), (2, 2, '2026-05-29', 'Attended'),
(3, 2, '2026-05-29', 'Attended'), (4, 2, '2026-05-29', 'Attended'),
(5, 2, '2026-05-29', 'Attended'), (6, 2, '2026-05-29', 'Attended');

INSERT INTO Bookings (member_id, class_id, booking_date, attendance_status) VALUES
(1, 3, '2026-05-30', 'Attended'),
(2, 3, '2026-05-30', 'Attended'),
(3, 4, '2026-05-30', 'Attended');