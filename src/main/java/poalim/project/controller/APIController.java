package poalim.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.ControllerLinkBuilder;
import org.springframework.hateoas.server.mvc.ControllerLinkRelationProvider;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import poalim.project.model.*;
import poalim.project.repository.*;
import java.util.*;

@RestController
public class APIController {
    @Autowired
    EmployeeRepo employeeRepo;
    @Autowired
    GeneralDetailsRepo generalDetailsRepo;
    @Autowired
    AddressRepo addressRepo;
    @Autowired
    SpouseRepo spouseRepo;
    @Autowired
    ChildRepo childRepo;

    @GetMapping(path = "/employee/{id}")
    public Employee getEmployeeById(@PathVariable int id){
        if (!employeeRepo.findById(id).isPresent()){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "employee {"+id+"} does not exist");
        }
        Link selfLink = ControllerLinkBuilder.linkTo(Employee.class).slash("employee").slash(id).withSelfRel();
        Employee employee = employeeRepo.findById(id).get();
        employee.add(selfLink);
        return employee;
    }

    @GetMapping("/employees")
    public List<Employee> getAllEmployees(){

        List<Employee> list = new ArrayList();
        employeeRepo.findAll().forEach(list::add);
        for (Employee employee: list){
            Link selfLink = ControllerLinkBuilder.linkTo(Employee.class).slash("employee").slash(employee.getEmployeeId()).withSelfRel();
            employee.add(selfLink);
        }

        return list;
    }

    @PostMapping("/employee")
    public Employee addEmployee(@RequestBody Employee employee) {
        try{
            if (employee.getEmployeeId() != 0 && employeeRepo.existsById(employee.getEmployeeId())){
                throw new IllegalArgumentException("employee is already exist");
            }
            if(employee.getDetails() != null && generalDetailsRepo.existsById(employee.getDetails().getDetailsId())){
                throw new IllegalArgumentException("Unique details must be given in order to save a new employee");
            }

           return saveEmployeeData(employee);
        }
        catch (IllegalArgumentException e){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping(path = "/employee")
    public Employee saveOrUpdateEmployee(@RequestBody Employee employee){
        try{
            if (employee.getEmployeeId() == 0){
                throw new IllegalArgumentException("employee ID must be stated in JSON body");
            }
            return saveEmployeeData(employee);
        }
        catch (IllegalArgumentException e){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @DeleteMapping(path = "/employee/{id}")
    public String deleteEmployee(@PathVariable int id){
        if (!employeeRepo.findById(id).isPresent()){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "An employee with id: "+id+" was not found");
        }
        employeeRepo.deleteById(id);
        return "Employee: "+id+" was deleted successfully";
    }

    private Employee saveEmployeeData(Employee employee) throws IllegalArgumentException{
        if (employee.getDetails()!= null ){
            generalDetailsRepo.save(employee.getDetails());
        }
        else{
            throw new IllegalArgumentException("Details must be given in order to save an employee");
        }
        if (employee.getAddresses() != null){
            for (Address address: employee.getAddresses()){
                addressRepo.save(address);
            }
        }
        if (employee.getSpouse() != null){
            spouseRepo.save(employee.getSpouse());
        }
        if (employee.getChildren() != null){
            for (Child child: employee.getChildren()){
                childRepo.save(child);
            }
        }
        Link selfLink = ControllerLinkBuilder.linkTo(Employee.class).slash("employee").slash(employee.getEmployeeId()).withSelfRel();
        employee.add(selfLink);
        return employeeRepo.save(employee);
    }

    @PatchMapping(path = "/employee")
    public Employee saveOrPatchEmployee(@RequestBody Employee employee){
        //update employee only if its id is found in employee repository.
        if (employeeRepo.findById(employee.getEmployeeId()).isPresent()){
            Employee tempEmployee = employeeRepo.findById(employee.getEmployeeId()).get();
            if (employee.getDetails() != null) {
                GeneralDetails detailsToSave;
                //in case details exist, change only given parameters, otherwise create a new details objects.
                if (generalDetailsRepo.findById(employee.getDetails().getDetailsId()).isPresent()) {
                    GeneralDetails details = generalDetailsRepo.findById(employee.getDetails().getDetailsId()).get();
                    if (employee.getDetails().getName() != null) {
                        details.setName(employee.getDetails().getName());
                    }
                    if (employee.getDetails().getGender() != null) {
                        details.setGender(employee.getDetails().getGender());
                    }
                    if (employee.getDetails().getAge() != 0) {
                        details.setAge(employee.getDetails().getAge());
                    }
                    detailsToSave = details;
                }
                else {
                    detailsToSave =employee.getDetails();
                }
                generalDetailsRepo.save(detailsToSave);
                tempEmployee.setDetails(detailsToSave);
            }
            if (employee.getAddresses() != null ){
                for (Address address: employee.getAddresses()){
                    Set<Address> addresses = new HashSet<>();
                    Address addressToSave;
                    if (addressRepo.findById(address.getAddressId()).isPresent()){
                        Address tempAddress = addressRepo.findById(address.getAddressId()).get();
                        if (address.getCity() != null){
                            tempAddress.setCity(address.getCity());
                        }
                        if (address.getApartmentNumber() != 0){
                            tempAddress.setApartmentNumber(address.getApartmentNumber());
                        }
                        if (address.getStreet() != null){
                            tempAddress.setStreet(address.getStreet());
                        }
                        if (address.getEmployee() != null){
                            tempAddress.setEmployee(address.getEmployee());
                        }
                        addressToSave = tempAddress;
                    }
                    else {
                        addressToSave = address;
                    }

                    addressRepo.save(addressToSave);
                    addresses.add(addressToSave);
                    //update employee object
                    if (!addresses.isEmpty()){
                        tempEmployee.setAddresses(addresses);
                    }
                }
            }
            if (employee.getSpouse() != null){
                Spouse spouseToSave;
                if (spouseRepo.findById(employee.getSpouse().getSpouseId()).isPresent()){
                    Spouse spouse = spouseRepo.findById(employee.getSpouse().getSpouseId()).get();
                    if (employee.getSpouse().getDetails() != null){
                        spouse.setDetails(employee.getSpouse().getDetails());
                    }
                    spouseToSave = spouse;
                }
                else{
                    spouseToSave = employee.getSpouse();
                }
                spouseRepo.save(spouseToSave);
                tempEmployee.setSpouse(spouseToSave);
            }
            if (employee.getChildren() != null){
                Set<Child> children = new HashSet<>();
                Child childToSave;
                for (Child child: employee.getChildren()){
                    if (childRepo.findById(child.getChildId()).isPresent()){
                        Child tempChild = childRepo.findById(child.getChildId()).get();
                        if (child.getParent() != null){
                            tempChild.setParent(child.getParent());
                        }
                        if (child.getDetails() != null){
                            tempChild.setDetails(child.getDetails());
                        }
                        childToSave = tempChild;;
                    }
                    else{
                        childToSave = child;
                    }
                    childRepo.save(childToSave);
                    children.add(childToSave);
                }
                if (!children.isEmpty()){
                    tempEmployee.setChildren(children);
                }
            }
            return employeeRepo.save(tempEmployee);
        }
        else{
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Employee with id: "+employee.getEmployeeId()+ " does not exist");
        }
    }
}
