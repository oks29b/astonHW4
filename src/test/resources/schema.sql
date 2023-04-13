create table if not exists departments(
    id int not null auto_increment,
    name varchar (20),
    max_salary int,
    min_salary int,
    primary key (id)
);

create table if not exists  employees(
    id int not null auto_increment,
    name varchar (20),
    surname varchar(30),
    salary int,
    primary key (id)
);

create table if not exists  bankAccounts(
    id int not null auto_increment,
    name varchar (20),
    amount int,
    employee_id int,
    primary key (id),
    foreign key (employee_id) references employees(id)
);

create table if not exists department_employee(
    employee_id int not null,
    department_id int not null,
    primary key (employee_id, department_id),
    foreign key (department_id) references departments(id),
    foreign key (employee_id) references employees(id)
);
