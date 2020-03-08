
--add details
insert into general_details (id,name,age,gender) values (1,'Booba_child',5,'FEMALE');
insert into general_details (id,name,age,gender) values (2,'Bob_child',10,'MALE');
insert into general_details (id,name,age,gender) values (3,'Bob_employee',30,'MALE');
insert into general_details (id,name,age,gender) values (4,'Baba_spouse',25,'FEMALE');

--add a spouse
insert into spouses (id,details_id) values (1,4);

--add an employee
insert into employees (id,details_id,spouse_id) values (1,3,1);

--add children
insert into children (id,details_id,parent_id) values (1,1,1);
insert into children (id,details_id,parent_id) values (2,2,1);

--add addresses
insert into addresses(id,city,apartment_number,street,employee_id) values(1,'Oranit',84,'Hazait',1);
