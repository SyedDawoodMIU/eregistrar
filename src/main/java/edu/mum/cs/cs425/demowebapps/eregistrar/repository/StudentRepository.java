

package edu.mum.cs.cs425.demowebapps.eregistrar.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import edu.mum.cs.cs425.demowebapps.eregistrar.model.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findByStudentNumberContainingOrFirstNameContainingOrLastNameContaining(String studentNumber, String firstName, String lastName);
}
