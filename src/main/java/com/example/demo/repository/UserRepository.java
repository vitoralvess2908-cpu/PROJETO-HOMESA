//2#Responsavel por fazer a comunicação com o banco de dados
package com.example.demo.repository;

//Importa a classe que está ligada ao banco de dados (com uma entidade)
import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


//Ele somente herda da classe "JpaRepository" pois,essa classe já contém os métodos necessários (básicos)
//E se passa a tabela que está se referindo e o seu tipo da 'Primary Key'(PK)
public interface UserRepository extends JpaRepository<User,Long>  {
    Boolean existsByEmail(String email);


    Optional<User> findByEmail(String email);
}
