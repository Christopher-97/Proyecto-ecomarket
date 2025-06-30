package com.example.Cliente;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@SpringBootApplication
@Service
@Transactional
public class ClienteApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClienteApplication.class, args);
	}

}

public class ClienteApplication {

    @Autowired
    private ClienteApplication ClienteApplication;

    public List<User> findAll(){
        return ClienteApplication.findAll();
    }

    public User saveUser(User usr){
        return ClienteApplication.save(usr);
    }

    public User findById(Integer id){
        return ClienteApplication.findById(id).orElse(null);
    }

    public User update(User usr, Integer id){
        User aux = findById(id);
        if (aux != null)  {
            aux.setNombre(usr.getNombre());
            aux.setApPaterno(usr.getApPaterno());
            aux.setApMaterno(usr.getApMaterno());
            aux.setMail(usr.getMail());
            aux.setRun(usr.getRun());
            aux.setRol(usr.getRol());
            userRepository.save(aux);
        }
        return aux;
    }

    public User findByMail(String email){
        return ClienteApplication.findByMail(email).orElse(null);
    }

    public void deleteUsr(Integer id){
        User aux = findById(id);
        if(aux != null){
            ClienteApplication.delete(aux);
        }
        return;
    }



}