package ee.drivingschool.config;

import ee.drivingschool.model.User;
import ee.drivingschool.repository.UserRepository;
import ee.drivingschool.service.StudentService;
import ee.drivingschool.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
//@EnableMethodSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/admin/students/**").hasAnyAuthority("TEACHER", "ADMIN")
                        .requestMatchers("/admin/teachers/**").hasAnyAuthority("TEACHER", "ADMIN")
                        .requestMatchers("/driving_card/**").hasAnyAuthority("TEACHER", "ADMIN")
                        .requestMatchers("/admin/**").hasAnyAuthority("ADMIN")
                        .anyRequest().authenticated()
                )
                .httpBasic(withDefaults())
                .formLogin(withDefaults())
                .logout(withDefaults());
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Autowired
            private UserRepository userRepository;

            @Autowired
            private PasswordEncoder passwordEncoder;

            @Autowired
            private TeacherService teacherService;

            @Autowired
            private StudentService studentService;

            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                User user = userRepository.findByEmail(username);

                if (user == null) {
                    throw new UsernameNotFoundException("No user found with this email: " + username);
                }
                boolean enabled = true;
                boolean accountNonExpired = true;
                boolean credentialsNonExpired = true;
                boolean accountNonLocked = true;
                String password = passwordEncoder.encode(user.getPassword());

                System.out.println("here is the user: " + user);

                return new org.springframework.security.core.userdetails.User(
                        user.getEmail(),
                        user.getPassword(),
                        enabled,
                        accountNonExpired,
                        credentialsNonExpired,
                        accountNonLocked,
                        getAuthorities(user));
            }

            private List<GrantedAuthority> getAuthorities(User user) {
                List<GrantedAuthority> authorities = new ArrayList<>();
                if (user.getIsAdmin()) {
                    authorities.add(new SimpleGrantedAuthority("ADMIN"));
                }
                if (teacherService.isTeacherWithEmailExists(user.getEmail())) {
                    authorities.add(new SimpleGrantedAuthority("TEACHER"));
                }
                if (studentService.isStudentWithEmailExists(user.getEmail())) {
                    authorities.add(new SimpleGrantedAuthority("STUDENT"));
                }
                return authorities;
            }
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}