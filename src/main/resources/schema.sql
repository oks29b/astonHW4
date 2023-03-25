create table if not exists departments(
    id int not null auto_increment,
    name varchar (20),
    max_salary int,
    min_salary int,
    primary key (id)
);

create table if not exists employees(
    id int not null auto_increment,
    name varchar (20),
    surname varchar(30),
    salary int,
    department_id int,
    primary key (id),
    foreign key (department_id) references departments(id)
);
