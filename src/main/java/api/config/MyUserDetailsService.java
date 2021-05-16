package api.config;
import database.entities.AdminsEntity;
import database.jpaRepositories.JPAAdmins;
import database.repositories.AdminsRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {
    AdminsRepository adminsRepository;

    public MyUserDetailsService() {
        adminsRepository = new JPAAdmins();
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        AdminsEntity adminsEntity = adminsRepository.findByName(s);

        if(adminsEntity == null)
            throw new UsernameNotFoundException("Not found: " + s);

        MyUserDetails details = new MyUserDetails(adminsEntity.getUser());
        details.setPassword(adminsEntity.getPassword());

        return details;
    }
}
