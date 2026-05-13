CREATE INDEX idx_employees_email ON employee(email);
CREATE INDEX idx_employees_department_id ON employee(department_id);
CREATE INDEX idx_employees_salary ON employee(salary);
CREATE INDEX idx_employees_dept_salary ON employee(department_id, salary);

DROP INDEX idx_employees_email ON employee;
DROP INDEX idx_employees_department_id ON employee;
DROP INDEX idx_employees_salary ON employee;
DROP INDEX idx_employees_dept_salary ON employee;

SHOW INDEX FROM employee;

EXPLAIN ANALYZE SELECT * FROM employee WHERE email = 'employee9999@example.com';