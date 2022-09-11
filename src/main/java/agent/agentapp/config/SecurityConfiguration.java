package agent.agentapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import agent.agentapp.security.TokenUtils;
import agent.agentapp.security.auth.RestAuthenticationEntryPoint;
import agent.agentapp.security.auth.TokenAuthenticationFilter;
import agent.agentapp.services.impl.CustomUserDetailsService;


@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private EncoderConfig encoderConfig;

    @Autowired
    private CustomUserDetailsService userService;

    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    // Registrujemo authentication manager koji ce da uradi autentifikaciju
    // korisnika za nas
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    // Definisemo uputstvo za authentication managera koji servis da koristi da
    // izvuce podatke o korisniku koji zeli da se autentifikuje,
    // kao i kroz koji enkoder da provuce lozinku koju je dobio od klijenta u
    // zahtevu da bi adekvatan hash koji dobije kao rezultat bcrypt algoritma
    // uporedio sa onim koji se nalazi u bazi (posto se u bazi ne cuva plain
    // lozinka)
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(encoderConfig.passwordEncoder());
    }

    // Injektujemo implementaciju iz TokenUtils klase kako bismo mogli da koristimo
    // njene metode za rad sa JWT u TokenAuthenticationFilteru
    @Autowired
    private TokenUtils tokenUtils;

    // Definisemo prava pristupa odredjenim URL-ovima
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // komunikacija izmedju klijenta i servera je stateless posto je u pitanju REST
                // aplikacija
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()

                // sve neautentifikovane zahteve obradi uniformno i posalji 401 gresku
                .exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint).and()

                // svim korisnicima dopusti da pristupe putanjama /auth/**, (/h2-console/** ako
                // se koristi H2 baza) i /api/foo
                .authorizeRequests().antMatchers("/authentication/**").permitAll()
                .antMatchers("/company/**").permitAll()
                .antMatchers("/job-position/**").permitAll()

                // za svaki drugi zahtev korisnik mora biti autentifikovan
                .anyRequest().authenticated().and()
                // custom cors
//				.cors().configurationSource(request -> config).and()

                // umetni custom filter TokenAuthenticationFilter kako bi se vrsila provera JWT
                // tokena umesto cistih korisnickog imena i lozinke (koje radi
                // BasicAuthenticationFilter)
                .addFilterBefore(new TokenAuthenticationFilter(tokenUtils, userService),
                        BasicAuthenticationFilter.class);
        // zbog jednostavnosti primera
        http.csrf().disable();
    }

    // Generalna bezbednost aplikacije
    @Override
    public void configure(WebSecurity web) throws Exception {
        // TokenAuthenticationFilter ce ignorisati sve ispod navedene putanje
        web.ignoring().antMatchers(HttpMethod.POST, "/authentication/login", "/authentication/register");
        web.ignoring().antMatchers(HttpMethod.GET, "/", "/webjars/**", "/*.html", "/favicon.ico", "/**/*.html",
                "/**/*.css", "/**/*.js");
    }
}
