package universitytest.feature.student;

import com.ck.dev.karateexample.KarateExampleApplication;
import com.intuit.karate.junit5.Karate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest(classes = {KarateExampleApplication.class})
public class StudentTest {

    @Karate.Test
    Karate testEmail() {
        return Karate.run("classpath:universitytest/feature/university/").relativeTo(getClass());
    }
}
