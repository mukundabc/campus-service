package org.campus.web;

import org.campus.domain.Student;
import org.campus.repository.AttendanceRepository;
import org.campus.repository.StudentRepository;
import org.campus.web.helper.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.campus.web.helper.Preconditions.checkNotNull;

/**
 * Created by amey on 3/8/16.
 */
@RestController
@RequestMapping("/api/students")
public class StudentController {

    @Autowired private StudentRepository studentRepository;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public Student create(@RequestBody Student student) {
        checkNotNull(student.getName(), "Name");
        checkNotNull(student.getRoll(), "Roll");
        checkNotNull(student.getStandard(), "Standard");
        checkNotNull(student.getBranch(), "Branch");
        return studentRepository.save(student);
    }

    @RequestMapping(value = "{standard}/{branch}/{roll}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody Student find(@PathVariable("standard") String standard, @PathVariable("branch") String branch, @PathVariable("roll") Integer roll) {
        Student student = studentRepository.findByRollAndStandardAndBranch(roll, standard, branch);
        Preconditions.checkFound(student, "Student");
        return student;
    }

    @RequestMapping(value = "{roll}/attendance/{standard}/{branch}/{date}/{subject}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody List<String> findAttendanceFor(@PathVariable("roll") Integer roll,@PathVariable("standard") String standard, @PathVariable("branch") String branch, @PathVariable("date") String date, @PathVariable("subject") String subject) {
        Student student = studentRepository.findByRoll(roll);
        List<String> stringList=student.findAttendanceFor(subject,date);
        return stringList;
    }

}
