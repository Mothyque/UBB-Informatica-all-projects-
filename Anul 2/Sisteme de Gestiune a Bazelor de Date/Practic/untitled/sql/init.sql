PRAGMA foreign_keys = ON;

CREATE TABLE Instructors (
instructor_id INTEGER PRIMARY KEY AUTOINCREMENT ,
instructor_name TEXT NOT NULL,
specialization TEXT,
hourly_rate REAL
);

CREATE TABLE Members (
member_id INTEGER PRIMARY KEY AUTOINCREMENT,
name TEXT NOT NULL,
city TEXT,
join_date TEXT,
membership_type TEXT
);

CREATE TABLE Classes (
class_id INTEGER PRIMARY KEY AUTOINCREMENT,
instructor_id INTEGER,
class_name TEXT NOT NULL,
category TEXT,
price_per_session REAL,
FOREIGN KEY (instructor_id) REFERENCES Instructors(instructor_id) ON DELETE CASCADE
);

CREATE TABLE Bookings (
booking_id INTEGER PRIMARY KEY AUTOINCREMENT,
member_id INTEGER,
class_id INTEGER,
booking_date TEXT,
attendance_status TEXT,
FOREIGN KEY (member_id) REFERENCES Members(member_id) ON DELETE CASCADE,
FOREIGN KEY (class_id) REFERENCES Classes(class_id) ON DELETE CASCADE
);

CREATE TABLE Payments (
payment_id INTEGER PRIMARY KEY AUTOINCREMENT,
member_id INTEGER,
amount REAL,
payment_date TEXT,
payment_method TEXT,
FOREIGN KEY (member_id) REFERENCES Members(member_id) ON DELETE CASCADE
);