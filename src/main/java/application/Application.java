package application;

import application.entity.Table;
import application.utils.DTO.TableDTO;
import application.utils.converter.TableMapper;
import application.utils.enums.Color;
import application.utils.enums.Material;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
